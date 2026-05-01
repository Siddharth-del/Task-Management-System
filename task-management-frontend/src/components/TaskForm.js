import React, { useState } from "react";
import { createTask } from "../api";

function TaskForm({ token, refreshTasks }) {
    const [form, setForm] = useState({
        title: "",
        description: "",
        status: "PENDING"
    });

    const handleSubmit = async (e) => {
        e.preventDefault();

        const res = await createTask(form, token);

        if (res.success) {
            refreshTasks();
            setForm({
                title: "",
                description: "",
                status: "PENDING"
            });
        } else {
            alert(res.message);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Create Task</h2>

            <input
                placeholder="Title"
                value={form.title}
                onChange={(e) => setForm({...form, title: e.target.value})}
            />

            <textarea
                placeholder="Description"
                value={form.description}
                onChange={(e) => setForm({...form, description: e.target.value})}
            />

            <select
                value={form.status}
                onChange={(e) => setForm({...form, status: e.target.value})}
            >
                <option value="PENDING">PENDING</option>
                <option value="IN_PROGRESS">IN_PROGRESS</option>
                <option value="COMPLETED">COMPLETED</option>
            </select>

            <button type="submit">Add Task</button>
        </form>
    );
}

export default TaskForm;