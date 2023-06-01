import {
  ReactNode,
  createContext,
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useState,
} from "react";
import { ProfileType } from "../type/member.type";
import axios, { AxiosResponse } from "axios";
import { ApiResponse } from "../type/repository.type";

type AuthStatus = "PENDING" | "DONE";

type AuthContextType = {
  status: AuthStatus;
  user: ProfileType | null;
  login: (data: any) => void;
  logout: () => void;
};

const AuthContext = createContext<AuthContextType>({
  status: "PENDING",
  user: null,
  login: (data: ProfileType) => {},
  logout: () => {},
});

type Provider = React.FC<{ children: ReactNode }>;

export const AuthProvider: Provider = ({ children }) => {
  const [status, setStatus] = useState<AuthStatus>("PENDING");
  const [user, setUser] = useState<ProfileType | null>(null);

  const checkLogin = useCallback(async () => {
    try {
      const response = await axios.post<
        void,
        AxiosResponse<ApiResponse<ProfileType>>
      >("/auth/session");
      setUser(response.data.data);
    } catch (e) {
    } finally {
      setStatus("DONE");
    }
  }, []);

  useEffect(() => {
    checkLogin();
  }, [checkLogin]);

  const login = useCallback((data: ProfileType) => {
    setUser(data);
  }, []);

  const logout = useCallback(() => {
    setUser(null);
  }, []);

  const value = useMemo(
    () => ({
      status,
      user,
      login,
      logout,
    }),
    [login, logout, status, user]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => useContext(AuthContext);
