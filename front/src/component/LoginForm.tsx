import { Button, Form, Input, Space, Typography } from "antd";
import { useForm } from "antd/es/form/Form";
import { LoginFormType } from "../type/auth.type";
import useRepository from "../hook/useRepository";
import { useState } from "react";
import styles from "./LoginForm.module.scss";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const LoginForm = () => {
  const [form] = useForm<LoginFormType>();

  const { AuthRepository } = useRepository();

  const [globalError, setGlobalError] = useState<string>("");

  const navigate = useNavigate();

  const { login } = useAuth();

  form.submit = async () => {
    try {
      const requestData = await form.validateFields();
      if (
        !requestData.account ||
        requestData.account.length < 3 ||
        !requestData.password ||
        requestData.password.length < 5
      ) {
        setGlobalError(
          "가입되지 않은 회원이거나 잘못된 암호를 입력하셨습니다."
        );
        navigate("/");
        return;
      }
      const response = await AuthRepository.login(requestData);
      login(response.data);
      setGlobalError("");
      console.log(response);
    } catch (e) {
      setGlobalError("가입되지 않은 회원이거나 잘못된 암호를 입력하셨습니다.");
    }
  };

  return (
    <Form form={form} style={{ width: "100%", maxWidth: "15rem" }}>
      <label style={{ color: "white" }}>Account</label>
      <Form.Item name={"account"}>
        <Input type="text" />
      </Form.Item>
      <label style={{ color: "white" }}>Password</label>
      <Form.Item name={"password"}>
        <Input.Password />
      </Form.Item>
      <Typography.Paragraph type="danger">{globalError}</Typography.Paragraph>
      <Space direction="vertical" style={{ width: "100%", maxWidth: "15rem" }}>
        <button type="submit" className={styles.my_btn}>
          {"Sign in"}
        </button>
        <button type="button" className={styles.my_btn}>
          {"Sign up"}
        </button>
      </Space>
    </Form>
  );
};

export default LoginForm;
