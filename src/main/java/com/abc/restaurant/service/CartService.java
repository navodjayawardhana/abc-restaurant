package com.abc.restaurant.service;

import com.abc.restaurant.model.CartItem;
import com.abc.restaurant.model.Product;

import javax.servlet.http.HttpSession;
import java.util.List;

public class CartService {

    public void addToCart(Product product, int quantity, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new java.util.ArrayList<>();
        }

        boolean productExists = false;
        for (CartItem item : cart) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            cart.add(new CartItem(product, quantity));
        }

        session.setAttribute("cart", cart);
    }

    public void updateCart(int productId, int quantity, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getProduct().getId() == productId) {
                    item.setQuantity(quantity);
                    break;
                }
            }
        }

        session.setAttribute("cart", cart);
    }

    public void removeFromCart(int productId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            cart.removeIf(item -> item.getProduct().getId() == productId);
        }

        session.setAttribute("cart", cart);
    }

    public void increaseQuantity(int productId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getProduct().getId() == productId) {
                    item.setQuantity(item.getQuantity() + 1);
                    break;
                }
            }
        }
    }

    public void decreaseQuantity(int productId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getProduct().getId() == productId && item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                    break;
                }
            }
        }
    }
}
