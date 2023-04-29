package org.scalefocus.business;

import org.scalefocus.customExceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BusinessService {

    private final BusinessAccessor businessAccessor;
    @Autowired
    public BusinessService(BusinessAccessor businessAccessor) {
        this.businessAccessor = businessAccessor;
    }

    public List<BusinessDto> getAllBusinesses(){
        return businessAccessor.getAllBusinesses();
    }

    public List<BusinessDto> getBusinessesByType(String type) throws BusinessNotFoundException {
        return businessAccessor.getBusinessByType(type);
    }

    public List<BusinessDto> getBusinessesByCity(String city) throws BusinessNotFoundException {
        return businessAccessor.getBusinessByCity(city);
    }

    public List<BusinessDto> getBusinessesByRating(int rating) throws BusinessNotFoundException {
        return businessAccessor.getBusinessByRating(rating);
    }

    public BusinessRequest addBusiness(BusinessRequest businessRequest) throws InvalidTypeException, InvalidEmailException, InvalidPhoneNumberFormatException, InvalidCityException {
        String validType = validateType(businessRequest.getType());
        String validEmail = validateEmail(businessRequest.getEmail());
        String validPhone = validatePhoneNumber(businessRequest.getPhone());
        String validCity = validateCity(businessRequest.getCity());
        BusinessRequest validatedBusinessRequest = new BusinessRequest(validType, validCity, validPhone, validEmail);
        return businessAccessor.addBusiness(validatedBusinessRequest);
    }

    public String validateType(String type) throws InvalidTypeException {
        List<String> validTypes = new ArrayList<>();
        validTypes.add("bar");
        validTypes.add("hotel");
        validTypes.add("restaurant");
        if (validTypes.contains(type)){
            return type;
        }
        else {
            throw new InvalidTypeException("The type is invalid. Valid types : bar, hotel, restaurant");
        }
    }
    private String validateEmail(String email) throws InvalidEmailException {
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