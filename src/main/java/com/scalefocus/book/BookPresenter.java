package com.scalefocus.book;

import com.scalefocus.util.ConsoleRangeReader;
import com.scalefocus.util.ConsoleReader;

import java.util.List;

public class BookPresenter {

    private static final BookService bookService = new BookService();

    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 4;

    private static final String BOOK_NAME_INSERT = "Please enter the book's name :";
    private static final String BOOK_AUTHOR_INSERT = "Please enter the author's name :";
    private static final String BOOK_DATE_OF_CREATION_INSERT = "Please enter a date of creation :";



    private static final String OPTIONS = "Choose what to do with the Books : " +
            "\n 1:Show all books" +
            "\n 2:Add book" +
            "\n 3:Edit book" +
            "\n 4:Remove book";
    public void showBookMenu(){
        System.out.println(OPTIONS);
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice){
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
        }
    }
    public void printAllBooks(){
        List<Book> bookList = bookService.getAllBooks();
        for (Book book : bookList) {
            System.out.println(book);
        }
    }

    public void addBook(){
        System.out.printf(BOOK_NAME_INSERT);
        String name = ConsoleReader.readString();
        System.out.printf(BOOK_AUTHOR_INSERT);
        String authorName = ConsoleReader.readString();
        System.out.printf(BOOK_DATE_OF_CREATION_INSERT);
        String date = ConsoleReader.readString();

        bookService.addBook(name, authorName, date);
    }

    public void editBook(){

    }

    public void removeBook(){

    }
}
