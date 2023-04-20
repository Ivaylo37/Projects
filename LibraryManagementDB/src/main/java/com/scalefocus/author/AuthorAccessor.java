package com.scalefocus.author;

import com.scalefocus.util.db.DBConnector;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.util.List;

@Component
public class AuthorAccessor {

    private final AuthorMapper authorMapper;

    public AuthorAccessor(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    public List<Author> getAllAuthors() {
        ResultSet resultSet;
        List<Author> authors;
        Connection connection = DBConnector.getInstance().getConnection();
        try (Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery("SELECT * FROM library_management.authors");
            authors = authorMapper.mapResultSetToAuthors(resultSet);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return authors;
    }

    public void addAuthor(String authorName) {
        String sql = "";
        Connection connection = DBConnector.getInstance().getConnection();
        try (PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO library_management.authors(author_name) VALUES(?);")){
            preparedStatement.setString(1, authorName);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteAuthor(String toBeDeleted) {
        String sql = "DELETE FROM library_management.authors WHERE author_name = ?";
        Connection connection = DBConnector.getInstance().getConnection();
        try (PreparedStatement preparedStatement =
                connection.prepareStatement(sql)){
            preparedStatement.setString(1, toBeDeleted);
            preparedStatement.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void editAuthor(String toBeEdited, String newName) {
        String sql = "UPDATE library_management.authors SET author_name = ? WHERE author_name = ?";
        Connection connection = DBConnector.getInstance().getConnection();
        try (PreparedStatement preparedStatement =
                connection.prepareStatement(sql)){
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, toBeEdited);
            preparedStatement.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Author getAuthorByID(int id){
        ResultSet resultSet;
        Author author;
        String sql = "SELECT * FROM library_management.authors WHERE author_id = ?";
        Connection connection = DBConnector.getInstance().getConnection();
        try (PreparedStatement preparedStatement =
                connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            List<Author> authors = authorMapper.mapResultSetToAuthors(resultSet);
            author = authors.get(0);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return author;
    }
}