package org.scalefocus.customExceptions;

public class InvalidEmailException extends Throwable{

    public InvalidEmailException(String message){
        super(message);
    }
}
