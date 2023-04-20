package com.scalefocus.order;

import com.scalefocus.book.Book;
import com.scalefocus.client.Client;
import com.scalefocus.client.ClientService;
import com.scalefocus.util.db.DBConnector;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.util.List;

@Component
public class OrderAccessor {

    private final OrderMapper orderMapper;
    private final ClientService clientService;

    public OrderAccessor(OrderMapper orderMapper, ClientService clientService) {
        this.orderMapper = orderMapper;
        this.clientService = clientService;
    }
    public List<Order> getAllOrders() {
        ResultSet resultSet;
        List<Order> orders;
        String sql = "SELECT * FROM library_management.orders";
        Connection connection = DBConnector.getInstance().getConnection();
        try (Statement statement = connection.createStatement();){
            resultSet = statement.executeQuery(sql);
            orders = orderMapper.mapResultSetToOrder(resultSet);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return orders;
    }

    public void addOrder(Order order) {
        Client client = order.getClient();
        Book book = order.getBook();
        String sql = "INSERT INTO library_management.orders(order_client, order_book, from_date, due_date) VALUES(?, ?, ?, ?);";
        Connection connection = DBConnector.getInstance().getConnection();
        try (PreparedStatement preparedStatement =
                connection.prepareStatement(sql)){
            preparedStatement.setInt(1, clientService.getClientID(client));
            preparedStatement.setInt(2, getIDbyBook(book));
            preparedStatement.setDate(3, Date.valueOf(order.getFromDate()));
            preparedStatement.setDate(4, Date.valueOf(order.getDueDate()));
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteOrder(int client, int book) {
        String sql = "DELETE FROM library_management.orders WHERE order_client = ? AND order_book = ?";
        Connection connection = DBConnector.getInstance().getConnection();
        try ( PreparedStatement preparedStatement =
                connection.prepareStatement(sql)){
            preparedStatement.setInt(1, client);
            preparedStatement.setInt(2, book);
            preparedStatement.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public int getIDbyBook(Book book){
        int id;
        ResultSet resultSet;
        String sql = "SELECT book_id FROM library_management.books WHERE book_name = ?";
        Connection connection = DBConnector.getInstance().getConnection();
        try (PreparedStatement preparedStatement =
                connection.prepareStatement(sql)){
            preparedStatement.setString(1, book.getName());
            resultSet = preparedStatement.executeQuery();
            id = orderMapper.mapResultSetToInt(resultSet);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return id;
    }
}
