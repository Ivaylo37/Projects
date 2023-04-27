package org.scalefocus.user;

import java.time.LocalDate;
import java.util.List;

public class User {
    int id;
    String username;
    String email;
    String phone;
    String city;
    List<String> reviews; //to be List of Reviews
    LocalDate registrationDate;

    public User(String username, String email, String phone,  String city) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.city = city;
    }

    public User(int id, String username, String email, String phone, String city, LocalDate registrationDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.registrationDate = registrationDate;
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

    public String getPhone() {
        return phone;
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