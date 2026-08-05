package com.keystone.deliverableservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keystone.deliverableservice.entity.WorkOrder;
import com.keystone.deliverableservice.entity.WorkOrderStatusHistory;

public interface WorkOrderStatusHistoryRepository  extends JpaRepository<WorkOrderStatusHistory, Long>
{
	List<WorkOrderStatusHistory> findByWorkOrderOrderByChangedAtAsc(WorkOrder workOrder);

}
