package org.scalefocus.customExceptions;

public class InvalidRatingException extends Throwable{

    public InvalidRatingException(String message){
        super(message);
    }
}
