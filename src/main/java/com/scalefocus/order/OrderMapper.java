package com.scalefocus.order;

import com.scalefocus.book.Book;
import com.scalefocus.client.Client;
import com.scalefocus.util.DateFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderMapper {

    Order mapStringToOrder(String orderString){
        String[] tokens = orderString.split("_");
        Client client = new Client(tokens[0]);
        Book book = new Book(tokens[1]);
        LocalDate from = DateFormatter.formatter(tokens[2]);
        LocalDate to = DateFormatter.formatter(tokens[3]);
        return new Order(client, book, from, to);
    }

    String mapOrderToString(Order order){
        String client = order.getClient().getName();
        String book = order.getBook().getName();
        String from = order.getFromDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String to = order.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return String.join("_", client, book, from, to + "\n");
    }
}