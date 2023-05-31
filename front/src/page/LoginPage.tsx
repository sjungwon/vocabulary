import { Form, Input, Row, Space, Typography } from "antd";
import logo from "../image/logo.png";
import LoginForm from "../component/LoginForm";
import { useAuth } from "../context/AuthContext";
import { Navigate } from "react-router-dom";

const LoginPage: React.FC = () => {
  const { user } = useAuth();

  if (user) {
    return <Navigate to="/" />;
  }

  return (
    <>
      <div
        style={{
          backgroundColor: "#8c8c8c72",
          height: "70%",
          width: "70%",
          margin: "auto",
          borderRadius: "1rem",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          flexDirection: "column",
        }}
      >
        <div
          style={{
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
          }}
        >
          <img src={logo} alt="로고" width={"150px"} height={"90px"} />
        </div>
        <div>
          <Typography.Paragraph
            style={{
              textAlign: "center",
              color: "#fcde3d",
              fontSize: "1.5rem",
              margin: 0,
            }}
          >
            {"토익 단어 암기 도우미"}
          </Typography.Paragraph>
          <Typography.Paragraph
            style={{
              textAlign: "center",
              color: "#fcde3d",
              fontSize: "1.5rem",
              margin: 0,
            }}
          >
            {"Todan Todan Vocap"}
          </Typography.Paragraph>
        </div>
        <div style={{ marginTop: "1rem" }}>
          <Typography.Paragraph
            style={{
              textAlign: "center",
              color: "#f5eff0",
              margin: 0,
            }}
          >
            {"모든 기능은 회원가입 이후에 가능합니다."}
          </Typography.Paragraph>
          <Typography.Paragraph
            style={{
              textAlign: "center",
              color: "#f5eff0",
              margin: 0,
            }}
          >
            {"회원이시라면 아래를 통해 로그인을 해 주세요."}
          </Typography.Paragraph>
        </div>
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            alignContent: "center",
            width: "100%",
            marginTop: "2rem",
          }}
        >
          <LoginForm />
        </div>
      </div>
    </>
  );
};

export default LoginPage;
