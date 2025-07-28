import "./Register.css";
import { useNavigate } from "react-router-dom";

const Register = () => {
  const handleSubmit = (e) => {
    e.preventDefault();
  };

  const navigate = useNavigate();

  return (
    <div className="register-parent">
      <form className="register-form" onSubmit={handleSubmit}>
        <input type="mail" placeholder="Email Address"></input>
        <input type="password" placeholder="Password"></input>
        <button type="submit">Register</button>
        <button type="button" onClick={() => navigate("/login")}>
          LogIn
        </button>
      </form>
    </div>
  );
};

export default Register;
