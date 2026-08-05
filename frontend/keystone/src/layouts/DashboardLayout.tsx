import { Outlet } from "react-router-dom";
import Navbar from "../components/Navbar";
import Sidebar from "../components/Sidebar";

import Footer from "../components/Footer";

const DashboardLayout = () => {

    return (

    <div
        style={{
            display: "flex",
            flexDirection: "column"
        }}
    >

        <Navbar />

        <div
            className="d-flex"
            style={{ flex: 1 }}
        >

            <Sidebar />

            <div
                className="flex-grow-1 p-4"
                style={{
                    background: "#f5f7fa"
                }}
            >

                <Outlet />

            </div>

        </div>

        <Footer />

    </div>

);

};

export default DashboardLayout;