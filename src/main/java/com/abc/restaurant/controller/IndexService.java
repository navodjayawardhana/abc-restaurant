package com.abc.restaurant.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.restaurant.dao.DatabaseConnection;
import com.abc.restaurant.dao.HeadDAO;
import com.abc.restaurant.dao.ProductDAO;
import com.abc.restaurant.dao.PromotionDAO; // Import PromotionDAO
import com.abc.restaurant.dao.RatingDAO;
import com.abc.restaurant.model.Head;
import com.abc.restaurant.model.Product;
import com.abc.restaurant.model.Promotion;
import com.abc.restaurant.model.Rating;
import com.abc.restaurant.service.HeadService;
import com.abc.restaurant.service.ProductService;
import com.abc.restaurant.service.PromotionService;
import com.abc.restaurant.service.RatingService;

@WebServlet("/index")
public class IndexService extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;
    private PromotionService promotionService;
    private HeadService headService;
    private RatingService ratingService;
    

    @Override
    public void init() {
        productService = new ProductService(new ProductDAO(DatabaseConnection.getConnection()));
        promotionService = new PromotionService(new PromotionDAO(DatabaseConnection.getConnection())); 
        headService = new HeadService(new HeadDAO(DatabaseConnection.getConnection())); 
        ratingService = new RatingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Product> productList = productService.getAllProducts();
            request.setAttribute("products", productList);
            
            List<Promotion> promotions = promotionService.getAllPromotions();
            request.setAttribute("promotions", promotions);
            
            List<Head> heads = headService.getAllHeads();
            request.setAttribute("heads", heads);
            
            List<Rating> ratings = ratingService.getAllRatings(); 
            request.setAttribute("ratings", ratings); 
            
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot retrieve products, promotions, heads, or ratings", e);
        }
    }
}

