package com.scalefocus.client;

import com.scalefocus.author.Author;
import com.scalefocus.db.JdbcDriver;

import java.io.*;
import java.sql.*;
import java.util.List;

public class ClientAccessor {
    private static final ClientMapper clientMapper = new ClientMapper();

    public List<Client> getAllClients() {
        ResultSet resultSet;
        List<Client> clients;
        try (Connection connection = JdbcDriver.getConnection(); Statement statement = connection.createStatement();){
            resultSet = statement.executeQuery("SELECT * FROM library_management.clients");
            clients = clientMapper.mapResultSetToClients(resultSet);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return clients;
        /*BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(CLIENT_FILE_PATH));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return reader.lines().collect(Collectors.toList());*/
    }

    public void addClient(String clientName) {
        String sql = "";
        try (Connection connection = JdbcDriver.getConnection(); PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO library_management.clients(client_name) VALUES(?);")){
            preparedStatement.setString(1, clientName);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        /*try (BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENT_FILE_PATH, true))) {
            writer.append(string);
        } catch (IOException e) {
            throw new RuntimeException(FILE_NOT_FOUND_MESSAGE, e);
        }*/
    }

    public void deleteClient(String toBeDeleted) {
        String sql = "DELETE FROM library_management.clients WHERE client_name = ?";
        try (Connection connection = JdbcDriver.getConnection(); PreparedStatement preparedStatement =
                connection.prepareStatement(sql)){
            preparedStatement.setString(1, toBeDeleted);
            preparedStatement.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        /*try (BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENT_FILE_PATH, false))) {
            writer.write(fileWithoutTheDeleted);
        } catch (IOException e) {
            throw new RuntimeException(FILE_NOT_FOUND_MESSAGE, e);
        }*/
    }

    public void editClient(String toBeEdited, String newName) {
        String sql = "UPDATE library_management.clients SET client_name = ? WHERE client_name = ?";
        try (Connection connection = JdbcDriver.getConnection(); PreparedStatement preparedStatement =
                connection.prepareStatement(sql)){
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, toBeEdited);
            preparedStatement.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Client getClientByID(int id){
        ResultSet resultSet;
        Client client;
        String sql = "SELECT * FROM library_management.clients WHERE client_id = ?";
        try (Connection connection = JdbcDriver.getConnection(); PreparedStatement preparedStatement =
                connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            List<Client> clients = clientMapper.mapResultSetToClients(resultSet);
            client = clients.get(0);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return client;
    }

    public int getIDbyClient(Client client){
        int id;
        ResultSet resultSet;
        String sql = "SELECT client_id FROM library_management.clients WHERE client_name = ?";
        try (Connection connection = JdbcDriver.getConnection(); PreparedStatement preparedStatement =
                connection.prepareStatement(sql)){
            preparedStatement.setString(1, client.getName());
            resultSet = preparedStatement.executeQuery();
            id = clientMapper.mapResultSetToInt(resultSet);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return id;
    }
}