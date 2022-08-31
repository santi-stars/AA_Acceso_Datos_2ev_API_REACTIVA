package com.svalero.gestitaller2.repository;

import com.svalero.gestitaller2.domain.WorkOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface WokrOrderRepository extends ReactiveMongoRepository<WorkOrder, Long> {

    Flux<WorkOrder> findAll();

    // TODO PROBAR LIKE CON FILTRADO
    @Query("select w from work_order w " +
            "where w.client.name like concat('%', ?1, '%') or w.client.surname like concat('%', ?1, '%') " +
            "or w.bike.brand like concat('%', ?2, '%') or w.bike.model like concat('%', ?2, '%') " +
            "or w.bike.licensePlate like concat('%', ?3, '%')")
    Flux<WorkOrder> findByClient_NameContainingOrClient_SurnameContainingOrBike_BrandContainingOrBike_ModelContainingOrBike_LicensePlateContaining
    (String nameSurname, String brandModel, String licensePlate);

}
