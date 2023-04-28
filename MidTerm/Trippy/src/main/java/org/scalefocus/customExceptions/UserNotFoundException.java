package org.scalefocus.customExceptions;

import java.io.UncheckedIOException;

public class UserNotFoundException extends Throwable {

    public UserNotFoundException(String message){
        super(message);
    }
}
