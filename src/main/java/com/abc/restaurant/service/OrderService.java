package com.abc.restaurant.service;

import java.sql.SQLException;

import com.abc.restaurant.dao.OrderDAO;

public class OrderService {
    private OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public int createOrder(String customerName, String email, String address, int branchId, String orderType, String paymentMethod, double totalPrice) throws SQLException {
        return orderDAO.createOrder(customerName, email, address, branchId, orderType, paymentMethod, totalPrice);
    }

    public void addOrderItem(int orderId, String productName, int quantity, double price) throws SQLException {
        orderDAO.addOrderItem(orderId, productName, quantity, price);
    }
}
