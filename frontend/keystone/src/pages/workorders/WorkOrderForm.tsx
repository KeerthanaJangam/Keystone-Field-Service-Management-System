import { useEffect, useState } from "react";
import { toast } from "react-toastify";

import customerService from "../../services/customerService";
import siteService from "../../services/siteService";
import workOrderService from "../../services/workOrderService";

import type { Customer } from "../../types/Customer";
import type { Site } from "../../types/Site";
import type { WorkOrder } from "../../types/WorkOrder";

interface Props {
    onSuccess: () => void;
    workOrder?: WorkOrder;
}

const WorkOrderForm = ({
    onSuccess,
    workOrder: existingWorkOrder
}: Props) => {

    const [customers, setCustomers] = useState<Customer[]>([]);
    const [sites, setSites] = useState<Site[]>([]);

    const [workOrder, setWorkOrder] = useState<WorkOrder>(
        existingWorkOrder ?? {
            title: "",
            description: "",
            priority: "LOW",
            customerId: 0,
            siteId: 0
        }
    );

    useEffect(() => {

        // eslint-disable-next-line react-hooks/immutability
        loadCustomers();

    }, []);

    useEffect(() => {

        if (workOrder.customerId) {

            // eslint-disable-next-line react-hooks/immutability
            loadSites(workOrder.customerId);

        }

    }, [workOrder.customerId]);

    const loadCustomers = async () => {

        try {

            const data = await customerService.getAllCustomers();

            setCustomers(data);

        } catch {

            toast.error("Failed to load customers");

        }

    };

    const loadSites = async (customerId: number) => {

        try {

            const data = await siteService.getAllSites();

            setSites(
                data.filter(site =>
                    site.customerId === customerId
                )
            );

        } catch {

            toast.error("Failed to load sites");

        }

    };

    const handleChange = (
        e: React.ChangeEvent<
            HTMLInputElement |
            HTMLTextAreaElement |
            HTMLSelectElement
        >
    ) => {

        const { name, value } = e.target;

        setWorkOrder({

            ...workOrder,

            [name]:

                name === "customerId" ||

                name === "siteId"

                    ? Number(value)

                    : value

        });

    };

    const handleSubmit = async (
        e: React.FormEvent
    ) => {

        e.preventDefault();

        try {

            if (workOrder.id) {

                await workOrderService.updateWorkOrder(
                    workOrder.id,
                    workOrder
                );

                toast.success(
                    "Work Order updated successfully"
                );

            } else {

                await workOrderService.createWorkOrder(
                    workOrder
                );

                toast.success(
                    "Work Order created successfully"
                );

            }

            onSuccess();

        } catch {

            toast.error(
                "Failed to save work order"
            );

        }

    };

    return (

        <form onSubmit={handleSubmit} className="form-card">

            {/* Title */}

            <div className="form-section">

                <label className="form-label">Title</label>

                <input
                    className="form-control"
                    name="title"
                    value={workOrder.title}
                    onChange={handleChange}
                    required
                />

            </div>

            {/* Description */}

             <div className="form-section">

                <label className="form-label">Description</label>

                <textarea
                    className="form-control"
                    rows={3}
                    name="description"
                    value={workOrder.description}
                    onChange={handleChange}
                />

            </div>

            {/* Customer */}

             <div className="form-section">

                <label className="form-label">Customer</label>

                <select
                    className="form-select"
                    name="customerId"
                    value={workOrder.customerId}
                    onChange={handleChange}
                    required
                >

                    <option value={0}>
                        Select Customer
                    </option>

                    {

                        customers.map(customer => (

                            <option
                                key={customer.id}
                                value={customer.id}
                            >

                                {customer.companyName}

                            </option>

                        ))

                    }

                </select>

            </div>

            {/* Site */}

            <div className="form-section">

                <label className="form-label">Site</label>

                <select
                    className="form-select"
                    name="siteId"
                    value={workOrder.siteId}
                    onChange={handleChange}
                    required
                >

                    <option value={0}>
                        Select Site
                    </option>

                    {

                        sites.map(site => (

                            <option
                                key={site.id}
                                value={site.id}
                            >

                                {site.siteName}

                            </option>

                        ))

                    }

                </select>

            </div>

            {/* Priority */}

            <div className="form-section">

                <label className="form-label">Priority</label>

                <select
                    className="form-select"
                    name="priority"
                    value={workOrder.priority}
                    onChange={handleChange}
                >

                    <option value="LOW">
                        LOW
                    </option>

                    <option value="MEDIUM">
                        MEDIUM
                    </option>

                    <option value="HIGH">
                        HIGH
                    </option>

                </select>

            </div>

            <div className="form-footer">

                <button
                className="btn btn-primary save-btn"
                type="submit"
                >

                Save WorkOrder

                </button>

                </div>

        </form>

    );

};

export default WorkOrderForm;