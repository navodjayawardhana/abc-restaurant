package com.abc.restaurant.controller;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.restaurant.dao.BranchDAO;
import com.abc.restaurant.dao.DatabaseConnection;
import com.abc.restaurant.model.Branch;
import com.abc.restaurant.service.BranchService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/branches")
public class BranchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BranchService branchService;

    @Override
    public void init() {
        branchService = new BranchService(new BranchDAO(DatabaseConnection.getConnection()));
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
                    insertBranch(request, response);
                    break;
                case "delete":
                    deleteBranch(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateBranch(request, response);
                    break;
                default:
                    listBranches(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Cannot perform operation", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listBranches(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Branch> branches = branchService.getAllBranches();
        request.setAttribute("branches", branches);
        request.getRequestDispatcher("/WEB-INF/views/branchList.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/addBranch.jsp").forward(request, response);
    }

    private void insertBranch(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String contact = request.getParameter("contact");
        String address = request.getParameter("address");
        String password = encryptPassword(request.getParameter("password"));

        Branch branch = new Branch();
        branch.setName(name);
        branch.setEmail(email);
        branch.setContact(contact);
        branch.setAddress(address);
        branch.setPassword(password);

        HttpSession session = request.getSession();

        try {
            branchService.addBranch(branch);
            session.setAttribute("message", "Branch added successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to add branch: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }

        response.sendRedirect("main");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Branch existingBranch = branchService.getBranch(id);
        request.setAttribute("branch", existingBranch);
        request.getRequestDispatcher("/WEB-INF/views/editBranch.jsp").forward(request, response);
    }

    private void updateBranch(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String contact = request.getParameter("contact");
        String address = request.getParameter("address");
        String password = encryptPassword(request.getParameter("password"));

        Branch branch = new Branch(id, name, email, contact, address, password);

        HttpSession session = request.getSession();

        try {
            branchService.updateBranch(branch);
            session.setAttribute("message", "Branch updated successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to update branch: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }

        response.sendRedirect("main");
    }

    private void deleteBranch(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        HttpSession session = request.getSession();

        try {
            branchService.deleteBranch(id);
            session.setAttribute("message", "Branch deleted successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to delete branch: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }

        response.sendRedirect("main");
    }

    // Simple password encryption (for demonstration purposes only)
    private String encryptPassword(String password) {
        // Replace with a secure hashing algorithm in production
        return Integer.toHexString(password.hashCode());
    }
}
