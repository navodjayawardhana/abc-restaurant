<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Gallery Management</title>
    <style>
        .table-container {
            margin-top: 20px;
        }
    </style>
   <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
       

       
        <button class="btn btn-success mb-3" data-bs-toggle="modal" data-bs-target="#addGalleryModal">Add New Image</button>

        <div class="table-responsive table-container">
            <table class="table table-bordered" id="galleryTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Image</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="gallery" items="${galleries}">
                        <tr>
                            <td>${gallery.id}</td>
                            <td>${gallery.name}</td>
                            <td><img src="${pageContext.request.contextPath}/${gallery.imagePath}" alt="${gallery.name}" width="100"></td>
                            <td>
                              
                                <button class="btn btn-warning btn-sm edit-gallery-btn" data-id="${gallery.id}" data-name="${gallery.name}" data-image-path="${gallery.imagePath}" data-bs-toggle="modal" data-bs-target="#editGalleryModal">Edit</button>

                           
                                <button class="btn btn-danger btn-sm delete-gallery-btn" data-id="${gallery.id}" data-bs-toggle="modal" data-bs-target="#deleteGalleryModal">Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Add Gallery Modal -->
    <div class="modal fade" id="addGalleryModal" tabindex="-1" aria-labelledby="addGalleryModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addGalleryModalLabel">Add New Gallery Image</h5>
                </div>
                <div class="modal-body">
                    <form id="addGalleryForm" action="galleryadmin?action=insert" method="post" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label for="galleryName" class="form-label">Name</label>
                            <input type="text" class="form-control" name="name" id="galleryName" required>
                        </div>
                        <div class="mb-3">
                            <label for="galleryImage" class="form-label">Image</label>
                            <input type="file" class="form-control" name="image" id="galleryImage" required>
                        </div>
                        <button type="submit" class="btn btn-success">Add Image</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Edit Gallery Modal -->
    <div class="modal fade" id="editGalleryModal" tabindex="-1" aria-labelledby="editGalleryModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editGalleryModalLabel">Edit Gallery Image</h5>
                </div>
                <div class="modal-body">
                    <form id="editGalleryForm" action="galleryadmin?action=update" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="id" id="editGalleryId">
                        <div class="mb-3">
                            <label for="editGalleryName" class="form-label">Name</label>
                            <input type="text" class="form-control" name="name" id="editGalleryName" required>
                        </div>
                        <div class="mb-3">
                            <label for="editGalleryImage" class="form-label">Image</label>
                            <input type="file" class="form-control" name="image" id="editGalleryImage">
                            <div class="mt-2">
                                <img id="editGalleryCurrentImage" src="" alt="Gallery Image" width="100">
                                <p>Current Image</p>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning">Update Image</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Delete Gallery Modal -->
    <div class="modal fade" id="deleteGalleryModal" tabindex="-1" aria-labelledby="deleteGalleryModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteGalleryModalLabel">Delete Gallery Image</h5>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to delete this image?</p>
                </div>
                <div class="modal-footer">
                    <form id="deleteGalleryForm" action="galleryadmin?action=delete" method="post">
                        <input type="hidden" name="id" id="deleteGalleryId">
                        <button type="submit" class="btn btn-danger">Delete</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <script>
    $(document).ready(function() {
       
        $('.edit-gallery-btn').click(function() {
            var galleryId = $(this).data('id');
            var galleryName = $(this).data('name');
            var galleryImagePath = $(this).data('image-path');

            $('#editGalleryId').val(galleryId);
            $('#editGalleryName').val(galleryName);
            $('#editGalleryCurrentImage').attr('src', galleryImagePath);
        });

       
        $('.delete-gallery-btn').click(function() {
            var galleryId = $(this).data('id');
            $('#deleteGalleryId').val(galleryId);
        });
    });
    </script>
</body>
</html>
