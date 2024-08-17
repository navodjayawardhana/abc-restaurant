package com.abc.restaurant.service;

import com.abc.restaurant.dao.HeadDAO;
import com.abc.restaurant.model.Head;

import java.sql.SQLException;
import java.util.List;

public class HeadService {
    private HeadDAO headDAO;

    // Constructor with DAO parameter
    public HeadService(HeadDAO headDAO) {
        this.headDAO = headDAO;
    }

    // Add new head
    public void addHead(Head head) throws SQLException {
        headDAO.insertHead(head);
    }

    // Get head by ID
    public Head getHeadById(int id) throws SQLException {
        return headDAO.getHeadById(id);
    }

    // Get all heads
    public List<Head> getAllHeads() throws SQLException {
        return headDAO.getAllHeads();
    }

    // Update head
    public void updateHead(Head head) throws SQLException {
        headDAO.updateHead(head);
    }

    // Delete head
    public void deleteHead(int id) throws SQLException {
        headDAO.deleteHead(id);
    }
}
