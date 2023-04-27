package org.scalefocus.user;

import org.scalefocus.util.db.DBConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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


}