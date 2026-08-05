import { useState, type FormEvent } from "react";

import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import authService from "../../services/authService";
import { useAuth } from "../../context/AuthContext";

const Login = () => {

    const navigate = useNavigate();

    const { login } = useAuth();

    const [email, setEmail] = useState("");

    const [password, setPassword] = useState("");

    const [loading, setLoading] = useState(false);

    const handleSubmit = async (
        e: FormEvent<HTMLFormElement>
    ) => {

        e.preventDefault();

        setLoading(true);

        try {

            const response = await authService.login({

                email,

                password,

            });

          localStorage.setItem("token", response.token);

            localStorage.setItem("name", response.name);

            localStorage.setItem("email", response.email);

            localStorage.setItem("role", response.role);

            login(response.token, response.role);

            toast.success("Login Successful");

            navigate("/dashboard");

        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        } catch (error: any) {

            console.error(error);

            toast.error("Invalid Email or Password");

        } finally {

            setLoading(false);

        }

    };

    return (

        <div className="container vh-100 d-flex justify-content-center align-items-center">

            <div
                className="card shadow p-4"
                style={{ width: "420px" }}
            >

                <h2 className="text-center mb-4">

                    Keystone Dispatcher Login

                </h2>

                <form onSubmit={handleSubmit}>

                    <div className="mb-3">

                        <label className="form-label">

                            Email

                        </label>

                        <input
                            type="email"
                            className="form-control"
                            value={email}
                            onChange={(e) =>
                                setEmail(e.target.value)
                            }
                            required
                        />

                    </div>

                    <div className="mb-4">

                        <label className="form-label">

                            Password

                        </label>

                        <input
                            type="password"
                            className="form-control"
                            value={password}
                            onChange={(e) =>
                                setPassword(e.target.value)
                            }
                            required
                        />

                    </div>

                    <button
                        className="btn btn-primary w-100"
                        disabled={loading}
                    >

                        {

                            loading

                                ? "Logging in..."

                                : "Login"

                        }

                    </button>

                </form>

            </div>

        </div>

    );

};

export default Login;