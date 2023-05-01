package org.scalefocus.domain.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.scalefocus.enums.Type;

public class BusinessRequest {

    private Type type;
    private String name;
    private String city;
    private String phone;
    private String email;

    @JsonCreator
    public BusinessRequest(Type type, String name, String city, String phone, String email) {
        this.type = type;
        this.name = name;
        this.city = city;
        this.phone = phone;
        this.email = email;
    }

    public Type getType() {
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