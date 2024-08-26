package com.abc.restaurant.controller;

import com.abc.restaurant.service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        
            HttpSession session = request.getSession();
            String message = (String) session.getAttribute("message");
            String messageType = (String) session.getAttribute("messageType");
            if (message != null && messageType != null) {
                request.setAttribute("message", message);
                request.setAttribute("messageType", messageType);
                session.removeAttribute("message");
                session.removeAttribute("messageType");
            }
            request.getRequestDispatcher("/WEB-INF/views/bookings.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot retrieve bookings", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int id = Integer.parseInt(request.getParameter("id"));

        HttpSession session = request.getSession();

        try {
            if ("approve".equals(action)) {
                bookingService.approveBooking(id);  
                session.setAttribute("message", "Booking approved successfully!");
                session.setAttribute("messageType", "success");
            } else if ("reject".equals(action)) {
                bookingService.rejectBooking(id);  
                session.setAttribute("message", "Booking rejected successfully!");
                session.setAttribute("messageType", "success");
            } else {
                session.setAttribute("message", "Unknown action!");
                session.setAttribute("messageType", "danger");
            }
            response.sendRedirect("main");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to process booking: " + e.getMessage());
            session.setAttribute("messageType", "danger");
            response.sendRedirect("bookings");
        }
    }
}
