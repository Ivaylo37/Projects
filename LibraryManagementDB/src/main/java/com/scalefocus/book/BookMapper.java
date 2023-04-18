package com.scalefocus.book;

import com.scalefocus.author.Author;
import com.scalefocus.author.AuthorAccessor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Component
public class BookMapper {

    private final AuthorAccessor authorAccessor;

    public BookMapper(AuthorAccessor authorAccessor) {
        this.authorAccessor = authorAccessor;
    }

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

    public List<Book> mapResultSetToBookByNames(ResultSet bookResultSet){
        List<Book> bookList = new ArrayList<>();
        try (bookResultSet){
            while (bookResultSet.next()){
                String name = bookResultSet.getString(1);
                Book book = new Book(name);
                bookList.add(book);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return bookList;
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
