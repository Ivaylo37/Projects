package org.scalefocus.business;

import org.scalefocus.customExceptions.*;
import org.scalefocus.review.Review;
import org.scalefocus.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Business> getAllBusinesses() {
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

    public List<Business> setReviewsToBusinesses(List<Business> businesses) {
        List<Business> businessesWithReviews = new ArrayList<>();
        for (Business business : businesses) {
            business.setReviews(getReviewsByBusiness(business.getId()));
            businessesWithReviews.add(business);
        }
        return businessesWithReviews;
    }

    private void updateRating(int businessId, int rating) {
        businessAccessor.updateRating(businessId, rating);
    }

    public void updateReviewsCount(int businessId) {
        int reviewsCount = 0;
        try {
            reviewsCount = getBusinessById(businessId).getReviews().size();
        } catch (BusinessNotFoundException e) {
            throw new RuntimeException(e);
        }
        businessAccessor.updateReviewsCount(businessId, reviewsCount);
    }

    public void calculateAndSetRating(int businessId, int rating) {
        Business businessToBeEdited;
        try {
            businessToBeEdited = getBusinessById(businessId);
        } catch (BusinessNotFoundException e) {
            throw new RuntimeException(e);
        }
        int oldRating = businessToBeEdited.getRating();
        if (oldRating == 0) {
            updateRating(businessId, rating * 10);
        } else {
            int newRating = ((oldRating * 10) + (rating * 10)) / 2;
            updateRating(businessId, newRating);
        }
    }

    public BusinessRequest createBusiness(BusinessRequest businessRequest) throws InvalidTypeException, InvalidEmailException, InvalidPhoneNumberFormatException, InvalidCityException, InvalidNameException, BusinessAlreadyExistsException {
        validateType(businessRequest.getType());
        validateEmail(businessRequest.getEmail());
        validatePhoneNumber(businessRequest.getPhone());
        validateCity(businessRequest.getCity());
        validateName(businessRequest.getName());
        validateUniqueBusiness(businessRequest.getName(), businessRequest.getCity());
        BusinessRequest validatedBusinessRequest = new BusinessRequest(businessRequest.getType(), businessRequest.getName(),
                businessRequest.getCity(), businessRequest.getPhone(), businessRequest.getEmail());
        return businessAccessor.createBusiness(validatedBusinessRequest);
    }

    public void updateEmail(int businessId, String newEmail) throws InvalidEmailException {
        validateEmail(newEmail);
        businessAccessor.updateEmail(businessId, newEmail);
    }

    public void updatePhoneNumber(int businessId, String newPhoneNumber) throws InvalidPhoneNumberFormatException {
        validatePhoneNumber(newPhoneNumber);
        businessAccessor.updatePhoneNumber(businessId, newPhoneNumber);
    }

    public void validateType(String type) throws InvalidTypeException {
        List<String> validTypes = new ArrayList<>();
        validTypes.add("bar");
        validTypes.add("hotel");
        validTypes.add("restaurant");
        if (!validTypes.contains(type)) {
            throw new InvalidTypeException(Constants.INVALID_TYPE_MESSAGE);
        }
    }

    private void validateEmail(String email) throws InvalidEmailException {
        if (email.length() > Constants.EMAIL_MAX_LENGTH) {
            throw new InvalidEmailException(Constants.EMAIL_MAX_LENGTH_EXCEEDED_MESSAGE);
        }
        String regex = Constants.EMAIL_VALIDATION_REGEX;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailException(Constants.INVALID_EMAIL_FORMAT_MESSAGE);
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

    private void validateCity(String city) throws InvalidCityException {
        if (city.length() == 0) {
            throw new InvalidCityException(Constants.EMPTY_CITY_NAME_FIELD_MESSAGE);
        }
        if (city.length() > Constants.CITY_NAME_MAX_LENGTH) {
            throw new InvalidCityException(Constants.CITY_NAME_MAX_LENGTH_EXCEEDED_MESSAGE);
        }
    }

    private void validateName(String name) throws InvalidNameException {
        if (name.length() == 0 || name.length() > Constants.BUSINESS_NAME_MAX_LENGTH) {
            throw new InvalidNameException(Constants.BUSINESS_NAME_INVALID_LENGTH_MESSAGE);
        }
    }

    public void validateUniqueBusiness(String businessName, String businessCity) throws BusinessAlreadyExistsException {
        if (businessAccessor.getBusinessByNameAndCity(businessName, businessCity) != null) {
            throw new BusinessAlreadyExistsException(Constants.BUSINESS_NOT_UNIQUE_MESSAGE);
        }
    }
}