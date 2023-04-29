package org.scalefocus.business;

import org.scalefocus.customExceptions.BusinessNotFoundException;
import org.scalefocus.user.UserRequest;
import org.scalefocus.util.db.DBConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class BusinessAccessor {

    private final BusinessMapper businessMapper;
    @Autowired
    public BusinessAccessor(BusinessMapper businessMapper) {
        this.businessMapper = businessMapper;
    }

    public List<BusinessDto> getAllBusinesses(){
        List<BusinessDto> businesses;
        String sql = "SELECT * FROM trippy.businesses";
        try(Connection connection = DBConnector.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            businesses = businessMapper.mapResultSetToBusinessesDtos(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return businesses;
    }
    public List<BusinessDto> getBusinessByType(String type) throws BusinessNotFoundException {
        List<BusinessDto> businessDtos;
        String sql = "SELECT * FROM trippy.businesses WHERE type = ?";
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, type);
            ResultSet resultSet = preparedStatement.executeQuery();
            businessDtos = businessMapper.mapResultSetToBusinessesDtos(resultSet);
            if (businessDtos.size() == 0){
                throw new BusinessNotFoundException("Businesses from this type not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return businessDtos;
    }

    public List<BusinessDto> getBusinessByCity(String city) throws BusinessNotFoundException {
        List<BusinessDto> businessDtos;
        String sql = "SELECT * FROM trippy.businesses WHERE city = ?";
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, city);
            ResultSet resultSet = preparedStatement.executeQuery();
            businessDtos = businessMapper.mapResultSetToBusinessesDtos(resultSet);
            if (businessDtos.size() == 0){
                throw new BusinessNotFoundException("Businesses from this city not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return businessDtos;
    }

    public List<BusinessDto> getBusinessByRating(int rating) throws BusinessNotFoundException {
        List<BusinessDto> businessDtos;
        String sql = "SELECT * FROM trippy.businesses WHERE rating BETWEEN ? and ?";
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, rating);
            int ratingPlusOne = rating + 10;
            preparedStatement.setInt(2, ratingPlusOne);
            ResultSet resultSet = preparedStatement.executeQuery();
            businessDtos = businessMapper.mapResultSetToBusinessesDtos(resultSet);
            if (businessDtos.size() == 0){
                throw new BusinessNotFoundException("Businesses with rating " + rating + "not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return businessDtos;
    }

    public BusinessRequest addBusiness(BusinessRequest businessRequest){
        String sql = "INSERT INTO trippy.businesses(type, email, phone, city) VALUES (?, ?, ?, ?);";
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, businessRequest.getType());
            preparedStatement.setString(2, businessRequest.getEmail());
            preparedStatement.setString(3, businessRequest.getPhone());
            preparedStatement.setString(4, businessRequest.getCity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return businessRequest;
    }
}