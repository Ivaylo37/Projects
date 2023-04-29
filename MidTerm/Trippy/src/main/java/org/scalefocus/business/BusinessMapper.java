package org.scalefocus.business;

import org.scalefocus.user.User;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BusinessMapper {

    public List<BusinessDto> mapResultSetToBusinessesDtos(ResultSet businessResultSet){
        List<BusinessDto> businesses = new ArrayList<>();
        try(businessResultSet){
            while (businessResultSet.next()){
                int id = businessResultSet.getInt(1);
                String type = businessResultSet.getString(2);
                double rating = businessResultSet.getDouble(3);
                int reviewsCount = businessResultSet.getInt(4);
                String email = businessResultSet.getString(5);
                String phone = businessResultSet.getString(6);
                String city = businessResultSet.getString(7);
                List<String> reviews = new ArrayList<>();//!!!!!
                BusinessDto businessDto = new BusinessDto(id, type, rating, reviews,city, phone, email);
                businesses.add(businessDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return businesses;
    }
}
