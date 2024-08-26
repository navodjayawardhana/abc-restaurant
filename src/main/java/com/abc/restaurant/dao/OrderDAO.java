package com.abc.restaurant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;  // Correct import for Statement

public class OrderDAO {
    private Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    public int createOrder(String customerName, String email, String address, int branchId, String orderType, String paymentMethod, double totalPrice) throws SQLException {
        String sql = "INSERT INTO orders (customer_name, customer_email, address, branch_id, order_type, payment_method, total_price, status) VALUES (?, ?, ?, ?, ?, ?, ?, 'Pending')";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {  // Use java.sql.Statement
            stmt.setString(1, customerName);
            stmt.setString(2, email);
            stmt.setString(3, address);
            stmt.setInt(4, branchId);
            stmt.setString(5, orderType);
            stmt.setString(6, paymentMethod);
            stmt.setDouble(7, totalPrice);
            stmt.executeUpdate();

           
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);  
            }
        }
        return -1;
    }

    public void addOrderItem(int orderId, String productName, int quantity, double price) throws SQLException {
        String sql = "INSERT INTO order_items (order_id, product_name, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.setString(2, productName);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);
            stmt.executeUpdate();
        }
    }
}
