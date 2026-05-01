import React, { useState } from "react";
import { registerUser } from "../api";

function Register() {
    const [form, setForm] = useState({
        name: "",
        email: "",
        password: ""
    });

    const handleSubmit = async (e) => {
        e.preventDefault();
        const res = await registerUser(form);

        if (res.success) {
            alert("Registration successful");
        } else {
            alert(res.message);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Register</h2>

            <input
                placeholder="Name"
                onChange={(e) => setForm({...form, name: e.target.value})}
            />

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

            <button type="submit">Register</button>
        </form>
    );
}

export default Register;