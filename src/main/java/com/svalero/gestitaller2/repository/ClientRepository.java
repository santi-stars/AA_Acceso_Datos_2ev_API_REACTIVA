package com.svalero.gestitaller2.repository;

import com.svalero.gestitaller2.domain.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, Long> {

    Flux<Client> findAll();

    Flux<Client> findByNameContainingOrSurnameContainingOrDniContaining(String name, String surname, String dni);

}
