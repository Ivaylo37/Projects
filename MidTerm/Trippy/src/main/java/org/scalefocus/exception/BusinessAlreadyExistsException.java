package org.scalefocus.exception;

public class BusinessAlreadyExistsException extends RuntimeException{

    public BusinessAlreadyExistsException(String message){
        super(message);
    }
}