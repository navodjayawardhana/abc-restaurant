package com.abc.restaurant.controller;

import com.abc.restaurant.dao.DatabaseConnection;
import com.abc.restaurant.dao.GalleryDAO;
import com.abc.restaurant.model.Gallery;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/galleryadmin")
@MultipartConfig
public class GalleryServlet extends HttpServlet {
    private GalleryDAO galleryDAO;

    @Override
    public void init() {
        galleryDAO = new GalleryDAO(DatabaseConnection.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action == null ? "list" : action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertGallery(request, response);
                    break;
                case "delete":
                    deleteGallery(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateGallery(request, response);
                    break;
                default:
                    listGallery(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error performing operation", e);
        }
    }

    private void listGallery(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Gallery> galleries = galleryDAO.getAllGallery();
        request.setAttribute("galleries", galleries);
        request.getRequestDispatcher("/WEB-INF/views/galleryadmin.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/addGallery.jsp").forward(request, response);
    }

    private void insertGallery(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");

        Part filePart = request.getPart("image");
        String fileName = getFileName(filePart);
        String imagePath = saveImage(filePart, fileName);

        Gallery gallery = new Gallery();
        gallery.setName(name);
        gallery.setImagePath(imagePath);

        HttpSession session = request.getSession();
        try {
            galleryDAO.addGallery(gallery);
            session.setAttribute("message", "Gallery image added successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to add gallery image.");
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
        Gallery existingGallery = galleryDAO.getGalleryById(id);
        request.setAttribute("gallery", existingGallery);
        request.getRequestDispatcher("/WEB-INF/views/editGallery.jsp").forward(request, response);
    }

    private void updateGallery(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");

        Gallery existingGallery = galleryDAO.getGalleryById(id);
        String imagePath = existingGallery.getImagePath();

        Part filePart = request.getPart("image");
        String fileName = getFileName(filePart);
        if (fileName != null && !fileName.isEmpty()) {
            imagePath = saveImage(filePart, fileName);
        }

        Gallery gallery = new Gallery(id, name, imagePath);
        HttpSession session = request.getSession();
        try {
            galleryDAO.updateGallery(gallery);
            session.setAttribute("message", "Gallery image updated successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to update gallery image.");
            session.setAttribute("messageType", "danger");
        }

        response.sendRedirect("main");
    }

    private void deleteGallery(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        try {
            galleryDAO.deleteGallery(id);
            session.setAttribute("message", "Gallery image deleted successfully!");
            session.setAttribute("messageType", "success");
        } catch (SQLException e) {
            session.setAttribute("message", "Failed to delete gallery image.");
            session.setAttribute("messageType", "danger");
        }

        response.sendRedirect("main");
    }
}
