<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Dashboard</title>
   <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .card {
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .card .card-body {
            padding: 1.5rem;
        }

        .text-xs {
            font-size: 0.8rem;
            font-weight: bold;
        }

        .icon {
            font-size: 2rem;
            color: rgba(0, 0, 0, 0.3);
        }

        .table-container {
            margin-top: 20px;
        }

        .pagination {
            justify-content: center;
        }
    </style>
</head>

<body>

    <div class="container-fluid">
        <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">Dashboard</h1>
             <c:if test="${user.role != 'STAFF'}">
            <a href="generatePdfReport?report=incomeStatement" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
                <i class="fas fa-download fa-sm text-white-50"></i> Generate Report
            </a>
            </c:if>
        </div>

        <div class="row">
            <c:if test="${user.role != 'STAFF'}">
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card border-left-primary shadow h-100 py-2">
                    <div class="card-body">
                        <div class="row no-gutters align-items-center">
                            <div class="col mr-2">
                                <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">Total Earnings</div>
                                <div class="h5 mb-0 font-weight-bold text-gray-800">LKR <c:out value="${totalEarnings}"/></div>
                            </div>
                            <div class="col-auto">
                                <i class="fas fa-dollar-sign icon"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            

            
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card border-left-info shadow h-100 py-2">
                    <div class="card-body">
                        <div class="row no-gutters align-items-center">
                            <div class="col mr-2">
                                <div class="text-xs font-weight-bold text-info text-uppercase mb-1">Total Branches</div>
                                <div class="h5 mb-0 font-weight-bold text-gray-800">${totalBranchCount}</div>
                            </div>
                            <div class="col-auto">
                                <i class="fas fa-building icon"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </c:if>

            
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card border-left-success shadow h-100 py-2">
                    <div class="card-body">
                        <div class="row no-gutters align-items-center">
                            <div class="col mr-2">
                                <div class="text-xs font-weight-bold text-success text-uppercase mb-1">Pending Bookings</div>
                                <div class="h5 mb-0 font-weight-bold text-gray-800">${pendingBookingCount}</div>
                            </div>
                            <div class="col-auto">
                                <i class="fas fa-calendar-alt icon"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

           
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card border-left-warning shadow h-100 py-2">
                    <div class="card-body">
                        <div class="row no-gutters align-items-center">
                            <div class="col mr-2">
                                <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">Pending Orders</div>
                                <div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value="${pendingOrderCount}"/></div>
                            </div>
                            <div class="col-auto">
                                <i class="fas fa-receipt icon"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

       
        <c:if test="${user.role != 'STAFF'}">
        <div class="table-responsive table-container mt-4">
            <h3>Daily Sales Report</h3>
            <table class="table table-bordered" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Total Selling Products</th>
                        <th>Income</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="sale" items="${dailySales}">
                        <tr>
                            <td>${sale.orderDate}</td>
                            <td>${sale.totalProducts}</td>
                            <td>${sale.totalIncome}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        </c:if>
    </div>

   
    <script src="https://kit.fontawesome.com/a076d05399.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
</body>

</html>
