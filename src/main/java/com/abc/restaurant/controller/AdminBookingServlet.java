package com.abc.restaurant.controller;

import com.abc.restaurant.service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/bookings")
public class AdminBookingServlet extends HttpServlet {
    private BookingService bookingService;

    @Override
    public void init() {
        bookingService = new BookingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("bookings", bookingService.getAllBookings());
            request.getRequestDispatcher("/WEB-INF/views/bookings.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            if ("approve".equals(action)) {
                bookingService.approveBooking(id);  // This sends an approval email
            } else if ("reject".equals(action)) {
                bookingService.rejectBooking(id);  // This sends a rejection email
            }
            response.sendRedirect("main");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
