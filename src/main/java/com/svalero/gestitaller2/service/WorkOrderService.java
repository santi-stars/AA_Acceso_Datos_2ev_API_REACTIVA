package com.svalero.gestitaller2.service;

import com.svalero.gestitaller2.domain.WorkOrder;
import com.svalero.gestitaller2.domain.dto.WorkOrderDTO;
import com.svalero.gestitaller2.exception.BikeNotFoundException;
import com.svalero.gestitaller2.exception.ClientNotFoundException;
import com.svalero.gestitaller2.exception.WorkOrderNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WorkOrderService {

    Flux<WorkOrder> findAllOrders();

    Flux<WorkOrder> findAllOrders(String nameSurname, String brandModel, String licensePlate);

    Mono<WorkOrder> findById(long id) throws WorkOrderNotFoundException;

    Mono<WorkOrder> deleteOrder(long id) throws WorkOrderNotFoundException;

    Mono<WorkOrder> addOrder(WorkOrderDTO newWorkOrderDTO) throws
            BikeNotFoundException, ClientNotFoundException;

    Mono<WorkOrder> modifyOrder(long id, WorkOrderDTO workOrderDTO) throws WorkOrderNotFoundException,
            BikeNotFoundException, ClientNotFoundException;

}
