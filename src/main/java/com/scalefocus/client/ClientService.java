package com.scalefocus.client;

import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private static final ClientAccessor clientAccessor = new ClientAccessor();
    private static final ClientMapper clientMapper = new ClientMapper();

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

    public Client searchForClient(String nameToSearchFor){
        List<Client> clients = getAllClients();
        boolean found = false;
        for(Client client : clients){
            if (nameToSearchFor.equalsIgnoreCase(client.getName())){
                found = true;
                return client;
            }
        }
        return null;
    }

    public void removeClient(Client clientToBeDeleted){
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

}