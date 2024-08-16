<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Product Management</title>
   
    <style>
        .table-container {
            margin-top: 20px;
        }

        .pagination {
            justify-content: center;
        }
    </style>
</head>

<body>
    <div class="container mt-4">
        <div class="row mb-3">
            <div class="col-md-4">
                <input type="text" id="searchInput" class="form-control" placeholder="Search by Name...">
            </div>
            <div class="col-md-4">
                <select id="categoryFilter" class="form-control">
                    <option value="">All Categories</option>
                    <option value="Burger">Burger</option>
                    <option value="Pizza">Pizza</option>
                    <option value="Pasta">Pasta</option>
                    <option value="Fries">Fries</option>
                </select>
            </div>
            
        </div>
        <div class="row mb-3">
			    <a href="generatePdfReport?report=product" class="btn btn-primary">Download PDF Report</a>
			    <a class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addProductModal">Add New Product</a>
		</div>
	

        

        <div class="table-responsive table-container">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Available</th>
                        <th>Category</th> <!-- Add Category Column -->
                        <th>Image</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${products}">
                        <tr>
                            <td>${product.name}</td>
                            <td>${product.description}</td>
                            <td>${product.price}</td>
                            <td style="color: ${product.available ? 'green' : 'red'};">${product.available ? "Yes" : "No"}</td>
                            <td>${product.category}</td> <!-- Display the Category -->
                            <td>
                                <c:if test="${product.imagePath != null}">
                                    <img src="${pageContext.request.contextPath}/${product.imagePath}" alt="${product.name}" width="50">
                                </c:if>
                            </td>
                            <td>
                                <button class="btn btn-success btn-sm edit-btn" data-id="${product.id}" data-name="${product.name}" data-description="${product.description}" data-price="${product.price}" data-available="${product.available}" data-category="${product.category}" data-image-path="${product.imagePath}" data-bs-toggle="modal" data-bs-target="#editProductModal">Edit</button>
                                <button class="btn btn-danger btn-sm delete-btn" data-id="${product.id}" data-bs-toggle="modal" data-bs-target="#deleteProductModal">Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <nav>
            <ul class="pagination">
                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Previous" id="prevPage">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Next" id="nextPage">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>

    <!-- Add Product Modal -->
    <div class="modal fade" id="addProductModal" tabindex="-1" aria-labelledby="addProductModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addProductModalLabel">Add New Product</h5>
                </div>
                <div class="modal-body">
                    <form action="products?action=insert" method="post" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label for="name" class="form-label">Name:</label>
                            <input type="text" class="form-control" name="name" id="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="form-label">Description:</label>
                            <textarea class="form-control" name="description" id="description" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="price" class="form-label">Price:</label>
                            <input type="number" class="form-control" step="0.01" name="price" id="price" required>
                        </div>
                        <div class="mb-3">
                            <label for="available" class="form-label">Available:</label>
                            <input type="checkbox" name="available" id="available">
                        </div>
                        <div class="mb-3">
                            <label for="category" class="form-label">Category:</label>
                            <select class="form-control" name="category" id="category" required>
                                <option value="Burger">Burger</option>
                                <option value="Pizza">Pizza</option>
                                <option value="Pasta">Pasta</option>
                                <option value="Fries">Fries</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="image" class="form-label">Product Image:</label>
                            <input type="file" class="form-control" name="image" id="image">
                        </div>
                        <button type="submit" class="btn btn-success">Add Product</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Edit Product Modal -->
    <div class="modal fade" id="editProductModal" tabindex="-1" aria-labelledby="editProductModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editProductModalLabel">Edit Product</h5>
                </div>
                <div class="modal-body">
                    <form id="editProductForm" action="products?action=update" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="id" id="editProductId">
                        <div class="mb-3">
                            <label for="editName" class="form-label">Name:</label>
                            <input type="text" class="form-control" name="name" id="editName" required>
                        </div>
                        <div class="mb-3">
                            <label for="editDescription" class="form-label">Description:</label>
                            <textarea class="form-control" name="description" id="editDescription" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="editPrice" class="form-label">Price:</label>
                            <input type="number" class="form-control" step="0.01" name="price" id="editPrice" required>
                        </div>
                        <div class="mb-3 form-check">
                            <input type="checkbox" class="form-check-input" name="available" id="editAvailable">
                            <label for="editAvailable" class="form-check-label">Available</label>
                        </div>
                        <div class="mb-3">
                            <label for="editCategory" class="form-label">Category:</label>
                            <select class="form-control" name="category" id="editCategory" required>
                                <option value="Burger">Burger</option>
                                <option value="Pizza">Pizza</option>
                                <option value="Pasta">Pasta</option>
                                <option value="Fries">Fries</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="editImage" class="form-label">Product Image:</label>
                            <input type="file" class="form-control" name="image" id="editImage">
                            <div id="currentImageContainer" class="mt-3">
                                <img id="currentImage" src="" alt="" width="200" class="img-thumbnail">
                                <p>Current Image</p>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-success">Update Product</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Delete Product Modal -->
    <div class="modal fade" id="deleteProductModal" tabindex="-1" aria-labelledby="deleteProductModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteProductModalLabel">Confirm Delete</h5>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to delete this product?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-danger" id="confirmDeleteButton">Delete</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        $(document).ready(function() {
            var currentPage = 1;
            var rowsPerPage = 10;

            function renderTableRows() {
                var allRows = $('#dataTable tbody tr');
                var filteredRows = allRows.filter(function() {
                    return $(this).is(':visible');
                });

                var totalRows = filteredRows.length;
                var totalPages = Math.ceil(totalRows / rowsPerPage);

                allRows.hide();
                filteredRows.slice((currentPage - 1) * rowsPerPage, currentPage * rowsPerPage).show();

                $('#prevPage').parent().toggleClass('disabled', currentPage === 1);
                $('#nextPage').parent().toggleClass('disabled', currentPage === totalPages || totalRows === 0);
            }

            $('#prevPage').click(function(e) {
                e.preventDefault();
                if (currentPage > 1) {
                    currentPage--;
                    renderTableRows();
                }
            });

            $('#nextPage').click(function(e) {
                e.preventDefault();
                if (currentPage < Math.ceil($('#dataTable tbody tr:visible').length / rowsPerPage)) {
                    currentPage++;
                    renderTableRows();
                }
            });

            $('#searchInput').keyup(function() {
                var searchValue = $(this).val().toLowerCase();
                $('#dataTable tbody tr').each(function() {
                    $(this).toggle($(this).find('td:first').text().toLowerCase().indexOf(searchValue) > -1);
                });
                currentPage = 1;
                renderTableRows();
            });

            $('#categoryFilter').change(function() {
                var selectedCategory = $(this).val().toLowerCase();
                $('#dataTable tbody tr').each(function() {
                    var category = $(this).find('td:nth-child(5)').text().toLowerCase();
                    $(this).toggle(selectedCategory === "" || category === selectedCategory);
                });
                currentPage = 1;
                renderTableRows();
            });

            // Edit button click event
            $('.edit-btn').click(function() {
                var productId = $(this).data('id');
                var productName = $(this).data('name');
                var productDescription = $(this).data('description');
                var productPrice = $(this).data('price');
                var productAvailable = $(this).data('available');
                var productCategory = $(this).data('category');
                var productImagePath = $(this).data('image-path');

                $('#editProductId').val(productId);
                $('#editName').val(productName);
                $('#editDescription').val(productDescription);
                $('#editPrice').val(productPrice);
                $('#editAvailable').prop('checked', productAvailable);
                $('#editCategory').val(productCategory);

                if (productImagePath) {
                    $('#currentImage').attr('src', '${pageContext.request.contextPath}/' + productImagePath);
                    $('#currentImageContainer').show();
                } else {
                    $('#currentImageContainer').hide();
                }
            });

            // Delete button click event
            $('.delete-btn').click(function() {
                var productId = $(this).data('id');
                $('#confirmDeleteButton').data('id', productId);
            });

            $('#confirmDeleteButton').click(function() {
                var productId = $(this).data('id');
                window.location.href = 'products?action=delete&id=' + productId;
            });

            renderTableRows();
        });
    </script>

</body>
</html>
