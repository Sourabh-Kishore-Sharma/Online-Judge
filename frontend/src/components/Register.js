import "./Register.css";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

const Register = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const response = await fetch("http://localhost:8080/api/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });
      const data = response.json();
      if (response.ok) {
        navigate("/login");
      } else setError(data.message || "Registeration Failed !!");
    } catch {
      setError("Opps, something went wrong.");
    }
  };

  return (
    <div className="register-parent">
      <form className="register-form" onSubmit={handleSubmit}>
        <input
          type="mail"
          placeholder="Email Address"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        ></input>
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => {
            setPassword(e.target.value);
          }}
        ></input>
        <button type="submit">Register</button>
        <button type="button" onClick={() => navigate("/login")}>
          LogIn
        </button>
        {error && <div className="error">{error}</div>}
      </form>
    </div>
  );
};

export default Register;
