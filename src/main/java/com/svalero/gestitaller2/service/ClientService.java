package com.svalero.gestitaller2.service;

import com.svalero.gestitaller2.domain.Client;
import com.svalero.gestitaller2.exception.ClientNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    Flux<Client> findAllClients();

    Flux<Client> findAllClients(String name, String surname, String dni);

    Mono<Client> findById(long id) throws ClientNotFoundException;

    Mono<Client> addClient(Client client);

    Mono<Client> deleteClient(long id) throws ClientNotFoundException;

    Mono<Client> modifyClient(long id, Client client) throws ClientNotFoundException;

}
