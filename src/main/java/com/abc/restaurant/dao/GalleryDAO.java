package com.abc.restaurant.dao;

import com.abc.restaurant.model.Gallery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GalleryDAO {
    private Connection connection;

    public GalleryDAO(Connection connection) {
        this.connection = connection;
    }

    // Add new gallery item
    public void addGallery(Gallery gallery) throws SQLException {
        String sql = "INSERT INTO gallery (name, image_path) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, gallery.getName());
            statement.setString(2, gallery.getImagePath());
            statement.executeUpdate();
        }
    }

    // Get gallery item by ID
    public Gallery getGalleryById(int id) throws SQLException {
        String sql = "SELECT * FROM gallery WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Gallery(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("image_path")
                    );
                }
            }
        }
        return null;
    }

    // Update gallery item
    public void updateGallery(Gallery gallery) throws SQLException {
        String sql = "UPDATE gallery SET name = ?, image_path = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, gallery.getName());
            statement.setString(2, gallery.getImagePath());
            statement.setInt(3, gallery.getId());
            statement.executeUpdate();
        }
    }

    // Delete gallery item
    public void deleteGallery(int id) throws SQLException {
        String sql = "DELETE FROM gallery WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Get all gallery items
    public List<Gallery> getAllGallery() throws SQLException {
        List<Gallery> galleries = new ArrayList<>();
        String sql = "SELECT * FROM gallery";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                galleries.add(new Gallery(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("image_path")
                ));
            }
        }
        return galleries;
    }
}
