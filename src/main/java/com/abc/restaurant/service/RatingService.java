package com.abc.restaurant.service;

import com.abc.restaurant.dao.RatingDAO;
import com.abc.restaurant.model.Rating;

import java.sql.SQLException;
import java.util.List;

public class RatingService {
    private RatingDAO ratingDAO;

    public RatingService() {
        ratingDAO = new RatingDAO();
    }

    public void saveRating(Rating rating) {
        try {
            ratingDAO.saveRating(rating);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Rating> getAllRatings() throws SQLException {
        return ratingDAO.getAllRatings();
    }

    public void deleteRating(int ratingId) throws SQLException {
        ratingDAO.deleteRating(ratingId);
    }
}
