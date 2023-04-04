package com.scalefocus.order;

import com.scalefocus.author.Author;
import com.scalefocus.author.AuthorService;
import com.scalefocus.book.Book;
import com.scalefocus.book.BookService;
import com.scalefocus.client.Client;
import com.scalefocus.client.ClientService;
import com.scalefocus.util.ConsoleRangeReader;
import com.scalefocus.util.ConsoleReader;
import com.scalefocus.util.DateFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

public class OrderPresenter {

    private static final ClientService clientService = new ClientService();
    private static final OrderService orderService = new OrderService();
    private static final BookService bookService = new BookService();
    private static final String BOOK_NAME_DELETE = "Enter book name :";
    private static final String BOOK_NOT_FOUND = "Book not found, please try again.";
    private static final String CLIENT_NOT_FOUND = "Client not found, please try again";
    private static final String CLIENT_NAME_INSERT = "Please enter a client's name from the list";
    private static final String BOOK_NAME_INSERT = "Please enter a book's name from the list";
    private static final String ORDER_NOT_FOUND = "Order not found, please try again.";
    private static final String FILTER_ORDERS_OPTIONS = "Choose how to filter the orders :\n" +
            "1. By client \n" +
            "2. By issued date \n" +
            "3. By due date \n";
    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 6;
    private static final int MIN_EDIT_OPTION = 1;
    private static final int MAX_EDIT_OPTION = 5;
    private static final int MIN_EDIT_DATE_OPTION = 1;
    private static final int MAX_EDIT_DATE_OPTION = 3;
    private static final int MIN_FILTER_OPTION = 1;
    private static final int MAX_FILTER_OPTION = 3;
    private static final String ON_BEFORE_AFTER_ISSUED = "Please choose \n" +
            "1.On \n" +
            "2.Before \n" +
            "3.After \n" +
            "the issued date";
    private static final String ON_BEFORE_AFTER_DUE = "Please choose \n" +
            "1.On \n" +
            "2.Before \n" +
            "3.After \n" +
            "the due date";
    private static final String DELETE_ORDER = "Choose which order to delete by client and book" + "\n" +
            "Enter client's name";;
    private static final String OPTIONS = "Choose what to do with the Orders : " +
            "\n ---------------------" +
            "\n  1:Show all orders" +
            "\n  2:Filter orders" +
            "\n  3:Add order" +
            "\n  4:Edit order" +
            "\n  5:Remove order" +
            "\n  6:Back" +
            "\n ---------------------";

    private static final String EDIT_OPTIONS = "Choose what do you want to edit : " +
            "\n 1:Edit the client" +
            "\n 2:Edit the book" +
            "\n 3:Extend the due date";
    private static final String EDIT_ORDER_CHOOSE = "Choose order to edit by client name and book" + "\n" +
            "Enter client's name";


    private static final String EDIT_OPTION_CLIENT_NAME = "Enter the new client's name";
    private static final String EDIT_OPTION_BOOK_NAME = "Enter the new book's name";
    private static final String EDIT_OPTION_EXTEND_DATE = "How do you want to extend the date? :" +
            "\n 1.Extend by days" +
            "\n 2.Extend by months" +
            "\n 3.Extend by years";

    private static final String EXTEND_BY_DAYS = "How many days to extend with?";
    private static final String EXTEND_BY_MONTHS = "How many months to extend with?";
    private static final String EXTEND_BY_YEARS = "How many years to extend with?";

    public void showOrderMenu() {
        while (true) {
            System.out.println(OPTIONS);
            int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
            switch (choice) {
                case 1:
                    printAllOrders();
                    break;
                case 2:
                    filterOrders();
                    break;
                case 3:
                    addOrder();
                    break;
                case 4:
                    editOrder();
                    break;
                case 5:
                    removeOrder();
                    break;
                case 6:
                    return;
            }
        }
    }

    public void printAllOrders() {
        List<Order> orderList = orderService.getAllOrders();
        for (Order order : orderList) {
            System.out.println(order);
        }
    }
    public void filterOrders(){
        System.out.println(FILTER_ORDERS_OPTIONS);
        int choice = ConsoleRangeReader.readInt(MIN_FILTER_OPTION, MAX_FILTER_OPTION);
        switch (choice){
            case 1:
                orderService.showAllClients();
                System.out.println(CLIENT_NAME_INSERT);
                String name = ConsoleReader.readString();
                List<Order> orders = orderService.findAllOrdersByClient(name);
                if (orders.size() == 0){//add exception
                    System.out.println(ORDER_NOT_FOUND);
                    return;
                }
                for (Order order : orders){
                    System.out.println(order);
                }
                break;
            case 2:
                System.out.println(ON_BEFORE_AFTER_ISSUED);
                int choiceIssuedDate = ConsoleRangeReader.readInt(MIN_FILTER_OPTION, MAX_FILTER_OPTION);
                switch (choiceIssuedDate){
                    case 1:
                        LocalDate on = orderService.insertDate();
                        orderService.ordersOnDate(on);
                        break;
                    case 2:
                        LocalDate before = orderService.insertDate();
                        orderService.ordersBeforeDate(before);
                        break;
                    case 3:
                        LocalDate after = orderService.insertDate();
                        orderService.ordersAfterDate(after);
                        break;
                }
                break;
            case 3:
                System.out.println(ON_BEFORE_AFTER_DUE);
                int choiceDueDate = ConsoleRangeReader.readInt(MIN_FILTER_OPTION, MAX_FILTER_OPTION);
                switch (choiceDueDate){
                    case 1:
                        LocalDate on = orderService.insertDate();
                        orderService.ordersOnDate(on);
                        break;
                    case 2:
                        LocalDate before = orderService.insertDate();
                        orderService.ordersBeforeDate(before);
                        break;
                    case 3:
                        LocalDate after = orderService.insertDate();
                        orderService.ordersAfterDate(after);
                        break;
                }
                break;
        }
    }

    public void addOrder() {
        orderService.showAllClients();
        System.out.println(CLIENT_NAME_INSERT);
        String clientName = ConsoleReader.readString();
        Client client = clientService.searchForClient(clientName);//to move in service
        if (client == null){// add exception
            System.out.println(CLIENT_NOT_FOUND);
            return;
        }
        orderService.showAllAuthors();
        System.out.println(BOOK_NAME_INSERT);
        String bookName = ConsoleReader.readString();
        Book book = bookService.findBookByName(bookName);
        if (book == null){
            System.out.println(BOOK_NOT_FOUND);
            return;
        }
        String from = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate start = DateFormatter.formatter(from);
        String to = LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate due = DateFormatter.formatter(to);
        Order toBeAdded = new Order(client, book, start, due);
        orderService.addOrder(toBeAdded);
    }

    public void editOrder() {
        System.out.println(EDIT_ORDER_CHOOSE);
        printAllOrders();// to filter by client
        String nameToSearchFor = ConsoleReader.readString();
        System.out.println(EDIT_OPTION_BOOK_NAME);
        String bookToSearchFor = ConsoleReader.readString();
        Order orderToBeEdited = orderService.findOrderByClient(nameToSearchFor, bookToSearchFor);
        if (orderToBeEdited == null){//add exception
            System.out.println(ORDER_NOT_FOUND);
            return;
        }
        System.out.println(EDIT_OPTIONS);
        int choice = ConsoleRangeReader.readInt(MIN_EDIT_OPTION, MAX_EDIT_OPTION);
        Order editedOrder = null;
        String newField;
        switch (choice) {
            case 1:
                System.out.println(EDIT_OPTION_CLIENT_NAME);
                newField = ConsoleReader.readString();
                editedOrder = new Order(new Client(newField), orderToBeEdited.getBook(),
                        orderToBeEdited.getFromDate(), orderToBeEdited.getDueDate());
                break;
            case 2:
                System.out.println(EDIT_OPTION_BOOK_NAME);
                newField = ConsoleReader.readString();
                editedOrder = new Order(orderToBeEdited.getClient(), new Book(newField),
                        orderToBeEdited.getFromDate(), orderToBeEdited.getDueDate());
                break;
            case 3:
                System.out.println(EDIT_OPTION_EXTEND_DATE);
                int choiceDate = ConsoleRangeReader.readInt(MIN_EDIT_DATE_OPTION, MAX_EDIT_DATE_OPTION);
                int input;
                switch (choiceDate) {
                    case 1:
                        System.out.println(EXTEND_BY_DAYS);
                        input = ConsoleReader.readInt();
                        editedOrder = new Order(orderToBeEdited.getClient(), orderToBeEdited.getBook(),
                                orderToBeEdited.getFromDate(), orderToBeEdited.getDueDate().plusDays(input));
                        break;
                    case 2:
                        System.out.println(EXTEND_BY_MONTHS);
                        input = ConsoleReader.readInt();
                        editedOrder = new Order(orderToBeEdited.getClient(), orderToBeEdited.getBook(),
                                orderToBeEdited.getFromDate(), orderToBeEdited.getDueDate().plusMonths(input));
                        break;
                    case 3:
                        System.out.println(EXTEND_BY_YEARS);
                        input = ConsoleReader.readInt();
                        editedOrder = new Order(orderToBeEdited.getClient(), orderToBeEdited.getBook(),
                                orderToBeEdited.getFromDate(), orderToBeEdited.getDueDate().plusYears(input));
                        break;
                }
        }
        orderService.deleteOrder(orderToBeEdited);
        orderService.addOrder(editedOrder);
    }

    public void removeOrder() {
        printAllOrders();
        System.out.println(DELETE_ORDER);
        String nameToSearchFor = ConsoleReader.readString();
        System.out.println(BOOK_NAME_DELETE);
        String bookToSearchFor = ConsoleReader.readString();
        Order orderToBeDeleted = orderService.findOrderByClient(nameToSearchFor, bookToSearchFor);
        if (orderToBeDeleted == null){// add exception
            System.out.println(ORDER_NOT_FOUND);
            return;
        }
        orderService.deleteOrder(orderToBeDeleted);
    }

}