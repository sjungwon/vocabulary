import axios, { AxiosResponse } from "axios";
import { useCallback, useMemo } from "react";
import { LoginFormType } from "../type/auth.type";
import { ApiResponse } from "../type/repository.type";
import { VocabularyType } from "../type/vocabulary.type";
import { ExamAnswerType, ExamResultType, ExamType } from "../type/exam.type";

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

  const repo = useMemo(
    () => ({
      login,
      logout,
    }),
    [login, logout]
  );

  return repo;
};

const useVocabularyRepository = () => {
  const getTodayVoca = useCallback(async (size: number) => {
    const response = await axios.get<
      void,
      AxiosResponse<ApiResponse<VocabularyType[]>>
    >(`/vocabulary?size=${size}`);
    return response.data;
  }, []);

  const repo = useMemo(
    () => ({
      getTodayVoca,
    }),
    [getTodayVoca]
  );

  return repo;
};

const useTestRepository = () => {
  const getTest = useCallback(async () => {
    const response = await axios.get<
      void,
      AxiosResponse<ApiResponse<ExamType>>
    >("/vocabulary/test");
    return response.data;
  }, []);

  const postAnswer = useCallback(async (answer: ExamAnswerType) => {
    const response = await axios.post<
      ExamAnswerType,
      AxiosResponse<ApiResponse<ExamResultType>>
    >("/vocabulary/test", answer);

    return response;
  }, []);

  const getPrevResult = useCallback(async () => {
    const response = await axios.get<
      void,
      AxiosResponse<ApiResponse<ExamResultType>>
    >("/vocabulary/test/prev");
    return response.data;
  }, []);

  const repo = useMemo(
    () => ({
      getTest,
      postAnswer,
      getPrevResult,
    }),
    [getPrevResult, getTest, postAnswer]
  );

  return repo;
};

const useRepository = () => {
  const AuthRepository = useAuthRepository();
  const VocabularyRepository = useVocabularyRepository();
  const TestRepository = useTestRepository();
  const repo = useMemo(
    () => ({ AuthRepository, VocabularyRepository, TestRepository }),
    [AuthRepository, TestRepository, VocabularyRepository]
  );
  return repo;
};

export default useRepository;
