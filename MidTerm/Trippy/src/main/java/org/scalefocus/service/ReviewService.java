package org.scalefocus.service;

import org.scalefocus.domain.Review;
import org.scalefocus.repository.ReviewAccessor;
import org.scalefocus.exception.*;
import org.scalefocus.util.Constants;
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

    public List<Review> getAllReviews() {
        return reviewAccessor.getAllReviews();
    }

    public void createReview(int userId, int businessId, int rating, String feedback) throws InvalidFeedbackException, InvalidRatingException, UserNotFoundException {
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

    public void validateBusinessId(int businessId) {
        businessService.getBusinessById(businessId);
    }

    public void validateRating(int rating) throws InvalidRatingException {
        if (rating < Constants.MIN_RATING_VALUE || rating > Constants.MAX_RATING_VALUE) {
            throw new InvalidRatingException(Constants.INVALID_RATING_MESSAGE);
        }
    }

    public void validateFeedback(String feedback) throws InvalidFeedbackException {
        if (feedback == null || feedback.length() == 0) {
            throw new InvalidFeedbackException(Constants.EMPTY_FEEDBACK_MESSAGE);
        }
    }
}