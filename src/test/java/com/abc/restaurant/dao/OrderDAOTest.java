package com.abc.restaurant.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class OrderDAOTest {

    private OrderDAO orderDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws Exception {
        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);
        orderDAO = new OrderDAO(mockConnection);
    }

    @Test
    public void testCreateOrder() throws SQLException {
      
        String customerName = "Navod";
        String email = "navod@gmail.com";
        String address = "1234 Main St";
        int branchId = 1;
        String orderType = "Delivery";
        String paymentMethod = "Credit Card";
        double totalPrice = 100.50;

       
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

       
        int generatedOrderId = orderDAO.createOrder(customerName, email, address, branchId, orderType, paymentMethod, totalPrice);

       
        assertEquals(1, generatedOrderId);
        verify(mockPreparedStatement).setString(1, customerName);
        verify(mockPreparedStatement).setString(2, email);
        verify(mockPreparedStatement).setString(3, address);
        verify(mockPreparedStatement).setInt(4, branchId);
        verify(mockPreparedStatement).setString(5, orderType);
        verify(mockPreparedStatement).setString(6, paymentMethod);
        verify(mockPreparedStatement).setDouble(7, totalPrice);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testAddOrderItem() throws SQLException {
      
        int orderId = 1;
        String productName = "Pizza";
        int quantity = 2;
        double price = 20.0;

       
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

      
        orderDAO.addOrderItem(orderId, productName, quantity, price);

       
        verify(mockPreparedStatement).setInt(1, orderId);
        verify(mockPreparedStatement).setString(2, productName);
        verify(mockPreparedStatement).setInt(3, quantity);
        verify(mockPreparedStatement).setDouble(4, price);
        verify(mockPreparedStatement).executeUpdate();
    }
}
