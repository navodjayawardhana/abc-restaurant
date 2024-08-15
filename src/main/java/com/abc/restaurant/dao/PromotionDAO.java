package com.abc.restaurant.dao;

import com.abc.restaurant.model.Promotion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAO {
    private Connection connection;

    public PromotionDAO(Connection connection) {
        this.connection = connection;
    }

    // Create a new promotion
    public void createPromotion(Promotion promotion) throws SQLException {
        String sql = "INSERT INTO promotions (title, prestige, description, image_path) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, promotion.getTitle());
            statement.setInt(2, promotion.getPrestige());
            statement.setString(3, promotion.getDescription());
            statement.setString(4, promotion.getImagePath());
            statement.executeUpdate();
        }
    }

    // Retrieve a promotion by ID
    public Promotion getPromotionById(int id) throws SQLException {
        String sql = "SELECT * FROM promotions WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Promotion(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("prestige"),
                        resultSet.getString("description"),
                        resultSet.getString("image_path")
                    );
                }
            }
        }
        return null;
    }

    // Update a promotion
    public void updatePromotion(Promotion promotion) throws SQLException {
        String sql = "UPDATE promotions SET title = ?, prestige = ?, description = ?, image_path = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, promotion.getTitle());
            statement.setInt(2, promotion.getPrestige());
            statement.setString(3, promotion.getDescription());
            statement.setString(4, promotion.getImagePath());
            statement.setInt(5, promotion.getId());
            statement.executeUpdate();
        }
    }

    // Delete a promotion
    public void deletePromotion(int id) throws SQLException {
        String sql = "DELETE FROM promotions WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Retrieve all promotions
    public List<Promotion> getAllPromotions() throws SQLException {
        List<Promotion> promotions = new ArrayList<>();
        String sql = "SELECT * FROM promotions";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                promotions.add(new Promotion(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getInt("prestige"),
                    resultSet.getString("description"),
                    resultSet.getString("image_path")
                ));
            }
        }
        return promotions;
    }
}
