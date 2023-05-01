package org.scalefocus.repository;

import org.scalefocus.domain.Review;
import org.scalefocus.exception.ReviewNotFoundException;
import org.scalefocus.mapper.ReviewMapper;
import org.scalefocus.util.db.DBConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewAccessor {

    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewAccessor(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    public List<Review> getAllReviews() {
        List<Review> reviews;
        String sql = "SELECT * FROM trippy.review";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            reviews = reviewMapper.mapReviewsResultSetToReviews(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }

    public void createReview(int userId, int businessId, int rating, String feedback) {
        String sql = "INSERT INTO trippy.review(user_id, business_id, rating, feedback, stamp_created) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, businessId);
            preparedStatement.setInt(3, rating);
            preparedStatement.setString(4, feedback);
            preparedStatement.setDate(5, Date.valueOf(LocalDate.now()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Review> getReviewsByBusiness(int businessId) throws ReviewNotFoundException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM trippy.review WHERE business_id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, businessId);
            ResultSet resultSet = preparedStatement.executeQuery();
            reviews = reviewMapper.mapReviewsResultSetToReviews(resultSet);
            if (reviews.size() == 0) {
                throw new ReviewNotFoundException("No reviews found for this business.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }
}