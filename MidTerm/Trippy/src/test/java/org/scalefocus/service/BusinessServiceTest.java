package org.scalefocus.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.scalefocus.accessor.BusinessAccessor;
import org.scalefocus.enums.Type;
import org.scalefocus.exception.*;
import org.scalefocus.model.Business;
import org.scalefocus.model.request.BusinessRequest;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.scalefocus.enums.Type.BAR;
import static org.scalefocus.enums.Type.RESTAURANT;

@RunWith(MockitoJUnitRunner.class)
public class BusinessServiceTest {
    @Mock
    private BusinessAccessor businessAccessor;

    @InjectMocks
    private BusinessService businessService;

    @Test
    public void testUpdateRating() {
        int businessId = 1;
        int rating = 4;
        doNothing().when(businessAccessor).updateRating(businessId, rating);
        businessService.updateRating(businessId, rating);
        Mockito.verify(businessAccessor, times(1)).updateRating(businessId, rating);
    }

    @Test(expected = BusinessAlreadyExistsException.class)
    public void testValidateUniqueBusiness() {
        String businessName = "Test";
        String businessCity = "Test";
        Business existingBusiness = new Business(1, businessName, BAR,
                1, businessCity, "0881234567", "test@gmail.com");
        when(businessAccessor.getBusinessByNameAndCity(businessName, businessCity))
                .thenReturn(existingBusiness);
        businessService.validateUniqueBusiness(businessName, businessCity);
    }

    @Test(expected = InvalidNameException.class)
    public void testValidateNameWithEmptyName() throws InvalidNameException {
        String name = "";
        businessService.validateName(name);
    }

    @Test(expected = InvalidNameException.class)
    public void testValidateNameWithTooLongName() throws InvalidNameException {
        String name = "This is a very long business name that exceeds the maximum length allowed by the application";
        businessService.validateName(name);
    }

    @Test
    public void testValidateNameWithValidName() throws InvalidNameException {
        String name = "Valid Business Name";
        businessService.validateName(name);
    }

    @Test(expected = InvalidCityException.class)
    public void testValidateCityWithEmptyCity() throws InvalidCityException {
        String city = "";
        businessService.validateCity(city);
    }

    @Test(expected = InvalidCityException.class)
    public void testValidateCityWithTooLongCity() throws InvalidCityException {
        String city = "This is a very long city name that exceeds the maximum length allowed by the application";
        businessService.validateCity(city);
    }

    @Test
    public void testValidateCityWithValidCity() throws InvalidCityException {
        String city = "Valid City Name";
        businessService.validateCity(city);
    }

    @Test(expected = InvalidPhoneNumberFormatException.class)
    public void testValidatePhoneNumberWithInvalidFormat() throws InvalidPhoneNumberFormatException {
        String phoneNumber = "123456789";
        businessService.validatePhoneNumber(phoneNumber);
    }

    @Test
    public void testValidatePhoneNumberWithValidFormat() throws InvalidPhoneNumberFormatException {
        String phoneNumber = "0877565112";
        businessService.validatePhoneNumber(phoneNumber);
    }

    @Test(expected = InvalidEmailException.class)
    public void testValidateEmailWithInvalidFormat() throws InvalidEmailException {
        String email = "test.com";
        businessService.validateEmail(email);
    }

    @Test
    public void testValidateEmailWithValidFormat() throws InvalidEmailException {
        String email = "test@test.com";
        businessService.validateEmail(email);
    }

    @Test
    public void testValidateTypeWithValidType() throws InvalidTypeException {
        Type type = RESTAURANT;
        businessService.validateType(type);
    }

    @Test(expected = InvalidPhoneNumberFormatException.class)
    public void testUpdatePhoneNumberWithInvalidPhoneNumber() throws InvalidPhoneNumberFormatException {
        String newPhoneNumber = "1234567";
        businessService.updatePhoneNumber(1, newPhoneNumber);
    }

    @Test
    public void testUpdatePhoneNumberWithValidPhoneNumber() throws InvalidPhoneNumberFormatException {
        String newPhoneNumber = "0877565112";
        businessService.updatePhoneNumber(1, newPhoneNumber);
        verify(businessAccessor).updatePhoneNumber(1, newPhoneNumber);
    }

    @Test
    public void testCreateBusiness() throws InvalidTypeException, InvalidPhoneNumberFormatException, InvalidNameException {
        BusinessRequest request = new BusinessRequest(BAR, "test", "test", "0877555111", "test@gmail.com");
        when(businessService.createBusiness(request)).thenReturn(request);
        BusinessRequest result = businessService.createBusiness(request);
        assertNotNull(result);
        assertEquals(request.getName(), result.getName());
        assertEquals(request.getCity(), result.getCity());
        assertEquals(request.getType(), result.getType());
        assertEquals(request.getPhone(), result.getPhone());
        assertEquals(request.getEmail(), result.getEmail());
    }

    @Test
    public void testUpdateReviewsCount() {
        Business business = new Business(1, "test", BAR, 1, "test", "0877555111", "test@gmail.com");
        when(businessAccessor.getBusinessById(1)).thenReturn(business);
        businessService.updateReviewsCount(1);
        verify(businessAccessor, times(1)).updateReviewsCount(1, 0);
    }

}