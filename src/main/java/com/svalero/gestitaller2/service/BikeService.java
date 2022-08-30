package com.svalero.gestitaller2.service;

import com.svalero.gestitaller2.domain.Bike;
import com.svalero.gestitaller2.domain.Client;
import com.svalero.gestitaller2.domain.dto.BikeDTO;
import com.svalero.gestitaller2.exception.BikeNotFoundException;
import com.svalero.gestitaller2.exception.ClientNotFoundException;

import java.util.List;

public interface BikeService {

    List<Bike> findAll();

    List<Bike> findAll(String brand, String model, String license);

    Bike findById(long id) throws BikeNotFoundException;

    List<Bike> findBikesByClient(long id) throws ClientNotFoundException, BikeNotFoundException;

    Bike addBike(BikeDTO bikeDTO) throws ClientNotFoundException;

    Bike deleteBike(long id) throws BikeNotFoundException;

    Bike modifyBike(long id, BikeDTO bikeDTO) throws BikeNotFoundException, ClientNotFoundException;

}
