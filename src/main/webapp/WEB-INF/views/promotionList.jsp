<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Promotion Management</title>
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
                <a class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addPromotionModal">Add New Promotion</a>
            </div>
        </div>

        <div class="table-responsive table-container">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Prestige</th>
                        <th>Description</th>
                        <th>Image</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="promotion" items="${promotions}">
                        <tr>
                            <td>${promotion.title}</td>
                            <td>${promotion.prestige}</td>
                            <td>${promotion.description}</td>
                            <td>
                                <c:if test="${promotion.imagePath != null}">
                                    <img src="${pageContext.request.contextPath}/${promotion.imagePath}" alt="${promotion.title}" width="50">
                                </c:if>
                            </td>
                            <td>
                                <button class="btn btn-success btn-sm edit-btn" data-id="${promotion.id}" data-title="${promotion.title}" data-prestige="${promotion.prestige}" data-description="${promotion.description}" data-image-path="${promotion.imagePath}" data-bs-toggle="modal" data-bs-target="#editPromotionModal">Edit</button>
                                <button class="btn btn-danger btn-sm delete-btn" data-id="${promotion.id}" data-bs-toggle="modal" data-bs-target="#deletePromotionModal">Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Pagination -->
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

    <!-- Add Promotion Modal -->
    <div class="modal fade" id="addPromotionModal" tabindex="-1" aria-labelledby="addPromotionModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addPromotionModalLabel">Add New Promotion</h5>
                </div>
                <div class="modal-body">
                    <form action="promotions?action=insert" method="post" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label for="title" class="form-label">Title:</label>
                            <input type="text" class="form-control" name="title" id="title" required>
                        </div>
                        <div class="mb-3">
                            <label for="prestige" class="form-label">Prestige:</label>
                            <input type="number" class="form-control" name="prestige" id="prestige" required>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="form-label">Description:</label>
                            <textarea class="form-control" name="description" id="description" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="image" class="form-label">Promotion Image:</label>
                            <input type="file" class="form-control" name="image" id="image">
                        </div>
                        <button type="submit" class="btn btn-success">Add Promotion</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Edit Promotion Modal -->
    <div class="modal fade" id="editPromotionModal" tabindex="-1" aria-labelledby="editPromotionModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editPromotionModalLabel">Edit Promotion</h5>
                </div>
                <div class="modal-body">
                    <form id="editPromotionForm" action="promotions?action=update" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="id" id="editPromotionId">
                        <div class="mb-3">
                            <label for="editTitle" class="form-label">Title:</label>
                            <input type="text" class="form-control" name="title" id="editTitle" required>
                        </div>
                        <div class="mb-3">
                            <label for="editPrestige" class="form-label">Prestige:</label>
                            <input type="number" class="form-control" name="prestige" id="editPrestige" required>
                        </div>
                        <div class="mb-3">
                            <label for="editDescription" class="form-label">Description:</label>
                            <textarea class="form-control" name="description" id="editDescription" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="editImage" class="form-label">Promotion Image:</label>
                            <input type="file" class="form-control" name="image" id="editImage">
                            <div id="currentImageContainer" class="mt-3">
                                <img id="currentImage" src="" alt="" width="200" class="img-thumbnail">
                                <p>Current Image</p>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-success">Update Promotion</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Delete Promotion Modal -->
    <div class="modal fade" id="deletePromotionModal" tabindex="-1" aria-labelledby="deletePromotionModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deletePromotionModalLabel">Confirm Delete</h5>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to delete this promotion?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-danger" id="confirmDeleteButton">Delete</button>
                </div>
            </div>
        </div>
    </div>

    <!-- jQuery & Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        $(document).ready(function () {
            var currentPage = 1;
            var rowsPerPage = 10;

            function renderTableRows() {
                var allRows = $('#dataTable tbody tr');
                var filteredRows = allRows.filter(function () {
                    return $(this).is(':visible');
                });

                var totalRows = filteredRows.length;
                var totalPages = Math.ceil(totalRows / rowsPerPage);

                allRows.hide();
                filteredRows.slice((currentPage - 1) * rowsPerPage, currentPage * rowsPerPage).show();

                $('#prevPage').parent().toggleClass('disabled', currentPage === 1);
                $('#nextPage').parent().toggleClass('disabled', currentPage === totalPages || totalRows === 0);
            }

            $('#prevPage').click(function (e) {
                e.preventDefault();
                if (currentPage > 1) {
                    currentPage--;
                    renderTableRows();
                }
            });

            $('#nextPage').click(function (e) {
                e.preventDefault();
                if (currentPage < Math.ceil($('#dataTable tbody tr:visible').length / rowsPerPage)) {
                    currentPage++;
                    renderTableRows();
                }
            });

            $('#searchInput').keyup(function () {
                var searchValue = $(this).val().toLowerCase();
                $('#dataTable tbody tr').each(function () {
                    $(this).toggle($(this).find('td:first').text().toLowerCase().indexOf(searchValue) > -1);
                });
                currentPage = 1;
                renderTableRows();
            });

            // Edit button click event
            $('.edit-btn').click(function () {
                var promotionId = $(this).data('id');
                var promotionTitle = $(this).data('title');
                var promotionPrestige = $(this).data('prestige');
                var promotionDescription = $(this).data('description');
                var promotionImagePath = $(this).data('image-path');

                $('#editPromotionId').val(promotionId);
                $('#editTitle').val(promotionTitle);
                $('#editPrestige').val(promotionPrestige);
                $('#editDescription').val(promotionDescription);

                if (promotionImagePath) {
                    $('#currentImage').attr('src', '${pageContext.request.contextPath}/' + promotionImagePath);
                    $('#currentImageContainer').show();
                } else {
                    $('#currentImageContainer').hide();
                }
            });

            // Delete button click event
            $('.delete-btn').click(function () {
                var promotionId = $(this).data('id');
                $('#confirmDeleteButton').data('id', promotionId);
            });

            $('#confirmDeleteButton').click(function () {
                var promotionId = $(this).data('id');
                window.location.href = 'promotions?action=delete&id=' + promotionId;
            });

            renderTableRows();
        });
    </script>

</body>

</html>
