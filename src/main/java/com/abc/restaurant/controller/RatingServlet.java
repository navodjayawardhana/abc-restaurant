package com.abc.restaurant.controller;

import com.abc.restaurant.model.Rating;
import com.abc.restaurant.service.RatingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/rate")
public class RatingServlet extends HttpServlet {
    private RatingService ratingService;

    @Override
    public void init() {
        ratingService = new RatingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String orderId = request.getParameter("orderId");
        request.setAttribute("orderId", orderId);
        request.getRequestDispatcher("/WEB-INF/views/rateOrder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int ratingValue = Integer.parseInt(request.getParameter("rating"));
        String feedback = request.getParameter("feedback");

        Rating rating = new Rating(orderId, ratingValue, feedback);
        ratingService.saveRating(rating);

     
        response.sendRedirect("index");
    }
}
