package com.abc.restaurant.controller;

import com.abc.restaurant.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activate")
public class ActivateAccountServlet extends HttpServlet {

    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String code = request.getParameter("code");

        if (userService.activateAccount(email, code)) {
            response.sendRedirect("login.jsp");
        } else {
            response.sendRedirect("activationFail.jsp");
        }
    }
}
