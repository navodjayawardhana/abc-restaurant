<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <c:if test="${empty cart}">
        <p>Your cart is empty.</p>
    </c:if>

    <!-- Initialize the totalPrice variable -->
    <c:set var="totalPrice" value="0" />

    <c:forEach var="item" items="${cart}">
        <!-- Calculate total for each item -->
        <c:set var="itemTotal" value="${item.product.price * item.quantity}" />
        <!-- Add item total to totalPrice -->
        <c:set var="totalPrice" value="${totalPrice + itemTotal}" />

        <div class="cart-item d-flex justify-content-between align-items-center">
            <div>
                <h6>${item.product.name}</h6>
                <p>Price: LKR ${item.product.price}</p>
            </div>
            <div class="d-flex align-items-center">
                <form action="cart" method="get" class="me-2">
                    <input type="hidden" name="productId" value="${item.product.id}">
                    <button type="submit" name="action" value="decreaseQty" class="btn btn-secondary">-</button>
                </form>
                <span>${item.quantity}</span>
                <form action="cart" method="get" class="ms-2">
                    <input type="hidden" name="productId" value="${item.product.id}">
                    <button type="submit" name="action" value="increaseQty" class="btn btn-secondary">+</button>
                </form>
            </div>
            <div>
                <h6 class="item-total">Total: LKR ${itemTotal}</h6> <!-- Display total for each item -->
            </div>
            <form action="cart" method="get">
                <input type="hidden" name="productId" value="${item.product.id}">
                <button type="submit" name="action" value="removeFromCart" class="btn btn-danger">Remove</button>
            </form>
        </div>
        <hr>
    </c:forEach>

    <!-- Display the calculated total price for all items -->
    <c:if test="${not empty cart}">
        <h4>Total Price: LKR ${totalPrice}</h4>
        <a href="checkout" class="btn btn-success">Proceed to Checkout</a>
    </c:if>
</div>
