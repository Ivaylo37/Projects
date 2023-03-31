package com.scalefocus.order;

import com.scalefocus.author.Author;
import com.scalefocus.book.Book;
import com.scalefocus.client.Client;
import com.scalefocus.util.ConsoleRangeReader;
import com.scalefocus.util.ConsoleReader;
import com.scalefocus.util.DateFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.PrimitiveIterator;

public class OrderPresenter {

    private static final OrderService orderService = new OrderService();

    private static final String CLIENT_NAME_INSERT = "Please enter a client's name";
    private static final String BOOK_NAME_INSERT = "Please enter a book's name";
    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 5;
    private static final int MIN_EDIT_OPTION = 1;
    private static final int MAX_EDIT_OPTION = 5;
    private static final int MIN_EDIT_DATE_OPTION = 1;
    private static final int MAX_EDIT_DATE_OPTION = 3;
    private static final String DELETE_ORDER = "Choose which order to delete by client";
    private static final String OPTIONS = "Choose what to do with the Orders : " +
            "\n 1:Show all orders" +
            "\n 2:Add order" +
            "\n 3:Edit order" +
            "\n 4:Remove order" +
            "\n 5:Back";

    private static final String EDIT_OPTIONS = "Choose what do you want to edit : " +
            "\n 1:Edit the client" +
            "\n 2:Edit the book" +
            "\n 3:Extend the due date";
    private static final String EDIT_ORDER_CHOOSE = "Choose order to edit by client name";
    private static final String EDIT_OPTION_CLIENT_NAME = "Enter the new client's name";
    private static final String EDIT_OPTION_BOOK_NAME = "Enter the new book's name";
    private static final String EDIT_OPTION_EXTEND_DATE = "How do you want to extend the date? :" +
            "\n 1.Extend by days" +
            "\n 2.Extend by months" +
            "\n 3.Extend by years";

    private static final String EXTEND_BY_DAYS = "How many days to extend with?";
    private static final String EXTEND_BY_MONTHS = "How many months to extend with?";
    private static final String EXTEND_BY_YEARS = "How many years to extend with?";

    public void showOrderMenu(){
        System.out.println(OPTIONS);
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice){
            case 1:
                printAllOrders();
                break;
            case 2:
                addOrder();
                break;
            case 3:
                editOrder();
                break;
            case 4:
                removeOrder();
                break;
            case 5:
                return;
        }
    }
    public void printAllOrders(){
        List<Order> orderList = orderService.getAllOrders();
        for(Order order: orderList){
            System.out.println(order);
        }
    }
    public void addOrder(){
        System.out.println(CLIENT_NAME_INSERT);
        String clientName = ConsoleReader.readString();
        Client client = new Client(clientName);
        System.out.println(BOOK_NAME_INSERT);
        String bookName = ConsoleReader.readString();
        Book book = new Book(bookName);
        String from = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate start = DateFormatter.formatter(from);
        String to = LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate due = DateFormatter.formatter(to);
        Order toBeAdded = new Order(client, book, start, due);
        orderService.addOrder(toBeAdded);
    }
    public void editOrder(){
        System.out.println(EDIT_ORDER_CHOOSE);
        printAllOrders();
        String nameToSearchFor = ConsoleReader.readString();
        Order orderToBeEdited = orderService.findOrderByClient(nameToSearchFor);
        System.out.println(EDIT_OPTIONS);
        int choice = ConsoleRangeReader.readInt(MIN_EDIT_OPTION, MAX_EDIT_OPTION);
        Order editedOrder = null;
        String newField;
        switch (choice){
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
            case 3 :
                System.out.println(EDIT_OPTION_EXTEND_DATE);
                int choiceDate = ConsoleRangeReader.readInt(MIN_EDIT_DATE_OPTION, MAX_EDIT_DATE_OPTION);
                int input;
                switch (choiceDate){
                    case 1:
                        System.out.println(EXTEND_BY_DAYS);
                        input = ConsoleReader.readInt();
                        editedOrder = new Order(orderToBeEdited.getClient(), orderToBeEdited.getBook(),
                                orderToBeEdited.getFromDate(),orderToBeEdited.getDueDate().plusDays(input));
                        break;
                    case 2:
                        System.out.println(EXTEND_BY_MONTHS);
                        input = ConsoleReader.readInt();
                        editedOrder = new Order(orderToBeEdited.getClient(), orderToBeEdited.getBook(),
                                orderToBeEdited.getFromDate(),orderToBeEdited.getDueDate().plusMonths(input));
                        break;
                    case 3:
                        System.out.println(EXTEND_BY_YEARS);
                        input = ConsoleReader.readInt();
                        editedOrder = new Order(orderToBeEdited.getClient(), orderToBeEdited.getBook(),
                                orderToBeEdited.getFromDate(),orderToBeEdited.getDueDate().plusYears(input));
                        break;
                }
        }
        orderService.deleteOrder(orderToBeEdited);
        orderService.addOrder(editedOrder);
    }
    public void removeOrder(){
        System.out.println(DELETE_ORDER);
        printAllOrders();
        String nameToSearchFor = ConsoleReader.readString();
        Order orderToBeDeleted = orderService.findOrderByClient(nameToSearchFor);
        orderService.deleteOrder(orderToBeDeleted);
    }
}