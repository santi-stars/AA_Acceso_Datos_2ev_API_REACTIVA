package com.svalero.gestitaller2.service;

import com.svalero.gestitaller2.domain.Client;
import com.svalero.gestitaller2.exception.ClientNotFoundException;

import java.util.List;

public interface ClientService {

    List<Client> findAllClients();

    List<Client> findAllClients(String name, String surname, String dni);

    Client findById(long id) throws ClientNotFoundException;

    Client addClient(Client client);

    Client deleteClient(long id) throws ClientNotFoundException;

    Client modifyClient(long id, Client client) throws ClientNotFoundException;

}
