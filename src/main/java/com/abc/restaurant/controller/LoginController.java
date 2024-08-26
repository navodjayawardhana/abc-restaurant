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

        HttpSession session = request.getSession();

       
        if (userService.validateUser(email, password)) {
            User user = userService.getUserByEmail(email);
            session.setAttribute("user", user);
            session.removeAttribute("message"); 
            session.removeAttribute("messageType");

           
            String role = user.getRole();
            if ("customer".equalsIgnoreCase(role)) {
                response.sendRedirect("checkout");
            } else if ("admin".equalsIgnoreCase(role) || "staff".equalsIgnoreCase(role)) {
                response.sendRedirect("main");
            } else {
                response.sendRedirect("login.jsp");
            }
        } else {
            
            session.setAttribute("message", "Invalid email or password. Please try again.");
            session.setAttribute("messageType", "danger");

           
            response.sendRedirect("login.jsp");
        }
    }
}
