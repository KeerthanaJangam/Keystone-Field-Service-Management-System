import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useAuth } from "../context/AuthContext";
import "../assets/navbar.css";
import { FaBell } from "react-icons/fa";

const Navbar = () => {

    const navigate = useNavigate();

    const { logout } = useAuth();

    const name = localStorage.getItem("name");

    const role = localStorage.getItem("role");

    const handleLogout = () => {

        logout();

        toast.success("Logged out successfully");

        navigate("/");
    };

    return (

        <nav className="top-navbar">

    <div>

        <div className="page-heading">

            Dispatcher Dashboard

        </div>

    </div>

    <div className="navbar-right">

        <div className="notification">

            <FaBell />

        </div>

        <div className="user-info">

            {/* <small>

                Welcome back,

            </small> */}

            <h6>

                {name}

            </h6>

            <span className="badge bg-primary">

                {role}

            </span>

        </div>

        <button
            className="btn btn-outline-danger logout-btn"
            onClick={handleLogout}
        >

            Logout

        </button>

    </div>

</nav>
    );

};

export default Navbar;