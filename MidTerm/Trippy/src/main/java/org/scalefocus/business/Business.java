package org.scalefocus.business;

import java.util.List;

public class Business {

    String type;
    double rating;
    List<String> reviews;
    String city;
    String phone;
    String email;

    public Business(String type, String city, String phone, String email) {
        this.type = type;
        this.city = city;
        this.phone = phone;
        this.email = email;
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

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
