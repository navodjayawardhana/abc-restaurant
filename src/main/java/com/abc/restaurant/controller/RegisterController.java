package com.abc.restaurant.controller;

import com.abc.restaurant.model.User;
import com.abc.restaurant.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User(name, email, password, "CUSTOMER");
        boolean isRegistered = userService.registerUser(user);

        if (isRegistered) {
            response.sendRedirect("registrationSuccess.jsp");
        } else {
            response.sendRedirect("registrationFail.jsp");
        }
    }
}
