package com.scalefocus.order;

import com.scalefocus.author.Author;
import com.scalefocus.book.Book;
import com.scalefocus.book.BookAccessor;
import com.scalefocus.client.Client;
import com.scalefocus.client.ClientAccessor;
import com.scalefocus.util.DateFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    private static final ClientAccessor clientAccessor = new ClientAccessor();
    private static final BookAccessor bookAccessor = new BookAccessor();

    public List<Order> mapResultSetToOrder(ResultSet orderResultSet){
        List<Order> orderList = new ArrayList<>();
        try (orderResultSet){
            while (orderResultSet.next()){
                Client client = clientAccessor.getClientByID(orderResultSet.getInt(2));
                Book book = bookAccessor.getBookByID(orderResultSet.getInt(3));
                LocalDate fromDate = orderResultSet.getDate(4).toLocalDate();
                LocalDate dueDate = orderResultSet.getDate(5).toLocalDate();
                Order order = new Order(client, book, fromDate, dueDate);
                orderList.add(order);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return orderList;
    }

    Order mapStringToOrder(String orderString) {
        String[] tokens = orderString.split("_");
        Client client = new Client(tokens[0]);
        Book book = new Book(tokens[1]);
        LocalDate from = DateFormatter.formatter(tokens[2]);
        LocalDate to = DateFormatter.formatter(tokens[3]);
        return new Order(client, book, from, to);
    }

    String mapOrderToString(Order order) {
        String client = order.getClient().getName();
        String book = order.getBook().getName();
        String from = order.getFromDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String to = order.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return String.join("_", client, book, from, to + "\n");
    }
}