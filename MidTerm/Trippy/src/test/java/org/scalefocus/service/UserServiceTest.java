package org.scalefocus.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.scalefocus.accessor.UserAccessor;
import org.scalefocus.exception.InvalidCityException;
import org.scalefocus.exception.InvalidEmailException;
import org.scalefocus.exception.InvalidPhoneNumberFormatException;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserAccessor userAccessor;

    @InjectMocks
    private UserService userService;

    @Test(expected = InvalidCityException.class)
    public void testValidateCityWithEmptyCity() throws InvalidCityException {
        String city = "";
        userService.validateCity(city);
    }

    @Test
    public void testValidateCityWithValidCity() throws InvalidCityException {
        String city = "Valid City Name";
        userService.validateCity(city);
    }

    @Test(expected = InvalidPhoneNumberFormatException.class)
    public void testValidatePhoneNumberWithInvalidFormat() throws InvalidPhoneNumberFormatException {
        String phoneNumber = "123456789";
        userService.validatePhoneNumber(phoneNumber);
    }

    @Test
    public void testValidatePhoneNumberWithValidFormat() throws InvalidPhoneNumberFormatException {
        String phoneNumber = "0877565112";
        userService.validatePhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidEmailException.class)
    public void testValidateEmailWithInvalidFormat() throws InvalidEmailException {
        String email = "test.com";
        userService.validateEmail(email);
    }

    @Test
    public void testValidateEmailWithValidFormat() throws InvalidEmailException {
        String email = "test@test.com";
        userService.validateEmail(email);
    }
}