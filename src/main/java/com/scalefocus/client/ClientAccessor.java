package com.scalefocus.client;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class ClientAccessor {

    private static final String CLIENT_FILE_PATH = "src/Clients.txt";
    private static final String FILE_NOT_FOUND_MESSAGE = "File was not found";

    private static final BufferedReader bufferedReader;
    private static final BufferedWriter bufferedWriter;

    static {
        try {
            bufferedReader = new BufferedReader(new FileReader(CLIENT_FILE_PATH));
            bufferedWriter = new BufferedWriter(new FileWriter(CLIENT_FILE_PATH, true));
        } catch (IOException e) {
            throw new RuntimeException("File not found with path : " + CLIENT_FILE_PATH, e);
        }
    }

    public List<String> takeAllClients() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(CLIENT_FILE_PATH));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return reader.lines().collect(Collectors.toList());
    }

    public void addClient(String string) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENT_FILE_PATH, true))) {
            writer.append(string);
        } catch (IOException e) {
            throw new RuntimeException(FILE_NOT_FOUND_MESSAGE, e);
        }
    }

    public void deleteClient(String fileWithoutTheDeleted) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENT_FILE_PATH, false))) {
            writer.write(fileWithoutTheDeleted);
        } catch (IOException e) {
            throw new RuntimeException(FILE_NOT_FOUND_MESSAGE, e);
        }
    }
}
