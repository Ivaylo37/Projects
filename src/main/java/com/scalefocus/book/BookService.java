package com.scalefocus.book;

import java.util.ArrayList;
import java.util.List;

public class BookService {

    private static final BookAccessor bookAccessor = new BookAccessor();
    private static final BookMapper bookMapper = new BookMapper();

    public List<Book> getAllBooks(){
        List<String> bookStrings = bookAccessor.readAllBooks();
        List<Book> books = new ArrayList<>();
        for (String bookString : bookStrings) {
            Book book = bookMapper.mapStringToBook(bookString);
            books.add(book);
        }
        return books;
    }

    public void addBook(String name, String author, String date){
        Book book = new Book(name, author, date);
        String bookToString = bookMapper.mapBookToString(book);
        bookAccessor.addBook(bookToString);
        }
    }

