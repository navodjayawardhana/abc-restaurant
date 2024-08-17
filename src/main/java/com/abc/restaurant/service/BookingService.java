package com.abc.restaurant.service;

import com.abc.restaurant.dao.BookingDAO;
import com.abc.restaurant.model.Booking;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class BookingService {
    private BookingDAO bookingDAO;

    public BookingService() {
        bookingDAO = new BookingDAO();
    }

    public void addBooking(Booking booking) throws SQLException {
        bookingDAO.createBooking(booking);
    }

    public List<Booking> getAllBookings() throws SQLException {
        return bookingDAO.getAllBookings();
    }

    public void approveBooking(int id) throws SQLException {
        bookingDAO.updateBookingStatus(id, "Approved");
        Booking booking = bookingDAO.getBookingById(id);
        
        String subject = "Booking Approved";
        String content = "<html>"
            + "<body style='font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px;'>"
            + "<div style='background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);'>"
            + "<h2 style='color: #4CAF50;'>Booking Approved</h2>"
            + "<p style='font-size: 16px; color: #333;'>Dear " + booking.getUserName() + ",</p>"
            + "<p style='font-size: 16px; color: #333;'>Your table booking has been approved. Here are your booking details:</p>"
            + "<table border='1' cellpadding='10' cellspacing='0' style='border-collapse: collapse; width: 100%; font-size: 16px;'>"
            + "<tr><td><b>Name</b></td><td>" + booking.getUserName() + "</td></tr>"
            + "<tr><td><b>Email</b></td><td>" + booking.getUserEmail() + "</td></tr>"
            + "<tr><td><b>Phone</b></td><td>" + booking.getPhoneNumber() + "</td></tr>"
            + "<tr><td><b>Number of Persons</b></td><td>" + booking.getNumPersons() + "</td></tr>"
            + "<tr><td><b>Booking Date</b></td><td>" + booking.getBookingDate() + "</td></tr>"
            + "<tr><td><b>Status</b></td><td>" + booking.getStatus() + "</td></tr>"
            + "</table>"
            + "<p style='font-size: 16px; color: #333;'>We look forward to seeing you soon!</p>"
            + "<p style='font-size: 16px; color: #333;'>Sincerely,<br>ABC Restaurant Team</p>"
            + "</div>"
            + "</body>"
            + "</html>";
        
        sendEmail(booking.getUserEmail(), subject, content);
    }

    public void rejectBooking(int id) throws SQLException {
        bookingDAO.updateBookingStatus(id, "Rejected");
        Booking booking = bookingDAO.getBookingById(id);
        
        String subject = "Booking Rejected";
        String content = "<html>"
            + "<body style='font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px;'>"
            + "<div style='background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);'>"
            + "<h2 style='color: #f44336;'>Booking Rejected</h2>"
            + "<p style='font-size: 16px; color: #333;'>Dear " + booking.getUserName() + ",</p>"
            + "<p style='font-size: 16px; color: #333;'>We regret to inform you that your table booking has been rejected.</p>"
            + "<p style='font-size: 16px; color: #333;'>We apologize for the inconvenience.</p>"
            + "<p style='font-size: 16px; color: #333;'>Sincerely,<br>ABC Restaurant Team</p>"
            + "</div>"
            + "</body>"
            + "</html>";
        
        sendEmail(booking.getUserEmail(), subject, content);
    }

    public void deleteBooking(int id) throws SQLException {
        bookingDAO.deleteBooking(id);
    }

    // Send an email with HTML content and colors
    private void sendEmail(String to, String subject, String content) {
        String from = "abcrest39@gmail.com"; // Your Gmail
        String host = "smtp.gmail.com"; // Gmail SMTP server

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true"); // SSL must be enabled for port 465
        properties.put("mail.smtp.auth", "true");

        // Get the Session object and pass username and password
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("abcrest39@gmail.com", "qlszgczchqwnamyh"); // Replace with your actual email and app password
            }
        });

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(from));

            // Set To: header field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the HTML part of the email
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(content, "text/html");

            // Create multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);

            // Set the multipart content to the message
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            System.out.println("Email sent successfully to " + to);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
    
    public int getPendingOrderCount() throws SQLException {
        return bookingDAO.getPendingBookingCount();
    }
}
