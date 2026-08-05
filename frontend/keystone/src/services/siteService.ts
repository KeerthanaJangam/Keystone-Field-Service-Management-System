import axiosInstance from "../api/axiosConfig";
import { API } from "../constants/api";
import type { Site } from "../types/Site";

class SiteService {

    async getAllSites(): Promise<Site[]> {

        const response = await axiosInstance.get<Site[]>(
            API.SITES
        );

        return response.data;
    }

    async getSite(id: number): Promise<Site> {

        const response = await axiosInstance.get<Site>(
            `${API.SITES}/${id}`
        );

        return response.data;
    }

    async createSite(site: Site): Promise<Site> {

        const response = await axiosInstance.post<Site>(
            API.SITES,
            site
        );

        return response.data;
    }

    async updateSite(
        id: number,
        site: Site
    ): Promise<Site> {

        const response = await axiosInstance.put<Site>(
            `${API.SITES}/${id}`,
            site
        );

        return response.data;
    }

}

export default new SiteService();