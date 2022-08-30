package com.svalero.gestitaller2.service;

import com.svalero.gestitaller2.domain.Bike;
import com.svalero.gestitaller2.domain.Client;
import com.svalero.gestitaller2.domain.WorkOrder;
import com.svalero.gestitaller2.domain.dto.BikeDTO;
import com.svalero.gestitaller2.exception.BikeNotFoundException;
import com.svalero.gestitaller2.exception.ClientNotFoundException;
import com.svalero.gestitaller2.repository.BikeRepository;
import com.svalero.gestitaller2.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeServiceImpl implements BikeService {

    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Bike> findAll() {
        return bikeRepository.findAll();
    }

    @Override
    public List<Bike> findAll(String brand, String model, String license) {
        return bikeRepository.findByBrandContainingOrModelContainingOrLicensePlateContaining(brand, model, license);
    }

    @Override
    public Bike findById(long id) throws BikeNotFoundException {
        return bikeRepository.findById(id).orElseThrow(BikeNotFoundException::new);
    }

    @Override
    public List<Bike> findBikesByClient(long id) {
        return bikeRepository.findBikesByClient_Id(id);
    }

    @Override
    public Bike addBike(BikeDTO bikeDTO) throws ClientNotFoundException {

        ModelMapper mapper = new ModelMapper();
        Bike bike = mapper.map(bikeDTO, Bike.class);

        bike.setClient(clientRepository.findById(bikeDTO.getClient())
                .orElseThrow(ClientNotFoundException::new));

        return bikeRepository.save(bike);
    }

    @Override
    public Bike deleteBike(long id) throws BikeNotFoundException {
        Bike bike = bikeRepository.findById(id).orElseThrow(BikeNotFoundException::new);
        // Pone a null la lista de órdenes de la moto a borrar
        for (WorkOrder workOrder : bike.getWorkOrders()) workOrder.setBike(null);
        bikeRepository.delete(bike);
        return bike;
    }

    @Override
    public Bike modifyBike(long id, BikeDTO bikeDTO) throws BikeNotFoundException, ClientNotFoundException {

        bikeRepository.findById(id).orElseThrow(BikeNotFoundException::new);

        ModelMapper mapper = new ModelMapper();
        Bike bike = mapper.map(bikeDTO, Bike.class);

        bike.setId(id);
        bike.setClient(clientRepository.findById(bikeDTO.getClient())
                .orElseThrow(ClientNotFoundException::new));

        return bikeRepository.save(bike);
    }

}
