package com.abc.restaurant.model;

public class Booking {
    private int id;
    private String userName;
    private String userEmail;
    private String phoneNumber;
    private int numPersons;
    private String bookingDate;
    private String status;

    // Default constructor
    public Booking() {}

    // Constructor with all fields
    public Booking(int id, String userName, String userEmail, String phoneNumber, int numPersons, String bookingDate, String status) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.phoneNumber = phoneNumber;
        this.numPersons = numPersons;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    // Getters and setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getNumPersons() {
        return numPersons;
    }

    public void setNumPersons(int numPersons) {
        this.numPersons = numPersons;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
