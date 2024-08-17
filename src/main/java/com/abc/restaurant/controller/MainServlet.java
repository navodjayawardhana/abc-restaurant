package com.abc.restaurant.controller;

import com.abc.restaurant.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Get the session, but don't create a new one if it doesn't exist

        if (session != null) {
            User user = (User) session.getAttribute("user");

            if (user != null) {
                String role = user.getRole();

                // Check if the role is 'customer' and redirect them
                if ("customer".equalsIgnoreCase(role)) {
                    response.sendRedirect("index"); // Redirect customer to their dashboard
                    return; // End execution
                }
            }
        }

        // If no user is logged in or the user is not a customer, forward to the main page
        request.getRequestDispatcher("main.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
