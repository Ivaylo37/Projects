package org.scalefocus.user;

import org.scalefocus.customExceptions.InvalidCityException;
import org.scalefocus.customExceptions.InvalidEmailException;
import org.scalefocus.customExceptions.InvalidPhoneNumberFormatException;
import org.scalefocus.customExceptions.InvalidUsernameException;
import org.springframework.stereotype.Component;

import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserService {

    private final UserAccessor userAccessor;

    public UserService(UserAccessor userAccessor) {
        this.userAccessor = userAccessor;
    }

    public List<User> getAllUsers(){
        return userAccessor.getAllUsers();
    }

    public User addUser(String username, String email, String phone,  String city){
        String validatedUsername = null;
        try {
            validatedUsername = validateUsername(username);
        } catch (InvalidUsernameException e) {
            throw new RuntimeException(e);
        }
        String validatedEmail = null;
        try {
            validatedEmail = validateEmail(email);
        } catch (InvalidEmailException e) {
            throw new RuntimeException(e);
        }
        String validatedPhone = null;
        try {
            validatedPhone = validatePhoneNumber(phone);
        } catch (InvalidPhoneNumberFormatException e) {
            throw new RuntimeException(e);
        }
        String validatedCity = null;
        try {
            validatedCity = validateCity(city);
        } catch (InvalidCityException e) {
            throw new RuntimeException(e);
        }
        User user = new User(validatedUsername, validatedEmail, validatedPhone, validatedCity);
        userAccessor.addUser(validatedUsername, validatedEmail, validatedPhone, validatedCity);
        return user;
    }

    public User findUserByEmail(String email){
        return userAccessor.findUserByEmail(email);
    }

    private String validateEmail(String email) throws InvalidEmailException{
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()){
            return email;
        }
        else {
            throw new InvalidEmailException("The email is invalid.");
        }
    }

    private String validateUsername(String username) throws InvalidUsernameException {
        if (username.length() < 4){
            throw new InvalidUsernameException("The username should contain at least 4 characters!");
        }
        return username;
    }

    private String validatePhoneNumber(String number) throws InvalidPhoneNumberFormatException {
        String regex = "08[7-9][0-9]{7}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        if (matcher.matches()){
            return number;
        }
        else{
            throw new InvalidPhoneNumberFormatException("The format is invalid. Should be : 08(7/8/9).......");
        }
    }

    private String validateCity(String city) throws InvalidCityException {
        if (city.length() == 0){
            throw new InvalidCityException("The city field can't be empty");
        }
        return city;
    }
}
