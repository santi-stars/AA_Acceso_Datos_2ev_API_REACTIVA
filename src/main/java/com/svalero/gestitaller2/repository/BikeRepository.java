package com.svalero.gestitaller2.repository;

import com.svalero.gestitaller2.domain.Bike;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepository extends CrudRepository<Bike, Long> {
    List<Bike> findAll();

    List<Bike> findByBrandContainingOrModelContainingOrLicensePlateContaining(String brand, String model, String license);

    @Query("select b from bike b where b.client.id = ?1")
    List<Bike> findBikesByClient_Id(long id);
}
