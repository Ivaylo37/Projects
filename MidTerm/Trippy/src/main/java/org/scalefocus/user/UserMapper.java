package org.scalefocus.user;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public List<User> mapResultSetToUsers(ResultSet usersResultSet) {
        List<User> users = new ArrayList<>();
        try (usersResultSet) {
            while (usersResultSet.next()) {
                int id = usersResultSet.getInt(1);
                String username = usersResultSet.getString(2);
                String email = usersResultSet.getString(3);
                String phone = usersResultSet.getString(4);
                String city = usersResultSet.getString(5);
                LocalDate registrationDate = usersResultSet.getDate(6).toLocalDate();
                User user = new User(id, username, email, phone, city, registrationDate);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}