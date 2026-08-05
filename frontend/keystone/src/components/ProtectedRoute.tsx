import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import type { ReactNode } from "react";


interface Props {
    children: ReactNode;
}

const ProtectedRoute = ({ children }: Props) => {

    const { token, role } = useAuth();

    if (!token) {
        return <Navigate to="/" replace />;
    }

    if (role !== "DISPATCHER") {
        return <Navigate to="/" replace />;
    }

    return children;
};

export default ProtectedRoute;