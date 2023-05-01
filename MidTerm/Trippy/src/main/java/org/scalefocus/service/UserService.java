package org.scalefocus.service;

import org.scalefocus.exception.*;
import org.scalefocus.domain.Review;
import org.scalefocus.domain.User;
import org.scalefocus.repository.UserAccessor;
import org.scalefocus.util.Constants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return setReviewsToUsers(userAccessor.getAllUsers());
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        return setReviewToUser(userAccessor.findUserByEmail(email));
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        return setReviewToUser(userAccessor.findUserByUsername(username));
    }

    public User getUserById(int id) throws UserNotFoundException {
        return setReviewToUser(userAccessor.findUserById(id));
    }

    public List<Review> getReviewsByUser(int userId) {
        return userAccessor.getReviewsByUser(userId);
    }

    public List<User> setReviewsToUsers(List<User> users) {
        List<User> usersWithReviews = new ArrayList<>();
        for (User user : users) {
            user.setReviews(getReviewsByUser(user.getId()));
            usersWithReviews.add(user);
        }
        return usersWithReviews;
    }

    public User setReviewToUser(User user) {
        user.setReviews(getReviewsByUser(user.getId()));
        return user;
    }

    public User createUser(String username, String email, String phone, String city) throws InvalidUsernameException, InvalidEmailException, InvalidPhoneNumberFormatException {
        validateUsername(username);
        validateEmail(email);
        validatePhoneNumber(phone);
        validateCity(city);
        return userAccessor.createUser(username, email, phone, city);
    }

    private void validateEmail(String email) throws InvalidEmailException {
        String regex = Constants.EMAIL_VALIDATION_REGEX;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailException(Constants.INVALID_EMAIL_FORMAT_MESSAGE);
        }
    }

    private void validateUsername(String username) throws InvalidUsernameException {
        if (username.length() < Constants.MIN_USERNAME_LENGTH || username.length() > Constants.MAX_USERNAME_LENGTH) {
            throw new InvalidUsernameException(Constants.INVALID_USERNAME_LENGTH_MESSAGE);
        }
        User user = null;
        try {
            user = getUserByUsername(username);
        } catch (UserNotFoundException e) {//TODO

        }
        if (user != null) {
            throw new InvalidUsernameException(Constants.USERNAME_NOT_UNIQUE_MESSAGE);
        }
    }

    private void validatePhoneNumber(String number) throws InvalidPhoneNumberFormatException {
        String regex = Constants.PHONE_VALIDATION_REGEX;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            throw new InvalidPhoneNumberFormatException(Constants.INVALID_PHONE_FORMAT_MESSAGE);
        }
    }

    private void validateCity(String city) {
        if (city.length() == 0) {
            throw new InvalidCityException(Constants.EMPTY_CITY_NAME_FIELD_MESSAGE);
        }
    }
}