package com.scalefocus.order;

import com.scalefocus.book.Book;
import com.scalefocus.book.BookService;
import com.scalefocus.client.Client;
import com.scalefocus.client.ClientService;
import com.scalefocus.exception.InvalidClientException;
import com.scalefocus.exception.InvalidDateException;
import com.scalefocus.exception.InvalidOrderException;
import com.scalefocus.exception.NoOrdersFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private static final OrderAccessor orderAccessor = new OrderAccessor();
    private static final OrderMapper orderMapper = new OrderMapper();
    private static final ClientService clientService = new ClientService();
    private static final BookService bookService = new BookService();

    public List<Order> getAllOrders() {
        return orderAccessor.getAllOrders();
    }

    public void addOrder(Order order) {
        orderAccessor.addOrder(order);
    }

    public Order findOrderByClientAndBook(String nameToLookFor, String bookToLookFor) throws InvalidOrderException{
        List<Order> orders = getAllOrders();
        Order foundOrder = null;
        for (Order order : orders) {
            if (nameToLookFor.equalsIgnoreCase(order.getClient().getName())
                    && bookToLookFor.equalsIgnoreCase(order.getBook().getName())) {
                foundOrder = order;
            }
        }
        if (foundOrder == null){
            throw new InvalidOrderException("Order not found");
        }
        return foundOrder;
    }

    public List<Order> findAllOrdersByClient(String nameToLookFor) throws NoOrdersFoundException{
        List<Order> orders = getAllOrders();
        List<Order> foundOrders = new ArrayList<>();
        for (Order order : orders) {
            if (nameToLookFor.equalsIgnoreCase(order.getClient().getName())) {
                foundOrders.add(order);
            }
        }
        if (foundOrders.size() == 0){
            throw new NoOrdersFoundException("No orders found for this client");
        }
        return foundOrders;
    }

    public Client findExistingClient(String name) throws InvalidClientException{
        Client client = null;
        try {
            client = clientService.searchForClient(name);
        } catch (InvalidClientException e) {
            throw new InvalidClientException("Client not found.");
        }
        return client;
    }

    public void deleteOrder(Order orderToBeDeleted) { //to remove the order from the client
        int client = clientService.getClientID(orderToBeDeleted.getClient());
        int book = bookService.getBookID(orderToBeDeleted.getBook());
        orderAccessor.deleteOrder(client, book);
    }

    public void showAllClients(){
        List<Client> clients = clientService.getAllClients();
        for (Client client : clients){
            System.out.println(client);
        }
    }
    public void showAllBooks(){
        List<Book> books = bookService.getAllBooks();
        for (Book book : books){
            System.out.println(book);
        }
    }
    public List<Order> findOrdersByClient(String name){
        List<Order> matchedOrders = new ArrayList<>();
        List<Order> allOrders = getAllOrders();
        for (Order order : allOrders){
            if (order.getClient().getName().equalsIgnoreCase(name)){
                matchedOrders.add(order);
            }
        }
        return matchedOrders;
    }

    public LocalDate insertDate() throws InvalidDateException{
        LocalDate date = null;
        try {
            date = bookService.insertDate();
        }
        catch (InvalidDateException e){
            throw new InvalidDateException("Invalid date");
        }
        return date;
    }
    public void ordersOnDate(LocalDate date){
        List<Order> allOrders = getAllOrders();
        boolean found = false;
        for (Order order : allOrders){
            if (order.getFromDate().isEqual(date)){
                System.out.println(order);
                found = true;
            }
        }
        if (!found){
            try {
                throw new NoOrdersFoundException("No orders found on this date");
            } catch (NoOrdersFoundException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    public void ordersBeforeDate(LocalDate date){
        List<Order> allOrders = getAllOrders();
        boolean found = false;
        for (Order order : allOrders){
            if (order.getFromDate().isBefore(date)){
                System.out.println(order);
                found = true;
            }
        }
        if (!found){
            try {
                throw new NoOrdersFoundException("No orders found before this date");
            } catch (NoOrdersFoundException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    public void ordersAfterDate(LocalDate date){
        List<Order> allOrders = getAllOrders();
        boolean found = false;
        for (Order order : allOrders){
            if (order.getFromDate().isAfter(date)){
                System.out.println(order);
                found = true;
            }
        }
        if (!found){
            try {
                throw new NoOrdersFoundException("No orders found after this date");
            } catch (NoOrdersFoundException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }
}
