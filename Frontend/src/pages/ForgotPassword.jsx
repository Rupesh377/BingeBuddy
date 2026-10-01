import { useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../api/axios';
import styles from './Auth.module.css';

export default function ForgotPassword() {
  const [email, setEmail] = useState('');
  const [error, setError] = useState('');
  const [sent, setSent] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      await api.post('/public/forgot-password', { email });
      setSent(true);
    } catch (err) {
      setError(err.response?.data?.message || 'Something went wrong');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.page}>
      <div className={styles.card}>
        <div className={styles.header}>
          <h1 className={styles.title}>Reset password</h1>
          <p className={styles.subtitle}>We'll send a reset link to your email</p>
        </div>

        {error && <div className={styles.error}>{error}</div>}

        {sent ? (
          <div className={styles.successMsg}>
            Check your inbox — a reset link has been sent to <strong>{email}</strong>
          </div>
        ) : (
          <form onSubmit={handleSubmit} className={styles.form}>
            <div className={styles.field}>
              <label className={styles.label}>Email</label>
              <input
                className={styles.input}
                type="email"
                placeholder="you@example.com"
                value={email}
                onChange={(e) => { setEmail(e.target.value); setError(''); }}
                required
              />
            </div>
            <button className={styles.btn} type="submit" disabled={loading}>
              {loading ? <span className={styles.spinner} /> : 'Send Reset Link'}
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
