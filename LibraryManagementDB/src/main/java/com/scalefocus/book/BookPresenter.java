package com.scalefocus.book;

import com.scalefocus.author.AuthorPresenter;
import com.scalefocus.exception.InvalidAuthorException;
import com.scalefocus.exception.InvalidBookException;
import com.scalefocus.exception.InvalidDateException;
import com.scalefocus.util.ConsoleRangeReader;
import com.scalefocus.util.ConsoleReader;

import java.time.LocalDate;
import java.util.List;

public class BookPresenter {

    private static final BookService bookService = new BookService();

    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 5;
    private static final String BOOK_NAME_INSERT = "Please enter the book's name :";
    private static final String BOOK_AUTHOR_INSERT = "Choose from the existing authors :";
    private static final String BOOK_DATE_OF_CREATION_INSERT = "Please enter a date of creation :";
    private static final String ENTER_BOOK_TO_EDIT = "Choose which book to edit by name";
    private static final String BOOK_EDIT_CHOICE = "Choose what to edit : \n" +
            "1.Name \n" +
            "2.Author \n" +
            "3.Date of creation";
    private static final int MIN_EDIT_OPTION = 1;
    private static final int MAX_EDIT_OPTION = 5;
    private static final String EDIT_OPTION_NAME = "Enter new name";
    private static final String EDIT_OPTION_AUTHOR = "Enter new author";
    private static final String EDIT_OPTION_DATE = "Enter new date";
    private static final String DELETE_BOOK = "Choose book to delete by name";

    private static final String OPTIONS = "Choose what to do with the Books : " +
            "\n ---------------------" +
            "\n   1:Show all books" +
            "\n   2:Add book" +
            "\n   3:Edit book" +
            "\n   4:Remove book" +
            "\n   5:Back" +
            "\n ---------------------";;

    public void showBookMenu() {
        while (true) {
            System.out.println(OPTIONS);
            int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
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
        System.out.printf(BOOK_NAME_INSERT);
        String name = ConsoleReader.readString();
        System.out.printf(BOOK_AUTHOR_INSERT);
        AuthorPresenter.printAllAuthors();
        String validatedAuthorName;
        try {
            validatedAuthorName = bookService.inputValidAuthor();
        } catch (InvalidAuthorException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(BOOK_DATE_OF_CREATION_INSERT);
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
        System.out.println(ENTER_BOOK_TO_EDIT);
        printAllBooks();
        String nameToSearchFor = ConsoleReader.readString();
        Book bookToBeEdited;
        try {
            bookToBeEdited = bookService.findBookByName(nameToSearchFor);
        } catch (InvalidBookException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(BOOK_EDIT_CHOICE);
        int choice = ConsoleRangeReader.readInt(MIN_EDIT_OPTION, MAX_EDIT_OPTION);
        Book editedBook = null;
        String newField;
        switch (choice) {
            case 1:
                System.out.println(EDIT_OPTION_NAME);
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
                System.out.println(EDIT_OPTION_DATE);
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
        System.out.println(DELETE_BOOK);
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