import {
    FaUsers,
    FaBuilding,
    FaClipboardList,
    FaUserCog,
    FaServer,
    FaHeadset,
    FaMapMarkerAlt,
    FaPhoneAlt,
    FaEnvelope,
    FaGlobe,
    FaGithub,
    FaLinkedin
} from "react-icons/fa";

import HomeNavbar from "../components/HomeNavbar";
import Hero from "../components/Hero";

import "../assets/home.css";

const Home = () => {

    return (

        <>

            {/* Professional Navbar */}

            <HomeNavbar />

            {/* Hero Section */}

            <Hero />

            {/* Features */}

<section
    className="features-section"
    id="features"
>

    <span className="section-subtitle">

        OUR FEATURES

    </span>

    <h2 className="section-title">

        Everything You Need to Manage Field Services

    </h2>

    <div className="feature-grid">

        <div className="feature-card">

            <div className="feature-icon customer">

                <FaUsers />

            </div>

            <h4>Customer Management</h4>

            <p>

                Manage all customer information in one secure centralized system.

            </p>

        </div>

        <div className="feature-card">

            <div className="feature-icon site">

                <FaBuilding />

            </div>

            <h4>Site Management</h4>

            <p>

                Organize customer locations and monitor every service site.

            </p>

        </div>

        <div className="feature-card">

            <div className="feature-icon work">

                <FaClipboardList />

            </div>

            <h4>Work Orders</h4>

            <p>

                Create, assign and track work orders from start to completion.

            </p>

        </div>

        <div className="feature-card">

            <div className="feature-icon technician">

                <FaUserCog />

            </div>

            <h4>Technician Assignment</h4>

            <p>

                Assign the right technician for every work order efficiently.

            </p>

        </div>

        <div className="feature-card">

            <div className="feature-icon dashboard">

                📊

            </div>

            <h4>Dashboard & Reports</h4>

            <p>

                View live statistics, reports and overall system performance.

            </p>

        </div>

        <div className="feature-card">

            <div className="feature-icon notification">

                🔔

            </div>

            <h4>Notifications</h4>

            <p>

                Stay updated with real-time alerts and work order notifications.

            </p>

        </div>

    </div>

</section>
{/* Statistics */}

<section className="stats-section">

    <div className="stats-container">

        <div className="stat-item">

            <FaClipboardList className="stat-icon"/>

            <h2>500+</h2>

            <p>Work Orders</p>

        </div>

        <div className="stat-item">

            <FaUsers className="stat-icon"/>

            <h2>150+</h2>

            <p>Customers</p>

        </div>

        <div className="stat-item">

            <FaServer className="stat-icon"/>

            <h2>99.9%</h2>

            <p>Availability</p>

        </div>

        <div className="stat-item">

            <FaHeadset className="stat-icon"/>

            <h2>24×7</h2>

            <p>Support</p>

        </div>

    </div>

</section>

      {/* About */}

<section
    className="about-section"
    id="about"
>

    <div className="about-container">

        {/* Left */}

        <div className="about-left">

            <span className="section-subtitle">

                ABOUT KEYSTONE

            </span>

            <h2>

                Smart Field Service
                Management Platform

            </h2>

            <p>

                Keystone Field Service Management System is designed
                to simplify field operations by providing a centralized
                platform to manage customers, service sites,
                work orders and technician assignments.

            </p>

            <p>

                Built using modern web technologies, Keystone helps
                organizations improve productivity, reduce response
                times and efficiently track field activities from
                one secure dashboard.

            </p>

            <button className="btn btn-primary mt-3">

                Learn More

            </button>

        </div>

        {/* Right */}

        <div className="about-right">

            <div className="tech-card">

                <h3>

                    Technology Stack

                </h3>

                <div className="tech-grid">

                    <span>⚛ React</span>

                    <span>☕ Spring Boot</span>

                    <span>🗄 MySQL</span>

                    <span>🔐 JWT</span>

                    <span>🌐 REST API</span>

                    <span>📱 Responsive UI</span>

                </div>

            </div>

        </div>

    </div>

</section>
{/* Contact */}

<section
    className="contact-section"
    id="contact"
>

    <div className="contact-container">

        {/* Left Side */}

        <div className="contact-info">

            <span className="section-subtitle">

                CONTACT US

            </span>

            <h2>

                We'd Love to Hear From You

            </h2>

            <p>

                Have questions about Keystone Field Service Management?
                Contact us and we'll be happy to help.

            </p>

            <div className="contact-item">

                <FaMapMarkerAlt />

                <span>

                    Hyderabad, Telangana, India

                </span>

            </div>

            <div className="contact-item">

                <FaPhoneAlt />

                <span>

                    +91 98765 43210

                </span>

            </div>

            <div className="contact-item">

                <FaEnvelope />

                <span>

                    support@keystonefsm.com

                </span>

            </div>

            <div className="contact-item">

                <FaGlobe />

                <span>

                    www.keystonefsm.com

                </span>

            </div>

        </div>

        {/* Right Side */}

        <div className="contact-form-card">

            <h3>

                Send a Message

            </h3>

            <form>

                <input
                    type="text"
                    className="form-control mb-3"
                    placeholder="Your Name"
                />

                <input
                    type="email"
                    className="form-control mb-3"
                    placeholder="Your Email"
                />

                <textarea
                    className="form-control mb-3"
                    rows={5}
                    placeholder="Your Message"
                />

                <button
                    className="btn btn-primary w-100"
                >

                    Send Message

                </button>

            </form>

        </div>

    </div>

</section>

           {/* Footer */}

<footer className="footer">

    <div className="footer-container">

        {/* Company */}

        <div className="footer-column">

            <h2>Keystone</h2>

            <p>

                Field Service Management System that helps
                organizations manage customers, sites,
                work orders and technician assignments
                efficiently.

            </p>

        </div>

        {/* Quick Links */}

        <div className="footer-column">

            <h4>Quick Links</h4>

            <a href="#home">Home</a>

            <a href="#features">Features</a>

            <a href="#about">About</a>

            <a href="#contact">Contact</a>

        </div>

        {/* Modules */}

        <div className="footer-column">

            <h4>Modules</h4>

            <span>Customers</span>

            <span>Sites</span>

            <span>Work Orders</span>

            <span>Dashboard</span>

        </div>

        {/* Technologies */}

        <div className="footer-column">

            <h4>Technology</h4>

            <span>React</span>

            <span>Spring Boot</span>

            <span>MySQL</span>

            <span>JWT Security</span>

        </div>

    </div>

    <hr />

    <div className="footer-bottom">

        <div className="social-icons">

            <a
                href="https://github.com/"
                target="_blank"
                rel="noreferrer"
            >

                <FaGithub />

            </a>

            <a
                href="https://linkedin.com/"
                target="_blank"
                rel="noreferrer"
            >

                <FaLinkedin />

            </a>

            <a
                href="mailto:support@keystonefsm.com"
            >

                <FaEnvelope />

            </a>

        </div>

        <p>

            © 2026 Keystone Field Service Management System.
            All Rights Reserved.

        </p>

    </div>

</footer>
        </>

    );

};

export default Home;