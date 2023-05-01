package org.scalefocus.controller;

import org.scalefocus.exception.*;
import org.scalefocus.domain.Review;
import org.scalefocus.domain.request.ReviewRequest;
import org.scalefocus.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity getReviews(@RequestParam(required = false) Integer businessId) {
        List<Review> reviews;
        if (businessId != null) {
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

    @PostMapping
    public ResponseEntity createReview(@RequestBody ReviewRequest reviewRequest) {
        try {
            reviewService.createReview(reviewRequest.getUserId(), reviewRequest.getBusinessId(),
                    reviewRequest.getRating(), reviewRequest.getFeedback());
        } catch (InvalidFeedbackException | InvalidRatingException | UserNotFoundException |
                 BusinessNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.status(201).build();
    }
}