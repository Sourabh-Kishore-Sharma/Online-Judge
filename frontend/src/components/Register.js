const Register = () => {
  const handleSubmit = (e) => {
    e.preventDefault();
  };

  return (
    <div className="register-parent">
      <form className="register-form" onSubmit={handleSubmit}>
        <input type="mail" placeholder="Email Address"></input>
        <input type="password" placeholder="Password"></input>
        <button type="submit">Register</button>
      </form>
    </div>
  );
};

export default Register;
