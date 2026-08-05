import { useEffect, useState } from "react";
import { toast } from "react-toastify";

import siteService from "../../services/siteService";
import type { Site } from "../../types/Site";
import SiteForm from "./SiteForm";

import {
    FaEdit,
    FaPlus,
    FaSearch
} from "react-icons/fa";

const SiteList = () => {

    const [sites, setSites] = useState<Site[]>([]);
    const [loading, setLoading] = useState(true);
    const [searchTerm, setSearchTerm] = useState("");

    const [showForm, setShowForm] = useState(false);
    const [selectedSite, setSelectedSite] =
    useState<Site | undefined>();

    useEffect(() => {
        // eslint-disable-next-line react-hooks/immutability
        loadSites();
    }, []);

    const loadSites = async () => {

        try {

            const data = await siteService.getAllSites();

            setSites(data);

        } catch (error) {

            console.error(error);

            toast.error("Failed to load sites.");

        } finally {

            setLoading(false);

        }

    };

    const filteredSites = sites.filter(site => {

        const search = searchTerm.toLowerCase();

        return (

        site.siteName.toLowerCase().includes(search) ||

        site.customerName?.toLowerCase().includes(search) ||

        site.address.toLowerCase().includes(search) ||

        site.city.toLowerCase().includes(search) ||

        site.state.toLowerCase().includes(search) ||

        site.pincode.toLowerCase().includes(search)
        );

    });

    if (loading) {

        return (

    <div className="text-center mt-5">
    <div
        className="spinner-border text-primary"
        role="status"
    />
    <p className="mt-3">Loading...</p>
</div>

);

    }

    return (
<div className="page">


        <div>

            <div className="table-header">

                <h2 className="table-title">

                        Site Management

                    </h2>

                <div className="d-flex gap-2">

                    <div className="input-group search-input">

    <span className="input-group-text">

        <FaSearch />

    </span>

    <input
        type="text"
        className="form-control"
        placeholder="Search Sites..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
    />

</div>

                   <button
    className="btn btn-primary d-flex align-items-center"
    onClick={() => {

        setSelectedSite(undefined);

        setShowForm(true);

    }}
>

    <FaPlus className="me-2" />

    Add Site

</button>

                </div>

            </div>

            {

                filteredSites.length === 0 ?

                    (
            <div className="alert alert-info text-center">

                <h5>No Sites found</h5>

                <p>

                    Use the button above to create your first Site.

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

                        <th>Site Name</th>

                        <th>Customer</th>

                        <th>Address</th>

                        <th>City</th>

                        <th>State</th>

                        <th>Pincode</th>

                        <th>Actions</th>

                    </tr>

                </thead>

                <tbody>

                    {

            filteredSites.map((site) => (

                <tr key={site.id}>

                    <td>{site.id}</td>

                    <td>{site.siteName}</td>

                   <td>
                    <span className="badge bg-primary">
                        {site.customerName}
                        </span>
                    </td>

                   <td
                        style={{
                            maxWidth: "250px",
                            whiteSpace: "normal"
                        }}
                    >
                        {site.address}
                    </td>

                    <td>{site.city}</td>

                    <td>{site.state}</td>

                    <td>{site.pincode}</td>

                    <td>

                        <button
    className="btn btn-outline-warning btn-sm d-flex align-items-center"
    onClick={() => {

        setSelectedSite(site);

        setShowForm(true);

    }}
>

    <FaEdit className="me-1"/>

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

            <div className="modal-dialog modal-lg">

                <div className="modal-content">

                    <div className="modal-header">

                        <h5>

                            {
                                selectedSite
                                    ? "Edit Site"
                                    : "Add Site"
                            }

                        </h5>

                        <button
                            className="btn-close" aria-label="Close"
                            onClick={() => {

                                setShowForm(false);

                                setSelectedSite(undefined);

                            }}
                        />

                    </div>

                    <div className="modal-body">

                        <SiteForm
                            site={selectedSite}
                            onSuccess={() => {

                                setShowForm(false);

                                setSelectedSite(undefined);

                                loadSites();

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

export default SiteList;