package com.scalefocus.author;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class AuthorMapper {

    String mapAuthorToString(Author author) {
        return author.getName();
    }

    public List<Author> mapResultSetToAuthors(ResultSet authorsResultSet){//add books
        List<Author> authorsList = new ArrayList<>();
        try (authorsResultSet){
            while (authorsResultSet.next()){
                String name = authorsResultSet.getString(2);
                Author author = new Author(name);
                authorsList.add(author);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return authorsList;
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
