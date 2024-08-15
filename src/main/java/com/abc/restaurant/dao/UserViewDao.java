package com.abc.restaurant.dao;

import com.abc.restaurant.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserViewDao {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    // Create a new user
    public boolean saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email, password, role, is_active, activation_code) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.setBoolean(5, user.isActive());
            statement.setString(6, user.getActivationCode());
            return statement.executeUpdate() > 0;
        }
    }

    // Retrieve a user by ID
    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        }
        return null;
    }

    // Retrieve all users
    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        }
        return users;
    }

    // Update an existing user
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, email = ?, password = ?, role = ?, is_active = ?, activation_code = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.setBoolean(5, user.isActive());
            statement.setString(6, user.getActivationCode());
            statement.setInt(7, user.getId());
            return statement.executeUpdate() > 0;
        }
    }

    // Delete a user by ID
    public boolean deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    // Helper method to map ResultSet to User object
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        user.setActive(rs.getBoolean("is_active"));
        user.setActivationCode(rs.getString("activation_code"));
        return user;
    }
}
