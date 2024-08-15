package com.abc.restaurant.controller;

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

        if (userService.validateUser(email, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userService.getUserByEmail(email));
            response.sendRedirect("main");
        } else {
            response.sendRedirect("loginFail.jsp");
        }
    }
}
