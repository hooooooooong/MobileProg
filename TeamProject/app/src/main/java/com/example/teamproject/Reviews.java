package com.example.teamproject;

public class Reviews {
    public double rating;
    public String review;
    public String date;

    public Reviews(){
        // Default constructor required for calls to DataSnapshot.getValue(Reviews.class)
    }

    public Reviews(double rating, String review, String date){
        this.rating = rating;
        this.review = review;
        this.date = date;
    }
}
