const BASE_URL = 'http://localhost:8080/api';

function getToken() {
  return localStorage.getItem('token');
}

function authHeaders() {
  return {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${getToken()}`,
  };
}

async function handleResponse(res) {
  const data = await res.json();
  if (!res.ok) {
    throw new Error(data.message || 'Something went wrong');
  }
  return data;
}

// Auth
export async function registerUser(payload) {
  const res = await fetch(`${BASE_URL}/auth/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  });
  return handleResponse(res);
}

export async function loginUser(payload) {
  const res = await fetch(`${BASE_URL}/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  });
  return handleResponse(res);
}

// Tasks
export async function createTask(payload) {
  const res = await fetch(`${BASE_URL}/tasks`, {
    method: 'POST',
    headers: authHeaders(),
    body: JSON.stringify(payload),
  });
  return handleResponse(res);
}

export async function getMyTasks(page = 0, size = 10) {
  const res = await fetch(`${BASE_URL}/tasks/my?page=${page}&size=${size}`, {
    headers: authHeaders(),
  });
  return handleResponse(res);
}

export async function getAllTasks(page = 0, size = 10) {
  const res = await fetch(`${BASE_URL}/tasks/all?page=${page}&size=${size}`, {
    headers: authHeaders(),
  });
  return handleResponse(res);
}

export async function getTask(id) {
  const res = await fetch(`${BASE_URL}/tasks/${id}`, {
    headers: authHeaders(),
  });
  return handleResponse(res);
}

export async function updateTask(id, payload, isAdmin = false) {
  const res = await fetch(`${BASE_URL}/tasks/${id}?isAdmin=${isAdmin}`, {
    method: 'PUT',
    headers: authHeaders(),
    body: JSON.stringify(payload),
  });
  return handleResponse(res);
}

export async function deleteTask(id, isAdmin = false) {
  const res = await fetch(`${BASE_URL}/tasks/${id}?isAdmin=${isAdmin}`, {
    method: 'DELETE',
    headers: authHeaders(),
  });
  return handleResponse(res);
}
