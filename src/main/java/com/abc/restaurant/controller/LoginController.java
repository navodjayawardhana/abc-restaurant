package com.abc.restaurant.controller;

import com.abc.restaurant.model.User;
import com.abc.restaurant.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Check if user credentials are valid
        if (userService.validateUser(email, password)) {
            HttpSession session = request.getSession();
            User user = userService.getUserByEmail(email);

            // Set user details in session
            session.setAttribute("user", user);

            String role = user.getRole();

            // Redirect based on the user role
            if ("customer".equalsIgnoreCase(role)) {
                // If role is 'customer', redirect to checkout
                response.sendRedirect("checkout");
            } else if ("admin".equalsIgnoreCase(role) || "staff".equalsIgnoreCase(role)) {
                // If role is 'admin' or 'staff', redirect to main dashboard
                response.sendRedirect("main");
            } else {
                // Default behavior for any other roles, go to login fail page
                response.sendRedirect("login.jsp");
            }
        } else {
            // Invalid login credentials, redirect to login fail page
            response.sendRedirect("login.jsp");
        }
    }
}
