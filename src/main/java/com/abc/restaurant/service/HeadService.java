package com.abc.restaurant.service;

import com.abc.restaurant.dao.HeadDAO;
import com.abc.restaurant.model.Head;

import java.sql.SQLException;
import java.util.List;

public class HeadService {
    private HeadDAO headDAO;

   
    public HeadService(HeadDAO headDAO) {
        this.headDAO = headDAO;
    }

   
    public void addHead(Head head) throws SQLException {
        headDAO.insertHead(head);
    }

   
    public Head getHeadById(int id) throws SQLException {
        return headDAO.getHeadById(id);
    }

    
    public List<Head> getAllHeads() throws SQLException {
        return headDAO.getAllHeads();
    }

    
    public void updateHead(Head head) throws SQLException {
        headDAO.updateHead(head);
    }

   
    public void deleteHead(int id) throws SQLException {
        headDAO.deleteHead(id);
    }
}
