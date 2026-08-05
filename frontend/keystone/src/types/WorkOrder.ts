export interface WorkOrder {

    id?: number;

    workOrderCode?: string;

    title: string;

    description: string;

    priority: string;

    status?: string;

    customerId: number;

    customerName?: string;

    siteId: number;

    siteName?: string;

    technicianId?: number;

    technicianName?: string;

    slaDueDate?: string;
}