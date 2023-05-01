package org.scalefocus.mapper;

import org.scalefocus.domain.Review;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewMapper {

    public List<Review> mapReviewsResultSetToReviews(ResultSet resultSet) {
        List<Review> reviews = new ArrayList<>();
        try (resultSet) {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int userId = resultSet.getInt(2);
                int businessID = resultSet.getInt(3);
                int rating = resultSet.getInt(4);
                String feedback = resultSet.getString(5);
                LocalDate stampCreated = resultSet.getDate(6).toLocalDate();
                Review review = new Review(id, userId, businessID, rating, feedback, stampCreated);
                reviews.add(review);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }
}