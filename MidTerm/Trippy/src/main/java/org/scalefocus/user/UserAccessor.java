package org.scalefocus.user;

import org.scalefocus.customExceptions.UserNotFoundException;
import org.scalefocus.review.Review;
import org.scalefocus.review.ReviewMapper;
import org.scalefocus.util.db.DBConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserAccessor {

    private final UserMapper userMapper;

    private final ReviewMapper reviewMapper;

    @Autowired
    public UserAccessor(UserMapper userMapper, ReviewMapper reviewMapper) {
        this.userMapper = userMapper;
        this.reviewMapper = reviewMapper;
    }

    public List<User> getAllUsers() {
        List<User> users;
        String sql = "SELECT * FROM trippy.user";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            users = userMapper.mapResultSetToUsers(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public User createUser(String username, String email, String phone, String city) {
        User user = new User(username, email, phone, city);
        String sql = "INSERT INTO trippy.user(username, email, phone, city, registration_date) VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, city);
            preparedStatement.setDate(5, Date.valueOf(LocalDate.now()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User findUserByEmail(String email) throws UserNotFoundException {
        User user;
        String sql = "SELECT * FROM trippy.user WHERE email = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = userMapper.mapResultSetToUsers(resultSet);
            if (users.size() == 0) {
                throw new UserNotFoundException("User with this email not found.");
            }
            user = users.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User findUserByUsername(String username) throws UserNotFoundException {
        User user;
        String sql = "SELECT * FROM trippy.user WHERE username = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = userMapper.mapResultSetToUsers(resultSet);
            if (users.size() == 0) {
                throw new UserNotFoundException("User with this username not found.");
            }
            user = users.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User findUserById(int id) throws UserNotFoundException {
        User user;
        String sql = "SELECT * FROM trippy.user WHERE id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = userMapper.mapResultSetToUsers(resultSet);
            if (users.size() == 0) {
                throw new UserNotFoundException("User with this id not found.");
            }
            user = users.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public List<Review> getReviewsByUser(int userId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM trippy.review WHERE user_id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            reviews = reviewMapper.mapReviewsResultSetToReviews(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }
}