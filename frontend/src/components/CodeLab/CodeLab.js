import { useEffect, useState } from "react";
import "./CodeLab.css";
import Problem from "../Problem";
import NavBar from "./NavBar";
import Editor from "./Editor";

const boilerplates = {
  cpp: `#include <bits/stdc++.h>
using namespace std;

int main() {
  // Your code here
  return 0;
}`,
  java: `public class Main {
  public static void main(String[] args) {
      // Your code here
  }
}`,
  python: `def main():
  # Your code here
  pass

if __name__ == "__main__":
  main()`,
};

const CodeLab = () => {
  const [selectedProblem, setSelectedProblem] = useState(null);
  const [language, setLanguage] = useState("java");
  const [code, setCode] = useState(boilerplates["java"]);
  const [output, setOutput] = useState("");
  const [action, setAction] = useState("");
  const [input, setInput] = useState("");
  const [selectedView, setSelectedView] = useState("input");

  const API_URL = process.env.REACT_APP_API_URL || "";

  const extMap = {
    cpp: "cpp",
    java: "java",
    python: "py",
  };

  useEffect(() => {
    setCode(boilerplates[language]);
  }, [language]);

  const handleAction = async (type, e) => {
    e.preventDefault();
    setOutput("");
    setAction(type);

    let endpoint = "";
    if (type === "run") {
      endpoint = `${API_URL}/api/compiler/submit`;
    } else {
      endpoint = `${API_URL}/api/compiler/validate`;
    }
    setSelectedView("output");

    try {
      const token = localStorage.getItem("token");
      const formData = new FormData();

      const ext = extMap[language];
      const codeBlob = new Blob([code], { type: "text/plain" });
      formData.append("file", codeBlob, `Main.${ext}`);

      formData.append("language", language);
      formData.append("input", input);
      formData.append("problemId", selectedProblem.id);

      const response = await fetch(endpoint, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${token}`,
        },
        body: formData,
      });
      const data = await response.json();
      if (response.ok) {
        setOutput(data.verdict || "No output received.");
      } else {
        setOutput(data.message || "Compilation Failed.");
      }
    } catch {
      setOutput("Network Error.");
    }
  };

  return (
    <div className="lab-container">
      <div className="lab-header">
        <button
          className="all-problems-nav-button"
          onClick={() => setSelectedProblem(null)}
        >
          All Problems
        </button>
        <div className="action-buttons">
          <button
            onClick={(e) => handleAction("run", e)}
            disabled={!selectedProblem || !code}
          >
            Run
          </button>
          <button
            onClick={(e) => handleAction("submit", e)}
            disabled={!selectedProblem || !code}
          >
            Submit
          </button>
        </div>
        <select
          className="language-select"
          value={language}
          onChange={(e) => setLanguage(e.target.value)}
          required
        >
          <option value="cpp">C++</option>
          <option value="python">Python 3</option>
          <option value="java">Java 8</option>
        </select>
      </div>
      <div className="navbar-overlay">
        {!selectedProblem && <NavBar onSelectProblem={setSelectedProblem} />}
      </div>
      <div className="lab-parent">
        <div className="lab-item problem-pane">
          {selectedProblem && (
            <div>
              <Problem problem={selectedProblem} />
            </div>
          )}
        </div>
        <div className="lab-item workspace-pane">
          <Editor language={language} code={code} setCode={setCode} />
          <div className="io-toggle-buttons">
            <button
              className={selectedView === "input" ? "active" : ""}
              onClick={() => setSelectedView("input")}
            >
              Input
            </button>
            <button
              className={selectedView === "output" ? "active" : ""}
              onClick={() => setSelectedView("output")}
            >
              Output
            </button>
          </div>
          {selectedView === "input" && (
            <div className="input-window">
              <textarea
                placeholder="Enter custom input here..."
                className="input-textarea"
                rows={6}
                value={input}
                onChange={(e) => setInput(e.target.value)}
              />
            </div>
          )}

          {selectedView === "output" && (
            <div className="output-window">
              {output || "Program output will appear here"}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default CodeLab;
