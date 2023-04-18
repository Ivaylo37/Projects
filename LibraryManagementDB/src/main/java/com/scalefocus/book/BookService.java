package com.scalefocus.book;

import com.scalefocus.author.Author;
import com.scalefocus.author.AuthorService;
import com.scalefocus.exception.BookNotDeletableException;
import com.scalefocus.exception.InvalidAuthorException;
import com.scalefocus.exception.InvalidBookException;
import com.scalefocus.exception.InvalidDateException;
import com.scalefocus.util.ConsoleReader;
import com.scalefocus.util.DateFormatter;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import static com.scalefocus.constants.GlobalConstants.BS_BOOK_AUTHOR_INSERT;

@Service
public class BookService {
    private final BookAccessor bookAccessor;
    private final AuthorService authorService;

    public BookService(BookAccessor bookAccessor, AuthorService authorService) {
        this.bookAccessor = bookAccessor;
        this.authorService = authorService;
    }

    public List<Book> getAllBooks() {
        return bookAccessor.getAllBooks();
    }

    public void addBook(String name, String author, LocalDate date) {
        Book book = new Book(name, author, date);
        bookAccessor.addBook(book);
    }

    public void addBook(Book book) {
        bookAccessor.addBook(book);
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

    public void removeBook(Book bookToDelete) {
        try {
            boolean existsInOrder = existingOrderForBook(bookToDelete);
        } catch (BookNotDeletableException e) {
            System.out.println(e.getMessage());
            return;
        }

        bookAccessor.deleteBook(bookToDelete.getName());
    }

    public boolean existingOrderForBook(Book bookToLookFor) throws BookNotDeletableException{
        List<Book> books = bookAccessor.getAllBooksFromOrders();
        boolean bookFound = false;
        for (Book book : books){
            if (book.getName().equalsIgnoreCase(bookToLookFor.getName())){
                bookFound = true;
                throw new BookNotDeletableException("There is an order including this book. Can not be deleted/edited");
            }
        }
        return bookFound;
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
        System.out.printf(BS_BOOK_AUTHOR_INSERT);
        String authorName = ConsoleReader.readString();
        Author author;
        try {
            author = authorService.findAuthorByName(authorName);
        } catch (InvalidAuthorException e) {
            throw new InvalidAuthorException("Author not found");
        }
        String foundAuthor = author.getName();
        return foundAuthor;
    }

    public int getBookID(Book book){
        return bookAccessor.getIDbyBook(book);
    }

    public void printAllAuthors(){
        List<Author> authorList = authorService.getAllAuthors();
        for (Author author : authorList) {
            System.out.println(author);
        }
    }
}