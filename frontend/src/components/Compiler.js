import { useState } from "react";
import "./Compiler.css";

const Compiler = () => {
  const [file, setFile] = useState(null);
  const [language, setLanguage] = useState("");
  const [input, setInput] = useState("");
  const [output, setOutput] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState("");

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
    setOutput("");
    setError("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!file) {
      setError("Please select a file.");
      return;
    }
    setLoading(true);
    setError("");
    setOutput("");

    try {
      const token = localStorage.getItem("token");
      const formData = new FormData();
      formData.append("file", file);
      formData.append("language", language);
      formData.append("input", input);

      const response = await fetch(
        "http://localhost:8080/api/compiler/submit",
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${token}`,
          },
          body: formData,
        }
      );
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
        <label>
          Source File:
          <input type="file" onChange={handleFileChange} required />
        </label>
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
