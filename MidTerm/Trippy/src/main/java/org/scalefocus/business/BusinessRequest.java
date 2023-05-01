package org.scalefocus.business;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BusinessRequest {

    String type;
    String name;
    String city;
    String phone;
    String email;
    @JsonCreator
    public BusinessRequest(String type, String name, String city, String phone, String email) {
        this.type = type;
        this.name = name;
        this.city = city;
        this.phone = phone;
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
