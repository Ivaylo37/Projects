package com.scalefocus.book;

import com.scalefocus.author.Author;

import java.time.LocalDate;

public class Book {
    private String name;
    private String author;
    private LocalDate dateOfCreation;

    public Book(String name, String author, LocalDate date) {
        this.name = name;
        this.author = author;
        this.dateOfCreation = date;
    }

    public Book(String name) { // to be used for orderMapper
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getDateOfCreation() {
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