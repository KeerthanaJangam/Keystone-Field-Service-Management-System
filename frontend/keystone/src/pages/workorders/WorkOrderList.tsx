import { useEffect, useState } from "react";
import { toast } from "react-toastify";

import workOrderService from "../../services/workOrderService";
import type { WorkOrder } from "../../types/WorkOrder";
import WorkOrderForm from "./WorkOrderForm";

import AssignTechnicianForm from "./AssignTechnicianForm";

import {
    FaEdit,
    FaPlus,
    FaSearch,
    FaUserCog
} from "react-icons/fa";

const WorkOrderList = () => {

    const [workOrders, setWorkOrders] = useState<WorkOrder[]>([]);
    const [loading, setLoading] = useState(true);
    const [searchTerm, setSearchTerm] = useState("");

    const [showForm, setShowForm] = useState(false);

    const [selectedWorkOrder, setSelectedWorkOrder] = useState<WorkOrder | undefined>();

    const [showAssignModal, setShowAssignModal] = useState(false);
    const [selectedWorkOrderId, setSelectedWorkOrderId] =
    useState<number | null>(null);

    useEffect(() => {

        // eslint-disable-next-line react-hooks/immutability
        loadWorkOrders();

    }, []);

    const loadWorkOrders = async () => {

        try {

            const data =
                await workOrderService.getAllWorkOrders();

            setWorkOrders(data);

        } catch (error) {

            console.error(error);

            toast.error("Failed to load work orders.");

        } finally {

            setLoading(false);

        }

    };

    const filteredWorkOrders = workOrders.filter(workOrder => {

        const search = searchTerm.toLowerCase();

        return (

            workOrder.workOrderCode?.toLowerCase().includes(search) ||

            workOrder.title.toLowerCase().includes(search) ||

            workOrder.customerName?.toLowerCase().includes(search) ||

            workOrder.siteName?.toLowerCase().includes(search)

        );

    });

    if (loading) {

        return (


            <div className="text-center mt-5">
    <div
        className="spinner-border text-primary"
        role="status"
    />
    <p className="mt-3">Loading...</p>
</div>

        );

    }

    return (

        <div className="page">


            <div className="table-header">

                <h2 className="table-title">

                Work Order Management

            </h2>

                <div className="d-flex gap-2">

                  <div className="input-group search-input">

    <span className="input-group-text">

        <FaSearch />

    </span>

    <input
        type="text"
        className="form-control"
        placeholder="Search Work Orders..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
    />

</div>
                    <button
    className="btn btn-primary d-flex align-items-center"
    onClick={() => {

        setSelectedWorkOrder(undefined);

        setShowForm(true);

    }}
>

    <FaPlus className="me-2"/>

    Create Work Order

</button>

                </div>

            </div>

            {

                filteredWorkOrders.length === 0 ?

                    (

                        <div className="alert alert-info text-center">

                        <h5>No Work Orders Found</h5>

                        <p>
                             Use the button above to create your first workorder.
                        </p>

                    </div>

                    )

                    :

                    (

                        <div className="table-responsive">

                            <table className="table">

                            <thead>

                                <tr>

                                    <th>Code</th>

                                    <th>Title</th>

                                    <th>Customer</th>

                                    <th>Site</th>

                                    <th>Priority</th>

                                    <th>Status</th>

                                    <th>Technician</th>

                                    <th>Actions</th>

                                </tr>

                            </thead>

                            <tbody>

                                {

                                    filteredWorkOrders.map(workOrder => (

                                        <tr key={workOrder.id}>

                                            <td>{workOrder.workOrderCode}</td>

                                            <td>{workOrder.title}</td>

                                            <td>{workOrder.customerName}</td>

                                            <td>{workOrder.siteName}</td>

                                           <td>
                                                <span
                                                    className={`badge ${
                                                        workOrder.priority === "HIGH"
                                                            ? "bg-danger"
                                                            : workOrder.priority === "MEDIUM"
                                                            ? "bg-warning text-dark"
                                                            : "bg-success"
                                                    }`}
                                                >
                                                    {workOrder.priority}
                                                </span>
                                            </td>

                                            <td>
                                            <span
                                                className={`badge ${
                                                    workOrder.status === "ASSIGNED"
                                                        ? "bg-primary"
                                                        : workOrder.status === "COMPLETED"
                                                        ? "bg-success"
                                                        : "bg-secondary"
                                                }`}
                                            >
                                                {workOrder.status}
                                            </span>
                                        </td>

                                            <td>
                                                {workOrder.technicianName ? (
                                                    workOrder.technicianName
                                                ) : (
                                                    <span className="text-muted">
                                                        Not Assigned
                                                    </span>
                                                )}
                                            </td>

                                            <td>

                                                <button
    className="btn btn-outline-warning btn-sm d-flex align-items-center"
    onClick={() => {

        setSelectedWorkOrder(workOrder);

        setShowForm(true);

    }}
>

    <FaEdit className="me-1"/>

    Edit

</button>
                                               <button
    className="btn btn-outline-primary btn-sm d-flex align-items-center"
    onClick={() => {

        if (!workOrder.id) return;

        setSelectedWorkOrderId(workOrder.id);

        setShowAssignModal(true);

    }}
>

    <FaUserCog className="me-1"/>

    Assign

</button>

                                            </td>

                                        </tr>

                                    ))

                                }

                            </tbody>

                        </table>
                        </div>

                    )

            }
{
    showForm && (

        <div
            className="modal d-block"
            style={{
                background: "rgba(0,0,0,0.5)"
            }}
        >

            <div className="modal-dialog modal-lg">

                <div className="modal-content">

                    <div className="modal-header">

                        <h5>

                            {
                                selectedWorkOrder
                                    ? "Edit Work Order"
                                    : "Create Work Order"
                            }

                        </h5>

                        <button
                            className="btn-close" aria-label="Close"
                            onClick={() => {

                                setShowForm(false);

                                setSelectedWorkOrder(undefined);

                            }}
                        />

                    </div>

                    <div className="modal-body">

                        <WorkOrderForm
                            workOrder={selectedWorkOrder}
                            onSuccess={() => {

                                setShowForm(false);

                                setSelectedWorkOrder(undefined);

                                loadWorkOrders();

                            }}
                        />

                    </div>

                </div>

            </div>

        </div>

    )
}
{
    showAssignModal && selectedWorkOrderId && (

        <div
            className="modal d-block"
            style={{
                background: "rgba(0,0,0,0.5)"
            }}
        >

            <div className="modal-dialog">

                <div className="modal-content">

                    <div className="modal-header">

                        <h5>

                            Assign Technician

                        </h5>

                        <button
                            className="btn-close"
                            onClick={() => {

                                setShowAssignModal(false);

                                setSelectedWorkOrderId(null);

                            }}
                        />

                    </div>

                    <div className="modal-body">

                        <AssignTechnicianForm
                            workOrderId={selectedWorkOrderId}
                            onSuccess={() => {

                                setShowAssignModal(false);

                                setSelectedWorkOrderId(null);

                                loadWorkOrders();

                            }}
                        />

                    </div>

                </div>

            </div>

        </div>

    )
}
        </div>


    );

};

export default WorkOrderList;