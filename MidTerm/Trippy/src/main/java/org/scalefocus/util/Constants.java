package org.scalefocus.util;

import jakarta.servlet.http.PushBuilder;

public class Constants {
   public static final String EMAIL_VALIDATION_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

   public static final String PHONE_VALIDATION_REGEX = "08[789]\\d{7}";

   public static final int EMAIL_MAX_LENGTH = 30;

   public static final int CITY_NAME_MAX_LENGTH = 20;

   public static final int BUSINESS_NAME_MAX_LENGTH = 30;

   public static final int MIN_RATING_VALUE = 1;

   public static final int MAX_RATING_VALUE = 5;

   public static final int MIN_USERNAME_LENGTH = 4;

   public static final int MAX_USERNAME_LENGTH = 20;

   public static final String INVALID_TYPE_MESSAGE = "The type is invalid! Valid types : bar, hotel, restaurant";

   public static final String EMAIL_MAX_LENGTH_EXCEEDED_MESSAGE = "Max length 30";

   public static final String INVALID_EMAIL_FORMAT_MESSAGE = "The email is invalid.";

   public static final String INVALID_PHONE_FORMAT_MESSAGE = "The format is invalid. Should be : 08(7/8/9).......";

   public static final String CITY_NAME_MAX_LENGTH_EXCEEDED_MESSAGE = "Max length 20";

   public static final String EMPTY_CITY_NAME_FIELD_MESSAGE = "The city field can't be empty";

   public static final String BUSINESS_NAME_INVALID_LENGTH_MESSAGE = "The name must be between 0 and 30 characters.";

   public static final String BUSINESS_NOT_UNIQUE_MESSAGE = "Business with this name from this city already exists";

   public static final String INVALID_RATING_MESSAGE = "Rating should be 1 and 5";

   public static final String EMPTY_FEEDBACK_MESSAGE = "The feedback can't be empty.";

   public static final String INVALID_USERNAME_LENGTH_MESSAGE = "The username must contain from 4 to 20 characters";

   public static final String USERNAME_NOT_UNIQUE_MESSAGE = "Username already taken.";

}