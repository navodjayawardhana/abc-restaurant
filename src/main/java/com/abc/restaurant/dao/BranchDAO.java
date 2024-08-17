package com.abc.restaurant.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.abc.restaurant.model.Branch;

public class BranchDAO {
    private Connection connection;

    public BranchDAO(Connection connection) {
        this.connection = connection;
    }

    // Create branch
    public void createBranch(Branch branch) throws SQLException {
        String sql = "INSERT INTO branches (name, email, contact, address, password) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, branch.getName());
            statement.setString(2, branch.getEmail());
            statement.setString(3, branch.getContact());
            statement.setString(4, branch.getAddress());
            statement.setString(5, branch.getPassword());
            statement.executeUpdate();
        }
    }

    // Retrieve branch by ID
    public Branch getBranchById(int id) throws SQLException {
        String sql = "SELECT * FROM branches WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Branch(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("contact"),
                        resultSet.getString("address"),
                        resultSet.getString("password")
                    );
                }
            }
        }
        return null;
    }

    // Update branch
    public void updateBranch(Branch branch) throws SQLException {
        String sql = "UPDATE branches SET name = ?, email = ?, contact = ?, address = ?, password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, branch.getName());
            statement.setString(2, branch.getEmail());
            statement.setString(3, branch.getContact());
            statement.setString(4, branch.getAddress());
            statement.setString(5, branch.getPassword());
            statement.setInt(6, branch.getId());
            statement.executeUpdate();
        }
    }

    // Delete branch
    public void deleteBranch(int id) throws SQLException {
        String sql = "DELETE FROM branches WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Retrieve all branches
    public List<Branch> getAllBranches() throws SQLException {
        List<Branch> branches = new ArrayList<>();
        String sql = "SELECT * FROM branches";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                branches.add(new Branch(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("contact"),
                    resultSet.getString("address"),
                    resultSet.getString("password")
                ));
            }
        }
        return branches;
    }

    public int getTotalBranch() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM branches"; // Correct column alias
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getInt("total"); // Use the alias "total" instead of "total_count"
            }
        }
        return 0; // Return 0 if no branches are found
    }

}
