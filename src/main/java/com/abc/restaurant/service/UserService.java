package com.abc.restaurant.service;

import com.abc.restaurant.dao.UserDao;
import com.abc.restaurant.model.User;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.util.UUID;

public class UserService {

    private UserDao userDao = new UserDao();

    public boolean registerUser(User user) {
        String activationCode = generateActivationCode();
        user.setActivationCode(activationCode);
        boolean isSaved = userDao.saveUser(user);
        if (isSaved) {
            sendConfirmationEmail(user.getEmail(), activationCode);
            return true;
        }
        return false;
    }

    public boolean activateAccount(String email, String activationCode) {
        return userDao.activateUser(email, activationCode);
    }

    public boolean validateUser(String email, String password) {
        User user = userDao.getUserByEmailAndPassword(email, password);
        return user != null && user.isActive();
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    private void sendConfirmationEmail(String email, String activationCode) {
        try {
            Email mail = new SimpleEmail();
            mail.setHostName("smtp.gmail.com"); 
            mail.setSmtpPort(465);
            mail.setAuthenticator(new DefaultAuthenticator("abcrest39@gmail.com", "qlszgczchqwnamyh"));
            mail.setSSLOnConnect(true);
            mail.setFrom("abcrest39@gmail.com", "ABC Restaurant"); 
            mail.setSubject("Activate Your Account");

           
            String htmlMessage = "<html>" +
                "<body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>" +
                "<div style='max-width: 600px; margin: 0 auto; background-color: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);'>" +
                "<h2 style='color: #333;'>Welcome to ABC Restaurant!</h2>" +
                "<p style='color: #555;'>Thank you for signing up. We're excited to have you on board!</p>" +
                "<p style='color: #555;'>To activate your account, simply click the button below:</p>" +
                "<a href='http://localhost:8081/abc-restaurant/activate?email=" + email + "&code=" + activationCode + "' " +
                "style='background-color: #28a745; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;'>Activate Account</a>" +
                "<p style='color: #555; margin-top: 20px;'>Or you can paste the following link into your browser:</p>" +
                "<p style='color: #28a745;'>http://localhost:8081/abc-restaurant/activate?email=" + email + "&code=" + activationCode + "</p>" +
                "<hr style='border: none; border-top: 1px solid #eee;'/>" +
                "<p style='color: #aaa; font-size: 12px;'>If you did not sign up for this account, please ignore this email.</p>" +
                "</div>" +
                "</body>" +
                "</html>";

           
            mail.setContent(htmlMessage, "text/html");

            mail.addTo(email);
            mail.setDebug(true); 
            mail.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }



    private String generateActivationCode() {
        return UUID.randomUUID().toString();
    }
}
