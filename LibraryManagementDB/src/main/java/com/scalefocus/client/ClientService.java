package com.scalefocus.client;

import com.scalefocus.author.Author;
import com.scalefocus.exception.InvalidClientException;
import com.scalefocus.exception.NoOrdersFoundException;
import com.scalefocus.order.Order;
import com.scalefocus.order.OrderService;

import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private static final ClientAccessor clientAccessor = new ClientAccessor();
    private static final ClientMapper clientMapper = new ClientMapper();
    private static final OrderService orderService = new OrderService();

    public List<Client> getAllClients(){
        return clientAccessor.getAllClients();
    }

    public void addClient(Client client){
        String clientToString = clientMapper.mapClientToString(client);
        clientAccessor.addClient(clientToString);
    }

    public Client searchForClient(String nameToSearchFor) throws InvalidClientException{
        List<Client> clients = getAllClients();
        Client foundCLient = null;
        for(Client client : clients){
            if (nameToSearchFor.equalsIgnoreCase(client.getName())){
                foundCLient = client;
                break;
            }
        }
        if (foundCLient == null){
            throw new InvalidClientException("Client not found");
        }
        return foundCLient;
    }

    public void removeClient(Client clientToBeDeleted){
        List<Client> clients = getAllClients();
        Client foundClient = null;
        for (Client client : clients) {
            if (clientToBeDeleted.getName().equalsIgnoreCase(client.getName())) {
                foundClient = client;
                break;
            }
        }
        if (foundClient == null){
            try {
                throw new InvalidClientException("Client not found");
            } catch (InvalidClientException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        clientAccessor.deleteClient(clientToBeDeleted.getName());
    }

    public List<Order> findOrders(String name){
        List foundOrders = new ArrayList<>();
        try {
            foundOrders = orderService.findAllOrdersByClient(name);
        } catch (NoOrdersFoundException e) {
            // no message because of the mapper
        }
        return foundOrders;
    }

    public void editClient(String oldName, String newName){
        clientAccessor.editClient(oldName, newName);
    }

    public int getClientID(Client client){
        return clientAccessor.getIDbyClient(client);
    }
}