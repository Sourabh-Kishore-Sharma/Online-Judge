import "./Problem.css";

const Problem = ({ problem }) => {
  return (
    <div className="problem">
      <div className="problem-title">
        {problem.id}. {problem.title}
      </div>
      <div className="problem-difficultLevel">{problem.difficultyLevel}</div>
      <div className="problem-description">
        <div>Description</div>
        {problem.description}
      </div>
      <div className="problem-inputFormat">{problem.inputFormat}</div>
      <div className="problem-outputFormat">{problem.outputFormat}</div>
      <div className="problem-inputexp">{problem.inputexp}</div>
      <div className="problem-outputexp">{problem.outputexp}</div>
      <div className="problem-constraints">{problem.constraints}</div>
    </div>
  );
};

export default Problem;
