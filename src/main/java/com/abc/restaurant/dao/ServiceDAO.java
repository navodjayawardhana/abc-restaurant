package com.abc.restaurant.dao;

import com.abc.restaurant.model.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {
    private Connection connection;

    public ServiceDAO(Connection connection) {
        this.connection = connection;
    }

    // Create a new service
    public void addService(Service service) throws SQLException {
        String sql = "INSERT INTO services (title, description) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, service.getTitle());
            statement.setString(2, service.getDescription());
            statement.executeUpdate();
        }
    }

    // Retrieve a service by its ID
    public Service getServiceById(int id) throws SQLException {
        String sql = "SELECT * FROM services WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Service(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description")
                    );
                }
            }
        }
        return null;
    }

    // Update an existing service
    public void updateService(Service service) throws SQLException {
        String sql = "UPDATE services SET title = ?, description = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, service.getTitle());
            statement.setString(2, service.getDescription());
            statement.setInt(3, service.getId());
            statement.executeUpdate();
        }
    }

    // Delete a service
    public void deleteService(int id) throws SQLException {
        String sql = "DELETE FROM services WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Retrieve all services
    public List<Service> getAllServices() throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM services";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                services.add(new Service(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description")
                ));
            }
        }
        return services;
    }
}
