import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";

import { ToastContainer } from "react-toastify";

import { AuthProvider } from "./context/AuthContext";

import "bootstrap/dist/css/bootstrap.min.css";
import "react-toastify/dist/ReactToastify.css";
import "./index.css";
import "./assets/theme.css";
import "./assets/table.css";
import "./assets/form.css";
import "./assets/animations.css";

ReactDOM.createRoot(document.getElementById("root")!).render(

    <React.StrictMode>

        <AuthProvider>

            <App />

            <ToastContainer
                position="top-right"
                autoClose={3000}
                hideProgressBar={false}
                newestOnTop
                closeOnClick
                pauseOnHover
                draggable
                theme="colored"
            />

        </AuthProvider>

    </React.StrictMode>
);