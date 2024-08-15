package com.abc.restaurant.controller;

import com.abc.restaurant.dao.DatabaseConnection;
import com.abc.restaurant.dao.HeadDAO;
import com.abc.restaurant.model.Head;
import com.abc.restaurant.service.HeadService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/heads")
public class HeadsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private HeadService headService;

    @Override
    public void init() {
        headService = new HeadService(new HeadDAO(DatabaseConnection.getConnection())); // Initialize with HeadDAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action == null ? "list" : action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertHead(request, response);
                    break;
                case "delete":
                    deleteHead(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateHead(request, response);
                    break;
                default:
                    listHeads(request, response);
                    break;
            }
        } catch (SQLException e) {
            handleException(request, response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listHeads(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Head> heads = headService.getAllHeads();
        request.setAttribute("heads", heads);
        request.getRequestDispatcher("/WEB-INF/views/headList.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/addHead.jsp").forward(request, response);
    }

    private void insertHead(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        HttpSession session = request.getSession();

        if (title == null || title.trim().isEmpty() || description == null || description.trim().isEmpty()) {
            session.setAttribute("message", "Title and description cannot be empty.");
            session.setAttribute("messageType", "danger");
            response.sendRedirect("heads?action=new");
            return;
        }

        Head newHead = new Head(title, description);
        try {
            headService.addHead(newHead);
            session.setAttribute("message", "Head added successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to add head: " + e.getMessage());
            session.setAttribute("messageType", "danger");
            throw e;
        }

        response.sendRedirect("main");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Head existingHead = headService.getHeadById(id);
        if (existingHead == null) {
            HttpSession session = request.getSession();
            session.setAttribute("message", "Head not found.");
            session.setAttribute("messageType", "danger");
            response.sendRedirect("heads");
            return;
        }
        request.setAttribute("head", existingHead);
        request.getRequestDispatcher("/WEB-INF/views/editHead.jsp").forward(request, response);
    }

    private void updateHead(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        HttpSession session = request.getSession();

        if (title == null || title.trim().isEmpty() || description == null || description.trim().isEmpty()) {
            session.setAttribute("message", "Title and description cannot be empty.");
            session.setAttribute("messageType", "danger");
            response.sendRedirect("heads?action=edit&id=" + id);
            return;
        }

        Head head = new Head(id, title, description);

        try {
            headService.updateHead(head);
            session.setAttribute("message", "Head updated successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to update head: " + e.getMessage());
            session.setAttribute("messageType", "danger");
            throw e;
        }

        response.sendRedirect("main");
    }

    private void deleteHead(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();

        try {
            headService.deleteHead(id);
            session.setAttribute("message", "Head deleted successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to delete head: " + e.getMessage());
            session.setAttribute("messageType", "danger");
            throw e;
        }

        response.sendRedirect("main");
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, SQLException e) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("message", "An error occurred: " + e.getMessage());
        session.setAttribute("messageType", "danger");
        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
    }
}
