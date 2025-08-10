import "./Register.css";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import LoadingOverlay from "./Overlay/LoadingOverlay";

const Register = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [middleName, setMiddleName] = useState("");
  const [lastName, setLastName] = useState("");
  const [loading, setLoading] = useState(false);

  const [error, setError] = useState("");
  const API_URL = process.env.REACT_APP_API_URL || "";

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      const response = await fetch(`${API_URL}/api/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          email,
          password,
          firstName,
          middleName,
          lastName,
        }),
      });
      const data = response.json();
      if (response.ok) {
        setLoading(false);
        navigate("/login");
      } else setError(data.message || "Registeration Failed !!");
    } catch {
      setError("Opps, something went wrong.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="register-parent">
      <LoadingOverlay show={loading} />
      <form className="register-form" onSubmit={handleSubmit}>
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
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        ></input>
        <input
          type="password"
          placeholder="Password*"
          value={password}
          onChange={(e) => {
            setPassword(e.target.value);
          }}
          required
        ></input>
        <div className="name-parent">
          <input
            type="text"
            placeholder="First Name*"
            value={firstName}
            required
            onChange={(e) => {
              setFirstName(e.target.value);
            }}
          ></input>
          <input
            type="text"
            placeholder="Middle Name"
            value={middleName}
            onChange={(e) => {
              setMiddleName(e.target.value);
            }}
          ></input>
          <input
            type="text"
            placeholder="Last Name*"
            required
            value={lastName}
            onChange={(e) => {
              setLastName(e.target.value);
            }}
          ></input>
        </div>
        <div className="form-actions">
          <button type="submit" className="register-button">
            Register
          </button>
          <div>
            Already Registered ?{" "}
            <button
              type="button"
              className="login-button"
              onClick={() => navigate("/login")}
            >
              LogIn
            </button>
          </div>
        </div>
        {error && <div className="error">{error}</div>}
      </form>
    </div>
  );
};

export default Register;
