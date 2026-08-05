import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/auth/Login";
import Dashboard from "./pages/dashboard/Dashboard";
import DashboardLayout from "./layouts/DashboardLayout";
import ProtectedRoute from "./components/ProtectedRoute";
import CustomerList from "./pages/customers/CustomerList";
import SiteList from "./pages/sites/SiteList";
import WorkOrderList from "./pages/workorders/WorkOrderList";
import Home from "./pages/Home";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
                path="/"
                element={<Home />}
                 />

              <Route
                path="/login"
                element={<Login />}
                />

        
        {/* <Route path="/" element={<Login />} /> */}

        {/* Protected Routes */}
        <Route
          element={
            <ProtectedRoute>
              <DashboardLayout />
            </ProtectedRoute>
          }
        >
          <Route path="/dashboard" element={<Dashboard />} />

          <Route
              path="/customers"
              element={<CustomerList />}
          />

          <Route
                path="/sites"
                element={<SiteList />}
            />

          <Route
                path="/workorders"
                element={<WorkOrderList />}
            />

        </Route>

      </Routes>
    </BrowserRouter>
  );
}

export default App;