package com.abc.restaurant.service;



import java.sql.SQLException;
import java.util.List;

import com.abc.restaurant.dao.ProductDAO;
import com.abc.restaurant.model.Product;

public class ProductService {
 private ProductDAO productDAO;

 public ProductService(ProductDAO productDAO) {
     this.productDAO = productDAO;
 }

 public void addProduct(Product product) throws SQLException {
     productDAO.createProduct(product);
 }

 public Product getProduct(int id) throws SQLException {
     return productDAO.getProductById(id);
 }

 public void updateProduct(Product product) throws SQLException {
     productDAO.updateProduct(product);
 }

 public void deleteProduct(int id) throws SQLException {
     productDAO.deleteProduct(id);
 }

 public List<Product> getAllProducts() throws SQLException {
     return productDAO.getAllProducts();
 }
}
