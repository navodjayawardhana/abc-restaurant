package com.abc.restaurant.controller;

import com.abc.restaurant.model.Rating;
import com.abc.restaurant.service.RatingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/adminratings")
public class AdminRatingServlet extends HttpServlet {
    private RatingService ratingService;

    @Override
    public void init() {
        ratingService = new RatingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Rating> ratings = ratingService.getAllRatings();
            request.setAttribute("ratings", ratings);
            request.getRequestDispatcher("/WEB-INF/views/adminRatings.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ratingId = Integer.parseInt(request.getParameter("ratingId"));
        try {
            ratingService.deleteRating(ratingId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("main");
    }
}
