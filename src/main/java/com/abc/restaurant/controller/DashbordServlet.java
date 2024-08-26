package com.abc.restaurant.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.restaurant.dao.BookingDAO;
import com.abc.restaurant.dao.BranchDAO;
import com.abc.restaurant.dao.DatabaseConnection;
import com.abc.restaurant.model.DailySales;
import com.abc.restaurant.service.OrderServiceview;

@WebServlet("/dashbord")
public class DashbordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
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

        request.getRequestDispatcher("WEB-INF/views/dashbord.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
