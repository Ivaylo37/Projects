package org.scalefocus.business;

import java.util.List;

public class Business {
    int id;
    String type;
    int rating;
    List<String> reviews;
    String city;
    String phone;
    String email;

    public Business(int id, String type, String city, String phone, String email) {
        this.id = id;
        this.type = type;
        this.city = city;
        this.phone = phone;
        this.email = email;
    }

    public Business(int id, String type, int rating, String city, String phone, String email) {
        this.id = id;
        this.type = type;
        this.rating = rating;
        this.city = city;
        this.phone = phone;
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public int getRating() {
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
