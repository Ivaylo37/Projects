package org.scalefocus.user;

import com.sun.source.tree.TryTree;
import org.scalefocus.util.db.DBConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Component
public class UserAccessor {

    private final UserMapper userMapper;
    @Autowired
    public UserAccessor(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    public List<User> getAllUsers(){
        List<User> users;
        String sql = "SELECT * FROM trippy.users";
        try(Connection connection = DBConnector.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
            users = userMapper.mapResultSetToUsers(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
    public User addUser(String username, String email, String phone,  String city){
        User user = new User(username, email, phone, city);
        String sql = "INSERT INTO trippy.users(username, email, phone, city, registration_date) VALUES(?, ?, ?, ?, ?)";
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);)
            {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, city);
            preparedStatement.setDate(5, Date.valueOf(LocalDate.now()));
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}