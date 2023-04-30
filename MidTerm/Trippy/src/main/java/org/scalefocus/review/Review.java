package org.scalefocus.review;

import java.time.LocalDate;

public class Review {
    private int id;
    private String userName;
    private String businessName;
    private int rating;
    private String feedback;
    private LocalDate stampCreated;

    public Review(int id, String userName, String businessName, int rating, String feedback) {
        this.id = id;
        this.userName = userName;
        this.businessName = businessName;
        this.rating = rating;
        this.feedback = feedback;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public int getRating() {
        return rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public LocalDate getStampCreated() {
        return stampCreated;
    }
}
