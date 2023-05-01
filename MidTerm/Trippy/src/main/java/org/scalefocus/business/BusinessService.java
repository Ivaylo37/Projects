package org.scalefocus.business;

import org.scalefocus.customExceptions.*;
import org.scalefocus.review.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BusinessService {

    private final BusinessAccessor businessAccessor;

    @Autowired
    public BusinessService(BusinessAccessor businessAccessor) {
        this.businessAccessor = businessAccessor;
    }

    public List<Business> getAllBusinesses(){
        return setReviewsToBusinesses(businessAccessor.getAllBusinesses());
    }

    public List<Business> getBusinessesByType(String type) throws BusinessNotFoundException {
        return setReviewsToBusinesses(businessAccessor.getBusinessByType(type));
    }

    public List<Business> getBusinessesByCity(String city) throws BusinessNotFoundException {
        return setReviewsToBusinesses(businessAccessor.getBusinessByCity(city));
    }

    public List<Business> getBusinessesByRating(int rating) throws BusinessNotFoundException {
        return setReviewsToBusinesses(businessAccessor.getBusinessByRating(rating));
    }

    public Business getBusinessById(int businessId) throws BusinessNotFoundException {
        List<Business> businesses = new ArrayList<>();
        businesses.add(businessAccessor.getBusinessById(businessId));
        setReviewsToBusinesses(businesses);
        return businesses.get(0);
    }

    public List<Review> getReviewsByBusiness(int businessId) {
        return businessAccessor.getReviewsByBusiness(businessId);
    }
    public List<Business> setReviewsToBusinesses(List<Business> businesses){
        List<Business> businessesWithReviews = new ArrayList<>();
        for(Business business : businesses){
            business.setReviews(getReviewsByBusiness(business.getId()));
            businessesWithReviews.add(business);
        }
        return businessesWithReviews;
    }

    public void calculateAndSetRating(int businessId, int rating){
        Business businessToBeEdited;
        try {
            businessToBeEdited = getBusinessById(businessId);
        } catch (BusinessNotFoundException e) {
            throw new RuntimeException(e);
        }
        int oldRating = businessToBeEdited.getRating();
        if (oldRating == 0){
            updateRating(businessId, rating * 10);
        }else {
            int newRating = ((oldRating * 10) + (rating * 10)) / 2;
            updateRating(businessId, newRating);
        }
    }

    public BusinessRequest addBusiness(BusinessRequest businessRequest) throws InvalidTypeException, InvalidEmailException, InvalidPhoneNumberFormatException, InvalidCityException, InvalidNameException {
        validateType(businessRequest.getType());
        validateEmail(businessRequest.getEmail());
        validatePhoneNumber(businessRequest.getPhone());
        validateCity(businessRequest.getCity());
        validateName(businessRequest.getName());
        BusinessRequest validatedBusinessRequest = new BusinessRequest(businessRequest.getType(), businessRequest.getName(),
                businessRequest.getCity(), businessRequest.getPhone(), businessRequest.getEmail());
        return businessAccessor.addBusiness(validatedBusinessRequest);
    }

    public void validateType(String type) throws InvalidTypeException {
        List<String> validTypes = new ArrayList<>();
        validTypes.add("bar");
        validTypes.add("hotel");
        validTypes.add("restaurant");
        if (!validTypes.contains(type)){
            throw new InvalidTypeException("The type is invalid. Valid types : bar, hotel, restaurant");
        }
    }
    private void validateEmail(String email) throws InvalidEmailException {
        if (email.length() > 30){
            throw new InvalidEmailException("Max length 30");
        }
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()){
            throw new InvalidEmailException("The email is invalid.");
        }
    }
    private void validatePhoneNumber(String number) throws InvalidPhoneNumberFormatException {
        String regex = "08[7-9][0-9]{7}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()){
            throw new InvalidPhoneNumberFormatException("The format is invalid. Should be : 08(7/8/9).......");
        }
    }
    private void validateCity(String city) throws InvalidCityException {
        if (city.length() == 0){
            throw new InvalidCityException("The city field can't be empty");
        }
        if (city.length() > 20){
            throw new InvalidCityException("Max length 20");
        }
    }
    private void validateName(String name) throws InvalidNameException {
        if (name.length() == 0 || name.length() > 30){
            throw new InvalidNameException("The name must be between 0 and 30 characters.");
        }
    }
    private void updateRating(int businessId, int rating){
        businessAccessor.updateRating(businessId, rating);
    }
    public void updateReviewsCount(int businessId){
        int reviewsCount = 0;
        try {
            reviewsCount = getBusinessById(businessId).getReviews().size();
        } catch (BusinessNotFoundException e) {
            throw new RuntimeException(e);
        }
        businessAccessor.updateReviewsCount(businessId, reviewsCount);
    }
}