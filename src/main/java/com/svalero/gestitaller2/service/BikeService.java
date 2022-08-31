package com.svalero.gestitaller2.service;

import com.svalero.gestitaller2.domain.Bike;
import com.svalero.gestitaller2.domain.Client;
import com.svalero.gestitaller2.domain.dto.BikeDTO;
import com.svalero.gestitaller2.exception.BikeNotFoundException;
import com.svalero.gestitaller2.exception.ClientNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BikeService {

    Flux<Bike> findAll();

    Flux<Bike> findAll(String  brand, String model, String license);

    Mono<Bike> findById(long id) throws BikeNotFoundException;

    Flux<Bike> findBikesByClient(long id) throws ClientNotFoundException, BikeNotFoundException;

    Mono<Bike> addBike(BikeDTO bikeDTO) throws ClientNotFoundException;

    Mono<Bike> deleteBike(long id) throws BikeNotFoundException;

    Mono<Bike> modifyBike(long id, BikeDTO bikeDTO) throws BikeNotFoundException, ClientNotFoundException;

}
