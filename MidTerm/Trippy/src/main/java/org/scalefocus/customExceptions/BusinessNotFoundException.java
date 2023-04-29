package org.scalefocus.customExceptions;

public class BusinessNotFoundException extends Throwable{

    public BusinessNotFoundException(String message){
        super(message);
    }
}
