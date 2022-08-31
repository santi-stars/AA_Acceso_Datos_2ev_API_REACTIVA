package com.svalero.gestitaller2.service;

import com.svalero.gestitaller2.controller.WorkOrderController;
import com.svalero.gestitaller2.domain.Bike;
import com.svalero.gestitaller2.domain.Client;
import com.svalero.gestitaller2.domain.WorkOrder;
import com.svalero.gestitaller2.domain.dto.WorkOrderDTO;
import com.svalero.gestitaller2.exception.BikeNotFoundException;
import com.svalero.gestitaller2.exception.ClientNotFoundException;
import com.svalero.gestitaller2.exception.WorkOrderNotFoundException;
import com.svalero.gestitaller2.repository.BikeRepository;
import com.svalero.gestitaller2.repository.ClientRepository;
import com.svalero.gestitaller2.repository.WokrOrderRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {

    @Autowired
    private WokrOrderRepository wokrOrderRepository;
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private ClientRepository clientRepository;
    private final Logger logger = LoggerFactory.getLogger(WorkOrderController.class);

    @Override
    public Flux<WorkOrder> findAllOrders() {
        return wokrOrderRepository.findAll();
    }

    @Override
    public Flux<WorkOrder> findAllOrders(String nameSurname, String brandModel, String licensePlate) {
        logger.info("Filtrado por parámetro (ServiceIMPL): nameSurname=" + nameSurname + "// brandModel=" + brandModel + "// licensePlate=" + licensePlate);
        return wokrOrderRepository.findByClient_NameContainingOrClient_SurnameContainingOrBike_BrandContainingOrBike_ModelContainingOrBike_LicensePlateContaining(nameSurname, brandModel, licensePlate);
    }

    @Override
    public Mono<WorkOrder> findById(long id) throws WorkOrderNotFoundException {
        return wokrOrderRepository.findById(id).onErrorReturn(new WorkOrder());
    }

    @Override
    public Mono<WorkOrder> deleteOrder(long id) throws WorkOrderNotFoundException {
        Mono<WorkOrder> mechanic = wokrOrderRepository.findById(id).onErrorReturn(new WorkOrder());
        wokrOrderRepository.delete(mechanic.block());
        return mechanic;
    }

    @Override
    public Mono<WorkOrder> addOrder(WorkOrderDTO newWorkOrderDTO) throws BikeNotFoundException, ClientNotFoundException {

        Mono<Bike> monoBike = bikeRepository.findById(newWorkOrderDTO.getBike()).onErrorReturn(new Bike());
        Mono<Client> monoClient = clientRepository.findById(newWorkOrderDTO.getClient()).onErrorReturn(new Client());

        Bike bike = monoBike.block();
        Client client = monoClient.block();

        ModelMapper mapper = new ModelMapper();
        WorkOrder workOrder = mapper.map(newWorkOrderDTO, WorkOrder.class);

        workOrder.setBike(bike);
        workOrder.setClient(client);

        return wokrOrderRepository.save(workOrder);
    }

    @Override
    public Mono<WorkOrder> modifyOrder(long id, WorkOrderDTO newWorkOrderDTO) throws WorkOrderNotFoundException,
            BikeNotFoundException, ClientNotFoundException {

        wokrOrderRepository.findById(id).onErrorReturn(new WorkOrder());

        ModelMapper mapper = new ModelMapper();
        WorkOrder workOrder = mapper.map(newWorkOrderDTO, WorkOrder.class);

        workOrder.setId(String.valueOf(id));
        workOrder.setBike(bikeRepository.findById(newWorkOrderDTO.getBike()).block());
        workOrder.setClient(clientRepository.findById(newWorkOrderDTO.getClient()).block());

        return wokrOrderRepository.save(workOrder);
    }

}
