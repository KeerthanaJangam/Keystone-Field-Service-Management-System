import {
    createContext,
    useContext,
    useState,
   type ReactNode,
} from "react";

interface AuthContextType {

    token: string | null;

    role: string | null;

    login: (token: string, role: string) => void;

    logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface Props {

    children: ReactNode;
}

export const AuthProvider = ({ children }: Props) => {

    const [token, setToken] = useState<string | null>(
        localStorage.getItem("token")
    );

    const [role, setRole] = useState<string | null>(
        localStorage.getItem("role")
    );

    const login = (jwt: string, userRole: string) => {

        localStorage.setItem("token", jwt);
        localStorage.setItem("role", userRole);

        setToken(jwt);
        setRole(userRole);
    };

    const logout = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("role");

        setToken(null);
        setRole(null);
    };

    return (
        <AuthContext.Provider
            value={{
                token,
                role,
                login,
                logout,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
};

// eslint-disable-next-line react-refresh/only-export-components
export const useAuth = () => {

    const context = useContext(AuthContext);

    if (!context) {
        throw new Error("useAuth must be used inside AuthProvider");
    }

    return context;
};