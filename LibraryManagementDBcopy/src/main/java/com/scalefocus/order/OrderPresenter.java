package com.scalefocus.order;

import com.scalefocus.util.ConsoleReader;
import org.springframework.stereotype.Component;
import com.scalefocus.book.Book;
import com.scalefocus.book.BookService;
import com.scalefocus.client.Client;
import com.scalefocus.exception.InvalidBookException;
import com.scalefocus.exception.InvalidClientException;
import com.scalefocus.exception.InvalidOrderException;
import com.scalefocus.util.ConsoleRangeReader;
import com.scalefocus.util.DateFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.scalefocus.constants.GlobalConstants.*;

@Component
public class OrderPresenter {

    private final OrderService orderService;
    private final BookService bookService;


    public OrderPresenter(OrderService orderService, BookService bookService) {
        this.orderService = orderService;
        this.bookService = bookService;
    }

    public void showOrderMenu() {
        while (true) {
            System.out.println(OP_OPTIONS);
            int choice = ConsoleRangeReader.readInt(OP_MIN_MENU_OPTION, OP_MAX_MENU_OPTION);
            switch (choice) {
                case 1 -> printAllOrders();
                case 2 -> filterOrders();
                case 3 -> addOrder();
                case 4 -> editOrder();
                case 5 -> removeOrder();
                case 6 -> {return;
                }
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
        System.out.println(OP_FILTER_ORDERS_OPTIONS);
        int choice = ConsoleRangeReader.readInt(OP_MIN_FILTER_OPTION, OP_MAX_FILTER_OPTION);
        switch (choice){
            case 1:
                orderService.filterOrdersByClient();
                break;
            case 2:
                orderService.filterOrdersByIssuedDate();
                break;
            case 3:
                orderService.filterOrdersByDueDate();
                break;
        }
    }

    public void addOrder() {
        orderService.showAllClients();
        System.out.println(OP_CLIENT_NAME_INSERT);
        String clientName = ConsoleReader.readString();
        Client client = null;
        try {
            client = orderService.findExistingClient(clientName);
        } catch (InvalidClientException e) {
            System.out.println(e.getMessage());
            return;
        }
        orderService.showAllBooks();
        System.out.println(OP_BOOK_NAME_INSERT);
        String bookName = ConsoleReader.readString();
        Book book = null;
        try {
            book = bookService.findBookByName(bookName);
        } catch (InvalidBookException e) {
            System.out.println(e.getMessage());
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
        System.out.println(OP_EDIT_ORDER_CHOOSE);
        printAllOrders();
        String nameToSearchFor = ConsoleReader.readString();
        System.out.println(OP_EDIT_OPTION_BOOK_NAME);
        String bookToSearchFor = ConsoleReader.readString();
        Order orderToBeEdited = null;
        try {
            orderToBeEdited = orderService.findOrderByClientAndBook(nameToSearchFor, bookToSearchFor);
        } catch (InvalidOrderException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(OP_EDIT_OPTIONS);
        int choice = ConsoleRangeReader.readInt(OP_MIN_EDIT_OPTION, OP_MAX_EDIT_OPTION);
        Order editedOrder = null;
        String newField;
        switch (choice) {
            case 1:
                System.out.println(OP_EDIT_OPTION_CLIENT_NAME);
                newField = ConsoleReader.readString();
                editedOrder = new Order(new Client(newField), orderToBeEdited.getBook(),
                        orderToBeEdited.getFromDate(), orderToBeEdited.getDueDate());
                break;
            case 2:
                System.out.println(OP_EDIT_OPTION_BOOK_NAME);
                newField = ConsoleReader.readString();
                editedOrder = new Order(orderToBeEdited.getClient(), new Book(newField),
                        orderToBeEdited.getFromDate(), orderToBeEdited.getDueDate());
                break;
            case 3:
                System.out.println(OP_EDIT_OPTION_EXTEND_DATE);
                int choiceDate = ConsoleRangeReader.readInt(OP_MIN_EDIT_DATE_OPTION, OP_MAX_EDIT_DATE_OPTION);
                int input;
                switch (choiceDate) {
                    case 1:
                        System.out.println(OP_EXTEND_BY_DAYS);
                        input = ConsoleReader.readInt();
                        editedOrder = new Order(orderToBeEdited.getClient(), orderToBeEdited.getBook(),
                                orderToBeEdited.getFromDate(), orderToBeEdited.getDueDate().plusDays(input));
                        break;
                    case 2:
                        System.out.println(OP_EXTEND_BY_MONTHS);
                        input = ConsoleReader.readInt();
                        editedOrder = new Order(orderToBeEdited.getClient(), orderToBeEdited.getBook(),
                                orderToBeEdited.getFromDate(), orderToBeEdited.getDueDate().plusMonths(input));
                        break;
                    case 3:
                        System.out.println(OP_EXTEND_BY_YEARS);
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
        System.out.println(OP_DELETE_ORDER);
        String nameToSearchFor = ConsoleReader.readString();
        System.out.println(OP_BOOK_NAME_DELETE);
        String bookToSearchFor = ConsoleReader.readString();
        Order orderToBeDeleted = null;
        try {
            orderToBeDeleted = orderService.findOrderByClientAndBook(nameToSearchFor, bookToSearchFor);
        } catch (InvalidOrderException e) {
            System.out.println(e.getMessage());
            return;
        }
        orderService.deleteOrder(orderToBeDeleted);
    }
}