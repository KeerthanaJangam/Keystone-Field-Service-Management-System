import axios from "axios";

const axiosInstance = axios.create({
   //baseURL: "http://localhost:2325/api",
    baseURL: "https://keystone-field-service-management-system-dfge.onrender.com/api",
    headers: {
        "Content-Type": "application/json",
    },
});

axiosInstance.interceptors.request.use((config) => {

    const token = localStorage.getItem("token");

    // Do not send JWT token during login/register
    if (
        token &&
        !config.url?.includes("/auth/login") &&
        !config.url?.includes("/auth/register")
    ) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});

export default axiosInstance;