package org.scalefocus.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.scalefocus.exception.*;
import org.scalefocus.model.User;

import java.time.LocalDate;

import static org.testng.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    UserService userService;

    @Test
    public void testCreateUser_Works_Properly() throws InvalidUsernameException, InvalidPhoneNumberFormatException, InvalidEmailException {
        //Arrange - Given
        String username = "Ivailo";
        String email = "test@gmail.com";
        String phoneNumber = "0888888888";
        String cityName = "Sofia";
        //LocalDate maybe ...
        User user = new User(1, username, email, phoneNumber, cityName, LocalDate.now());

        //Act - When
        Mockito.when(userService.createUser(username, email, phoneNumber, cityName)).thenReturn(user);
        User objectToTest = userService.createUser(username, email, phoneNumber, cityName);

        //Assert - Then
        //assert the id !
        Assert.assertEquals(user.getUsername(), objectToTest.getUsername());
        Assert.assertEquals(email, objectToTest.getEmail());
        Assert.assertEquals(phoneNumber, objectToTest.getPhone());
        Assert.assertEquals(cityName, objectToTest.getCity());
        Assert.assertEquals(cityName, objectToTest.getCity());
    }

    @Test
    public void testCreateUser_Throws_When_UsernameLengthIsUnderMinLength() {
        //Arrange, Act
        String username = "I";
        String email = "test@gmail.com";
        String phoneNumber = "0888888888";
        String cityName = "Sofia";

          Mockito.when(userService.createUser(username, email, phoneNumber, cityName))
                  .thenThrow(InvalidUsernameException.class);

        //Assert
        assertThrows(InvalidUsernameException.class, () -> userService.createUser(username, email, phoneNumber, cityName));
    }
}