import React, { useState, useEffect, useCallback } from 'react';
import { getAllTasks, updateTask, deleteTask } from '../services/api';
import StatusBadge from '../components/StatusBadge';
import TaskFormModal from '../components/TaskFormModal';
import ConfirmModal from '../components/ConfirmModal';

export default function AdminTasksPage() {
  const [tasks, setTasks] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const [editTask, setEditTask] = useState(null);
  const [deleteTarget, setDeleteTarget] = useState(null);

  const fetchTasks = useCallback(async () => {
    setLoading(true);
    setError('');
    try {
      const res = await getAllTasks(page);
      setTasks(res.data.content);
      setTotalPages(res.data.totalPages);
      setTotalElements(res.data.totalElements);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  }, [page]);

  useEffect(() => { fetchTasks(); }, [fetchTasks]);

  async function handleUpdate(payload) {
    await updateTask(editTask.taskId, payload, true);
    setEditTask(null);
    fetchTasks();
  }

  async function handleDelete() {
    await deleteTask(deleteTarget.taskId, true);
    setDeleteTarget(null);
    fetchTasks();
  }

  function formatDate(str) {
    if (!str) return '-';
    return new Date(str).toLocaleDateString('en-US', {
      month: 'short', day: 'numeric', year: 'numeric'
    });
  }

  return (
    <div className="page-container">
      <div className="flex-between mb-16">
        <h1 className="page-title" style={{ marginBottom: 0 }}>
          All Tasks
          {totalElements > 0 && (
            <span className="text-muted" style={{ fontSize: 14, fontWeight: 400, marginLeft: 8 }}>
              ({totalElements} total)
            </span>
          )}
        </h1>
        <span className="role-tag">Admin View</span>
      </div>

      {error && <div className="alert alert-error">{error}</div>}

      <div className="card" style={{ padding: 0 }}>
        {loading ? (
          <div className="spinner">Loading tasks...</div>
        ) : tasks.length === 0 ? (
          <div className="empty-state">No tasks in the system yet.</div>
        ) : (
          <table>
            <thead>
              <tr>
                <th>#</th>
                <th>Title</th>
                <th>Description</th>
                <th>Status</th>
                <th>User</th>
                <th>Created</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {tasks.map((task, i) => (
                <tr key={task.taskId}>
                  <td className="text-muted">{page * 10 + i + 1}</td>
                  <td style={{ fontWeight: 500 }}>{task.title}</td>
                  <td className="text-muted">
                    {task.description
                      ? task.description.length > 50
                        ? task.description.substring(0, 50) + '...'
                        : task.description
                      : '-'}
                  </td>
                  <td><StatusBadge status={task.status} /></td>
                  <td className="text-muted">{task.userEmail}</td>
                  <td className="text-muted">{formatDate(task.createdAt)}</td>
                  <td>
                    <div className="flex-row">
                      <button
                        className="btn btn-secondary btn-sm"
                        onClick={() => setEditTask(task)}
                      >
                        Edit
                      </button>
                      <button
                        className="btn btn-danger btn-sm"
                        onClick={() => setDeleteTarget(task)}
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      {totalPages > 1 && (
        <div className="pagination">
          <button onClick={() => setPage(p => p - 1)} disabled={page === 0}>
            &larr; Prev
          </button>
          <span>Page {page + 1} of {totalPages}</span>
          <button onClick={() => setPage(p => p + 1)} disabled={page >= totalPages - 1}>
            Next &rarr;
          </button>
        </div>
      )}

      {editTask && (
        <TaskFormModal
          task={editTask}
          onSave={handleUpdate}
          onClose={() => setEditTask(null)}
        />
      )}

      {deleteTarget && (
        <ConfirmModal
          message={`Delete task "${deleteTarget.title}" (owned by ${deleteTarget.userEmail})? This cannot be undone.`}
          onConfirm={handleDelete}
          onCancel={() => setDeleteTarget(null)}
        />
      )}
    </div>
  );
}
