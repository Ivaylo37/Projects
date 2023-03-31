package com.scalefocus.author;

import com.scalefocus.book.Book;
import com.scalefocus.book.BookService;
import com.scalefocus.util.ConsoleRangeReader;
import com.scalefocus.util.ConsoleReader;

import java.util.List;

public class AuthorPresenter {

    private static final AuthorService authorService = new AuthorService();

    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 5;
    private static final String AUTHOR_NAME_INSERT = "Please enter the author's name :";
    private static final String AUTHOR_TO_EDIT = "Author to edit";
    private static final String AUTHOR_NAME_EDIT = "Enter new name";
    private static final String AUTHOR_TO_DELETE = "Select author to delete by name : ";
    private static final String OPTIONS = "Choose what to do with the Books : " +
            "\n 1:Show all authors" +
            "\n 2:Add an author" +
            "\n 3:Edit author's name" +
            "\n 4:Remove an author" +
            "\n 5:Back";

    public void showAuthorMenu() {
        System.out.println(OPTIONS);
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice){
            case 1:
                printAllAuthors();
                break;
            case 2:
                addAuthor();
                break;
            case 3:
                editAuthor();
                break;
            case 4:
                removeAuthor();
                break;
            case 5:
                return;
        }
    }

    public static void printAllAuthors(){
        List<Author> authorList = authorService.getAllAuthors();
        for (Author author : authorList) {
            System.out.println(author);
        }
    }
    public void addAuthor(){
        System.out.println(AUTHOR_NAME_INSERT);
        String name = ConsoleReader.readString();
        authorService.addAuthor(name);
    }

    public void editAuthor(){
        System.out.println(AUTHOR_TO_EDIT);
        printAllAuthors();
        String nameToSearchFor = ConsoleReader.readString();
        Author authorToBeEdited = authorService.findAuthorByName(nameToSearchFor);
        System.out.println(AUTHOR_NAME_EDIT);
        String newName = ConsoleReader.readString();
        authorService.removeAuthor(authorToBeEdited);
        authorService.addAuthor(newName);
    }
    public void removeAuthor(){
        System.out.println(AUTHOR_TO_EDIT);
        printAllAuthors();
        String nameToSearchFor = ConsoleReader.readString();
        Author authorToDeleted = authorService.findAuthorByName(nameToSearchFor);
        authorService.removeAuthor(authorToDeleted);
    }
}
