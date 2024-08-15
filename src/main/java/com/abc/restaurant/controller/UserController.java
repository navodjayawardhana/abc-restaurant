package com.abc.restaurant.controller;

import com.abc.restaurant.dao.UserViewDao;
import com.abc.restaurant.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/users")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserViewDao userViewDao;

    public void init() {
        userViewDao = new UserViewDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action == null ? "list" : action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertUser(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateUser(request, response);
                    break;
                default:
                    listUsers(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<User> users = userViewDao.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/views/userList.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/userForm.jsp").forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

        User newUser = new User(name, email, password, role);
        newUser.setActive(isActive);
        userViewDao.saveUser(newUser);
        response.sendRedirect("main");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userViewDao.getUserById(id);
        request.setAttribute("user", existingUser);
        request.getRequestDispatcher("/WEB-INF/views/userForm.jsp").forward(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

        User user = new User(name, email, password, role);
        user.setId(id);
        user.setActive(isActive);

        userViewDao.updateUser(user);
        response.sendRedirect("main");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userViewDao.deleteUser(id);
        response.sendRedirect("main");
    }
}
