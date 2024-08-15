package com.abc.restaurant.dao;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.abc.restaurant.model.Product;

public class ProductDAO {
    private Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    // Create product with image path
    public void createProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, description, price, available, image_path, category) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setBoolean(4, product.isAvailable());
            statement.setString(5, product.getImagePath());
            statement.setString(6, product.getCategory()); 
            statement.executeUpdate();
        }
    }


    // Retrieve product by ID
    public Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getBoolean("available"),
                        resultSet.getString("image_path"),
                        resultSet.getString("category")  
                    );
                }
            }
        }
        return null;
    }


    // Update product with image path
    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, available = ?, image_path = ?, category = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setBoolean(4, product.isAvailable());
            statement.setString(5, product.getImagePath());
            statement.setString(6, product.getCategory());  
            statement.setInt(7, product.getId());
            statement.executeUpdate();
        }
    }


    // Delete product
    public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Retrieve all products
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                products.add(new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price"),
                    resultSet.getBoolean("available"),
                    resultSet.getString("image_path"),
                    resultSet.getString("category")  
                ));
            }
        }
        return products;
    }

       
}

