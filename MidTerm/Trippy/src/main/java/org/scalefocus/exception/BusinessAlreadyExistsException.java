package org.scalefocus.exception;

public class BusinessAlreadyExistsException extends Throwable{

    public BusinessAlreadyExistsException(String message){
        super(message);
    }
}