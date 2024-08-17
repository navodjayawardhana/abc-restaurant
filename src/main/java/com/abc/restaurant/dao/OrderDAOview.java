package com.abc.restaurant.dao;

import com.abc.restaurant.model.DailySales;
import com.abc.restaurant.model.Order;
import com.abc.restaurant.model.OrderItem;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOview {
    private Connection connection;

    public OrderDAOview() {
        this.connection = DatabaseConnection.getConnection(); // Assume you have a connection manager
    }

    // Fetch all orders from the 'orders' table
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            orders.add(new Order(
                rs.getInt("id"),  // Fetch 'id' instead of 'order_id'
                rs.getString("customer_name"),
                rs.getString("customer_email"),
                rs.getString("address"),
                rs.getString("branch_id"),
                rs.getString("order_type"),
                rs.getString("payment_method"),
                rs.getString("status"),
                rs.getTimestamp("created_at")
            ));
        }
        return orders;
    }

    // Fetch all order items for a given order ID
    public List<OrderItem> getOrderItems(int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String query = "SELECT * FROM order_items WHERE order_id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, orderId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            items.add(new OrderItem(
                rs.getInt("id"),  // 'id' is the primary key for order_items
                rs.getInt("order_id"),
                rs.getString("product_name"),
                rs.getInt("quantity"),
                rs.getBigDecimal("price")
            ));
        }
        return items;
    }

    // Update order status in the 'orders' table
    public void updateOrderStatus(int orderId, String status) throws SQLException {
        String query = "UPDATE orders SET status = ? WHERE id = ?";  // Use 'id' instead of 'order_id'
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, status);
        stmt.setInt(2, orderId);
        stmt.executeUpdate();
    }

    // Fetch a single order by its ID
    public Order getOrderById(int orderId) throws SQLException {
        String query = "SELECT * FROM orders WHERE id = ?";  // Use 'id' instead of 'order_id'
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, orderId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Order(
                rs.getInt("id"),  // Fetch 'id' instead of 'order_id'
                rs.getString("customer_name"),
                rs.getString("customer_email"),
                rs.getString("address"),
                rs.getString("branch_id"),
                rs.getString("order_type"),
                rs.getString("payment_method"),
                rs.getString("status"),
                rs.getTimestamp("created_at")
            );
        }
        return null;
    }
    
    public BigDecimal getTotalEarnings() throws SQLException {
        String sql = "SELECT SUM(price * quantity) AS total_earnings FROM order_items";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getBigDecimal("total_earnings");
        }
        return BigDecimal.ZERO;
    }
    
    public int getPendingOrderCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS pending_count FROM orders WHERE status = 'Pending'";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("pending_count");
        }
        return 0;
    }
    
    public List<DailySales> getDailySalesData() throws SQLException {
        List<DailySales> salesList = new ArrayList<>();
        String query = "SELECT DATE(created_at) AS orderDate, SUM(quantity) AS totalProducts, SUM(price * quantity) AS totalIncome "
                     + "FROM orders o "
                     + "JOIN order_items oi ON o.id = oi.order_id "
                     + "GROUP BY DATE(created_at)";
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            salesList.add(new DailySales(
                rs.getDate("orderDate").toString(),
                rs.getInt("totalProducts"),
                rs.getBigDecimal("totalIncome")
            ));
        }
        return salesList;
    }

    public List<Order> getAllOrdersWithTotalPrice() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT o.id, o.customer_name, o.created_at, SUM(oi.price * oi.quantity) AS total_price, SUM(oi.quantity) AS total_quantity " +
                       "FROM orders o " +
                       "JOIN order_items oi ON o.id = oi.order_id " +
                       "WHERE o.status = 'Completed' " +
                       "GROUP BY o.id, o.customer_name, o.created_at";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            Order order = new Order(
                rs.getInt("id"),
                rs.getString("customer_name"),
                rs.getTimestamp("created_at"),
                rs.getBigDecimal("total_price"),
                rs.getInt("total_quantity")  // New field for total quantity
            );
            orders.add(order);
        }
        return orders;
    }




}
