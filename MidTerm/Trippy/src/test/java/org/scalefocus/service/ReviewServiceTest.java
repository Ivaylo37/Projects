package org.scalefocus.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.scalefocus.accessor.ReviewAccessor;
import org.scalefocus.exception.InvalidFeedbackException;
import org.scalefocus.exception.InvalidRatingException;
import org.scalefocus.model.Review;
import org.scalefocus.util.Constants;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceTest {
    @Mock
    private ReviewAccessor reviewAccessor;
    @Mock
    private UserService userService;
    @Mock
    private BusinessService businessService;

    @InjectMocks
    private ReviewService reviewService;

    @Test(expected = InvalidRatingException.class)
    public void testInvalidRating() throws InvalidRatingException {
        int invalidRating = 0;
        reviewService.validateRating(invalidRating);
    }

    @Test
    public void testValidRating() throws InvalidRatingException {
        int validRating = 4; // rating within the allowed range
        reviewService.validateRating(validRating);
    }

    @Test
    public void testValidateFeedback() {
        String feedback = "This is a valid feedback.";
        try {
            reviewService.validateFeedback(feedback);
        } catch (InvalidFeedbackException e) {
            fail("Valid feedback should not throw an exception.");
        }

        feedback = "";
        try {
            reviewService.validateFeedback(feedback);
            fail("Empty feedback should throw an exception.");
        } catch (InvalidFeedbackException e) {
            assertEquals(Constants.EMPTY_FEEDBACK_MESSAGE, e.getMessage());
        }

        feedback = null;
        try {
            reviewService.validateFeedback(feedback);
            fail("Null feedback should throw an exception.");
        } catch (InvalidFeedbackException e) {
            assertEquals(Constants.EMPTY_FEEDBACK_MESSAGE, e.getMessage());
        }
    }

    @Test
    public void testGetAllReviews() {
        List<Review> reviewList = new ArrayList<>();
        Review review1 = new Review(1, 1, 1, 1, "Great", LocalDate.now());
        reviewList.add(review1);
        Review review2 = new Review(2, 2, 2, 2, "Great", LocalDate.now());
        reviewList.add(review2);
        when(reviewAccessor.getAllReviews()).thenReturn(reviewList);

        List<Review> actualResult = reviewService.getAllReviews();

        assertEquals(reviewList.size(), actualResult.size());
        assertEquals(reviewList.get(0).getId(), actualResult.get(0).getId());
        assertEquals(reviewList.get(0).getRating(), actualResult.get(0).getRating());
        assertEquals(reviewList.get(0).getFeedback(), actualResult.get(0).getFeedback());
        assertEquals(reviewList.get(1).getId(), actualResult.get(1).getId());
        assertEquals(reviewList.get(1).getRating(), actualResult.get(1).getRating());
        assertEquals(reviewList.get(1).getFeedback(), actualResult.get(1).getFeedback());
    }
}
