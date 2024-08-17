package com.abc.restaurant.controller;

import com.abc.restaurant.model.User;
import com.abc.restaurant.model.CartItem;
import com.abc.restaurant.model.Branch;
import com.abc.restaurant.service.BranchService;
import com.abc.restaurant.service.EmailService;
import com.abc.restaurant.service.OrderService;
import com.abc.restaurant.service.ProductService;
import com.abc.restaurant.dao.BranchDAO;
import com.abc.restaurant.dao.DatabaseConnection;
import com.abc.restaurant.dao.OrderDAO;
import com.abc.restaurant.dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private BranchService branchService;
    private ProductService productService;
    private OrderService orderService;

    @Override
    public void init() {
        branchService = new BranchService(new BranchDAO(DatabaseConnection.getConnection()));
        productService = new ProductService(new ProductDAO(DatabaseConnection.getConnection()));
        orderService = new OrderService(new OrderDAO(DatabaseConnection.getConnection()));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                request.setAttribute("user", user);
            }
        }

        try {
            List<Branch> branches = branchService.getAllBranches();
            request.setAttribute("branches", branches);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("WEB-INF/views/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");  // Assuming user is stored in session
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String branchId = request.getParameter("branch");
        String orderType = request.getParameter("orderType");
        String paymentMethod = request.getParameter("paymentMethod"); // Payment Method

        // Calculate total price
        double totalPrice = 0.0;
        StringBuilder orderDetails = new StringBuilder(); // Create HTML table rows for order details
        for (CartItem item : cart) {
            totalPrice += item.getTotalPrice();
            // Append each item's details to the orderDetails StringBuilder
            orderDetails.append("<tr>")
                .append("<td style='padding: 8px; border-bottom: 1px solid #ddd;'>")
                .append(item.getProduct().getName())
                .append(" (x").append(item.getQuantity()).append(")</td>")
                .append("<td style='text-align:right; padding: 8px; border-bottom: 1px solid #ddd;'>")
                .append(String.format("%.2f", item.getTotalPrice())).append(" LKR</td>")
                .append("</tr>");
        }

        try {
            // Create the order in the database and get the order ID
            int orderId = orderService.createOrder(name, email, address, Integer.parseInt(branchId), orderType, paymentMethod, totalPrice);

            // Add order items to the order
            for (CartItem item : cart) {
                orderService.addOrderItem(orderId, item.getProduct().getName(), item.getQuantity(), item.getTotalPrice());
            }

            // Debugging line to confirm email process is triggered
            System.out.println("Sending order confirmation email to " + email);

            // Send the order confirmation email with the detailed bill
            EmailService.sendOrderBillEmail(email, name, orderDetails.toString(), totalPrice);

            // Debugging line after email sending
            System.out.println("Order confirmation email sent successfully.");

            // Clear the cart after the order is placed
            session.removeAttribute("cart");

            // Redirect to success page
            response.sendRedirect("orderSuccess.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}