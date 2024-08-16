<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <title>Checkout - ABC Restaurant</title>
  <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
  <link href="css/style.css" rel="stylesheet" />
  <link href="css/responsive.css" rel="stylesheet" />
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
</head>

<body class="sub_page">
  <div class="hero_area">
    <div class="bg-box">
      <img src="images/hero-bg.jpg" alt="">
    </div>
    <%@ include file="navbar.jsp" %>
  </div>

  <!-- Checkout Section -->
  <div class="container mt-5">
    <div class="row">
        <!-- Left Section: Billing Information -->
        <div class="col-md-6">
            <h4>Billing Information</h4>
            <form action="checkout" method="post" id="checkoutForm">
                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" class="form-control" id="name" name="name" value="<c:out value='${user.name}'/>" required>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" value="<c:out value='${user.email}'/>" required>
                </div>
                <div class="mb-3">
                    <label for="address" class="form-label">Address</label>
                    <textarea class="form-control" id="address" name="address" required></textarea>
                </div>
                
                <!-- Branch Dropdown -->
                <div class="mb-3">
                    <label for="branch" class="form-label">Select Branch</label>
                    <select class="form-select" id="branch" name="branch">
                        <c:forEach var="branch" items="${branches}">
                            <option value="${branch.id}">${branch.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-12 mt-5">
                    <button type="submit" class="btn btn-warning btn-lg w-100">Place Order</button>
                </div>
            </form>
        </div>

        <!-- Right Section: Summary -->
        <div class="col-md-6">
            <div class="card p-4 bg-light">
                <h5 class="card-title">Summary</h5>
                <ul class="list-group">
                    <c:forEach var="item" items="${cart}">
                        <li class="list-group-item d-flex justify-content-between">
                            ${item.product.name} (x${item.quantity})
                            <span>LKR ${item.product.price * item.quantity}</span>
                        </li>
                    </c:forEach>
                    <li class="list-group-item d-flex justify-content-between">
                        <strong>Total:</strong>
                        <strong>LKR ${totalPrice}</strong>
                    </li>
                </ul>
            </div>
        </div>
    </div>
  </div>

  <!-- Login or Guest Modal -->
  <div class="modal fade" id="loginOrGuestModal" tabindex="-1" aria-labelledby="loginOrGuestModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="loginOrGuestModalLabel">Login or Continue as Guest</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <p>Please log in or continue as a guest to complete your order.</p>
          <div class="d-flex justify-content-around">
              <a href="login.jsp" class="btn btn-primary">Login</a>
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" id="guestCheckoutBtn">Continue as Guest</button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script>
    $(document).ready(function() {
        // Always show the modal when the page is loaded or refreshed
        $('#loginOrGuestModal').modal('show');

        // Handle guest checkout
        $('#guestCheckoutBtn').click(function() {
            // Close modal and allow the form to be submitted
            $('#loginOrGuestModal').modal('hide');
        });
    });
  </script>

</body>
</html>
