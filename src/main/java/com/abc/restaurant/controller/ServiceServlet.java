package com.abc.restaurant.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.restaurant.dao.DatabaseConnection;
import com.abc.restaurant.dao.ServiceDAO;
import com.abc.restaurant.model.Service;

@WebServlet("/service")
public class ServiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServiceDAO serviceDAO;

    @Override
    public void init() {
        serviceDAO = new ServiceDAO(DatabaseConnection.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Service> services = serviceDAO.getAllServices();
            request.setAttribute("services", services);
            request.getRequestDispatcher("WEB-INF/views/service.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve services", e); // Handle SQLException
        }
    }
}
