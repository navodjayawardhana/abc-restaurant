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
import com.abc.restaurant.dao.GalleryDAO;
import com.abc.restaurant.model.Gallery;

@WebServlet("/gallery")
public class GalleryServler extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private GalleryDAO galleryDAO;

    @Override
    public void init() {
        galleryDAO = new GalleryDAO(DatabaseConnection.getConnection());
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
           
            List<Gallery> galleries = galleryDAO.getAllGallery();
            request.setAttribute("galleries", galleries);
            
           
            request.getRequestDispatcher("WEB-INF/views/gallery.jsp").forward(request, response);
        } catch (SQLException e) {
            
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to retrieve gallery data at this time.");
            request.getRequestDispatcher("WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
