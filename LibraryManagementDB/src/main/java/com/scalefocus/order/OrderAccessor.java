package com.scalefocus.order;

import com.scalefocus.author.Author;
import com.scalefocus.book.Book;
import com.scalefocus.book.BookService;
import com.scalefocus.client.Client;
import com.scalefocus.client.ClientAccessor;
import com.scalefocus.client.ClientService;
import com.scalefocus.db.JdbcDriver;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class OrderAccessor {

    private static final OrderMapper orderMapper = new OrderMapper();
    private static final ClientService clientService = new ClientService();
    private static final BookService bookService = new BookService();


    public List<Order> getAllOrders() {
        ResultSet resultSet;
        List<Order> orders;
        try (Connection connection = JdbcDriver.getConnection(); Statement statement = connection.createStatement();){
            resultSet = statement.executeQuery("SELECT * FROM library_management.orders");
            orders = orderMapper.mapResultSetToOrder(resultSet);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return orders;
    }

    public void addOrder(Order order) {
        Client client = order.getClient();
        Book book = order.getBook();
        try (Connection connection = JdbcDriver.getConnection(); PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO library_management.orders(order_client, order_book, from_date, due_date) VALUES(?, ?, ?, ?);")){
            preparedStatement.setInt(1, clientService.getClientID(client));
            preparedStatement.setInt(2, bookService.getBookID(book));
            preparedStatement.setDate(3, Date.valueOf(order.getFromDate()));
            preparedStatement.setDate(4, Date.valueOf(order.getDueDate()));
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteOrder(int client, int book) {
        String sql = "DELETE FROM library_management.orders WHERE order_client = ? AND order_book = ?";
        try (Connection connection = JdbcDriver.getConnection(); PreparedStatement preparedStatement =
                connection.prepareStatement(sql)){
            preparedStatement.setInt(1, client);
            preparedStatement.setInt(2, book);
            preparedStatement.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
