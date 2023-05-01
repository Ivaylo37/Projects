package org.scalefocus.user;

import com.fasterxml.jackson.annotation.JsonCreator;


public class UserRequest {
    String username;
    String email;
    String phone;
    String city;

    @JsonCreator
    public UserRequest(String username, String email, String phone, String city) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.city = city;
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
}