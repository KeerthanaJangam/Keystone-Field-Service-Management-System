import { useEffect, useState } from "react";
import { toast } from "react-toastify";

import siteService from "../../services/siteService";
import customerService from "../../services/customerService";

import type { Site } from "../../types/Site";
import type { Customer } from "../../types/Customer";

interface Props {
    onSuccess: () => void;
    site?: Site;
}

const SiteForm = ({ onSuccess, site: existingSite }: Props) => {

    const [customers, setCustomers] = useState<Customer[]>([]);

    const [site, setSite] = useState<Site>(
        existingSite ?? {
            siteName: "",
            address: "",
            city: "",
            state: "",
            pincode: "",
            customerId: 0
        }
    );

    useEffect(() => {

        // eslint-disable-next-line react-hooks/immutability
        loadCustomers();

    }, []);

    const loadCustomers = async () => {

        try {

            const data = await customerService.getAllCustomers();

            setCustomers(data);

        } catch {

            toast.error("Failed to load customers");

        }

    };

    const handleChange = (

        e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>

    ) => {

        const { name, value } = e.target;

        setSite({

            ...site,

            [name]:
                name === "customerId"
                    ? Number(value)
                    : value

        });

    };

    const handleSubmit = async (

        e: React.FormEvent

    ) => {

        e.preventDefault();

        try {

            if (site.id) {

                await siteService.updateSite(site.id, site);

                toast.success("Site updated successfully");

            } else {

                await siteService.createSite(site);

                toast.success("Site created successfully");

            }

            onSuccess();

        } catch {

            toast.error("Failed to save site");

        }

    };

    return (

        <form onSubmit={handleSubmit} className="form-card">

             <div className="form-section">

                <label className="form-label">Site Name</label>

                <input
                    className="form-control"
                    name="siteName"
                    value={site.siteName}
                    onChange={handleChange}
                    required
                />

            </div>

             <div className="form-section">

                <label className="form-label">Customer</label>

                <select
                    className="form-select"
                    name="customerId"
                    value={site.customerId}
                    onChange={handleChange}
                    required
                >

                    <option value={0}>

                        Select Customer

                    </option>

                    {

                        customers.map(customer => (

                            <option
                                key={customer.id}
                                value={customer.id}
                            >

                                {customer.companyName}

                            </option>

                        ))

                    }

                </select>

            </div>

             <div className="form-section">

                <label className="form-label">Address</label>

                <input
                    className="form-control"
                    name="address"
                    value={site.address}
                    onChange={handleChange}
                    required
                />

            </div>

            <div className="row">

                <div className="col">

                    <label className="form-label">City</label>

                    <input
                        className="form-control"
                        name="city"
                        value={site.city}
                        onChange={handleChange}
                        required
                    />

                </div>

                <div className="col">

                    <label className="form-label">State</label>

                    <input
                        className="form-control"
                        name="state"
                        value={site.state}
                        onChange={handleChange}
                        required
                    />

                </div>

            </div>

            <div className="form-section">

                <label className="form-label">Pincode</label>

                <input
                    className="form-control"
                    name="pincode"
                    value={site.pincode}
                    onChange={handleChange}
                    required
                />

            </div>

           <div className="form-footer">

                <button
                className="btn btn-primary save-btn"
                type="submit"
                >

                Save Site

                </button>

                </div>

        </form>

    );

};

export default SiteForm;