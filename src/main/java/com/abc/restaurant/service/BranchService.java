package com.abc.restaurant.service;



import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.abc.restaurant.dao.BranchDAO;
import com.abc.restaurant.model.Branch;

public class BranchService {
    private BranchDAO branchDAO;

    public BranchService(BranchDAO branchDAO) {
        this.branchDAO = branchDAO;
    }

    public void addBranch(Branch branch) throws SQLException {
        branchDAO.createBranch(branch);
    }

    public Branch getBranch(int id) throws SQLException {
        return branchDAO.getBranchById(id);
    }

    public void updateBranch(Branch branch) throws SQLException {
        branchDAO.updateBranch(branch);
    }

    public void deleteBranch(int id) throws SQLException {
        branchDAO.deleteBranch(id);
    }

    public List<Branch> getAllBranches() throws SQLException {
        return branchDAO.getAllBranches();
    }
    
    public int totalBranchCount() throws SQLException {
        return branchDAO.getTotalBranch();
    }
}
