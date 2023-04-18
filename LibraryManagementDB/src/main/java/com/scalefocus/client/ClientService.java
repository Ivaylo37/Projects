package com.scalefocus.client;

import com.scalefocus.exception.InvalidClientException;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ClientService {

    private final ClientAccessor clientAccessor;
    private final ClientMapper clientMapper;

    public ClientService(ClientAccessor clientAccessor, ClientMapper clientMapper) {
        this.clientAccessor = clientAccessor;
        this.clientMapper = clientMapper;
    }

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

    public void editClient(String oldName, String newName){
        clientAccessor.editClient(oldName, newName);
    }

    public int getClientID(Client client){
        return clientAccessor.getIDbyClient(client);
    }
}