<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Booking Management</title>
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
        </div>
         <div class="row mb-3">
			    <a href="generatePdfReport?report=booking" class="btn btn-primary">Download PDF Report</a>
		 </div>

        <div class="table-responsive table-container">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Persons</th>
                        <th>Date</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="booking" items="${bookings}">
                        <tr>
                            <td>${booking.userName}</td>
                            <td>${booking.userEmail}</td>
                            <td>${booking.phoneNumber}</td>
                            <td>${booking.numPersons}</td>
                            <td>${booking.bookingDate}</td>
                            <td>${booking.status}</td>
                            <td>
                                <form action="bookings" method="post">
                                    <input type="hidden" name="id" value="${booking.id}">
                                    
                                    <!-- Disable Approve button if status is "Approved" -->
                                    <button type="submit" name="action" value="approve" class="btn btn-success" 
                                            <c:if test="${booking.status == 'Approved'}">disabled</c:if>>Approve</button>
                                    
                                    <!-- Disable Reject button if status is "Rejected" -->
                                    <button type="submit" name="action" value="reject" class="btn btn-danger" 
                                            <c:if test="${booking.status == 'Rejected'}">disabled</c:if>>Reject</button>
                                </form>
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

            renderTableRows();
        });
    </script>
</body>
</html>
