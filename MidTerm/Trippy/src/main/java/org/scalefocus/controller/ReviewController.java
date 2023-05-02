package org.scalefocus.controller;

import org.scalefocus.model.Review;
import org.scalefocus.model.request.ReviewRequest;
import org.scalefocus.service.ReviewService;
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
            return ResponseEntity.ok(reviewService.getReviewsByBusiness(businessId));
        }
        reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    public ResponseEntity createReview(@RequestBody ReviewRequest reviewRequest) {
        reviewService.createReview(reviewRequest.getUserId(), reviewRequest.getBusinessId(),
                reviewRequest.getRating(), reviewRequest.getFeedback());
        return ResponseEntity.status(201).build();
    }
}