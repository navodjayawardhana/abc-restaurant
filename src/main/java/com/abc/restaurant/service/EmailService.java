package com.abc.restaurant.service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailService {

    public static void sendEmail(String to, String subject, String body) {
        String from = "abcrest39@gmail.com";  // Your email
        String host = "smtp.gmail.com";  // Gmail SMTP server

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); // TLS port
        properties.put("mail.smtp.auth", "true");  // Enable authentication
        properties.put("mail.smtp.starttls.enable", "true");  // Enable TLS

        String username = "abcrest39@gmail.com";  // Gmail username
        String password = "qlszgczchqwnamyh";  // Gmail app-specific password

        // Get the session object
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set the From, To, Subject fields
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            // Set the email content in HTML format
            message.setContent(body, "text/html");

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully to " + to);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    // Method to send the order confirmation with a bill
    public static void sendOrderBillEmail(String customerEmail, String customerName, String orderDetails, double totalPrice) {
        String subject = "Your Order Bill - ABC Restaurant";

        // HTML content for the email
        String body = "<html>" +
                "<body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>" +
                "<div style='max-width: 600px; margin: 0 auto; background-color: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);'>" +
                "<h2 style='color: #333;'>Hello " + customerName + ",</h2>" +
                "<p style='color: #555;'>Thank you for your order! Here is the summary of your purchase:</p>" +
                "<table style='width: 100%; border-collapse: collapse;'>" +
                "<thead><tr>" +
                "<th style='text-align:left; padding: 8px; border-bottom: 1px solid #ddd;'>Item</th>" +
                "<th style='text-align:right; padding: 8px; border-bottom: 1px solid #ddd;'>Price (LKR)</th>" +
                "</tr></thead><tbody>" + orderDetails +
                "</tbody></table>" +
                "<p style='color: #555;'>Total: <strong>" + totalPrice + " LKR</strong></p>" +
                "<p style='color: #555;'>We hope you enjoy your meal! If you have any questions, feel free to contact us.</p>" +
                "<p style='color: #555;'>Best regards, <br>ABC Restaurant Team</p>" +
                "</div>" +
                "</body>" +
                "</html>";

        // Send the email
        sendEmail(customerEmail, subject, body);
    }
    
    
    public static void sendOrderCompletionEmail(String customerEmail, String customerName, int orderId) {
        String subject = "Your Order is Complete - ABC Restaurant";

        // HTML content for the order completion email with a rating link
        String body = "<html>" +
                "<body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>" +
                "<div style='max-width: 600px; margin: 0 auto; background-color: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);'>" +
                "<h2 style='color: #333;'>Order Completed Successfully!</h2>" +
                "<p style='color: #555;'>Hello " + customerName + ",</p>" +
                "<p style='color: #555;'>We're delighted to let you know that your order (ID: #" + orderId + ") has been completed successfully!</p>" +
                "<p style='color: #555;'>Thank you for choosing ABC Restaurant. We hope you enjoy your meal!</p>" +
                "<p style='color: #555;'>We would love to hear your feedback! Please <a href='http://localhost:8081/abc-restaurant/rate?orderId=" + orderId + "'>rate your experience</a>.</p>" +
                "<p style='color: #555;'>If you have any questions or concerns, feel free to reach out to us at <a href='mailto:contact@abcrestaurant.com'>contact@abcrestaurant.com</a>.</p>" +
                "<p style='color: #555;'>Best regards, <br><strong>ABC Restaurant Team</strong></p>" +
                "<hr style='border: none; border-top: 1px solid #eee;'/>" +
                "<p style='color: #aaa; font-size: 12px;'>This is an automated email. Please do not reply.</p>" +
                "</div>" +
                "</body>" +
                "</html>";

        // Send the email
        sendEmail(customerEmail, subject, body);
    }


}
