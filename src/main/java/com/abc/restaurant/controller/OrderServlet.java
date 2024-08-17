package com.abc.restaurant.controller;

import com.abc.restaurant.model.Order;
import com.abc.restaurant.model.OrderItem;
import com.abc.restaurant.service.EmailService;
import com.abc.restaurant.service.OrderServiceview;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    private OrderServiceview orderServiceview;

    @Override
    public void init() {
        orderServiceview = new OrderServiceview(); // Initialize the service that handles orders
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("list")) {
                listOrders(request, response);
            } else if (action.equals("viewItems")) {
                viewOrderItems(request, response);
            } else if (action.equals("completeOrder")) {
                completeOrder(request, response);
            } else if (action.equals("viewDetails")) {
                viewOrderDetails(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Order operation failed", e);
        }
    }

    // List orders
    private void listOrders(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Order> orders = orderServiceview.getAllOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/views/orderList.jsp").forward(request, response);
    }

    // View order items
    private void viewOrderItems(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        List<OrderItem> orderItems = orderServiceview.getOrderItems(orderId);
        String json = new Gson().toJson(orderItems);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    // View order details
    private void viewOrderDetails(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order order = orderServiceview.getOrder(orderId);
        String json = new Gson().toJson(order);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    private void completeOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        
        try {
            // Mark the order as completed
            orderServiceview.markOrderAsComplete(orderId);

            // Fetch order details
            Order order = orderServiceview.getOrder(orderId);

            if (order != null) {
                String customerEmail = order.getEmail();
                String customerName = order.getName();
                
                // Send order completion email
                EmailService.sendOrderCompletionEmail(customerEmail, customerName, orderId);

                // Set success message in the session
                HttpSession session = request.getSession();
                session.setAttribute("message", "Order completed successfully and email sent to the customer!");
                session.setAttribute("messageType", "success");
                
                // Redirect to the order list with a success message
                response.sendRedirect("main");
            } else {
                // Handle the case where the order does not exist
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
            }
        } catch (Exception e) {
            // Handle any errors, log the error, and return a user-friendly error message
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("message", "An error occurred while completing the order.");
            session.setAttribute("messageType", "danger");
            response.sendRedirect("orders?action=list");
        }
    }
}