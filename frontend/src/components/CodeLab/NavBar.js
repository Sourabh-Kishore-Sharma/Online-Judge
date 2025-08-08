import { useEffect, useState } from "react";
import "./NavBar.css";

const NavBar = ({ onSelectProblem }) => {
  const [problems, setProblems] = useState([]);
  const [error, setError] = useState("");
  const API_URL = process.env.REACT_APP_API_URL;

  useEffect(() => {
    const fetchProblems = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await fetch(`${API_URL}/api/problems`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        const data = await response.json();
        if (response.ok) setProblems(data);
      } catch {
        setError("Opps, something went wrong.");
      }
    };
    fetchProblems();
  }, []);

  return (
    <div className="navbar-container">
      <div className="navbar-parent">
        <div className="problems-list">
          {problems.map((problem) => {
            return (
              <div
                className="problems-item"
                key={problem.id}
                onClick={() => onSelectProblem(problem)}
              >
                {problem.id + ". " + problem.title}
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
};

export default NavBar;
