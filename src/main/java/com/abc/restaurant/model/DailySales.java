package com.abc.restaurant.model;

import java.math.BigDecimal;

public class DailySales {
    private String orderDate;
    private int totalProducts;
    private BigDecimal totalIncome;

    public DailySales(String orderDate, int totalProducts, BigDecimal totalIncome) {
        this.orderDate = orderDate;
        this.totalProducts = totalProducts;
        this.totalIncome = totalIncome;
    }

    // Getters and Setters
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }
}
