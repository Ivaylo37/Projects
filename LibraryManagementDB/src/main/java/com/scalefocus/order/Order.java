package com.scalefocus.order;

import com.scalefocus.book.Book;
import com.scalefocus.client.Client;
import java.time.LocalDate;

public class Order {

    private Client client;
    private Book book;
    private LocalDate fromDate;
    private LocalDate dueDate;

    public Order(Client client, Book book, LocalDate fromDate, LocalDate dueDate) {
        this.client = client;
        this.book = book;
        this.fromDate = fromDate;
        this.dueDate = dueDate;
    }

    public Client getClient() {
        return client;
    }

    public Book getBook() {
        return book;
    }
    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "client=" + client.getName() +
                ", book=" + book.getName() +
                ", fromDate=" + fromDate.toString() +
                ", dueDate=" + dueDate.toString() +
                '}';
    }
}
