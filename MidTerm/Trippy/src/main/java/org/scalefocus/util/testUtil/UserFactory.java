package org.scalefocus.util.testUtil;

import org.scalefocus.model.User;
import org.scalefocus.model.request.UserRequest;

import static org.scalefocus.util.testUtil.UserConstants.*;

public class UserFactory {

    private UserFactory(){
        throw new IllegalStateException();
    }

    public static User getDefaultUser(){
        return new User(USERNAME, USER_EMAIL, USER_PHONE, USER_CITY);
    }

    public static UserRequest getDefaultUserRequest(){
        return new UserRequest(USERNAME, USER_EMAIL, USER_PHONE, USER_CITY);
    }
}
