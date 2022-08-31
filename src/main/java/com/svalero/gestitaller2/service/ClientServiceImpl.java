package com.svalero.gestitaller2.service;

import com.svalero.gestitaller2.domain.Bike;
import com.svalero.gestitaller2.domain.Client;
import com.svalero.gestitaller2.domain.WorkOrder;
import com.svalero.gestitaller2.exception.ClientNotFoundException;
import com.svalero.gestitaller2.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Flux<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Flux<Client> findAllClients(String name, String surname, String dni) {
        return clientRepository.findByNameContainingOrSurnameContainingOrDniContaining(name, surname, dni);
    }

    @Override
    public Mono<Client> findById(long id) throws ClientNotFoundException {
        return clientRepository.findById(id).onErrorReturn(new Client());
    }

    @Override
    public Mono<Client> deleteClient(long id) throws ClientNotFoundException {
        Mono<Client> client = clientRepository.findById(id).onErrorReturn(new Client());
        // Pone a null la lista de órdenes y motos del cliente a borrar
        for (WorkOrder workOrder : client.block().getWorkOrders()) workOrder.setClient(null);
        for (Bike bike : client.block().getBikes()) bike.setClient(null);

        clientRepository.delete(client.block());
        return client;
    }

    @Override
    public Mono<Client> addClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Mono<Client> modifyClient(long id, Client newClient) throws ClientNotFoundException {
        clientRepository.findById(id).onErrorReturn(new Client());
        newClient.setId(String.valueOf(id));

        return clientRepository.save(newClient);
    }
}