package com.scalefocus.book;

import com.scalefocus.author.Author;
import com.scalefocus.author.AuthorAccessor;
import com.scalefocus.util.DateFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookMapper {

    private static final AuthorAccessor authorAccessor = new AuthorAccessor();

    public List<Book> mapResultSetToBook(ResultSet bookResultSet){
        List<Book> bookList = new ArrayList<>();
        try (bookResultSet){
            while (bookResultSet.next()){
                String name = bookResultSet.getString(2);
                int authorKey = bookResultSet.getInt(3);
                Author author = authorAccessor.getAuthorByID(authorKey);
                LocalDate date = bookResultSet.getDate(4).toLocalDate();
                Book book = new Book(name, author.getName(), date);
                bookList.add(book);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return bookList;
    }
    Book mapStringToBook(String string) {
        String[] tokens = string.split("_");
        String name = tokens[0];
        String authorName = tokens[1];
        LocalDate dateOfCreation = DateFormatter.formatter(tokens[2]);
        return new Book(name, authorName, dateOfCreation);
    }

    String mapBookToString(Book book) {
        String name = book.getName();
        String authorName = book.getAuthor();
        String dateOfCreation = DateFormatter.dateToString(book.getDateOfCreation());
        return String.join("_", name, authorName, dateOfCreation + "\n");
    }

    public int mapResultSetToInt(ResultSet resultSet){
        int id = 0;
        try (resultSet){
            while (resultSet.next()){
                id = resultSet.getInt(1);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return id;
    }
}
