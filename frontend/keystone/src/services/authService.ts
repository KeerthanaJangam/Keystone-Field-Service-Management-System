import axiosInstance from "../api/axiosConfig";
import { API } from "../constants/api";
import type { LoginRequest, LoginResponse } from "../types/Auth";

class AuthService {

    async login(request: LoginRequest): Promise<LoginResponse> {

        const response = await axiosInstance.post<LoginResponse>(
            API.LOGIN,
            request
        );

        return response.data;
    }

}

export default new AuthService();