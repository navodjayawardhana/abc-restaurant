package com.abc.restaurant.controller;

import com.abc.restaurant.model.Booking;
import com.abc.restaurant.service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/booking")
public class TableBookServlet extends HttpServlet {
    private BookingService bookingService;

    @Override
    public void init() {
        bookingService = new BookingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/views/tablebook.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        int persons = Integer.parseInt(request.getParameter("persons"));
        String date = request.getParameter("date");

        
        Booking booking = new Booking(0, name, email, phone, persons, date, "Pending");


        try {
            bookingService.addBooking(booking);
            response.sendRedirect("booking?success=true");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("booking?error=true");
        }
    }
}
