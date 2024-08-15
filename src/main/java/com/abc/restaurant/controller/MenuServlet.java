package com.abc.restaurant.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.abc.restaurant.dao.DatabaseConnection;
import com.abc.restaurant.dao.ProductDAO;
import com.abc.restaurant.model.Product;
import com.abc.restaurant.service.ProductService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;

    @Override
    public void init() {
        productService = new ProductService(new ProductDAO(DatabaseConnection.getConnection()));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Product> productList = productService.getAllProducts();
            request.setAttribute("products", productList);
            request.getRequestDispatcher("/WEB-INF/views/menu.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot retrieve products", e);
        }
    }
}
