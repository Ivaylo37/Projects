package com.scalefocus.client;

import com.scalefocus.exception.InvalidClientException;
import com.scalefocus.util.ConsoleRangeReader;
import com.scalefocus.util.ConsoleReader;

import java.util.List;

public class ClientPresenter {

    private static final ClientService clientService = new ClientService();
    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 5;
    private static final String CLIENT_NAME_INSERT = "Please insert the client's name : ";
    private static final String EDIT_NAME = "Please enter the new name : ";
    private static final String DELETE_CLIENT = "Please enter a client to delete : ";
    private static final String CLIENT_MENU_OPTIONS = "Choose what to do with the clients : " +
            "\n ---------------------" +
            "\n 1:Show all clients" +
            "\n 2:Add a client" +
            "\n 3:Edit client" +
            "\n 4:Remove client" +
            "\n 5:Back" +
            "\n ---------------------";

    public void showClientMenu() {
        while (true) {
            System.out.println(CLIENT_MENU_OPTIONS);
            int input = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
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
        System.out.println(CLIENT_NAME_INSERT);
        String name = ConsoleReader.readString();
        Client client = new Client(name);
        clientService.addClient(client);
    }

    public void editClient(){
        printAllClients();
        System.out.println(CLIENT_NAME_INSERT);
        String nameToSearchFor = ConsoleReader.readString();
        Client clientToBeEdited = null;
        try {
            clientToBeEdited = clientService.searchForClient(nameToSearchFor);
        } catch (InvalidClientException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(EDIT_NAME);
        String newName = ConsoleReader.readString();
        Client newClient = new Client(newName);
        clientService.removeClient(clientToBeEdited);
        clientService.addClient(newClient);
    }

    public void removeClient(){
        System.out.println(DELETE_CLIENT);
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