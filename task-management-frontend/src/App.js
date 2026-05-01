import React, { useState } from "react";
import Login from "./components/Login";
import Register from "./components/Register";
import Dashboard from "./components/Dashboard";
import "./styles.css";

function App() {
    const [token, setToken] = useState(localStorage.getItem("token"));
    const [showRegister, setShowRegister] = useState(false);

    const handleLogin = (jwt) => {
        localStorage.setItem("token", jwt);
        setToken(jwt);
    };

    const logout = () => {
        localStorage.removeItem("token");
        setToken(null);
    };

    if (token) {
        return <Dashboard token={token} logout={logout} />;
    }

    return (
        <div className="container">
            <h1>Task Management System</h1>
            {showRegister ? (
                <>
                    <Register />
                    <button onClick={() => setShowRegister(false)}>Go to Login</button>
                </>
            ) : (
                <>
                    <Login onLogin={handleLogin} />
                    <button onClick={() => setShowRegister(true)}>Go to Register</button>
                </>
            )}
        </div>
    );
}

export default App;