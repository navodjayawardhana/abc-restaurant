package com.abc.restaurant.controller;

import com.abc.restaurant.dao.BookingDAO;
import com.abc.restaurant.dao.BranchDAO;
import com.abc.restaurant.service.OrderServiceview;
import com.abc.restaurant.dao.DatabaseConnection;
import com.abc.restaurant.model.DailySales; // Assuming you have a DailySales model

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
    private OrderServiceview orderServiceview;
    private BranchDAO branchDAO;
    private BookingDAO bookingDAO;

    @Override
    public void init() {
        orderServiceview = new OrderServiceview();
        Connection connection = DatabaseConnection.getConnection();
        branchDAO = new BranchDAO(connection);
        bookingDAO = new BookingDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            
            BigDecimal totalEarnings = orderServiceview.calculateTotalEarnings();
            request.setAttribute("totalEarnings", totalEarnings);

          
            int pendingOrderCount = orderServiceview.getPendingOrderCount();
            request.setAttribute("pendingOrderCount", pendingOrderCount);

           
            int totalBranchCount = branchDAO.getTotalBranch();
            request.setAttribute("totalBranchCount", totalBranchCount); 
            
          
            int pendingBookingCount = bookingDAO.getPendingBookingCount();
            request.setAttribute("pendingBookingCount", pendingBookingCount);

           
            List<DailySales> dailySales = orderServiceview.getDailySalesData();
            request.setAttribute("dailySales", dailySales);

        } catch (SQLException e) {
            throw new ServletException("Failed to retrieve data", e);
        }

        request.getRequestDispatcher("main.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
