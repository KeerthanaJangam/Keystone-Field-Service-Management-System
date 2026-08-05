import { NavLink } from "react-router-dom";
import "../assets/sidebar.css";


import {
    FaHome,
    FaUsers,
    FaBuilding,
    FaClipboardList
} from "react-icons/fa";

const Sidebar = () => {

    const name = localStorage.getItem("name");

    const role = localStorage.getItem("role");

   

   
    return (

        <div
            className="bg-dark text-white d-flex flex-column"
            style={{
                width: "250px",
                minHeight: "100vh"
            }}
        >

            <div className="p-3">

                <div className="sidebar-logo">

    <div className="logo-circle">

        K

    </div>

    <div>

        <h3>Keystone</h3>

        <p>Field Service Management</p>

    </div>

</div>

                <NavLink
                    to="/dashboard"
                    className={({ isActive }) =>
                        isActive
                            ? "nav-link text-warning fw-bold"
                            : "nav-link text-white"
                    }
                >
                    <FaHome className="me-2" />
                    Dashboard
                </NavLink>

                <NavLink
                    to="/customers"
                    className={({ isActive }) =>
                        isActive
                            ? "nav-link text-warning fw-bold"
                            : "nav-link text-white"
                    }
                >
                    <FaUsers className="me-2" />
                    Customers
                </NavLink>

                <NavLink
                    to="/sites"
                    className={({ isActive }) =>
                        isActive
                            ? "nav-link text-warning fw-bold"
                            : "nav-link text-white"
                    }
                >
                    <FaBuilding className="me-2" />
                    Sites
                </NavLink>

                <NavLink
                    to="/workorders"
                    className={({ isActive }) =>
                        isActive
                            ? "nav-link text-warning fw-bold"
                            : "nav-link text-white"
                    }
                >
                    <FaClipboardList className="me-2" />
                    Work Orders
                </NavLink>

            </div>

            {/* Footer */}
          <div className="sidebar-footer">

    <div className="profile-square">

        {name?.charAt(0)}

    </div>

    <div>

        <div className="user-name">

            {name}

        </div>

        <div className="user-role">

            {role}

        </div>

    </div>
    

</div>

        </div>

    );

};

export default Sidebar;