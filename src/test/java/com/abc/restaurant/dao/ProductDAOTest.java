package com.abc.restaurant.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.abc.restaurant.model.Product;

public class ProductDAOTest {

    private ProductDAO productDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws Exception {
        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);
        productDAO = new ProductDAO(mockConnection);
    }

    @Test
    public void testCreateProduct() throws SQLException {
        Product product = new Product(1, "Product1", "Description1", 100.0, true, "image1.png", "Category1");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        productDAO.createProduct(product);

        verify(mockPreparedStatement).setString(1, product.getName());
        verify(mockPreparedStatement).setString(2, product.getDescription());
        verify(mockPreparedStatement).setDouble(3, product.getPrice());
        verify(mockPreparedStatement).setBoolean(4, product.isAvailable());
        verify(mockPreparedStatement).setString(5, product.getImagePath());
        verify(mockPreparedStatement).setString(6, product.getCategory());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testGetProductById() throws SQLException {
        int productId = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(productId);
        when(mockResultSet.getString("name")).thenReturn("Product1");
        when(mockResultSet.getString("description")).thenReturn("Description1");
        when(mockResultSet.getDouble("price")).thenReturn(100.0);
        when(mockResultSet.getBoolean("available")).thenReturn(true);
        when(mockResultSet.getString("image_path")).thenReturn("image1.png");
        when(mockResultSet.getString("category")).thenReturn("Category1");

        Product product = productDAO.getProductById(productId);

        assertNotNull(product);
        assertEquals(productId, product.getId());
        assertEquals("Product1", product.getName());
        assertEquals("Description1", product.getDescription());
        assertEquals(100.0, product.getPrice(), 0);
        assertEquals("image1.png", product.getImagePath());
        assertEquals("Category1", product.getCategory());

        verify(mockPreparedStatement).setInt(1, productId);
    }

    @Test
    public void testUpdateProduct() throws SQLException {
        Product product = new Product(1, "UpdatedProduct", "UpdatedDescription", 150.0, false, "updated_image.png", "UpdatedCategory");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        productDAO.updateProduct(product);

        verify(mockPreparedStatement).setString(1, product.getName());
        verify(mockPreparedStatement).setString(2, product.getDescription());
        verify(mockPreparedStatement).setDouble(3, product.getPrice());
        verify(mockPreparedStatement).setBoolean(4, product.isAvailable());
        verify(mockPreparedStatement).setString(5, product.getImagePath());
        verify(mockPreparedStatement).setString(6, product.getCategory());
        verify(mockPreparedStatement).setInt(7, product.getId());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteProduct() throws SQLException {
        int productId = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        productDAO.deleteProduct(productId);

        verify(mockPreparedStatement).setInt(1, productId);
        verify(mockPreparedStatement).executeUpdate();
    }
}
