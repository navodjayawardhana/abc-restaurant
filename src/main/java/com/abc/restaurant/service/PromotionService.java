package com.abc.restaurant.service;

import com.abc.restaurant.dao.PromotionDAO;
import com.abc.restaurant.model.Promotion;

import java.sql.SQLException;
import java.util.List;

public class PromotionService {
    private PromotionDAO promotionDAO;

    public PromotionService(PromotionDAO promotionDAO) {
        this.promotionDAO = promotionDAO;
    }

    public void addPromotion(Promotion promotion) throws SQLException {
        promotionDAO.createPromotion(promotion);
    }

    public Promotion getPromotion(int id) throws SQLException {
        return promotionDAO.getPromotionById(id);
    }

    public void updatePromotion(Promotion promotion) throws SQLException {
        promotionDAO.updatePromotion(promotion);
    }

    public void deletePromotion(int id) throws SQLException {
        promotionDAO.deletePromotion(id);
    }

    public List<Promotion> getAllPromotions() throws SQLException {
        return promotionDAO.getAllPromotions();
    }
}
