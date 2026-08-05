import { useEffect, useState } from "react";
import { toast } from "react-toastify";

import customerService from "../../services/customerService";
import type { Customer } from "../../types/Customer";
import CustomerForm from "./CustomerForm";
import {

FaEdit,

FaPlus,

FaSearch

} from "react-icons/fa";

const CustomerList = () => {

    const [customers, setCustomers] = useState<Customer[]>([]);
    const [loading, setLoading] = useState(true);
    const [selectedCustomer, setSelectedCustomer] =
    useState<Customer | undefined>();

    const [searchTerm, setSearchTerm] = useState("");

    useEffect(() => {
        // eslint-disable-next-line react-hooks/immutability
        loadCustomers();
    }, []);

    const [showForm, setShowForm] = useState(false);

    const loadCustomers = async () => {

        try {

            const data = await customerService.getAllCustomers();

            setCustomers(data);

        } catch (error) {

            console.error(error);

            toast.error("Failed to load customers.");

        } finally {

            setLoading(false);

        }

    };

    if (loading) {

        return <h5>Loading customers...</h5>;

    }

    const filteredCustomers = customers.filter((customer) => {

    const search = searchTerm.toLowerCase();

    return (

        customer.companyName.toLowerCase().includes(search) ||

        customer.contactPerson.toLowerCase().includes(search) ||

        customer.email.toLowerCase().includes(search)

    );

});

    return (

<div className="page">

<div className="table-container">

<div className="table-header">

   <h2 className="table-title">

    Customer Management

</h2>

    <div className="d-flex gap-2">

      <div className="input-group search-input">

    <span className="input-group-text">

        <FaSearch />

    </span>

    <input
        type="text"
        className="form-control"
        placeholder="Search customers..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
    />

</div>

      <button
    className="btn btn-primary d-flex align-items-center"
    onClick={() => {

        setSelectedCustomer(undefined);

        setShowForm(true);

    }}
>

    <FaPlus className="me-2" />

    Add Customer

</button>

    </div>

</div>
            {
                filteredCustomers.length === 0 ?

                    (

                        <div className="alert alert-info text-center">

                            <h5>No Records Found</h5>

                            <p>

                                Use the button above to create your first record.

                            </p>

                        </div>

                    )

                    :

                    (

                        <div className="table-responsive">

                            <table className="table">   
                            <thead>

                                <tr>

                                    <th>ID</th>

                                    <th>Company</th>

                                    <th>Contact Person</th>

                                    <th>Email</th>

                                    <th>Phone</th>

                                    <th>Address</th>

                                    <th>Actions</th>

                                </tr>

                           </thead>

                            <tbody>

                                {

                                    filteredCustomers.map((customer) => (

                                        <tr key={customer.id}>

                                            <td>{customer.id}</td>

                                            <td>{customer.companyName}</td>

                                            <td>{customer.contactPerson}</td>

                                            <td>{customer.email}</td>

                                            <td>{customer.phone}</td>

                                            <td>{customer.address}</td>

                                            <td>

 <button
    className="btn btn-outline-warning btn-sm d-flex align-items-center"
    onClick={() => {

        setSelectedCustomer(customer);

        setShowForm(true);

    }}
>

    <FaEdit className="me-1" />

    Edit

</button>
                                            </td>

                                        </tr>

                                    ))

                                }

                            </tbody>

                        </table>
                        </div>

                    )

            }

            {
    showForm && (

        <div
            className="modal d-block"
            style={{
                background: "rgba(0,0,0,0.5)"
            }}
        >

            <div className="modal-dialog">

                <div className="modal-content">

                    <div className="modal-header">

                     <h5>

                        {
                            selectedCustomer
                                ? "Edit Customer"
                                : "Add Customer"
                        }

                    </h5>

                        <button
                            className="btn-close" aria-label="Close"
                            onClick={() => {

                                setSelectedCustomer(undefined);

                                setShowForm(true);

                            }}
                        />

                    </div>

                    <div className="modal-body">

                        <CustomerForm
                        customer={selectedCustomer}
                        onSuccess={() => {

                            setShowForm(false);

                            setSelectedCustomer(undefined);

                            loadCustomers();

                        }}
                    />

                    </div>

                </div>

            </div>

        </div>

    )
}
        </div>

</div>

    );

};

export default CustomerList;