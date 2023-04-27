package org.scalefocus.customExceptions;

public class InvalidPhoneNumberFormatException extends Throwable{

    public InvalidPhoneNumberFormatException(String message){
        super(message);
    }
}
