package com.abc.restaurant.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/about")
public class AboutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to the about.jsp page
        request.getRequestDispatcher("WEB-INF/views/about.jsp").forward(request, response);
    }

}
