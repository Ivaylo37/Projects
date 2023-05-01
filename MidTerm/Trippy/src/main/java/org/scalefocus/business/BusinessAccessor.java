package org.scalefocus.business;

import org.scalefocus.customExceptions.BusinessNotFoundException;
import org.scalefocus.review.Review;
import org.scalefocus.review.ReviewMapper;
import org.scalefocus.util.Constants;
import org.scalefocus.util.db.DBConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class BusinessAccessor {

    private final BusinessMapper businessMapper;
    private final ReviewMapper reviewMapper;

    @Autowired
    public BusinessAccessor(BusinessMapper businessMapper, ReviewMapper reviewMapper) {
        this.businessMapper = businessMapper;
        this.reviewMapper = reviewMapper;
    }

    public List<Business> getAllBusinesses() {
        List<Business> businesses;
        String sql = "SELECT * FROM trippy.business";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            businesses = businessMapper.mapResultSetToBusinesses(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return businesses;
    }

    public List<Business> getBusinessByType(String type) throws BusinessNotFoundException {
        List<Business> business;
        String sql = "SELECT * FROM trippy.business WHERE type = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, type);
            ResultSet resultSet = preparedStatement.executeQuery();
            business = businessMapper.mapResultSetToBusinesses(resultSet);
            if (business.size() == 0) {
                throw new BusinessNotFoundException("Businesses from this type not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return business;
    }

    public List<Business> getBusinessByCity(String city) throws BusinessNotFoundException {
        List<Business> business;
        String sql = "SELECT * FROM trippy.business WHERE city = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, city);
            ResultSet resultSet = preparedStatement.executeQuery();
            business = businessMapper.mapResultSetToBusinesses(resultSet);
            if (business.size() == 0) {
                throw new BusinessNotFoundException("Businesses from this city not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return business;
    }

    public Business getBusinessByNameAndCity(String name, String city) {
        List<Business> businesses;
        String sql = "SELECT * FROM trippy.business WHERE name = ? AND city = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, city);
            ResultSet resultSet = preparedStatement.executeQuery();
            businesses = businessMapper.mapResultSetToBusinesses(resultSet);
            if (businesses.size() == 0) {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return businesses.get(0);
    }

    public List<Business> getBusinessByRating(int rating) throws BusinessNotFoundException {
        List<Business> business;
        String sql = "SELECT * FROM trippy.business WHERE rating BETWEEN ? and ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            int ratingTimesTen = rating * 10;
            preparedStatement.setInt(1, rating);
            int ratingPlusTen = ratingTimesTen + 10;
            preparedStatement.setInt(2, ratingPlusTen);
            ResultSet resultSet = preparedStatement.executeQuery();
            business = businessMapper.mapResultSetToBusinesses(resultSet);
            if (business.size() == 0) {
                throw new BusinessNotFoundException("Businesses with rating " + rating + "not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return business;
    }

    public Business getBusinessById(int businessId) throws BusinessNotFoundException {
        List<Business> businesses;
        String sql = "SELECT * FROM trippy.business WHERE id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, businessId);
            ResultSet resultSet = preparedStatement.executeQuery();
            businesses = businessMapper.mapResultSetToBusinesses(resultSet);
            if (businesses.size() == 0) {
                throw new BusinessNotFoundException("Businesses with id " + businessId + "not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return businesses.get(0);
    }

    public List<Review> getReviewsByBusiness(int businessId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM trippy.review WHERE business_id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, businessId);
            ResultSet resultSet = preparedStatement.executeQuery();
            reviews = reviewMapper.mapReviewsResultSetToReviews(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }

    public BusinessRequest createBusiness(BusinessRequest businessRequest) {
        String sql = "INSERT INTO trippy.business(type, name, email, phone, city) VALUES (?, ?, ?, ?, ?);";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, businessRequest.getType());
            preparedStatement.setString(2, businessRequest.getName());
            preparedStatement.setString(3, businessRequest.getEmail());
            preparedStatement.setString(4, businessRequest.getPhone());
            preparedStatement.setString(5, businessRequest.getCity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return businessRequest;
    }

    public void updateRating(int businessId, int rating) {
        String sql = "UPDATE trippy.business SET rating = ? WHERE id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, rating);
            preparedStatement.setInt(2, businessId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateReviewsCount(int businessId, int reviewsCount) {
        String sql = "UPDATE trippy.business SET reviews_count = ? WHERE id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, reviewsCount);
            preparedStatement.setInt(2, businessId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEmail(int businessId, String newEmail) {
        String sql = "UPDATE trippy.business SET email = ? WHERE id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newEmail);
            preparedStatement.setInt(2, businessId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePhoneNumber(int businessId, String newPhoneNumber) {
        String sql = "UPDATE trippy.business SET phone = ? WHERE id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newPhoneNumber);
            preparedStatement.setInt(2, businessId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}