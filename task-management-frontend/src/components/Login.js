import React, { useState } from "react";
import { loginUser } from "../api";

function Login({ onLogin }) {
    const [form, setForm] = useState({
        email: "",
        password: ""
    });

    const handleSubmit = async (e) => {
        e.preventDefault();
        const res = await loginUser(form);
        if (res.success) {
            onLogin(res.data.token);
        } else {
            alert(res.message);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Login</h2>

            <input
                type="email"
                placeholder="Email"
                onChange={(e) => setForm({...form, email: e.target.value})}
            />

            <input
                type="password"
                placeholder="Password"
                onChange={(e) => setForm({...form, password: e.target.value})}
            />

            <button type="submit">Login</button>
        </form>
    );
}

export default Login;