import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.scalefocus.customExceptions.*;
import org.scalefocus.user.User;
import org.scalefocus.user.UserService;

import java.time.LocalDate;

public class UserServiceTest {
    @Mock
    UserService userService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    //createUser_Throws_When_UsernameIsInvalid
    @Test
    public void createUser_Works_Properly() throws InvalidUsernameException, InvalidPhoneNumberFormatException, InvalidEmailException, InvalidCityException {
        //Arrange - Given
        String username = "Ivailo";
        String email = "ivssi@pederas.com";
        String phoneNumber = "0888888888";
        String cityName = "Galabovo";
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
        //TODO change the variable names !
    }
}