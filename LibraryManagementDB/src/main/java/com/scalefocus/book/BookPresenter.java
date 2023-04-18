package com.scalefocus.book;

import com.scalefocus.exception.InvalidAuthorException;
import com.scalefocus.exception.InvalidBookException;
import com.scalefocus.exception.InvalidDateException;
import com.scalefocus.util.ConsoleRangeReader;
import com.scalefocus.util.ConsoleReader;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import static com.scalefocus.constants.GlobalConstants.*;

@Component
public class BookPresenter {

    private final BookService bookService;


    public BookPresenter(BookService bookService) {
        this.bookService = bookService;
    }

    public void showBookMenu() {
        while (true) {
            System.out.println(BP_OPTIONS);
            int choice = ConsoleRangeReader.readInt(BP_MIN_MENU_OPTION, BP_MAX_MENU_OPTION);
            switch (choice) {
                case 1:
                    printAllBooks();
                    break;
                case 2:
                    addBook();
                    break;
                case 3:
                    editBook();
                    break;
                case 4:
                    removeBook();
                    break;
                case 5:
                    return;
            }
        }
    }

    public void printAllBooks() {
        List<Book> bookList = bookService.getAllBooks();
        for (Book book : bookList) {
            System.out.println(book);
        }
    }

    public void addBook() {
        System.out.printf(BP_BOOK_NAME_INSERT);
        String name = ConsoleReader.readString();
        System.out.printf(BP_BOOK_AUTHOR_INSERT);
        bookService.printAllAuthors();
        String validatedAuthorName;
        try {
            validatedAuthorName = bookService.inputValidAuthor();
        } catch (InvalidAuthorException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(BP_BOOK_DATE_OF_CREATION_INSERT);
        LocalDate date;
        try {
            date = bookService.insertDate();
        } catch (InvalidDateException e) {
            System.out.println(e.getMessage());
            return;
        }
        bookService.addBook(name, validatedAuthorName, date);
    }

    public void editBook(){
        System.out.println(BP_ENTER_BOOK_TO_EDIT);
        printAllBooks();
        String nameToSearchFor = ConsoleReader.readString();
        Book bookToBeEdited;
        try {
            bookToBeEdited = bookService.findBookByName(nameToSearchFor);
        } catch (InvalidBookException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(BP_BOOK_EDIT_CHOICE);
        int choice = ConsoleRangeReader.readInt(BP_MIN_EDIT_OPTION, BP_MAX_EDIT_OPTION);
        Book editedBook = null;
        String newField;
        switch (choice) {
            case 1:
                System.out.println(BP_EDIT_OPTION_NAME);
                newField = ConsoleReader.readString();
                editedBook = new Book(newField, bookToBeEdited.getAuthor(), bookToBeEdited.getDateOfCreation());
                break;
            case 2:
                try {
                    newField = bookService.inputValidAuthor();
                } catch (InvalidAuthorException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                editedBook = new Book(bookToBeEdited.getName(), newField, bookToBeEdited.getDateOfCreation());
                break;
            case 3:
                System.out.println(BP_EDIT_OPTION_DATE);
                LocalDate newDate;
                try {
                    newDate = bookService.insertDate();
                } catch (InvalidDateException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                editedBook = new Book(bookToBeEdited.getName(), bookToBeEdited.getAuthor(), newDate);
                break;
        }
        bookService.removeBook(bookToBeEdited);
        bookService.addBook(editedBook);
    }

    public void removeBook(){
        printAllBooks();
        System.out.println(BP_DELETE_BOOK);
        String nameToSearchFor = ConsoleReader.readString();
        Book bookToBeDeleted;
        try {
            bookToBeDeleted = bookService.findBookByName(nameToSearchFor);
        } catch (InvalidBookException e) {
            System.out.println(e.getMessage());
            return;
        }
        bookService.removeBook(bookToBeDeleted);
    }
}