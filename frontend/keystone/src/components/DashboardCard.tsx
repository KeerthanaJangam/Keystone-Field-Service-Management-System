import type { ReactNode } from "react";

interface DashboardCardProps {
    title: string;
    count: number;
    icon: ReactNode;
    color: string;
}

const DashboardCard = ({
    title,
    count,
    icon,
    color,
}: DashboardCardProps) => {
    return (
        <div className="col-lg-3 col-md-6 mb-4">
            <div className={`card border-0 shadow ${color}`}>
                <div className="card-body d-flex justify-content-between align-items-center">

                    <div>

                        <h6 className="text-muted">

                            {title}

                        </h6>

                        <h2>

                            {count}

                        </h2>

                    </div>

                    <div
                        style={{
                            fontSize: "2rem",
                        }}
                    >
                        {icon}
                    </div>

                </div>
            </div>
        </div>
    );
};

export default DashboardCard;