import axiosInstance from "../api/axiosConfig";
import { API } from "../constants/api";

import type { User } from "../types/User";

class UserService {

    async getTechnicians(): Promise<User[]> {

        const response = await axiosInstance.get<User[]>(
            API.TECHNICIANS
        );

        return response.data;

    }

}

export default new UserService();