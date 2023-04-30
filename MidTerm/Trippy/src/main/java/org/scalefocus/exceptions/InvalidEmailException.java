package org.scalefocus.exceptions;

public class InvalidEmailException extends Throwable{

    public InvalidEmailException(String message){
        super(message);
    }
}
