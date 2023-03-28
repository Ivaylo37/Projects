package com.scalefocus.book;

import com.scalefocus.author.Author;

public class Book {
    private String name;
    private String author;
    private String dateOfCreation;

    public Book (String name, String author, String date){
        this.name = name;
        this.author = author;
        this.dateOfCreation = date;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author=" + author +
                ", dateOfCreation='" + dateOfCreation + '\'' +
                '}';
    }
}
