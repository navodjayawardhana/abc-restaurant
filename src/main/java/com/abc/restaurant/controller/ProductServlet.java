package com.abc.restaurant.controller;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.abc.restaurant.dao.DatabaseConnection;
import com.abc.restaurant.dao.ProductDAO;
import com.abc.restaurant.model.Product;
import com.abc.restaurant.service.ProductService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/products")
@MultipartConfig
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;

    @Override
    public void init() {
        productService = new ProductService(new ProductDAO(DatabaseConnection.getConnection()));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action == null ? "list" : action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertProduct(request, response);
                    break;
                case "delete":
                    deleteProduct(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateProduct(request, response);
                    break;
                default:
                    listProducts(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Cannot perform operation", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Product> products = productService.getAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/views/productList.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/addProduct.jsp").forward(request, response);
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        boolean available = request.getParameter("available") != null;
        String category = request.getParameter("category");  // Retrieve category from the form

        Part filePart = request.getPart("image");
        String fileName = getFileName(filePart);
        String imagePath = saveImage(filePart, fileName);

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setAvailable(available);
        product.setImagePath(imagePath);
        product.setCategory(category); 

        HttpSession session = request.getSession();

        try {
            productService.addProduct(product);
            session.setAttribute("message", "Product added successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to add product: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }

        response.sendRedirect("main");
    }


    private String saveImage(Part filePart, String fileName) throws IOException {
        if (fileName == null || fileName.isEmpty()) {
            return null; 
        }
        String uploadDir = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdir();
        }

        String imagePath = "uploads" + File.separator + fileName;
        filePart.write(uploadDir + File.separator + fileName);
        return imagePath;
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 2, token.length() - 1);
            }
        }
        return null;
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product existingProduct = productService.getProduct(id);
        request.setAttribute("product", existingProduct);
        request.getRequestDispatcher("/WEB-INF/views/editProduct.jsp").forward(request, response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        boolean available = request.getParameter("available") != null;
        String category = request.getParameter("category");  // Retrieve category from the form

        Product existingProduct = productService.getProduct(id);
        String imagePath = existingProduct.getImagePath();

        Part filePart = request.getPart("image");
        String fileName = getFileName(filePart);
        if (fileName != null && !fileName.isEmpty()) {
            imagePath = saveImage(filePart, fileName);
        }

        Product product = new Product(id, name, description, price, available, imagePath, category);  

        HttpSession session = request.getSession();

        try {
            productService.updateProduct(product);
            session.setAttribute("message", "Product updated successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to update product: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }

        response.sendRedirect("main");
    }


    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        HttpSession session = request.getSession();

        try {
            productService.deleteProduct(id);
            session.setAttribute("message", "Product deleted successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to delete product: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }

        response.sendRedirect("main");
    }
}
