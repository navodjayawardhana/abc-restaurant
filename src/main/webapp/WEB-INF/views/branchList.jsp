<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Branch Management</title>
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
                <input type="text" id="searchInput" class="form-control" placeholder="Search by Name...">
            </div>
            <div class="col-md-6 text-end">
                <a class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addBranchModal">Add New Branch</a>
            </div>
        </div>

        <div class="table-responsive table-container">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Contact</th>
                        <th>Address</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="branch" items="${branches}">
                        <tr>
                            <td>${branch.name}</td>
                            <td>${branch.email}</td>
                            <td>${branch.contact}</td>
                            <td>${branch.address}</td>
                            <td>
                                <button class="btn btn-success btn-sm edit-btn" data-id="${branch.id}" data-name="${branch.name}" data-email="${branch.email}" data-contact="${branch.contact}" data-address="${branch.address}" data-password="${branch.password}" data-bs-toggle="modal" data-bs-target="#editBranchModal">Edit</button>
                                <button class="btn btn-danger btn-sm delete-btn" data-id="${branch.id}" data-bs-toggle="modal" data-bs-target="#deleteBranchModal">Delete</button>
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

    <!-- Add Branch Modal -->
    <div class="modal fade" id="addBranchModal" tabindex="-1" aria-labelledby="addBranchModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addBranchModalLabel">Add New Branch</h5>
                </div>
                <div class="modal-body">
                    <form action="branches?action=insert" method="post">
                        <div class="mb-3">
                            <label for="name" class="form-label">Name:</label>
                            <input type="text" class="form-control" name="name" id="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Email:</label>
                            <input type="email" class="form-control" name="email" id="email" required>
                        </div>
                        <div class="mb-3">
                            <label for="contact" class="form-label">Contact:</label>
                            <input type="text" class="form-control" name="contact" id="contact" required>
                        </div>
                        <div class="mb-3">
                            <label for="address" class="form-label">Address:</label>
                            <textarea class="form-control" name="address" id="address" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password:</label>
                            <input type="password" class="form-control" name="password" id="password" required>
                        </div>
                        <button type="submit" class="btn btn-success">Add Branch</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Edit Branch Modal -->
    <div class="modal fade" id="editBranchModal" tabindex="-1" aria-labelledby="editBranchModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editBranchModalLabel">Edit Branch</h5>
                </div>
                <div class="modal-body">
                    <form id="editBranchForm" action="branches?action=update" method="post">
                        <input type="hidden" name="id" id="editBranchId">
                        <div class="mb-3">
                            <label for="editName" class="form-label">Name:</label>
                            <input type="text" class="form-control" name="name" id="editName" required>
                        </div>
                        <div class="mb-3">
                            <label for="editEmail" class="form-label">Email:</label>
                            <input type="email" class="form-control" name="email" id="editEmail" required>
                        </div>
                        <div class="mb-3">
                            <label for="editContact" class="form-label">Contact:</label>
                            <input type="text" class="form-control" name="contact" id="editContact" required>
                        </div>
                        <div class="mb-3">
                            <label for="editAddress" class="form-label">Address:</label>
                            <textarea class="form-control" name="address" id="editAddress" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="editPassword" class="form-label">Password:</label>
                            <input type="password" class="form-control" name="password" id="editPassword" required>
                        </div>
                        <button type="submit" class="btn btn-success">Update Branch</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Delete Branch Modal -->
    <div class="modal fade" id="deleteBranchModal" tabindex="-1" aria-labelledby="deleteBranchModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteBranchModalLabel">Confirm Delete</h5>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to delete this branch?</p>
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
                var branchId = $(this).data('id');
                var branchName = $(this).data('name');
                var branchEmail = $(this).data('email');
                var branchContact = $(this).data('contact');
                var branchAddress = $(this).data('address');
                var branchPassword = $(this).data('password');

                $('#editBranchId').val(branchId);
                $('#editName').val(branchName);
                $('#editEmail').val(branchEmail);
                $('#editContact').val(branchContact);
                $('#editAddress').val(branchAddress);
                $('#editPassword').val(branchPassword);
            });

            // Delete button click event
            $('.delete-btn').click(function() {
                var branchId = $(this).data('id');
                $('#confirmDeleteButton').data('id', branchId);
            });

            $('#confirmDeleteButton').click(function() {
                var branchId = $(this).data('id');
                window.location.href = 'branches?action=delete&id=' + branchId;
            });

            renderTableRows();
        });
    </script>

</body>

</html>
