import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
  const { user, logout, getRole } = useAuth();
  const navigate = useNavigate();
  const role = getRole();

  function handleLogout() {
    logout();
    navigate('/login');
  }

  return (
    <nav className="navbar">
      <Link to="/tasks" className="brand">Task Manager</Link>

      {user && (
        <>
          <div className="nav-links">
            <Link to="/tasks">My Tasks</Link>
            {role === 'ADMIN' && <Link to="/admin/tasks">All Tasks</Link>}
          </div>
          <div className="flex-row">
            <span className="nav-user">
              {user.email}
              {role === 'ADMIN' && <span className="role-tag">ADMIN</span>}
            </span>
            <button className="btn-logout" onClick={handleLogout}>Logout</button>
          </div>
        </>
      )}
    </nav>
  );
}
