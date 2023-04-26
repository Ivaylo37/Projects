package com.scalefocus.author;

import com.scalefocus.book.Book;

import java.util.ArrayList;
import java.util.List;

public class Author {

    private final String name;
    private List<Book> writtenBooks = new ArrayList<>();

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setWrittenBooks(List<Book> writtenBooks) {
        this.writtenBooks = writtenBooks;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", writtenBooks=" + writtenBooks.toString() +
                '}';
    }
}