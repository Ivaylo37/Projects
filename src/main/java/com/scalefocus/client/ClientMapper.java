package com.scalefocus.client;

public class ClientMapper {

    private static final ClientService clientService = new ClientService();

    Client mapStringToClient(String clientString) {
        Client client = new Client(clientString);
        client.setOrders(clientService.findOrders(client.getName()));
        return client;
    }

    String mapClientToString(Client client) {
        return client.getName() + "\n";
    }
}