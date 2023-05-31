import { useAuth } from "../context/AuthContext";
import styles from "./StudyLayout.module.scss";
import { Navigate, Outlet } from "react-router-dom";

const StudyLayout: React.FC = () => {
  const { user, status } = useAuth();

  if (status === "DONE" && !user) {
    window.alert("로그인이 필요합니다.");
    return <Navigate to="/login" />;
  }

  return (
    <div className={styles.container}>
      <Outlet />
    </div>
  );
};

export default StudyLayout;
