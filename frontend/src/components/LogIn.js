import { useState } from "react";
import "./LogIn.css";
import { data, useNavigate } from "react-router-dom";

const LogIn = () => {
  const [mail, setMail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const API_URL = process.env.API_URL;

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const response = await fetch(`${API_URL}/api/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email: mail, password }),
      });
      const data = await response.json();
      if (response.ok) {
        localStorage.setItem("token", data.token);
        navigate("/codelab");
      }
    } catch {
      setError(data.message || "Opps, something went wrong.");
    }
  };

  return (
    <div className="login-parent">
      <form className="login-form" onSubmit={handleSubmit}>
        <input
          type="mail"
          placeholder="Email Address"
          value={mail}
          onChange={(e) => setMail(e.target.value)}
        ></input>
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        ></input>
        <button type="submit">LogIn</button>
        {error && <div className="error">{error}</div>}
      </form>
    </div>
  );
};

export default LogIn;
