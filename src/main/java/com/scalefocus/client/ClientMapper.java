package com.scalefocus.client;

public class ClientMapper {

    Client mapStringToClient(String clientString) {
        return new Client(clientString);
    }

    String mapClientToString(Client client) {
        return client.getName();
    }
}
