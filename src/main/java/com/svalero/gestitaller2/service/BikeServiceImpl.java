package com.svalero.gestitaller2.service;

import com.svalero.gestitaller2.domain.Bike;
import com.svalero.gestitaller2.domain.WorkOrder;
import com.svalero.gestitaller2.domain.dto.BikeDTO;
import com.svalero.gestitaller2.exception.BikeNotFoundException;
import com.svalero.gestitaller2.exception.ClientNotFoundException;
import com.svalero.gestitaller2.repository.BikeRepository;
import com.svalero.gestitaller2.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BikeServiceImpl implements BikeService {

    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Flux<Bike> findAll() {
        return bikeRepository.findAll();
    }

    @Override
    public Flux<Bike> findAll(String brand, String model, String license) {
        return bikeRepository.findByBrandContainingOrModelContainingOrLicensePlateContaining(brand, model, license);
    }

    @Override
    public Mono<Bike> findById(long id) throws BikeNotFoundException {
        return bikeRepository.findById(id).onErrorReturn(new Bike());
    }

    @Override
    public Flux<Bike> findBikesByClient(long id) {
        return bikeRepository.findBikesByClient_Id(id);
    }

    @Override
    public Mono<Bike> addBike(BikeDTO bikeDTO) throws ClientNotFoundException {

        ModelMapper mapper = new ModelMapper();
        Bike bike = mapper.map(bikeDTO, Bike.class);

        bike.setClient(clientRepository.findById(bikeDTO.getClient()).block());

        return bikeRepository.save(bike);
    }

    @Override
    public Mono<Bike> deleteBike(long id) throws BikeNotFoundException {
        Mono<Bike> bike = bikeRepository.findById(id).onErrorReturn(new Bike());
        // Pone a null la lista de órdenes de la moto a borrar
        for (WorkOrder workOrder : bike.block().getWorkOrders()) workOrder.setBike(null);
        bikeRepository.delete(bike.block());
        return bike;
    }

    @Override
    public Mono<Bike> modifyBike(long id, BikeDTO bikeDTO) throws BikeNotFoundException, ClientNotFoundException {

        Mono<Bike> monoBike = bikeRepository.findById(id).onErrorReturn(new Bike());

        ModelMapper mapper = new ModelMapper();
        Bike newBike = mapper.map(bikeDTO, Bike.class);

        newBike.setId(String.valueOf(id));
        newBike.setClient(clientRepository.findById(bikeDTO.getClient()).block());

        return bikeRepository.save(newBike);
    }

}
