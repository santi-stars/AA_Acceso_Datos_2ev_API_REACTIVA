package com.svalero.gestitaller2.service;

import com.svalero.gestitaller2.domain.Bike;
import com.svalero.gestitaller2.domain.Client;
import com.svalero.gestitaller2.domain.WorkOrder;
import com.svalero.gestitaller2.exception.ClientNotFoundException;
import com.svalero.gestitaller2.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public List<Client> findAllClients(String name, String surname, String dni) {
        return clientRepository.findByNameContainingOrSurnameContainingOrDniContaining(name, surname, dni);
    }

    @Override
    public Client findById(long id) throws ClientNotFoundException {
        return clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
    }

    @Override
    public Client deleteClient(long id) throws ClientNotFoundException {
        Client client = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        // Pone a null la lista de órdenes y motos del cliente a borrar
        for (WorkOrder workOrder : client.getWorkOrders()) workOrder.setClient(null);
        for (Bike bike : client.getBikes()) bike.setClient(null);

        clientRepository.delete(client);
        return client;
    }

    @Override
    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client modifyClient(long id, Client newClient) throws ClientNotFoundException {
        clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        newClient.setId(id);
        clientRepository.save(newClient);
        return newClient;
    }
}