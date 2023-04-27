package org.scalefocus.user;

import java.time.LocalDate;
import java.util.List;

public class User {
    int id;
    String username;
    String email;
    String city;
    List<String> reviews; //to be List of Reviews
    LocalDate registrationDate;

    public User(String username, String email, String city) {
        this.username = username;
        this.email = email;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

}