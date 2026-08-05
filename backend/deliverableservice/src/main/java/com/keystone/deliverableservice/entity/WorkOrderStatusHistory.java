package com.keystone.deliverableservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.keystone.deliverableservice.enums.WorkOrderStatus;

@Entity
@Table(name = "work_order_status_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WorkOrderStatus oldStatus;

    @Enumerated(EnumType.STRING)
    private WorkOrderStatus newStatus;

    private String remarks;

    private LocalDateTime changedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by")
    private User changedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder;

    @PrePersist
    public void prePersist() {
        changedAt = LocalDateTime.now();
    }
}
