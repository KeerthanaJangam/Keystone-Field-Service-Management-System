import { useNavigate } from "react-router-dom";

import {
    FaClipboardList,
    FaPlay,
    FaCheck
} from "react-icons/fa";

import technician from "../assets/images/hero-technician.png";
import "../assets/hero.css";

const Hero = () => {

    const navigate = useNavigate();

    return (

        <section className="hero-section" id="home">

            {/* Left Side */}

            <div className="hero-left">

                <h1>

                    Efficient Field Service.

                    <br />

                    <span>Better Outcomes.</span>

                </h1>

                <p>

                    Keystone Field Service Management helps dispatchers
                    manage customers, sites, work orders and technicians
                    from one centralized platform.

                </p>

                <div className="hero-buttons">

                    <button
                        className="primary-btn"
                        onClick={() => navigate("/login")}
                    >

                        <FaClipboardList />

                        Get Started

                    </button>

                    <button className="secondary-btn">

                        <FaPlay />

                        Watch Demo

                    </button>

                </div>

                <div className="hero-points">

                    <span>

                        <FaCheck />

                        Real-time Tracking

                    </span>

                    <span>

                        <FaCheck />

                        Smart Dispatching

                    </span>

                    <span>

                        <FaCheck />

                        Better Productivity

                    </span>

                </div>

            </div>

            {/* Right Side */}

            <div className="hero-right">

                <img
                    src={technician}
                    alt="Technician"
                    className="hero-image"
                />

            </div>

        </section>

    );

};

export default Hero;