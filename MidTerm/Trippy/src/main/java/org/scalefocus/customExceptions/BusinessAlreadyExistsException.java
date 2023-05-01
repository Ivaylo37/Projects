package org.scalefocus.customExceptions;

public class BusinessAlreadyExistsException extends Throwable{

    public BusinessAlreadyExistsException(String message){
        super(message);
    }
}