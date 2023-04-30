package org.scalefocus.business;

import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BusinessMapper {

    public List<Business> mapResultSetToBusinesses(ResultSet businessResultSet){
        List<Business> businesses = new ArrayList<>();
        try(businessResultSet){
            while (businessResultSet.next()){
                int id = businessResultSet.getInt(1);
                String type = businessResultSet.getString(2);
                int rating = businessResultSet.getInt(3);
                String email = businessResultSet.getString(5);
                String phone = businessResultSet.getString(6);
                String city = businessResultSet.getString(7);
                List<String> reviews = new ArrayList<>();//TODO
                Business business = new Business(id, type, rating, city, phone, email);
                businesses.add(business);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return businesses;
    }
}
