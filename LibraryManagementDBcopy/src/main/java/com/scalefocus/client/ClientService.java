package com.scalefocus.client;

import org.springframework.stereotype.Service;
import com.scalefocus.exception.InvalidClientException;

import java.util.List;

@Service
public class ClientService {

    private final ClientAccessor clientAccessor;

    public ClientService(ClientAccessor clientAccessor) {
        this.clientAccessor = clientAccessor;
    }

    public List<Client> getAllClients(){
        return clientAccessor.getAllClients();
    }

    public void addClient(Client client){
        clientAccessor.addClient(client.getName());
    }

    public Client searchForClient(String nameToSearchFor) throws InvalidClientException {
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

    public void editClient(String oldName, String newName){
        clientAccessor.editClient(oldName, newName);
    }

    public int getClientID(Client client){
        return clientAccessor.getIDbyClient(client);
    }
}