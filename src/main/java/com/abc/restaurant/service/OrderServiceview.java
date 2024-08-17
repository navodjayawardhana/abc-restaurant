package com.abc.restaurant.service;


import com.abc.restaurant.dao.OrderDAOview;
import com.abc.restaurant.model.DailySales;
import com.abc.restaurant.model.Order;
import com.abc.restaurant.model.OrderItem;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceview {
    private OrderDAOview orderDAO;

    public OrderServiceview() {
        this.orderDAO = new OrderDAOview();  // Initialize DAO
    }

    public List<Order> getAllOrders() throws SQLException {
        return orderDAO.getAllOrders();
    }

    public List<OrderItem> getOrderItems(int orderId) throws SQLException {
        return orderDAO.getOrderItems(orderId);
    }

    public void markOrderAsComplete(int orderId) throws SQLException {
        orderDAO.updateOrderStatus(orderId, "Completed");
    }

    public Order getOrder(int orderId) throws SQLException {
        return orderDAO.getOrderById(orderId);
    }
    
    public BigDecimal calculateTotalEarnings() throws SQLException {
        return orderDAO.getTotalEarnings(); // This method will get the sum of all order item prices
    }

    public int getPendingOrderCount() throws SQLException {
        return orderDAO.getPendingOrderCount();
    }
    
    public List<DailySales> getDailySalesData() throws SQLException {
        return orderDAO.getDailySalesData();
    }
    
    public List<Order> getAllOrdersWithTotalPrice() throws SQLException {
        return orderDAO.getAllOrdersWithTotalPrice();
    }


}
