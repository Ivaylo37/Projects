package com.scalefocus.book;

import com.scalefocus.author.Author;
import com.scalefocus.author.AuthorService;
import com.scalefocus.exception.BookNotDeletableException;
import com.scalefocus.exception.InvalidAuthorException;
import com.scalefocus.exception.InvalidBookException;
import com.scalefocus.exception.InvalidDateException;
import com.scalefocus.order.Order;
import com.scalefocus.order.OrderService;
import com.scalefocus.util.ConsoleReader;
import com.scalefocus.util.DateFormatter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private static final BookAccessor bookAccessor = new BookAccessor();
    private static final BookMapper bookMapper = new BookMapper();
    private static final OrderService orderService = new OrderService();
    private static final AuthorService authorService = new AuthorService();
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

    public Book findBookByName(String nameToLookFor) throws InvalidBookException{
        List<Book> books = getAllBooks();
        Book foundBook = null;
        for (Book book : books) {
            if (nameToLookFor.equalsIgnoreCase(book.getName())) {
                foundBook = book;
                break;
            }
        }
        if (foundBook == null){
            throw new InvalidBookException("Book not found.");
        }
        return foundBook;
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
        try {
            boolean existsInOrder = existingOrderForBook(bookToDelete);
        } catch (BookNotDeletableException e) {
            System.out.println(e.getMessage());
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

    public boolean existingOrderForBook(Book book) throws BookNotDeletableException{
        List<Order> orders = orderService.getAllOrders();
        boolean orderFound = false;
        for (Order order : orders){
            if (order.getBook().getName().equalsIgnoreCase(book.getName())){
                orderFound = true;
                throw new BookNotDeletableException("There is an order including this book. Can not be deleted/edited");
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
        if (year > 999 && year <= 2023) {
            return String.valueOf(year);
        } else return "";
    }

    public LocalDate insertDate() throws InvalidDateException {
        System.out.println("Enter day");
        int day = ConsoleReader.readInt();
        String dayString = validateDay(day);
        if (dayString == "") {
            throw new InvalidDateException("The day is invalid");
        }
        System.out.println("Enter month");
        int month = ConsoleReader.readInt();
        String monthString = validateMonth(month);
        if (monthString == "") {
            throw new InvalidDateException("The month is invalid");
        }
        System.out.println("Enter year");
        int year = ConsoleReader.readInt();
        String yearString = validateYear(year);
        if (yearString == ""){
            throw new InvalidDateException("The year is invalid");
        }
        String date = String.join("/", dayString, monthString, yearString);
        return DateFormatter.formatter(date);
    }

    public String inputValidAuthor() throws InvalidAuthorException{
        System.out.printf(BOOK_AUTHOR_INSERT);
        String authorName = ConsoleReader.readString();
        Author author;
        try {
            author = authorService.findAuthorByName(authorName);
        } catch (InvalidAuthorException e) {
            throw new InvalidAuthorException("Author not found");
        }
        String foundAuthor = author.getName();;
        return foundAuthor;
    }
}