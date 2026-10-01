import { useAuth } from '../context/AuthContext';
import styles from './Dashboard.module.css';

export default function Dashboard() {
  const { user } = useAuth();

  return (
    <div className={styles.page}>
      <div className={styles.hero}>
        <div className={styles.badge}>Welcome back</div>
        <h1 className={styles.title}>
          Hey, <span className={styles.accent}>{user?.username || user?.email}</span>
        </h1>
        <p className={styles.sub}>You're logged in and authenticated.</p>
      </div>

      <div className={styles.grid}>
        <div className={styles.card}>
          <div className={styles.cardIcon}>👤</div>
          <div className={styles.cardLabel}>Account</div>
          <div className={styles.cardValue}>{user?.email}</div>
        </div>
        <div className={styles.card}>
          <div className={styles.cardIcon}>🔑</div>
          <div className={styles.cardLabel}>Role</div>
          <div className={styles.cardValue}>{user?.role || 'USER'}</div>
        </div>
        <div className={styles.card}>
          <div className={styles.cardIcon}>✅</div>
          <div className={styles.cardLabel}>Status</div>
          <div className={`${styles.cardValue} ${styles.online}`}>Active</div>
        </div>
      </div>
    </div>
  );
}
