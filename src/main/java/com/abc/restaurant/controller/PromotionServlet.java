package com.abc.restaurant.controller;

import com.abc.restaurant.dao.DatabaseConnection;
import com.abc.restaurant.dao.PromotionDAO;
import com.abc.restaurant.model.Promotion;
import com.abc.restaurant.service.PromotionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/promotions")
@MultipartConfig
public class PromotionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PromotionService promotionService;

    @Override
    public void init() {
        promotionService = new PromotionService(new PromotionDAO(DatabaseConnection.getConnection()));
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
                    insertPromotion(request, response);
                    break;
                case "delete":
                    deletePromotion(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updatePromotion(request, response);
                    break;
                default:
                    listPromotions(request, response);
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

    private void listPromotions(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Promotion> promotions = promotionService.getAllPromotions();
        request.setAttribute("promotions", promotions);
        request.getRequestDispatcher("/WEB-INF/views/promotionList.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/addPromotion.jsp").forward(request, response);
    }

    private void insertPromotion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String title = request.getParameter("title");
        int prestige = Integer.parseInt(request.getParameter("prestige"));
        String description = request.getParameter("description");

        Part filePart = request.getPart("image");
        String fileName = getFileName(filePart);
        String imagePath = saveImage(filePart, fileName);

        Promotion promotion = new Promotion();
        promotion.setTitle(title);
        promotion.setPrestige(prestige);
        promotion.setDescription(description);
        promotion.setImagePath(imagePath);

        HttpSession session = request.getSession();

        try {
            promotionService.addPromotion(promotion);
            session.setAttribute("message", "Promotion added successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to add promotion: " + e.getMessage());
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
        Promotion existingPromotion = promotionService.getPromotion(id);
        request.setAttribute("promotion", existingPromotion);
        request.getRequestDispatcher("/WEB-INF/views/editPromotion.jsp").forward(request, response);
    }

    private void updatePromotion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        int prestige = Integer.parseInt(request.getParameter("prestige"));
        String description = request.getParameter("description");

        Promotion existingPromotion = promotionService.getPromotion(id);
        String imagePath = existingPromotion.getImagePath();

        Part filePart = request.getPart("image");
        String fileName = getFileName(filePart);
        if (fileName != null && !fileName.isEmpty()) {
            imagePath = saveImage(filePart, fileName);
        }

        Promotion promotion = new Promotion(id, title, prestige, description, imagePath);

        HttpSession session = request.getSession();

        try {
            promotionService.updatePromotion(promotion);
            session.setAttribute("message", "Promotion updated successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to update promotion: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }

        response.sendRedirect("main");
    }

    private void deletePromotion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        HttpSession session = request.getSession();

        try {
            promotionService.deletePromotion(id);
            session.setAttribute("message", "Promotion deleted successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to delete promotion: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }

        response.sendRedirect("main");
    }
}
