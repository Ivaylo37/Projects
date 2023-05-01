package org.scalefocus.user;

import org.scalefocus.customExceptions.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserAccessor userAccessor;

    public UserService(UserAccessor userAccessor) {
        this.userAccessor = userAccessor;
    }

    public List<User> getAllUsers() {
        return userAccessor.getAllUsers();
    }

    public User createUser(String username, String email, String phone, String city) throws InvalidUsernameException, InvalidEmailException, InvalidPhoneNumberFormatException, InvalidCityException {
        validateUsername(username);
        validateEmail(email);
        validatePhoneNumber(phone);
        validateCity(city);
        return userAccessor.createUser(username, email, phone, city);
    }

    public User findUserByEmail(String email) throws UserNotFoundException {
        return userAccessor.findUserByEmail(email);
    }

    public User findUserByUsername(String username) throws UserNotFoundException {
        return userAccessor.findUserByUsername(username);
    }

    public User findUserById(int id) throws UserNotFoundException {
        return userAccessor.findUserById(id);
    }

    private void validateEmail(String email) throws InvalidEmailException {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailException("The email is invalid.");
        }
    }

    private void validateUsername(String username) throws InvalidUsernameException {
        if (username.length() < 4) {
            throw new InvalidUsernameException("The username should contain at least 4 characters!");
        }
        User user = null;
        try {
            user = findUserByUsername(username);
        } catch (UserNotFoundException e) {//TODO

        }
        if (user != null){
            throw new InvalidUsernameException("Username already taken");
        }
    }

    private String validatePhoneNumber(String number) throws InvalidPhoneNumberFormatException {
        String regex = "08[7-9][0-9]{7}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        if (matcher.matches()) {
            return number;
        } else {
            throw new InvalidPhoneNumberFormatException("The format is invalid. Should be : 08(7/8/9).......");
        }
    }

    private void validateCity(String city) throws InvalidCityException {
        if (city.length() == 0) {
            throw new InvalidCityException("The city field can't be empty");
        }
    }
}