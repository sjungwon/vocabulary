import { Modal, Row } from "antd";
import { useCallback, useMemo } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import useRepository from "../hook/useRepository";

type Props = {
  to: string;
  text: string;
};

const MenuLink: React.FC<Props> = ({ to, text }) => {
  return (
    <Row style={{ width: "100%", marginLeft: "2rem", alignItems: "center" }}>
      <Link
        to={to}
        style={{
          fontSize: "1.2rem",
          color: "#f5eff0",
          fontWeight: "bold",
          textDecoration: "none",
        }}
      >
        {text}
      </Link>
    </Row>
  );
};

const LogoutLink: React.FC = () => {
  const { AuthRepository } = useRepository();

  const { logout } = useAuth();

  const navigate = useNavigate();

  const submitLogout = useCallback(async () => {
    try {
      await AuthRepository.logout();
    } catch (e) {
    } finally {
      logout();
      navigate("/");
    }
  }, [AuthRepository, logout, navigate]);

  const openConfirm = () => {
    confirm({
      title: "로그아웃 하시겠습니까",
      onOk: submitLogout,
    });
  };

  return (
    <Row style={{ width: "100%", marginLeft: "2rem", alignItems: "center" }}>
      <button
        style={{
          fontSize: "1.2rem",
          color: "#f5eff0",
          fontWeight: "bold",
          textDecoration: "none",
          backgroundColor: "transparent",
          border: "none",
          padding: 0,
          cursor: "pointer",
        }}
        onClick={openConfirm}
      >
        Logout
      </button>
    </Row>
  );
};

const { confirm } = Modal;

const MyMenu: React.FC = () => {
  const { user } = useAuth();

  const defaultMenu = useMemo(() => {
    return ["Study", "Test"].map((s) => (
      <MenuLink to={s.toLowerCase()} text={s} key={s} />
    ));
  }, []);

  const loginLink = <MenuLink to="login" text="Login" />;

  const logoutLink = <LogoutLink />;

  return (
    <>
      {user ? logoutLink : loginLink}
      <MenuLink to="/" text="Home" />
      {defaultMenu}
    </>
  );
};

export default MyMenu;
