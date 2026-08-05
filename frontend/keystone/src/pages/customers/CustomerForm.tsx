import { useState } from "react";
import { toast } from "react-toastify";

import customerService from "../../services/customerService";
import  type{ Customer } from "../../types/Customer";

interface Props {
    onSuccess: () => void;
    customer?: Customer;
}

const CustomerForm = ({
    onSuccess,
    customer: existingCustomer
}: Props) => {

   const [customer, setCustomer] = useState<Customer>(
    existingCustomer ?? {
        companyName: "",
        contactPerson: "",
        email: "",
        phone: "",
        address: ""
    }
);

    const handleChange = (
        e: React.ChangeEvent<HTMLInputElement>
    ) => {

        setCustomer({
            ...customer,
            [e.target.name]: e.target.value
        });

    };

    const handleSubmit = async (
        e: React.FormEvent
    ) => {

        e.preventDefault();

        try {

          if (customer.id) {

        await customerService.updateCustomer(
            customer.id,
            customer
        );

        toast.success("Customer updated successfully");

        } else {

            await customerService.createCustomer(customer);

            toast.success("Customer created successfully");
        }

            setCustomer({
                companyName: "",
                contactPerson: "",
                email: "",
                phone: "",
                address: ""
            });

            onSuccess();

        } catch (error) {

            console.error(error);

            toast.error("Failed to create customer");

        }

    };

    return (

        <form onSubmit={handleSubmit} className="form-card">

            <div className="form-section">

                <label className="form-label">Company Name</label>

                <input
                    className="form-control"
                    name="companyName"
                    value={customer.companyName}
                    onChange={handleChange}
                    required
                />

            </div>

            <div className="form-section">

                <label className="form-label">Contact Person</label>

                <input
                    className="form-control"
                    name="contactPerson"
                    value={customer.contactPerson}
                    onChange={handleChange}
                    required
                />

            </div>
                <div className="form-section">

                <label className="form-label">Email</label>

                <input
                    type="email"
                    className="form-control"
                    name="email"
                    value={customer.email}
                    onChange={handleChange}
                    required
                />

            </div>

            <div className="form-section">

                <label className="form-label">Phone</label>

                <input
                    className="form-control"
                    name="phone"
                    value={customer.phone}
                    onChange={handleChange}
                    required
                />

            </div>

           <div className="form-section">

                <label className="form-label">Address</label>

                <input
                    className="form-control"
                    name="address"
                    value={customer.address}
                    onChange={handleChange}
                    required
                />

            </div>

            <div className="form-footer">

                <button
                className="btn btn-primary save-btn"
                type="submit"
                >

                Save Customer

                </button>

                </div>

        </form>

    );

};

export default CustomerForm;