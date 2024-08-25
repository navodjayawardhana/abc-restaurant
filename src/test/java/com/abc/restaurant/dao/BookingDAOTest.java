package com.abc.restaurant.dao;

import com.abc.restaurant.model.Booking;
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

public class BookingDAOTest {

    private BookingDAO bookingDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws Exception {
        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);
        bookingDAO = new BookingDAO();
    }

    @Test
    public void testCreateBooking() throws SQLException {
      
        String userName = "Navod";  
        String userEmail = "navod@gmail.com";
        String phoneNumber = "1234567890";
        int numPersons = 4;
        String bookingDate = "2024-08-25";
        String status = "Pending";

        Booking booking = new Booking(1, userName, userEmail, phoneNumber, numPersons, bookingDate, status);

       
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

       
        bookingDAO.createBooking(booking);

       
        verify(mockPreparedStatement).setString(1, userName);
        verify(mockPreparedStatement).setString(2, userEmail);
        verify(mockPreparedStatement).setString(3, phoneNumber);
        verify(mockPreparedStatement).setInt(4, numPersons);
        verify(mockPreparedStatement).setString(5, bookingDate);
        verify(mockPreparedStatement).setString(6, status);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testGetBookingById() throws SQLException {
      
        int bookingId = 1;
        String userName = "Navod"; 
        String userEmail = "navod@gmail.com";
        String phoneNumber = "1234567890";
        int numPersons = 4;
        String bookingDate = "2024-08-25";
        String status = "Pending";

      
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(bookingId);
        when(mockResultSet.getString("user_name")).thenReturn("Navod"); 
        when(mockResultSet.getString("user_email")).thenReturn(userEmail);
        when(mockResultSet.getString("phone_number")).thenReturn(phoneNumber);
        when(mockResultSet.getInt("num_persons")).thenReturn(numPersons);
        when(mockResultSet.getString("booking_date")).thenReturn(bookingDate);
        when(mockResultSet.getString("status")).thenReturn(status);

      
        Booking booking = bookingDAO.getBookingById(bookingId);

        
        assertNotNull(booking);
        assertEquals(bookingId, booking.getId());
        assertEquals(userName, booking.getUserName()); 
        assertEquals(userEmail, booking.getUserEmail());
        assertEquals(phoneNumber, booking.getPhoneNumber());
        assertEquals(numPersons, booking.getNumPersons());
        assertEquals(bookingDate, booking.getBookingDate());
        assertEquals(status, booking.getStatus());

       
        verify(mockPreparedStatement).setInt(1, bookingId);
    }

    @Test
    public void testUpdateBookingStatus() throws SQLException {
       
        int bookingId = 1;
        String newStatus = "Approved";

     
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

       
        bookingDAO.updateBookingStatus(bookingId, newStatus);

        
        verify(mockPreparedStatement).setString(1, newStatus);
        verify(mockPreparedStatement).setInt(2, bookingId);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteBooking() throws SQLException {
       
        int bookingId = 1;

       
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

       
        bookingDAO.deleteBooking(bookingId);

        
        verify(mockPreparedStatement).setInt(1, bookingId);
        verify(mockPreparedStatement).executeUpdate();
    }
}
