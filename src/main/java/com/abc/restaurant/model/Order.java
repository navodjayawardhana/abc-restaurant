package com.abc.restaurant.model;

import java.sql.Timestamp;

public class Order {
    private int id;  // Use 'id' instead of 'orderId'
    private String name;
    private String email;
    private String address;
    private String branch;
    private String orderType;
    private String paymentMethod;
    private String status;
    private Timestamp createdAt;

    public Order(int id, String name, String email, String address, String branch, String orderType, String paymentMethod, String status, Timestamp createdAt) {
        this.id = id;  // 'id' corresponds to the 'id' field in the database
        this.name = name;
        this.email = email;
        this.address = address;
        this.branch = branch;
        this.orderType = orderType;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getter and setter for 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Other getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
