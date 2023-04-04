package com.scalefocus.book;

import com.scalefocus.author.Author;
import com.scalefocus.author.AuthorService;
import com.scalefocus.order.Order;
import com.scalefocus.order.OrderService;
import com.scalefocus.util.ConsoleReader;
import com.scalefocus.util.DateFormatter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    private static final Exception dateException = new RuntimeException("The date is invalid");
    private static final BookAccessor bookAccessor = new BookAccessor();
    private static final BookMapper bookMapper = new BookMapper();
    private static final OrderService orderService = new OrderService();
    private static final AuthorService authorService = new AuthorService();

    private static final String BOOK_FOUND = "Book found";

    private static final String BOOK_NOT_FOUND = "Book not found, please try again.";
    private static final String BOOK_AUTHOR_INSERT = "Enter the author's name :";

    public List<Book> getAllBooks() {
        List<String> bookStrings = bookAccessor.readAllBooks();
        List<Book> books = new ArrayList<>();
        for (String bookString : bookStrings) {
            Book book = bookMapper.mapStringToBook(bookString);
            books.add(book);
        }
        return books;
    }

    public void addBook(String name, String author, LocalDate date) {
        Book book = new Book(name, author, date);
        String bookToString = bookMapper.mapBookToString(book);
        bookAccessor.addBook(bookToString);
    }

    public void addBook(Book book) {
        String bookToString = bookMapper.mapBookToString(book);
        bookAccessor.addBook(bookToString);
    }

    public Book findBookByName(String nameToLookFor) {
        List<Book> books = getAllBooks();
        boolean found = false;
        for (Book book : books) {
            if (nameToLookFor.equalsIgnoreCase(book.getName())) {
                found = true;
                return book;
            }
        }
        return null; //TODO
    }

    public List<Book> findBookByAuthor(String nameToLookFor) {
        List<Book> books = getAllBooks();
        List<Book> foundBooks = new ArrayList<>();
        boolean found = false;
        for (Book book : books) {
            if (nameToLookFor.equalsIgnoreCase(book.getAuthor())) {
                found = true;
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public void removeBook(Book bookToDelete) {
        if (existingOrderForBook(bookToDelete)){
            System.out.println("This book is ordered, can not be removed");
            return;
        }
        String toDelete = bookMapper.mapBookToString(bookToDelete).trim();
        List<String> bookStrings = bookAccessor.readAllBooks();
        if (bookStrings.contains(toDelete)) {
            bookStrings.remove(toDelete);
        }
        String newString = new String();
        for (String string : bookStrings) {
            newString = newString.concat(string);
            newString = newString.concat("\n");
        }
        bookAccessor.deleteBook(newString);
    }

    public boolean existingOrderForBook(Book book){
        List<Order> orders = orderService.getAllOrders();
        boolean orderFound = false;
        for (Order order : orders){
            if (order.getBook().getName().equalsIgnoreCase(book.getAuthor())){
                orderFound = true;
            }
        }
        return orderFound;
    }

    public String validateDay(int day) {
        String dayString;
        if (day > 0 && day < 32) {
            dayString = String.valueOf(day);
        } else return "";
        if (day < 10){
            dayString = "0" + dayString;
        }
        return dayString;
    }

    public String validateMonth(int month) {
        String monthString;
        if (month > 0 && month < 13) {
            monthString = String.valueOf(month);
        } else return "";
        if (month < 10){
            monthString = "0" + monthString;
        }
        return monthString;
    }

    public String validateYear(int year) {
        if (year < 2023) {
            return String.valueOf(year);
        } else return "";
    }

    public LocalDate insertDate() throws Exception{
        System.out.println("Enter day");
        int day = ConsoleReader.readInt();
        String dayString = validateDay(day);
        if (dayString == "") {
            try {
                throw dateException;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        System.out.println("Enter month");
        int month = ConsoleReader.readInt();
        String monthString = validateMonth(month);
        if (monthString == "") {
            try {
                throw dateException;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        System.out.println("Enter year");
        int year = ConsoleReader.readInt();
        String yearString = validateYear(year);
        if (yearString == ""){
            try {
                throw dateException;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        String date = String.join("/", dayString, monthString, yearString);
        return DateFormatter.formatter(date);
    }

    public String inputValidAuthor(){
        System.out.printf(BOOK_AUTHOR_INSERT);
        String authorName = ConsoleReader.readString();
        Author author = authorService.findAuthorByName(authorName);
        if (author != null){
            return authorService.findAuthorByName(authorName).getName();
        }
        return null;
    }
}