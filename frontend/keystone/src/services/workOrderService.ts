import axiosInstance from "../api/axiosConfig";
import { API } from "../constants/api";

import type { WorkOrder } from "../types/WorkOrder";
import type { AssignTechnicianRequest } from "../types/AssignTechnician";

class WorkOrderService {

    async getAllWorkOrders(): Promise<WorkOrder[]> {

        const response = await axiosInstance.get<WorkOrder[]>(
            API.WORKORDERS
        );

        return response.data;

    }

    async getWorkOrder(id: number): Promise<WorkOrder> {

        const response = await axiosInstance.get<WorkOrder>(
            `${API.WORKORDERS}/${id}`
        );

        return response.data;

    }

    async createWorkOrder(
        workOrder: WorkOrder
    ): Promise<WorkOrder> {

        const response = await axiosInstance.post<WorkOrder>(
            API.WORKORDERS,
            workOrder
        );

        return response.data;

    }

    async updateWorkOrder(
        id: number,
        workOrder: WorkOrder
    ): Promise<WorkOrder> {

        const response = await axiosInstance.put<WorkOrder>(
            `${API.WORKORDERS}/${id}`,
            workOrder
        );

        return response.data;

    }

    async assignTechnician(
        id: number,
        request: AssignTechnicianRequest
    ): Promise<WorkOrder> {

        const response = await axiosInstance.put<WorkOrder>(
            `${API.WORKORDERS}/${id}/assign`,
            request
        );

        return response.data;

    }

}

export default new WorkOrderService();