package com.keystone.deliverableservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keystone.deliverableservice.entity.User;
import com.keystone.deliverableservice.entity.WorkOrder;
import com.keystone.deliverableservice.enums.WorkOrderStatus;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long>
{
	Optional<WorkOrder> findByWorkOrderCode(String workOrderCode);

    List<WorkOrder> findByStatus(WorkOrderStatus status);

    List<WorkOrder> findByAssignedTechnician(User technician);

    boolean existsByWorkOrderCode(String workOrderCode);
}
