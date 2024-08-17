<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Heads Management</title>
    <style>
        .table-container {
            margin-top: 20px;
        }

        .pagination {
            justify-content: center;
        }
    </style>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
    <div class="container mt-4">
        <div class="row mb-3">
            <div class="col-md-6">
                <input type="text" id="searchInput" class="form-control" placeholder="Search by Title...">
            </div>
            <div class="col-md-6 text-end">
                <a class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addHeadModal">Add New Head</a>
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
                    <c:forEach var="head" items="${heads}">
                        <tr>
                            <td>${head.title}</td>
                            <td>${head.description}</td>
                            <td>
                                <button class="btn btn-success btn-sm edit-btn" data-id="${head.id}" data-title="${head.title}" data-description="${head.description}" data-bs-toggle="modal" data-bs-target="#editHeadModal">Edit</button>
                                <button class="btn btn-danger btn-sm delete-btn" data-id="${head.id}" data-bs-toggle="modal" data-bs-target="#deleteHeadModal">Delete</button>
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

    <!-- Add Head Modal -->
    <div class="modal fade" id="addHeadModal" tabindex="-1" aria-labelledby="addHeadModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addHeadModalLabel">Add New Head</h5>
                </div>
                <div class="modal-body">
                    <form action="heads?action=insert" method="post">
                        <div class="mb-3">
                            <label for="title" class="form-label">Title:</label>
                            <input type="text" class="form-control" name="title" id="title" required>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="form-label">Description:</label>
                            <textarea class="form-control" name="description" id="description" required></textarea>
                        </div>
                        <button type="submit" class="btn btn-success">Add Head</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Edit Head Modal -->
    <div class="modal fade" id="editHeadModal" tabindex="-1" aria-labelledby="editHeadModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editHeadModalLabel">Edit Head</h5>
                </div>
                <div class="modal-body">
                    <form id="editHeadForm" action="heads?action=update" method="post">
                        <input type="hidden" name="id" id="editHeadId">
                        <div class="mb-3">
                            <label for="editTitle" class="form-label">Title:</label>
                            <input type="text" class="form-control" name="title" id="editTitle" required>
                        </div>
                        <div class="mb-3">
                            <label for="editDescription" class="form-label">Description:</label>
                            <textarea class="form-control" name="description" id="editDescription" required></textarea>
                        </div>
                        <button type="submit" class="btn btn-success">Update Head</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Delete Head Modal -->
    <div class="modal fade" id="deleteHeadModal" tabindex="-1" aria-labelledby="deleteHeadModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteHeadModalLabel">Confirm Delete</h5>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to delete this head?</p>
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

            // Edit button click event
            $('.edit-btn').click(function() {
                var headId = $(this).data('id');
                var headTitle = $(this).data('title');
                var headDescription = $(this).data('description');

                $('#editHeadId').val(headId);
                $('#editTitle').val(headTitle);
                $('#editDescription').val(headDescription);
            });

            // Delete button click event
            $('.delete-btn').click(function() {
                var headId = $(this).data('id');
                $('#confirmDeleteButton').data('id', headId);
            });

            $('#confirmDeleteButton').click(function() {
                var headId = $(this).data('id');
                window.location.href = 'heads?action=delete&id=' + headId;
            });

            renderTableRows();
        });
    </script>

</body>

</html>
