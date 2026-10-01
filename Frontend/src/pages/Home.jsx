import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import styles from './Home.module.css';

export default function Home() {
  const { user } = useAuth();

  return (
    <div className={styles.page}>
      <div className={styles.glow} />
      <div className={styles.content}>
        <div className={styles.pill}>Authentication Service</div>
        <h1 className={styles.title}>
          Secure auth for <br />
          <span className={styles.accent}>BingeBuddy</span>
        </h1>
        <p className={styles.desc}>
          JWT-based auth with OAuth2 support via Google and GitHub.
          Built with Spring Boot &amp; React.
        </p>
        <div className={styles.actions}>
          {user ? (
            <Link to="/dashboard" className={styles.btnPrimary}>Go to Dashboard</Link>
          ) : (
            <>
              <Link to="/register" className={styles.btnPrimary}>Get Started</Link>
              <Link to="/login" className={styles.btnGhost}>Sign In</Link>
            </>
          )}
        </div>
      </div>
    </div>
  );
}
