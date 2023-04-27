package org.scalefocus.customExceptions;

public class InvalidUsernameException extends Throwable{

    public InvalidUsernameException(String message){
        super(message);
    }
}
