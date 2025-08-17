import { useState } from "react";
import "./Problem.css";
import Submissions from "./CodeLab/Submissions";

const Problem = ({ problem, setCode }) => {
  const [activeTab, setActiveTab] = useState("problem");

  return (
    <div className="problem">
      <div className="problem-header">
        <button
          className={activeTab === "problem" ? "active" : ""}
          onClick={() => {
            setActiveTab("problem");
          }}
        >
          Problem
        </button>
        <button
          className={activeTab === "problem" ? "active" : ""}
          onClick={() => {
            setActiveTab("submissions");
          }}
        >
          Submissions
        </button>
      </div>

      {activeTab === "problem" && (
        <div className="problem-info">
          <div className="problem-title">
            {problem.id}. {problem.title}
          </div>
          <div
            className={`problem-difficulty ${problem.difficultyLevel.toLowerCase()}`}
          >
            {problem.difficultyLevel}
          </div>
          <div className="problem-description">{problem.description}</div>
          <div className="problem-inputFormat">{problem.inputFormat}</div>
          <div className="problem-outputFormat">{problem.outputFormat}</div>
          <div className="problem-inputexp">{problem.inputexp}</div>
          <div className="problem-outputexp">{problem.outputexp}</div>
          <div className="problem-constraints">{problem.constraints}</div>
        </div>
      )}

      {activeTab === "submissions" && (
        <div className="submissions">
          {" "}
          <Submissions problemId={problem.id} setCode={setCode} />{" "}
        </div>
      )}
    </div>
  );
};

export default Problem;
