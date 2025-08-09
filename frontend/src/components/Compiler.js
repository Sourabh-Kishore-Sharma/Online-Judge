import { useState } from "react";
import MonacoEditor from "@monaco-editor/react";
import "./Compiler.css";

const Compiler = () => {
  const [mode, setMode] = useState("file");
  const [file, setFile] = useState(null);
  const [language, setLanguage] = useState("java");
  const [input, setInput] = useState("");
  const [output, setOutput] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState("");
  const [code, setCode] = useState("");

  const API_URL = process.env.REACT_APP_API_URL || "";

  const extMap = {
    cpp: "cpp",
    java: "java",
    python: "py",
  };

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
    setOutput("");
    setError("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if ((mode === "file" && !file) || !language) {
      setError("Please select a file and select a language.");
      return;
    }

    if ((mode === "editor" && !code.trim()) || !language) {
      setError("Please write some code and select a language.");
      return;
    }

    setLoading(true);
    setError("");
    setOutput("");

    try {
      const token = localStorage.getItem("token");
      const formData = new FormData();

      if (mode === "file") {
        formData.append("file", file);
      } else {
        const ext = extMap[language];
        const codeBlob = new Blob([code], { type: "text/plain" });
        formData.append("file", codeBlob, `Main.${ext}`);
      }

      formData.append("language", language);
      formData.append("input", input);
      formData.append("problemId", 2);

      const response = await fetch(`${API_URL}/api/compiler/validate`, {
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
        setError(data.message || "Compilation Failed.");
      }
    } catch {
      setError("Network Error.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="compiler-parent">
      <form className="compiler-form" onSubmit={handleSubmit}>
        <label>
          Mode:
          <input
            type="radio"
            value="file"
            checked={mode === "file"}
            onChange={() => setMode("file")}
          />
          Upload File
          <input
            type="radio"
            value="editor"
            checked={mode === "editor"}
            onChange={() => setMode("editor")}
          />
          Editor
        </label>
        <label>
          Language:
          <select
            value={language}
            onChange={(e) => setLanguage(e.target.value)}
            required
          >
            <option value="">Select Language</option>
            <option value="cpp">C++</option>
            <option value="python">Python</option>
            <option value="java">Java</option>
          </select>
        </label>
        <label>
          Input (stdin):
          <textarea
            value={input}
            onChange={(e) => setInput(e.target.value)}
            placeholder="Enter input for your program"
          />
        </label>
        {mode === "file" ? (
          <label>
            Source File:
            <input type="file" onChange={handleFileChange} required />
          </label>
        ) : (
          <label>
            Code Editor:
            <MonacoEditor
              height="600px"
              width="1000px"
              language={language}
              value={code}
              onChange={(value) => setCode(value || "")}
              options={{
                theme: "vs-dark",
                fontSize: 16,
                minimap: { enabled: false },
              }}
            />
          </label>
        )}

        <button type="submit" disabled={loading}>
          {loading ? "Compiling..." : "Submit"}
        </button>
      </form>
      {output && (
        <div className="output">
          <h3>Output:</h3>
          <pre>{output}</pre>
        </div>
      )}
      {error && <div className="error">{error}</div>}
    </div>
  );
};

export default Compiler;
