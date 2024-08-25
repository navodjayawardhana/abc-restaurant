package com.abc.restaurant.dao;

import com.abc.restaurant.model.Rating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingDAO {
    private Connection connection;

    public RatingDAO() {
        connection = DatabaseConnection.getConnection();
    }

    public void saveRating(Rating rating) throws SQLException {
        String sql = "INSERT INTO ratings (order_id, rating, feedback) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, rating.getOrderId());
        statement.setInt(2, rating.getRating());
        statement.setString(3, rating.getFeedback());
        statement.executeUpdate();
    }

    public List<Rating> getAllRatings() throws SQLException {
        List<Rating> ratings = new ArrayList<>();
      
        String sql = "SELECT r.id, r.order_id, r.rating, r.feedback, o.customer_name " +
                     "FROM ratings r " +
                     "JOIN orders o ON r.order_id = o.id";  
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String customerName = rs.getString("customer_name"); 
            Rating rating = new Rating(rs.getInt("id"), rs.getInt("order_id"), rs.getInt("rating"), rs.getString("feedback"), customerName);
            ratings.add(rating);
        }

        return ratings;
    }



    public void deleteRating(int ratingId) throws SQLException {
        String sql = "DELETE FROM ratings WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, ratingId);
        stmt.executeUpdate();
    }
}
