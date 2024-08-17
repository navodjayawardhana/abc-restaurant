<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order List</title>
    <style>
        .table-container {
            margin-top: 20px;
        }
    </style>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h1>Orders</h1>
        <div class="table-responsive table-container">
            <table class="table table-bordered" id="orderTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Customer Name</th> 
                        <th>Order Status</th>
                        <th>Order Time</th>
                        <th>Payment Method</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.name}</td>
                            <td>${order.status}</td>
                            <td>${order.createdAt}</td>
                            <td>${order.paymentMethod}</td>
                            <td>
                                <!-- Button to view order items -->
                                <button class="btn btn-info btn-sm view-items-btn" data-id="${order.id}" data-bs-toggle="modal" data-bs-target="#viewOrderItemsModal">View Items</button>

                                <!-- Conditionally show Complete Order button only if the status is not Completed -->
                                <c:if test="${order.status != 'Completed'}">
                                    <a href="orders?action=completeOrder&orderId=${order.id}" class="btn btn-success btn-sm">Complete Order</a>
                                </c:if>

                                <!-- Button to view order details -->
                                <button class="btn btn-warning btn-sm view-details-btn" data-id="${order.id}" data-bs-toggle="modal" data-bs-target="#viewOrderDetailsModal">View Details</button>
                                 <a href="generatePdfReport?report=order&orderId=${order.id}" class="btn btn-primary btn-sm">Generate PDF</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Order Items Modal -->
    <div class="modal fade" id="viewOrderItemsModal" tabindex="-1" aria-labelledby="viewOrderItemsModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="viewOrderItemsModalLabel">Order Items</h5>
                </div>
                <div class="modal-body">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Product Name</th>
                                <th>Quantity</th>
                                <th>Price</th>
                            </tr>
                        </thead>
                        <tbody id="orderItemsTableBody">
                            <!-- Order items will be dynamically loaded here -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Order Details Modal -->
    <div class="modal fade" id="viewOrderDetailsModal" tabindex="-1" aria-labelledby="viewOrderDetailsModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="viewOrderDetailsModalLabel">Order Details</h5>
                </div>
                <div class="modal-body">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Address</th>
                                <th>Branch</th>
                            </tr>
                        </thead>
                        <tbody id="orderDetailsTableBody">
                            <!-- Order details will be dynamically loaded here -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <script>
    $(document).ready(function() {
        // Load order items in the modal when the "View Items" button is clicked
        $('.view-items-btn').click(function() {
            var orderId = $(this).data('id');
            $.ajax({
                url: 'orders?action=viewItems&orderId=' + orderId,
                method: 'GET',
                success: function(response) {
                    // Clear existing table rows
                    $('#orderItemsTableBody').empty();

                    // Parse the JSON response (response is already parsed if using jQuery)
                    response.forEach(function(item) {
                        $('#orderItemsTableBody').append(
                            '<tr>' +
                                '<td>' + item.productName + '</td>' +
                                '<td>' + item.quantity + '</td>' +
                                '<td>' + item.price + '</td>' +
                            '</tr>'
                        );
                    });
                },
                error: function(xhr, status, error) {
                    console.error("Error fetching order items:", error);
                }
            });
        });

        // Load order details in the modal when the "View Details" button is clicked
        $('.view-details-btn').click(function() {
            var orderId = $(this).data('id');
            $.ajax({
                url: 'orders?action=viewDetails&orderId=' + orderId,
                method: 'GET',
                success: function(response) {
                    // Clear existing table rows
                    $('#orderDetailsTableBody').empty();

                    // Append the order details
                    $('#orderDetailsTableBody').append(
                        '<tr>' +
                            '<td>' + response.name + '</td>' +
                            '<td>' + response.email + '</td>' +
                            '<td>' + response.address + '</td>' +
                            '<td>' + response.branch + '</td>' +
                        '</tr>'
                    );
                },
                error: function(xhr, status, error) {
                    console.error("Error fetching order details:", error);
                }
            });
        });
    });
    </script>
</body>
</html>
