package com.svalero.gestitaller2.service;

import com.svalero.gestitaller2.domain.WorkOrder;
import com.svalero.gestitaller2.domain.dto.WorkOrderDTO;
import com.svalero.gestitaller2.exception.BikeNotFoundException;
import com.svalero.gestitaller2.exception.ClientNotFoundException;
import com.svalero.gestitaller2.exception.WorkOrderNotFoundException;

import java.util.List;

public interface WorkOrderService {

    List<WorkOrder> findAllOrders();

    List<WorkOrder> findAllOrders(String nameSurname, String brandModel, String licensePlate);

    WorkOrder findById(long id) throws WorkOrderNotFoundException;

    WorkOrder deleteOrder(long id) throws WorkOrderNotFoundException;

    WorkOrder addOrder(WorkOrderDTO newWorkOrderDTO) throws
            BikeNotFoundException, ClientNotFoundException;

    WorkOrder modifyOrder(long id, WorkOrderDTO workOrderDTO) throws WorkOrderNotFoundException,
            BikeNotFoundException, ClientNotFoundException;

}
