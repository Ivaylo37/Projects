package com.scalefocus.book;

import com.scalefocus.author.Author;

public class BookMapper {

    Book mapStringToBook(String string){
        String[] tokens = string.split("_");
        String name = tokens[0];
        String authorName = tokens[1];
        String dateOfCreation = tokens[2];
        return new Book(name, authorName, dateOfCreation);
    }
    String mapBookToString(Book book){
        String name = book.getName();
        String authorName = book.getAuthor();
        String dateOfCreation = book.getDateOfCreation();
        return String.join("_", name, authorName, dateOfCreation + "\n");
    }
}
