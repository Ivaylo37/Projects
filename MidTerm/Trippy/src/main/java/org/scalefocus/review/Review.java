package org.scalefocus.review;

import java.time.LocalDate;

public class Review {
    private int id;
    private int user;
    private int business;
    private int rating;
    private String feedback;
    private LocalDate stampCreated;

    public Review(int id, int user, int business, int rating, String feedback, LocalDate stampCreated) {
        this.id = id;
        this.user = user;
        this.business = business;
        this.rating = rating;
        this.feedback = feedback;
        this.stampCreated = stampCreated;
    }

    public int getId() {
        return id;
    }

    public int getUser() {
        return user;
    }

    public int getBusiness() {
        return business;
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
