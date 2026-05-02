import React from 'react';

export default function StatusBadge({ status }) {
  const map = {
    PENDING: 'badge-pending',
    DONE: 'badge-done',
    IN_PROGRESS: 'badge-in-progress',
  };
  const label = {
    PENDING: 'Pending',
    DONE: 'Done',
    IN_PROGRESS: 'In Progress',
  };
  return (
    <span className={`badge ${map[status] || ''}`}>
      {label[status] || status}
    </span>
  );
}
