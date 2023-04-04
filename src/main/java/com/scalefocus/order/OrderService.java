package com.scalefocus.order;

import com.scalefocus.author.Author;
import com.scalefocus.author.AuthorService;
import com.scalefocus.book.Book;
import com.scalefocus.book.BookService;
import com.scalefocus.client.Client;
import com.scalefocus.client.ClientService;
import com.scalefocus.util.ConsoleReader;
import com.scalefocus.util.DateFormatter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private static final OrderAccessor orderAccessor = new OrderAccessor();
    private static final OrderMapper orderMapper = new OrderMapper();
    private static final ClientService clientService = new ClientService();
    private static final BookService bookService = new BookService();

    public List<Order> getAllOrders() {
        List<String> orderStrings = orderAccessor.readAllOrders();
        List<Order> orders = new ArrayList<>();
        for (String orderString : orderStrings) {
            Order order = orderMapper.mapStringToOrder(orderString);
            orders.add(order);
        }
        return orders;
    }

    public void addOrder(Order order) {
        String orderToString = orderMapper.mapOrderToString(order);
        orderAccessor.addOrder(orderToString);
    }

    public Order findOrderByClient(String nameToLookFor, String bookToLookFor) {
        List<Order> orders = getAllOrders();
        boolean found = false;
        for (Order order : orders) {
            if (nameToLookFor.equalsIgnoreCase(order.getClient().getName()) && bookToLookFor.equalsIgnoreCase(order.getBook().getName())) {
                found = true;
                return order;
            }
        }
        return null;
    }

    public List<Order> findAllOrdersByClient(String nameToLookFor){
        List<Order> orders = getAllOrders();
        List<Order> foundOrders = new ArrayList<>();
        for (Order order : orders) {
            if (nameToLookFor.equalsIgnoreCase(order.getClient().getName())) {
                foundOrders.add(order);
            }
        }
        return foundOrders;
    }

    public void deleteOrder(Order orderToBeDeleted) { //to remove the order from the client
        String toDelete = orderMapper.mapOrderToString(orderToBeDeleted).trim();
        List<String> orderStrings = orderAccessor.readAllOrders();
        if (orderStrings.contains(toDelete)) {
            orderStrings.remove(toDelete);
        }
        String newString = new String();
        for (String string : orderStrings) {
            newString = newString.concat(string);
            newString = newString.concat("\n");
        }
        orderAccessor.deleteOrder(newString);
    }

    public void showAllClients(){
        List<Client> clients = clientService.getAllClients();
        for (Client client : clients){
            System.out.println(client);
        }
    }
    public void showAllAuthors(){
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

    public LocalDate insertDate(){
        System.out.println("Enter day");
        int day = ConsoleReader.readInt();
        String dayString = bookService.validateDay(day);
        if (dayString == "") {
            System.out.println("Invalid day");
        }
        System.out.println("Enter month");
        int month = ConsoleReader.readInt();
        String monthString = bookService.validateMonth(month);
        if (monthString == "") {
            System.out.println("Invalid month");
        }
        System.out.println("Enter year");
        String year = ConsoleReader.readString();
        String date = String.join("/", dayString, monthString, year);
        return DateFormatter.formatter(date);
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
            System.out.println("No orders on this date");
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
            System.out.println("No orders before this date");
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
            System.out.println("No orders after this date");
        }
    }
}
