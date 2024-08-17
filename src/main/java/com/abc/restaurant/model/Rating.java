package com.abc.restaurant.model;

public class Rating {
    private int id;  // Add this field if it's missing
    private int orderId;
    private int rating;
    private String feedback;
    private String username; 

    public Rating(int id, int orderId, int rating, String feedback, String username) {
        this.id = id;
        this.orderId = orderId;
        this.rating = rating;
        this.feedback = feedback;
        this.username = username;
    }
    // Constructor to be used when saving a new rating (without id)
    public Rating(int orderId, int rating, String feedback) {
        this.orderId = orderId;
        this.rating = rating;
        this.feedback = feedback;
    }
    // Getters and Setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
