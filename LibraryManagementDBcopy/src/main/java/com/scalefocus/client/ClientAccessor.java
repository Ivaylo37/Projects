package com.scalefocus.client;

import com.scalefocus.util.db.DBConnector;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class ClientAccessor {
    private final ClientMapper clientMapper;

    public ClientAccessor(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    public List<Client> getAllClients() {
        ResultSet resultSet;
        List<Client> clients;
        Connection connection = DBConnector.getInstance().getConnection();
        try (Statement statement = connection.createStatement();){
            resultSet = statement.executeQuery("SELECT * FROM library_management.clients");
            clients = clientMapper.mapResultSetToClients(resultSet);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return clients;
    }

    public void addClient(String clientName) {
        String sql = "";
        Connection connection = DBConnector.getInstance().getConnection();
        try (PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO library_management.clients(client_name) VALUES(?);")){
            preparedStatement.setString(1, clientName);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteClient(String toBeDeleted) {
        String sql = "DELETE FROM library_management.clients WHERE client_name = ?";
        Connection connection = DBConnector.getInstance().getConnection();
        try (PreparedStatement preparedStatement =
                connection.prepareStatement(sql)){
            preparedStatement.setString(1, toBeDeleted);
            preparedStatement.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void editClient(String toBeEdited, String newName) {
        String sql = "UPDATE library_management.clients SET client_name = ? WHERE client_name = ?";
        Connection connection = DBConnector.getInstance().getConnection();
        try (PreparedStatement preparedStatement =
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
        Connection connection = DBConnector.getInstance().getConnection();
        try (PreparedStatement preparedStatement =
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
        Connection connection = DBConnector.getInstance().getConnection();
        try (PreparedStatement preparedStatement =
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