import axiosInstance from "../api/axiosConfig";
import { API } from "../constants/api";
import type { Customer } from "../types/Customer";

class CustomerService {

    async getAllCustomers(): Promise<Customer[]> {

        const response = await axiosInstance.get<Customer[]>(
            API.CUSTOMERS
        );

        return response.data;
    }

    async getCustomer(id: number): Promise<Customer> {

        const response = await axiosInstance.get<Customer>(
            `${API.CUSTOMERS}/${id}`
        );

        return response.data;
    }

    async createCustomer(customer: Customer): Promise<Customer> {

        const response = await axiosInstance.post<Customer>(
            API.CUSTOMERS,
            customer
        );

        return response.data;
    }

    async updateCustomer(
        id: number,
        customer: Customer
    ): Promise<Customer> {

        const response = await axiosInstance.put<Customer>(
            `${API.CUSTOMERS}/${id}`,
            customer
        );

        return response.data;
    }

    async deleteCustomer(id: number): Promise<void> {

        await axiosInstance.delete(
            `${API.CUSTOMERS}/${id}`
        );
    }

}

export default new CustomerService();