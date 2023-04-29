package org.scalefocus.business;

import org.scalefocus.util.db.DBConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
