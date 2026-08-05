import { useEffect, useState } from "react";
import { toast } from "react-toastify";

import workOrderService from "../../services/workOrderService";
import userService from "../../services/userService.ts";

import type { AssignTechnicianRequest } from "../../types/AssignTechnician";
import type { User } from "../../types/User";

interface Props {
    workOrderId: number;
    onSuccess: () => void;
}

const AssignTechnicianForm = ({
    workOrderId,
    onSuccess
}: Props) => {

    const [technicians, setTechnicians] = useState<User[]>([]);

    const [request, setRequest] =
        useState<AssignTechnicianRequest>({
            technicianId: 0
        });

    useEffect(() => {

        // eslint-disable-next-line react-hooks/immutability
        loadTechnicians();

    }, []);

    const loadTechnicians = async () => {

        try {

            const data =
                await userService.getTechnicians();

            setTechnicians(data);

        } catch {

            toast.error("Failed to load technicians");

        }

    };

    const handleSubmit = async (
        e: React.FormEvent
    ) => {

        e.preventDefault();

        try {

            await workOrderService.assignTechnician(
                workOrderId,
                request
            );

            toast.success(
                "Technician assigned successfully"
            );

            onSuccess();

        } catch {

            toast.error(
                "Failed to assign technician"
            );

        }

    };

    return (

        <form onSubmit={handleSubmit}>

            <div className="mb-4">

                <label>Technician</label>

                <select
                    className="form-select"
                    value={request.technicianId}
                    onChange={(e) =>
                        setRequest({
                            technicianId:
                                Number(e.target.value)
                        })
                    }
                >

                    <option value={0}>
                        Select Technician
                    </option>

                    {

                        technicians.map(user => (

                            <option
                                key={user.id}
                                value={user.id}
                            >

                                {user.name}

                            </option>

                        ))

                    }

                </select>

            </div>
                <button
                className="btn btn-primary save-btn"
                >

                Assign Technician

                </button>

        </form>

    );

};

export default AssignTechnicianForm;