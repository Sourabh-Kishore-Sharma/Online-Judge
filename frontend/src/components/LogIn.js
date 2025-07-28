import "./LogIn.css";

const LogIn = () => {
  const handleSubmit = (e) => {
    e.preventDefault();
  };

  return (
    <div className="login-parent">
      <form className="login-form" onSubmit={handleSubmit}>
        <input type="password"></input>
        <input type="mail"></input>
        <button type="submit">LogIn</button>
      </form>
    </div>
  );
};

export default LogIn;
