package com.scalefocus.author;

import com.scalefocus.exception.InvalidAuthorException;
import com.scalefocus.util.ConsoleRangeReader;
import com.scalefocus.util.ConsoleReader;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.scalefocus.constants.GlobalConstants.*;
@Component
public class AuthorPresenter {
    private final AuthorService authorService;

    public AuthorPresenter(AuthorService authorService) {
        this.authorService = authorService;
    }

    public void showAuthorMenu() {
        while (true) {
            System.out.println(AP_OPTIONS);
            int choice = ConsoleRangeReader.readInt(AP_MIN_MENU_OPTION, AP_MAX_MENU_OPTION);
            switch (choice) {
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
    }

    public void printAllAuthors() {
        List<Author> authorList = authorService.getAllAuthors();
        for (Author author : authorList) {
            System.out.println(author);
        }
    }

    public void addAuthor() {
        System.out.println(AP_AUTHOR_NAME_INSERT);
        String name = ConsoleReader.readString();
        authorService.addAuthor(name);
    }

    public void editAuthor() {
        System.out.println(AP_AUTHOR_TO_EDIT);
        printAllAuthors();
        String nameToSearchFor = ConsoleReader.readString();
        Author authorToBeEdited = null;
        try {
            authorToBeEdited = authorService.findAuthorByName(nameToSearchFor);
        } catch (InvalidAuthorException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(AP_AUTHOR_NAME_EDIT);
        String newName = ConsoleReader.readString();
        authorService.editAuthor(authorToBeEdited.getName(), newName);
    }

    public void removeAuthor() {
        System.out.println(AP_AUTHOR_TO_EDIT);
        printAllAuthors();
        String nameToSearchFor = ConsoleReader.readString();
        Author authorToDeleted = null;
        try {
            authorToDeleted = authorService.findAuthorByName(nameToSearchFor);
        } catch (InvalidAuthorException e) {
            System.out.println(e.getMessage());
            return;
        }
        authorService.removeAuthor(authorToDeleted);
    }
}
