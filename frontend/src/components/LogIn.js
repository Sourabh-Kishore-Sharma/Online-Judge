import { useState } from "react";
import "./LogIn.css";
import { data, useNavigate } from "react-router-dom";
import LoadingOverlay from "./Overlay/LoadingOverlay";

const LogIn = () => {
  const [mail, setMail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const API_URL = process.env.REACT_APP_API_URL || "";

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

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
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-parent">
      <LoadingOverlay show={loading} />
      <form className="login-form" onSubmit={handleSubmit}>
        <div className="logo-parent">
          <img
            src="favicon/logo.png"
            width={"80px"}
            height={"60px"}
            alt="logo"
          />
        </div>
        <input
          type="email"
          placeholder="Email Address*"
          value={mail}
          onChange={(e) => setMail(e.target.value)}
          required
        ></input>
        <input
          type="password"
          placeholder="Password*"
          value={password}
          required
          onChange={(e) => setPassword(e.target.value)}
        ></input>
        <div className="form-actions">
          <button className="login-button" type="submit">
            LogIn
          </button>
          <div>
            New User ?{" "}
            <button
              type="button"
              className="register-button"
              onClick={() => navigate("/")}
            >
              Register
            </button>
          </div>
        </div>
        {error && <div className="error">{error}</div>}
      </form>
    </div>
  );
};

export default LogIn;
