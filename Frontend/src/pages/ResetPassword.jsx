import { useState, useEffect } from 'react';
import { useSearchParams, useNavigate, Link } from 'react-router-dom';
import api from '../api/axios';
import styles from './Auth.module.css';

export default function ResetPassword() {
  const [searchParams] = useSearchParams();
  const [form, setForm] = useState({ token: '', newPassword: '', confirm: '' });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const token = searchParams.get('token');
    if (token) setForm((f) => ({ ...f, token }));
    else setError('Invalid or missing reset token.');
  }, [searchParams]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (form.newPassword !== form.confirm) { setError('Passwords do not match'); return; }
    if (form.newPassword.length < 8) { setError('Password must be at least 8 characters'); return; }
    setLoading(true);
    setError('');
    try {
      await api.post('/public/reset-password', { token: form.token, newPassword: form.newPassword });
      setSuccess(true);
      setTimeout(() => navigate('/login'), 2000);
    } catch (err) {
      setError(err.response?.data?.message || 'Reset failed. Link may have expired.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.page}>
      <div className={styles.card}>
        <div className={styles.header}>
          <h1 className={styles.title}>New password</h1>
          <p className={styles.subtitle}>Choose a strong password</p>
        </div>

        {error && <div className={styles.error}>{error}</div>}
        {success && <div className={styles.successMsg}>Password updated! Redirecting to login...</div>}

        {!success && (
          <form onSubmit={handleSubmit} className={styles.form}>
            <div className={styles.field}>
              <label className={styles.label}>New Password</label>
              <input
                className={styles.input}
                type="password"
                placeholder="Min. 8 characters"
                value={form.newPassword}
                onChange={(e) => { setForm({ ...form, newPassword: e.target.value }); setError(''); }}
                required
              />
            </div>
            <div className={styles.field}>
              <label className={styles.label}>Confirm Password</label>
              <input
                className={styles.input}
                type="password"
                placeholder="Repeat password"
                value={form.confirm}
                onChange={(e) => { setForm({ ...form, confirm: e.target.value }); setError(''); }}
                required
              />
            </div>
            <button className={styles.btn} type="submit" disabled={loading || !form.token}>
              {loading ? <span className={styles.spinner} /> : 'Update Password'}
            </button>
          </form>
        )}

        <p className={styles.footer}>
          <Link to="/login" className={styles.footerLink}>← Back to login</Link>
        </p>
      </div>
    </div>
  );
}
