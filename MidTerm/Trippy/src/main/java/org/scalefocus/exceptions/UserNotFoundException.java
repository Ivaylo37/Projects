package org.scalefocus.exceptions;

public class UserNotFoundException extends Throwable {

    public UserNotFoundException(String message){
        super(message);
    }
}
