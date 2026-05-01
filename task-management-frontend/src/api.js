const BASE_URL = "http://localhost:8080/api";

export async function registerUser(data) {
    const res = await fetch(`${BASE_URL}/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });
    return res.json();
}

export async function loginUser(data) {
    const res = await fetch(`${BASE_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });
    return res.json();
}

export async function createTask(data, token) {
    const res = await fetch(`${BASE_URL}/tasks`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(data)
    });
    return res.json();
}

export async function getMyTasks(token) {
    const res = await fetch(`${BASE_URL}/tasks/my`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    return res.json();
}