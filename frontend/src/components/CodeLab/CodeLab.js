import { useState } from "react";
import "./CodeLab.css";
import Problem from "../Problem";
import NavBar from "./NavBar";
import Editor from "./Editor";

const CodeLab = () => {
  const [selectedProblem, setSelectedProblem] = useState(null);
  const [language, setLanguage] = useState("java");

  return (
    <div className="lab-container">
      <div className="navbar-overlay">
        {!selectedProblem && <NavBar onSelectProblem={setSelectedProblem} />}
      </div>
      <div className="lab-parent">
        <div className="lab-item problem-pane">
          {selectedProblem && (
            <div>
              <button onClick={() => setSelectedProblem(null)}>
                All Problems
              </button>
              <Problem problem={selectedProblem} />
            </div>
          )}
        </div>
        <div className="lab-item workspace-pane">
          <Editor language={language} />
        </div>
      </div>
    </div>
  );
};

export default CodeLab;
