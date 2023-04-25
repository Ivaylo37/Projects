package com.scalefocus.client;

import com.scalefocus.exception.ClientNotDeletableException;
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
        List<String> clientStrings = clientAccessor.takeAllClients();
        List<Client> clients = new ArrayList<>();
        for (String string : clientStrings){
            Client client = clientMapper.mapStringToClient(string);
            clients.add(client);
        }
        return clients;
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
        if (clientToBeDeleted.getOrders().size() != 0){
            try {
                throw new ClientNotDeletableException("This client has order/s");
            } catch (ClientNotDeletableException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        String toDelete = clientMapper.mapClientToString(clientToBeDeleted).trim();
        List<String> clientStrings = clientAccessor.takeAllClients();
        if (clientStrings.contains(toDelete)){
            clientStrings.remove(toDelete);
        }
        String newString = new String();
        for (String string : clientStrings){
            newString = newString.concat(string);
            newString = newString.concat("\n");
        }
        clientAccessor.deleteClient(newString);
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
}