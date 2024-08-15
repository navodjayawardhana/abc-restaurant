package com.abc.restaurant.dao;

import com.abc.restaurant.model.Head;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HeadDAO {
    private Connection connection;

    public HeadDAO(Connection connection) {
        this.connection = connection;
    }

    // Insert new head into database
    public void insertHead(Head head) throws SQLException {
        String sql = "INSERT INTO heads (title, description) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, head.getTitle());
            statement.setString(2, head.getDescription());
            statement.executeUpdate();
        }
    }

    // Retrieve head by ID
    public Head getHeadById(int id) throws SQLException {
        String sql = "SELECT * FROM heads WHERE id = ?";
        Head head = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                head = new Head(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description")
                );
            }
        }
        return head;
    }

    // Retrieve all heads
    public List<Head> getAllHeads() throws SQLException {
        List<Head> heads = new ArrayList<>();
        String sql = "SELECT * FROM heads";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                heads.add(new Head(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description")
                ));
            }
        }
        return heads;
    }

    // Update a head record
    public void updateHead(Head head) throws SQLException {
        String sql = "UPDATE heads SET title = ?, description = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, head.getTitle());
            statement.setString(2, head.getDescription());
            statement.setInt(3, head.getId());
            statement.executeUpdate();
        }
    }

    // Delete head by ID
    public void deleteHead(int id) throws SQLException {
        String sql = "DELETE FROM heads WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
