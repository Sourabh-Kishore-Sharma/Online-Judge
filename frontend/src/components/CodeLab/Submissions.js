import { useEffect, useState } from "react";
import "./Submissions.css";

const Submissions = ({ problemId, setCode }) => {
  const [submissions, setSubmissions] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const API_URL = process.env.REACT_APP_API_URL || "";

  useEffect(() => {
    const fetchSubmissions = async () => {
      const endpoint = `${API_URL}/api/submissions?problemId=${problemId}`;
      const res = await fetch(endpoint, {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      });

      if (!res.ok) return;

      const data = await res.json();
      setSubmissions(data);
    };

    fetchSubmissions();
  }, [problemId, API_URL]);

  return (
    <div className="submissions-parent">
      <table className="submissions-table">
        <thead>
          <tr className="submission-header">
            <th>Submitted On</th>
            <th>Verdict</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {submissions.map((sub) => (
            <tr key={sub.id}>
              <td>{new Date(sub.createdOn).toLocaleString()}</td>
              <td>{sub.status}</td>
              <td>
                <button
                  onClick={() => {
                    setCode(sub.code);
                  }}
                >
                  Copy to Editor
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Submissions;
