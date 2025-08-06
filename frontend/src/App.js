import "./App.css";
import LogIn from "./components/LogIn";
import Register from "./components/Register";
import ProtectedRoute from "./components/ProtectedRoute";

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import CodeLab from "./components/CodeLab/CodeLab";

function App() {
  return (
    <div className="App">
      <div className="parent-container">
        <Router>
          <Routes>
            <Route path="/" element={<Register />}></Route>
            <Route path="/login" element={<LogIn />}></Route>
            <Route
              path="/codelab"
              element={
                <ProtectedRoute>
                  <CodeLab />
                </ProtectedRoute>
              }
            />
          </Routes>
        </Router>
      </div>
    </div>
  );
}

export default App;
