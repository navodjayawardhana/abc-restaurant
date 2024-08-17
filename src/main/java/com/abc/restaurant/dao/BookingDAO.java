package com.abc.restaurant.dao;

import com.abc.restaurant.model.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    private Connection connection;

    public BookingDAO() {
        connection = DatabaseConnection.getConnection();
    }

    // Create a new booking
    public void createBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO bookings (user_name, user_email, phone_number, num_persons, booking_date, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, booking.getUserName());
            stmt.setString(2, booking.getUserEmail());
            stmt.setString(3, booking.getPhoneNumber());
            stmt.setInt(4, booking.getNumPersons());
            stmt.setString(5, booking.getBookingDate());
            stmt.setString(6, booking.getStatus());
            stmt.executeUpdate();
        }
    }

    // Retrieve all bookings
    public List<Booking> getAllBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                bookings.add(new Booking(
                    rs.getInt("id"),
                    rs.getString("user_name"),
                    rs.getString("user_email"),
                    rs.getString("phone_number"),
                    rs.getInt("num_persons"),
                    rs.getString("booking_date"),
                    rs.getString("status")
                ));
            }
        }
        return bookings;
    }

    // Retrieve a booking by its ID
    public Booking getBookingById(int id) throws SQLException {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Booking(
                        rs.getInt("id"),
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        rs.getString("phone_number"),
                        rs.getInt("num_persons"),
                        rs.getString("booking_date"),
                        rs.getString("status")
                    );
                }
            }
        }
        return null;
    }

    // Update the status of a booking
    public void updateBookingStatus(int id, String status) throws SQLException {
        String sql = "UPDATE bookings SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    // Delete a booking by its ID
    public void deleteBooking(int id) throws SQLException {
        String sql = "DELETE FROM bookings WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public int getPendingBookingCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM bookings WHERE status = 'Pending'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
}
