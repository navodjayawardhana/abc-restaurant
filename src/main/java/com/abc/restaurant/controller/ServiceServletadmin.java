package com.abc.restaurant.controller;

import com.abc.restaurant.dao.DatabaseConnection;
import com.abc.restaurant.dao.ServiceDAO;
import com.abc.restaurant.model.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/serviceadmin")
public class ServiceServletadmin extends HttpServlet {
    private ServiceDAO serviceDAO;

    @Override
    public void init() {
        serviceDAO = new ServiceDAO(DatabaseConnection.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        try {
            switch (action == null ? "list" : action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertService(request, response, session);
                    break;
                case "delete":
                    deleteService(request, response, session);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateService(request, response, session);
                    break;
                default:
                    listServices(request, response);
                    break;
            }
        } catch (SQLException e) {
            session.setAttribute("message", "Error: " + e.getMessage());
            session.setAttribute("messageType", "danger");
            response.sendRedirect("main");
        }
    }

    private void listServices(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Service> services = serviceDAO.getAllServices();
        request.setAttribute("services", services);
        request.getRequestDispatcher("/WEB-INF/views/serviceList.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/addService.jsp").forward(request, response);
    }

    private void insertService(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, IOException, ServletException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        Service service = new Service();
        service.setTitle(title);
        service.setDescription(description);

        try {
            serviceDAO.addService(service);
            session.setAttribute("message", "Service added successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to add service: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }

        response.sendRedirect("main");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Service existingService = serviceDAO.getServiceById(id);
        request.setAttribute("service", existingService);
        request.getRequestDispatcher("/WEB-INF/views/editService.jsp").forward(request, response);
    }

    private void updateService(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        Service service = new Service(id, title, description);

        try {
            serviceDAO.updateService(service);
            session.setAttribute("message", "Service updated successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to update service: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }

        response.sendRedirect("main");
    }

    private void deleteService(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            serviceDAO.deleteService(id);
            session.setAttribute("message", "Service deleted successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to delete service: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }

        response.sendRedirect("main");
    }
}
