import axios, { Axios, AxiosError, AxiosResponse } from "axios";
import { useCallback } from "react";
import { LoginFormType } from "../type/auth.type";
import { ApiResponse } from "../type/repository.type";

const useAuthRepository = () => {
  const login = useCallback(async (loginForm: LoginFormType) => {
    const axiosResponse = await axios.post<
      LoginFormType,
      AxiosResponse<ApiResponse<string>>
    >("/auth/login", loginForm);
    const response = axiosResponse.data;
    return response;
  }, []);

  const logout = useCallback(async () => {
    const response = await axios.post<void, AxiosResponse<ApiResponse<string>>>(
      "/auth/logout"
    );
    return response.data;
  }, []);

  return {
    login,
    logout,
  };
};

const useRepository = () => {
  const AuthRepository = useAuthRepository();

  return { AuthRepository };
};

export default useRepository;
