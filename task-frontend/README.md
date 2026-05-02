# Task Management Frontend

React frontend for the Task Management System Spring Boot API.

## Setup

```bash
npm install
npm start
```

Runs on http://localhost:3000. Backend must be running on http://localhost:8080.

## Features

- Register / Login with JWT auth
- Create, edit, delete your own tasks
- Paginated task list
- Admin view to see and manage all tasks (ADMIN role required)

## Pages

- `/login` - Sign in
- `/register` - Create account
- `/tasks` - Your tasks (all authenticated users)
- `/admin/tasks` - All tasks (ADMIN only)

## Notes

- Backend URL is set in `src/services/api.js` as `http://localhost:8080/api`
- Change it there if your backend runs on a different port
