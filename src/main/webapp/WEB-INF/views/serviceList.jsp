<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Service Management</title>
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
            <div class="col-md-6">
                <input type="text" id="searchInput" class="form-control" placeholder="Search by Title...">
            </div>
            <div class="col-md-6 text-end">
                <a class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addServiceModal">Add New Service</a>
            </div>
        </div>

        <div class="table-responsive table-container">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="service" items="${services}">
                        <tr>
                            <td>${service.title}</td>
                            <td>${service.description}</td>
                            <td>
                                <button class="btn btn-success btn-sm edit-btn" 
                                    data-id="${service.id}" 
                                    data-title="${service.title}" 
                                    data-description="${service.description}" 
                                    data-bs-toggle="modal" 
                                    data-bs-target="#editServiceModal">Edit</button>

                                <button class="btn btn-danger btn-sm delete-btn" 
                                    data-id="${service.id}" 
                                    data-bs-toggle="modal" 
                                    data-bs-target="#deleteServiceModal">Delete</button>
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

    <!-- Add Service Modal -->
    <div class="modal fade" id="addServiceModal" tabindex="-1" aria-labelledby="addServiceModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addServiceModalLabel">Add New Service</h5>
                </div>
                <div class="modal-body">
                    <form action="serviceadmin?action=insert" method="post">
                        <div class="mb-3">
                            <label for="title" class="form-label">Title:</label>
                            <input type="text" class="form-control" name="title" id="title" required>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="form-label">Description:</label>
                            <textarea class="form-control" name="description" id="description" required></textarea>
                        </div>
                        <button type="submit" class="btn btn-success">Add Service</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Edit Service Modal -->
    <div class="modal fade" id="editServiceModal" tabindex="-1" aria-labelledby="editServiceModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editServiceModalLabel">Edit Service</h5>
                </div>
                <div class="modal-body">
                    <form id="editServiceForm" action="serviceadmin?action=update" method="post">
                        <input type="hidden" name="id" id="editServiceId">
                        <div class="mb-3">
                            <label for="editTitle" class="form-label">Title:</label>
                            <input type="text" class="form-control" name="title" id="editTitle" required>
                        </div>
                        <div class="mb-3">
                            <label for="editDescription" class="form-label">Description:</label>
                            <textarea class="form-control" name="description" id="editDescription" required></textarea>
                        </div>
                        <button type="submit" class="btn btn-success">Update Service</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Delete Service Modal -->
    <div class="modal fade" id="deleteServiceModal" tabindex="-1" aria-labelledby="deleteServiceModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteServiceModalLabel">Confirm Delete</h5>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to delete this service?</p>
                </div>
                <div class="modal-footer">
                    <form id="deleteServiceForm" action="serviceadmin?action=delete" method="post">
                        <input type="hidden" name="id" id="deleteServiceId">
                        <button type="submit" class="btn btn-danger">Delete</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- jQuery and Bootstrap JavaScript -->
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

            // Edit button click event
            $('.edit-btn').click(function() {
                var serviceId = $(this).data('id');
                var serviceTitle = $(this).data('title');
                var serviceDescription = $(this).data('description');

                $('#editServiceId').val(serviceId);
                $('#editTitle').val(serviceTitle);
                $('#editDescription').val(serviceDescription);
            });

            // Delete button click event
            $('.delete-btn').click(function() {
                var serviceId = $(this).data('id');
                $('#deleteServiceId').val(serviceId);
            });

            renderTableRows();
        });
    </script>
</body>

</html>
