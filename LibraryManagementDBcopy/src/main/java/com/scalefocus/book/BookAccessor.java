package com.scalefocus.book;

import com.scalefocus.util.db.DBConnector;
import org.springframework.stereotype.Component;
import com.scalefocus.author.Author;

import java.sql.*;
import java.util.List;

@Component
public class BookAccessor {
    private final BookMapper bookMapper;//iMapper

    public BookAccessor(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public List<Book> getAllBooks() {
        ResultSet resultSet;
        List<Book> books;
        Connection connection = DBConnector.getInstance().getConnection();
        try (Statement statement = connection.createStatement();){
            resultSet = statement.executeQuery("SELECT * FROM library_management.books");
            books = bookMapper.mapResultSetToBook(resultSet);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return books;
    }

    public void addBook(Book book) {
        Author author = new Author(book.getAuthor());
        Connection connection = DBConnector.getInstance().getConnection();
        try (PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO library_management.books(book_name, book_author, date_of_creation) VALUES(?, ?, ?);")){
            preparedStatement.setString(1, book.getName());
            preparedStatement.setInt(2, getIDbyAuthor(author));
            preparedStatement.setDate(3, Date.valueOf(book.getDateOfCreation()));
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteBook(String toBeDeleted) {
        String sql = "DELETE FROM library_management.books WHERE book_name = ?";
        Connection connection = DBConnector.getInstance().getConnection();
        try (PreparedStatement preparedStatement =
                connection.prepareStatement(sql)){
            preparedStatement.setString(1, toBeDeleted);
            preparedStatement.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Book getBookByID(int id){
        ResultSet resultSet;
        Book book;
        String sql = "SELECT * FROM library_management.books WHERE book_id = ?";
        Connection connection = DBConnector.getInstance().getConnection();
        try (PreparedStatement preparedStatement =
                connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            List<Book> books = bookMapper.mapResultSetToBook(resultSet);
            book = books.get(0);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return book;
    }

    public int getIDbyBook(Book book){
        int id;
        ResultSet resultSet;
        String sql = "SELECT book_id FROM library_management.books WHERE book_name = ?";
        Connection connection = DBConnector.getInstance().getConnection();
        try (PreparedStatement preparedStatement =
                connection.prepareStatement(sql)){
            preparedStatement.setString(1, book.getName());
            resultSet = preparedStatement.executeQuery();
            id = bookMapper.mapResultSetToInt(resultSet);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return id;
    }
    public int getIDbyAuthor(Author author){
        int id;
        ResultSet resultSet;
        String sql = "SELECT author_id FROM library_management.authors WHERE author_name = ?";
        Connection connection = DBConnector.getInstance().getConnection();
        try (PreparedStatement preparedStatement =
                connection.prepareStatement(sql)){
            preparedStatement.setString(1, author.getName());
            resultSet = preparedStatement.executeQuery();
            id = bookMapper.mapResultSetToInt(resultSet);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return id;
    }
    public List<Book> getAllBooksFromOrders() {
        ResultSet resultSet;
        List<Book> books;
        Connection connection = DBConnector.getInstance().getConnection();
        try (Statement statement = connection.createStatement();){
            resultSet = statement.executeQuery("SELECT order_book FROM library_management.orders");
            books = bookMapper.mapResultSetToBookByNames(resultSet);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return books;
    }
}