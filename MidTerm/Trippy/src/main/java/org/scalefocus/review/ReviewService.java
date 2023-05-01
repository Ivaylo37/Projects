package org.scalefocus.review;

import org.scalefocus.customExceptions.InvalidFeedbackException;
import org.scalefocus.customExceptions.InvalidRatingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewAccessor reviewAccessor;
    @Autowired
    public ReviewService(ReviewAccessor reviewAccessor) {
        this.reviewAccessor = reviewAccessor;
    }

    public List<Review> reviews(){
        return reviewAccessor.getAllReviews();
    }

    public void createReview(int userId, int businessId, int rating, String feedback) throws InvalidFeedbackException, InvalidRatingException {
        validateRating(rating);
        validateFeedback(feedback);
        reviewAccessor.createReview(userId, businessId, rating, feedback);
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