package org.scalefocus.review;

import org.scalefocus.business.BusinessService;
import org.scalefocus.customExceptions.*;
import org.scalefocus.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewAccessor reviewAccessor;
    private final UserService userService;
    private final BusinessService businessService;
    @Autowired
    public ReviewService(ReviewAccessor reviewAccessor, UserService userService, BusinessService businessService) {
        this.reviewAccessor = reviewAccessor;
        this.userService = userService;
        this.businessService = businessService;
    }

    public List<Review> getAllReviews(){
        return reviewAccessor.getAllReviews();
    }

    public void createReview(int userId, int businessId, int rating, String feedback) throws InvalidFeedbackException, InvalidRatingException, UserNotFoundException, BusinessNotFoundException {
        validateUserId(userId);
        validateBusinessId(businessId);
        validateRating(rating);
        validateFeedback(feedback);
        reviewAccessor.createReview(userId, businessId, rating, feedback);
        businessService.calculateAndSetRating(businessId, rating);
        businessService.updateReviewsCount(businessId);
    }

    public List<Review> getReviewsByBusiness(int businessId) throws ReviewNotFoundException {
        return reviewAccessor.getReviewsByBusiness(businessId);
    }

    public void validateUserId(int userId) throws UserNotFoundException {
        userService.getUserById(userId);
    }

    public void validateBusinessId(int businessId) throws BusinessNotFoundException {
        businessService.getBusinessById(businessId);
    }
    public void validateRating(int rating) throws InvalidRatingException {
        if (rating < 1 || rating > 5){
            throw new InvalidRatingException("Rating should be 1 and 5");
        }
    }
    public void validateFeedback(String feedback) throws InvalidFeedbackException {
        if (feedback == null || feedback.length() == 0){
            throw new InvalidFeedbackException("The feedback can't be empty.");
        }
    }
}