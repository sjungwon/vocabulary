import { Form, Input, Space, Typography } from "antd";
import { useForm } from "antd/es/form/Form";
import useRepository from "../hook/useRepository";
import { useState } from "react";
import styles from "./LoginForm.module.scss";
import { Link, useNavigate } from "react-router-dom";
import { AxiosError } from "axios";
import { ErrorResponse } from "../type/repository.type";

type ConfirmForm = {
  account: string;
  password: string;
  confirmPassword: string;
};

const RegisterForm = () => {
  const [form] = useForm<ConfirmForm>();

  const { AuthRepository } = useRepository();

  const [globalError, setGlobalError] = useState<string>("");

  const navigate = useNavigate();

  const [passwordMessage, setPasswordMessage] = useState<string>();

  form.submit = async () => {
    try {
      const requestData = await form.validateFields();
      if (requestData.confirmPassword !== requestData.password) {
        setPasswordMessage("확인 비밀번호가 다릅니다.");
        return;
      }
      setPasswordMessage("");

      await AuthRepository.register(requestData);
      setGlobalError("");
      window.alert("회원 가입에 성공했습니다.");
      navigate("/login");
    } catch (e) {
      const err = e as AxiosError<ErrorResponse>;
      const errResponse = err.response;
      if (errResponse?.status === 400) {
        setGlobalError(errResponse?.data.message || "가입 실패");
      } else {
        setGlobalError("가입 실패");
      }
    }
  };

  return (
    <Form form={form} style={{ width: "100%", maxWidth: "15rem" }}>
      <label style={{ color: "white" }}>Account</label>
      <Form.Item
        name={"account"}
        rules={[
          { required: true, message: "필수 값입니다." },
          { min: 4, message: "4자 이상 입력해주세요." },
          { max: 10, message: "10자 이하로 입력해주세요." },

          {
            pattern: new RegExp("^[a-zA-Z][a-zA-Z0-9]+$", "g"),
            message: "소문자로 시작해야하고, 대소문자와 숫자만 입력가능합니다.",
          },
        ]}
      >
        <Input type="text" />
      </Form.Item>
      <label style={{ color: "white" }}>Password</label>
      <Form.Item
        name={"password"}
        rules={[
          { required: true, message: "필수 값입니다." },
          { min: 8, message: "8자 이상 입력해주세요." },
          { max: 20, message: "10자 이하로 입력해주세요." },
          {
            pattern: new RegExp("^(?=.*[A-Za-z])(?=.*[0-9])[\\d\\D]+$", "g"),
            message: "영문자와 숫자를 각 1개 이상 포함해야합니다.",
          },
          {
            pattern: new RegExp("^[A-Za-z0-9@$!%*#?]+$", "g"),
            message: "특수문자는 ^, @, $, !, %, *, #, ?만 가능합니다.",
          },
        ]}
      >
        <Input.Password />
      </Form.Item>
      <label style={{ color: "white" }}>Confirm Password</label>
      <Form.Item
        name={"confirmPassword"}
        rules={[{ required: true, message: "필수 값입니다." }]}
        style={{ marginBottom: 0 }}
      >
        <Input.Password />
      </Form.Item>
      {passwordMessage ? (
        <Typography.Text type="danger" style={{ margin: 0 }}>
          {passwordMessage}
        </Typography.Text>
      ) : null}
      <Space
        direction="vertical"
        style={{ width: "100%", maxWidth: "15rem", marginTop: "1rem" }}
      >
        <button type="submit" className={styles.my_btn}>
          {"Sign up"}
        </button>
        <Link to="/login" style={{ color: "white" }}>
          <button type="button" className={styles.black_btn}>
            {"Sign in"}
          </button>
        </Link>
        <Typography.Paragraph type="danger">{globalError}</Typography.Paragraph>
      </Space>
    </Form>
  );
};

export default RegisterForm;
