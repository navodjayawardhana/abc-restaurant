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

        // Validate user credentials
        if (userService.validateUser(email, password)) {
            HttpSession session = request.getSession();
            User user = userService.getUserByEmail(email);

            // Set the user in the session
            session.setAttribute("user", user);

            // Check the role of the user
            String role = user.getRole();
            
            if ("customer".equals(role)) {
                // Redirect customer users to their dashboard
                response.sendRedirect("checkout");
            } else {
                // Allow other roles (e.g., admin, staff) to access the main page
                response.sendRedirect("main");
            }
        } else {
            // If validation fails, redirect to the login failure page
            response.sendRedirect("loginFail.jsp");
        }
    }
}
