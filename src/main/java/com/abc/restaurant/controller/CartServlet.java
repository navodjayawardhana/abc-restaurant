package com.abc.restaurant.controller;

import com.abc.restaurant.dao.DatabaseConnection;
import com.abc.restaurant.dao.ProductDAO;
import com.abc.restaurant.model.Product;
import com.abc.restaurant.service.CartService;
import com.abc.restaurant.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;
    private CartService cartService;

    @Override
    public void init() {
        productService = new ProductService(new ProductDAO(DatabaseConnection.getConnection()));
        cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewCart";
        }

        String previousPage = request.getParameter("previousPage");
        if (previousPage == null || previousPage.isEmpty()) {
            previousPage = request.getHeader("Referer"); // Default to referer if not provided
        }

        switch (action) {
            case "addToCart":
                addToCart(request, response, previousPage);
                break;
            case "viewCart":
                viewCart(request, response);
                break;
            case "updateCart":
                updateCart(request, response, previousPage);
                break;
            case "removeFromCart":
                removeFromCart(request, response, previousPage);
                break;
            case "increaseQty":
                increaseQty(request, response, previousPage);
                break;
            case "decreaseQty":
                decreaseQty(request, response, previousPage);
                break;
            default:
                viewCart(request, response);
                break;
        }
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response, String previousPage) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
            Product product = productService.getProduct(productId);
            HttpSession session = request.getSession();
            cartService.addToCart(product, quantity, session);
            response.sendRedirect(previousPage != null ? previousPage : "menu"); // Redirect back to the previous page
        } catch (SQLException e) {
            throw new ServletException("Cannot add product to cart", e);
        }
    }

    private void increaseQty(HttpServletRequest request, HttpServletResponse response, String previousPage) throws IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        HttpSession session = request.getSession();
        cartService.increaseQuantity(productId, session);
        response.sendRedirect(previousPage != null ? previousPage : "menu");
    }

    private void decreaseQty(HttpServletRequest request, HttpServletResponse response, String previousPage) throws IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        HttpSession session = request.getSession();
        cartService.decreaseQuantity(productId, session);
        response.sendRedirect(previousPage != null ? previousPage : "menu");
    }

    private void viewCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }

    private void updateCart(HttpServletRequest request, HttpServletResponse response, String previousPage) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        cartService.updateCart(productId, quantity, session);
        response.sendRedirect(previousPage != null ? previousPage : "menu"); // Redirect back to the previous page
    }

    private void removeFromCart(HttpServletRequest request, HttpServletResponse response, String previousPage) throws IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        HttpSession session = request.getSession();
        cartService.removeFromCart(productId, session);
        response.sendRedirect(previousPage != null ? previousPage : "menu"); // Redirect back to the previous page
    }
}
