package org.scalefocus.business;

import java.util.List;

public class BusinessDto {

    int id;
    String type;
    double rating;
    List<String> reviews;
    String phone;
    String email;
    String city;


    public BusinessDto(int id, String type, double rating, List<String> reviews, String city, String phone, String email) {
        this.id = id;
        this.type = type;
        this.rating = rating;
        this.reviews = reviews;
        this.city = city;
        this.phone = phone;
        this.email = email;
    }
}
