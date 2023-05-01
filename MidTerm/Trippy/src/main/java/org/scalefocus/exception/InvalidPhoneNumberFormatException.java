package org.scalefocus.exception;

public class InvalidPhoneNumberFormatException extends RuntimeException{

    public InvalidPhoneNumberFormatException(String message){
        super(message);
    }
}
