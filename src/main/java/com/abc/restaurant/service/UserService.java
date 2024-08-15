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
            mail.setFrom("abcrest39@gmail.com", "ABC Restaurant"); // Use the actual sender email
            mail.setSubject("Account Activation");
            mail.setMsg("Please click the following link to activate your account: " +
            	    "http://localhost:8081/abc-restaurant/activate?email=" + email + "&code=" + activationCode);

            mail.addTo(email);
            mail.setDebug(true); // Enable debugging for detailed output
            mail.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }


    private String generateActivationCode() {
        return UUID.randomUUID().toString();
    }
}
