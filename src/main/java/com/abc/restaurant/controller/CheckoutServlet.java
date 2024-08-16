package com.abc.restaurant.controller;

import com.abc.restaurant.model.User;
import com.abc.restaurant.model.Branch;
import com.abc.restaurant.service.BranchService;
import com.abc.restaurant.dao.BranchDAO;
import com.abc.restaurant.dao.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BranchService branchService;

    @Override
    public void init() {
        branchService = new BranchService(new BranchDAO(DatabaseConnection.getConnection()));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Check if session exists

        if (session != null) {
            User user = (User) session.getAttribute("user"); // Retrieve user from session
            if (user != null) {
                // User is logged in, proceed to checkout page with auto-filled details
                request.setAttribute("user", user); // Pass user data to the JSP
            }
        }

        // Retrieve the list of branches
        try {
            List<Branch> branches = branchService.getAllBranches();
            request.setAttribute("branches", branches); // Pass branch list to the JSP
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Forward to checkout page
        request.getRequestDispatcher("WEB-INF/views/checkout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle form submission here
    }
}
