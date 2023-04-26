package com.scalefocus.client;

import com.scalefocus.util.ConsoleReader;
import org.springframework.stereotype.Component;
import com.scalefocus.exception.InvalidClientException;
import com.scalefocus.util.ConsoleRangeReader;

import java.util.List;

import static com.scalefocusOld.constants.GlobalConstants.*;

@Component
public class ClientPresenter {
    private final ClientService clientService;

    public ClientPresenter(ClientService clientService) {
        this.clientService = clientService;
    }

    public void showClientMenu() {
        while (true) {
            System.out.println(CP_CLIENT_MENU_OPTIONS);
            int input = ConsoleRangeReader.readInt(CP_MIN_MENU_OPTION, CP_MAX_MENU_OPTION);
            switch (input) {
                case 1:
                    printAllClients();
                    break;
                case 2:
                    addClient();
                    break;
                case 3:
                    editClient();
                    break;
                case 4:
                    removeClient();
                    break;
                case 5:
                    return;
            }
        }
    }

    public void printAllClients(){
        List<Client> clients = clientService.getAllClients();
        for (Client client : clients){
            System.out.println(client);
        }
    }

    public void addClient(){
        System.out.println(CP_CLIENT_NAME_INSERT);
        String name = ConsoleReader.readString();
        Client client = new Client(name);
        clientService.addClient(client);
    }

    public void editClient(){
        printAllClients();
        System.out.println(CP_CLIENT_NAME_INSERT);
        String nameToSearchFor = ConsoleReader.readString();
        Client clientToBeEdited = null;
        try {
            clientToBeEdited = clientService.searchForClient(nameToSearchFor);
        } catch (InvalidClientException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(CP_EDIT_NAME);
        String newName = ConsoleReader.readString();
        clientService.editClient(clientToBeEdited.getName(), newName);
    }

    public void removeClient(){
        System.out.println(CP_DELETE_CLIENT);
        printAllClients();
        String nameToSearchFor = ConsoleReader.readString();
        Client clientToBeDeleted = null;
        try {
            clientToBeDeleted = clientService.searchForClient(nameToSearchFor);
        } catch (InvalidClientException e) {
            System.out.println(e.getMessage());
            return;
        }
        clientService.removeClient(clientToBeDeleted);
    }
}