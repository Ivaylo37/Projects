package org.scalefocus.review;

public class ReviewRequest {

    private int userId;
    private int businessId;
    private int rating;
    private String feedback;

    public ReviewRequest(int userId, int businessId, int rating, String feedback) {
        this.userId = userId;
        this.businessId = businessId;
        this.rating = rating;
        this.feedback = feedback;
    }

    public int getUserId() {
        return userId;
    }

    public int getBusinessId() {
        return businessId;
    }

    public int getRating() {
        return rating;
    }

    public String getFeedback() {
        return feedback;
    }
}