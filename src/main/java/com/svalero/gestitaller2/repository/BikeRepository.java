package com.svalero.gestitaller2.repository;

import com.svalero.gestitaller2.domain.Bike;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BikeRepository extends ReactiveMongoRepository<Bike, Long> {
    Flux<Bike> findAll();

    Flux<Bike> findByBrandContainingOrModelContainingOrLicensePlateContaining(String brand, String model, String license);

    @Query("select b from bike b where b.client.id = ?1")
    Flux<Bike> findBikesByClient_Id(long id);
}
