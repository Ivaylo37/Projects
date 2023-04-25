package com.scalefocus.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.stereotype.Component;

public class AuthorRequest {

    private String name;
    @JsonCreator
    public AuthorRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
