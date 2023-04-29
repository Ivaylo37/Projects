package org.scalefocus.business;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class BusinessDto {

    private int id;
    private String type;
    private double rating;
    private List<String> reviews;
    private String phone;
    private String email;
    private String city;

    @JsonCreator
    public BusinessDto(int id, String type, int rating, List<String> reviews, String city, String phone, String email) {
        this.id = id;
        this.type = type;
        this.rating = (double)(rating)/10;
        this.reviews = reviews;
        this.city = city;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getRating() {
        return rating;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }
}
