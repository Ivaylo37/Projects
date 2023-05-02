package org.scalefocus.exception;

public class ReviewNotFoundException extends RuntimeException{

    public ReviewNotFoundException(String message){
        super(message);
    }
}
