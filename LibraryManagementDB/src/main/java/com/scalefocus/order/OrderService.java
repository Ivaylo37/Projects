package com.scalefocus.order;

import com.scalefocus.book.Book;
import com.scalefocus.book.BookService;
import com.scalefocus.client.Client;
import com.scalefocus.client.ClientService;
import com.scalefocus.exception.InvalidClientException;
import com.scalefocus.exception.InvalidDateException;
import com.scalefocus.exception.InvalidOrderException;
import com.scalefocus.exception.NoOrdersFoundException;
import com.scalefocus.util.ConsoleRangeReader;
import com.scalefocus.util.ConsoleReader;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.scalefocus.constants.GlobalConstants.*;
import static com.scalefocus.constants.GlobalConstants.OP_MAX_FILTER_OPTION;

@Service
public class OrderService {
    private final OrderAccessor orderAccessor;
    private final ClientService clientService;
    private final BookService bookService;

    public OrderService(OrderAccessor orderAccessor, ClientService clientService, BookService bookService) {
        this.orderAccessor = orderAccessor;
        this.clientService = clientService;
        this.bookService = bookService;
    }

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

    public void filterOrdersByClient(){
        showAllClients();
        System.out.println(OP_CLIENT_NAME_INSERT);
        String name = ConsoleReader.readString();
        List<Order> orders = null;
        try {
            orders = findAllOrdersByClient(name);
        } catch (NoOrdersFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
        for (Order order : orders){
            System.out.println(order);
        }
    }

    public void filterOrdersByIssuedDate(){
        System.out.println(OP_ON_BEFORE_AFTER_ISSUED);
        int choiceIssuedDate = ConsoleRangeReader.readInt(OP_MIN_FILTER_OPTION, OP_MAX_FILTER_OPTION);
        switch (choiceIssuedDate){
            case 1:
                LocalDate on = null;
                try {
                    on = insertDate();
                }
                catch (InvalidDateException e){
                    System.out.println(e.getMessage());
                    return;
                }
                ordersOnDate(on);
                break;
            case 2:
                LocalDate before = null;
                try {
                    before = insertDate();
                }
                catch (InvalidDateException e){
                    System.out.println(e.getMessage());
                    return;
                }
                ordersBeforeDate(before);
                break;
            case 3:
                LocalDate after = null;
                try {
                    after = insertDate();
                }
                catch (InvalidDateException e){
                    System.out.println(e.getMessage());
                    return;
                }
                ordersAfterDate(after);
                break;
        }
    }

    public void filterOrdersByDueDate(){
        System.out.println(OP_ON_BEFORE_AFTER_DUE);
        int choiceDueDate = ConsoleRangeReader.readInt(OP_MIN_FILTER_OPTION, OP_MAX_FILTER_OPTION);
        switch (choiceDueDate){
            case 1:
                LocalDate on = null;
                try {
                    on = insertDate();
                }
                catch (InvalidDateException e){
                    System.out.println(e.getMessage());
                    return;
                }
                ordersOnDate(on);
                break;
            case 2:
                LocalDate before = null;
                try {
                    before = insertDate();
                }
                catch (InvalidDateException e){
                    System.out.println(e.getMessage());
                    return;
                }
                ordersBeforeDate(before);
                break;
            case 3:
                LocalDate after = null;
                try {
                    after = insertDate();
                }
                catch (InvalidDateException e){
                    System.out.println(e.getMessage());
                    return;
                }
                ordersAfterDate(after);
                break;
        }
    }
}