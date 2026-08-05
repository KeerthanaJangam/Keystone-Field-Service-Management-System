import { useEffect, useState } from "react";

import {
    FaUsers,
    FaBuilding,
    FaClipboardList,
    FaClock,
    FaUserCheck,
    FaCheckCircle
} from "react-icons/fa";

import customerService from "../../services/customerService";
import siteService from "../../services/siteService";
import workOrderService from "../../services/workOrderService";
import "../../assets/dashboard.css";
import type { WorkOrder } from "../../types/WorkOrder";


const Dashboard = () => {

    const [customerCount, setCustomerCount] = useState(0);

    const [siteCount, setSiteCount] = useState(0);

    const [workOrders, setWorkOrders] = useState<WorkOrder[]>([]);

    useEffect(() => {

        // eslint-disable-next-line react-hooks/immutability
        loadDashboard();

    }, []);

    const loadDashboard = async () => {

        try {

            const customers =
                await customerService.getAllCustomers();

            const sites =
                await siteService.getAllSites();

            const workOrders =
                await workOrderService.getAllWorkOrders();

            setCustomerCount(customers.length);

            setSiteCount(sites.length);

            setWorkOrders(workOrders);

        } catch (error) {

            console.error(error);

        }

    };

    const totalWorkOrders = workOrders.length;

    const newCount = workOrders.filter(
        workOrder => workOrder.status === "NEW"
    ).length;

    const assignedCount = workOrders.filter(
        workOrder => workOrder.status === "ASSIGNED"
    ).length;

    const completedCount = workOrders.filter(
        workOrder => workOrder.status === "COMPLETED"
    ).length;


    const name = localStorage.getItem("name");

const currentHour = new Date().getHours();

let greeting = "Good Evening";

if (currentHour < 12) {

    greeting = "Good Morning";

} else if (currentHour < 17) {

    greeting = "Good Afternoon";

}

    return (

<div className="page">


    <div className="dashboard">

        <div className="dashboard-header">

            <h2>

                {greeting}, {name} 👋

            </h2>

            <p>

                Welcome to Keystone Field Service Management Dashboard.

            </p>

        </div>

        <div className="stats-grid">

            {/* Customers */}

            <div className="stat-card">

                <div className="stat-top">

                    <div>

                        <div className="stat-title">

                            Customers

                        </div>

                        <div className="stat-value">

                            {customerCount}

                        </div>

                    </div>

                    <div className="stat-icon bg-blue">

                        <FaUsers />

                    </div>

                </div>

            </div>

            {/* Sites */}

            <div className="stat-card">

                <div className="stat-top">

                    <div>

                        <div className="stat-title">

                            Sites

                        </div>

                        <div className="stat-value">

                            {siteCount}

                        </div>

                    </div>

                    <div className="stat-icon bg-green">

                        <FaBuilding />

                    </div>

                </div>

            </div>

            {/* Work Orders */}

            <div className="stat-card">

                <div className="stat-top">

                    <div>

                        <div className="stat-title">

                            Work Orders

                        </div>

                        <div className="stat-value">

                            {totalWorkOrders}

                        </div>

                    </div>

                    <div className="stat-icon bg-orange">

                        <FaClipboardList />

                    </div>

                </div>

            </div>

            {/* Pending */}

            <div className="stat-card">

                <div className="stat-top">

                    <div>

                        <div className="stat-title">

                            Pending

                        </div>

                        <div className="stat-value">

                            {newCount}

                        </div>

                    </div>

                    <div className="stat-icon bg-red">

                        <FaClock />

                    </div>

                </div>

            </div>

            {/* Assigned */}

            <div className="stat-card">

                <div className="stat-top">

                    <div>

                        <div className="stat-title">

                            Assigned

                        </div>

                        <div className="stat-value">

                            {assignedCount}

                        </div>

                    </div>

                    <div className="stat-icon bg-cyan">

                        <FaUserCheck />

                    </div>

                </div>

            </div>

            {/* Completed */}

            <div className="stat-card">

                <div className="stat-top">

                    <div>

                        <div className="stat-title">

                            Completed

                        </div>

                        <div className="stat-value">

                            {completedCount}

                        </div>

                    </div>

                    <div className="stat-icon bg-purple">

                        <FaCheckCircle />

                    </div>

                </div>

            </div>

        </div>

    </div>

</div>

);
};

export default Dashboard;