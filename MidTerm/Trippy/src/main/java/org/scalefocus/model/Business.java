package org.scalefocus.model;

import org.scalefocus.enums.Type;

import java.util.List;

public class Business {
    private int id;
    private String name;
    private Type type;
    private int rating;
    private List<Review> reviews;
    private String city;
    private String phone;
    private String email;

    public Business(int id, String name, Type type, int rating, String city, String phone, String email) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.rating = rating;
        this.city = city;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Type getType() {
        return type;
    }

    public int getRating() {
        return rating;
    }
    public List<Review> getReviews() {
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
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
