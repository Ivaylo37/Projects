package org.scalefocus.user;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    private final UserAccessor userAccessor;

    public UserService(UserAccessor userAccessor) {
        this.userAccessor = userAccessor;
    }

    public List<User> getAllUsers(){
        return userAccessor.getAllUsers();
    }
}
