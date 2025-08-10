import "./LoadingOverlay.css";

const LoadingOverlay = ({ show }) => {
  if (!show) return null;

  return (
    <div className="overlay">
      <div className="spinner"></div>
    </div>
  );
};

export default LoadingOverlay;
