package org.scalefocus.customExceptions;

public class ReviewNotFoundException extends Throwable{

    public ReviewNotFoundException(String message){
        super(message);
    }
}
