import { useEffect, useState } from "react";
import "./Submissions.css";
import LoadingOverlay from "../Overlay/LoadingOverlay";
import { FaCopy } from "react-icons/fa6";
import { BiSolidFileImport } from "react-icons/bi";

const Submissions = ({ problemId, setCode }) => {
  const [submissions, setSubmissions] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const API_URL = process.env.REACT_APP_API_URL || "";

  const copyToClipboard = ({ text }) => {
    navigator.clipboard
      .writeText(text)
      .then(() => {
        alert("Copied to Clipboard !! ");
      })
      .catch((err) => {
        alert("Falied to Copy !!");
      });
  };

  useEffect(() => {
    const fetchSubmissions = async () => {
      const endpoint = `${API_URL}/api/submissions?problemId=${problemId}`;
      setLoading(true);
      const res = await fetch(endpoint, {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      });

      if (!res.ok) return;

      const data = await res.json();
      setSubmissions(data);
      setLoading(false);
    };

    fetchSubmissions();
  }, [problemId, API_URL]);

  return (
    <div className="submissions-parent">
      <LoadingOverlay show={loading} />
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
              <td className="actions">
                <FaCopy
                  title="Copy"
                  onClick={() => {
                    copyToClipboard({ text: sub.code });
                  }}
                  size={"16px"}
                />
                <BiSolidFileImport
                  title="Copy to Editor"
                  onClick={() => {
                    setCode(sub.code);
                  }}
                  size={"16px"}
                />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Submissions;
