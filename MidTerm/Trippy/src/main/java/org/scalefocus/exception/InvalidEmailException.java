package org.scalefocus.exception;

public class InvalidEmailException extends Throwable{

    public InvalidEmailException(String message){
        super(message);
    }
}
