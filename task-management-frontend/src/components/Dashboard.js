import React, { useEffect, useState } from "react";
import { getMyTasks } from "../api";
import TaskForm from "./TaskForm";

function Dashboard({ token, logout }) {
    const [tasks, setTasks] = useState([]);

    const loadTasks = async () => {
        const res = await getMyTasks(token);
        if (res.success) {
            setTasks(res.data.content);
        }
    };

    useEffect(() => {
        loadTasks();
    }, []);

    return (
        <div className="container">
            <h2>My Tasks</h2>

            <button onClick={logout}>Logout</button>

            <TaskForm token={token} refreshTasks={loadTasks} />

            <div>
                {tasks.map(task => (
                    <div key={task.id} className="task-card">
                        <h3>{task.title}</h3>
                        <p>{task.description}</p>
                        <p>Status: {task.status}</p>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Dashboard;