import { useNavigate } from "react-router-dom";

import { FaUser } from "react-icons/fa";

import logo from "../assets/images/logo.png";

import "../assets/home-navbar.css";

const HomeNavbar = () => {

    const navigate = useNavigate();

    return (

        <nav className="home-navbar">

            {/* Logo */}

            <div className="navbar-logo">

                <img
                    src={logo}
                    alt="Keystone"
                />

                <div>

                    <h2>

                        Keystone

                    </h2>

                    <span>

                        Field Service Management

                    </span>

                </div>

            </div>

            {/* Navigation */}

            <ul className="navbar-links">

                <li>

                    <a href="#home">

                        Home

                    </a>

                </li>

                <li>

                    <a href="#features">

                        Features

                    </a>

                </li>

                <li>

                    <a href="#about">

                        About Us

                    </a>

                </li>

                <li>

                    <a href="#modules">

                        Modules

                    </a>

                </li>

                <li>

                    <a href="#contact">

                        Contact Us

                    </a>

                </li>

            </ul>

            {/* Login */}

            <button
                className="login-btn"
                onClick={() => navigate("/login")}
            >

                <FaUser />

                Login

            </button>

        </nav>

    );

};

export default HomeNavbar;