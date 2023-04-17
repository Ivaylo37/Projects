package com.scalefocus.client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientMapper {

    private static final ClientService clientService = new ClientService();

    Client mapStringToClient(String clientString) {
        Client client = new Client(clientString);
        client.setOrders(clientService.findOrders(client.getName()));
        return client;
    }

    public List<Client> mapResultSetToClients(ResultSet clientsResultSet){
        List<Client> clientList = new ArrayList<>();
        try (clientsResultSet){
            while (clientsResultSet.next()){
                String name = clientsResultSet.getString(2);
                Client client = new Client(name);
                clientList.add(client);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return clientList;
    }

    String mapClientToString(Client client) {
        return client.getName();
    }

    public int mapResultSetToInt(ResultSet resultSet){
        int id = 0;
        try (resultSet){
            while (resultSet.next()){
                id = resultSet.getInt(1);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return id;
    }


}