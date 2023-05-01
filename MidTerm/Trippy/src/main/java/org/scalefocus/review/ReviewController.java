package org.scalefocus.review;

import org.scalefocus.customExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/review")
    public ResponseEntity getReviews(@RequestParam(required = false) Integer businessId){
        List<Review> reviews;
        if (businessId != null){
            try {
                reviews = reviewService.getReviewsByBusiness(businessId);
            } catch (ReviewNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.ok(reviews);
        }
        reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/review")
    public ResponseEntity createReview(@RequestBody ReviewRequest reviewRequest){
        try {
            reviewService.createReview(reviewRequest.getUserId(), reviewRequest.getBusinessId(),
                    reviewRequest.getRating(), reviewRequest.getFeedback());
        } catch (InvalidFeedbackException | InvalidRatingException | UserNotFoundException | BusinessNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.status(201).build();
    }
}