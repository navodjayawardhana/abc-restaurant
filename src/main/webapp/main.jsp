<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Admin - Dashboard</title>
    <link href="public/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">
    <link href="public/css/sb-admin-2.min.css" rel="stylesheet">

    <style>
        .alert-popup {
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 1000;
            min-width: 250px;
        }
    </style>
</head>

<body id="page-top">

    <!-- Message Display -->
    <div class="container">
        <c:if test="${not empty sessionScope.message}">
            <div class="alert alert-${sessionScope.messageType} alert-dismissible fade show alert-popup" role="alert">
                ${sessionScope.message}
            </div>
            <c:remove var="message" scope="session" />
            <c:remove var="messageType" scope="session" />
        </c:if>
    </div>

    <!-- Page Wrapper -->
    <div id="wrapper">
        <ul class="navbar-nav bg-gradient-dark sidebar sidebar-dark accordion" id="accordionSidebar">
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="">
                <div class="sidebar-brand-text mx-3">${user.role} Dashboard</div>
            </a>
            <hr class="sidebar-divider my-0">

            <!-- Nav Item - Dashboard -->
            <li class="nav-item active">
                <a class="nav-link" href="main">
                    <i class="fas fa-fw fa-tachometer-alt"></i>
                    <span>Dashboard</span></a>
            </li>

            <!-- Nav Item - Product -->
            <li class="nav-item">
                <a class="nav-link" href="#" onclick="loadProductList();">
                    <i class="fas fa-fw fa-table"></i>
                    <span>Product</span>
                </a>
            </li>
            
            <!-- Nav Item - Orders -->
            <li class="nav-item">
                <a class="nav-link" href="#" onclick="loadOrderList();">
                    <i class="fas fa-fw fa-receipt"></i>
                    <span>Orders</span>
                </a>
            </li>
            
			<li class="nav-item">
			    <a class="nav-link" href="#" onclick="loadPromotionList();">
			        <i class="fas fa-fw fa-tag"></i>
			        <span>Promotion</span>
			    </a>
			</li>
			<li class="nav-item">
			    <a class="nav-link" href="#" onclick="loadRatingList();">
			        <i class="fas fa-fw fa-star"></i>
			        <span>Ratings</span>
			    </a>
			</li>
						
						<!-- Nav Item - Booking -->
			<li class="nav-item">
			    <a class="nav-link" href="#" onclick="loadBookingList();">
			        <i class="fas fa-fw fa-calendar"></i>
			        <span>Booking</span>
			    </a>
			</li>
			<li class="nav-item">
			    <a class="nav-link" href="#" onclick="loadHeadList();">
			        <i class="fas fa-heading"></i>
			        <span>HeroHead</span>
			    </a>
			</li>
			
			
			
			<li class="nav-item">
			    <a class="nav-link" href="#" onclick="loadGalleryList();">
			        <i class="fas fa-fw fa-images"></i>
			        <span>Gallery</span>
			    </a>
			</li>
			
			<!-- New Nav Item - Service -->
			<li class="nav-item">
			    <a class="nav-link" href="#" onclick="loadServiceList();">
			        <i class="fas fa-fw fa-concierge-bell"></i>
			        <span>Service</span>
			    </a>
			</li>

			
			<c:if test="${user.role != 'STAFF'}">
            <!-- Nav Item - Branch -->
            <li class="nav-item">
                <a class="nav-link" href="#" onclick="loadBranchList();">
                    <i class="fas fa-fw fa-building"></i>
                    <span>Branch</span>
                </a>
            </li>

            <!-- Nav Item - User -->
            <li class="nav-item">
                <a class="nav-link" href="#" onclick="loadUserList();">
                    <i class="fas fa-fw fa-users"></i>
                    <span>User</span>
                </a>
            </li>
            </c:if>

            <!-- Divider -->
            <hr class="sidebar-divider d-none d-md-block">

            <!-- Sidebar Toggler (Sidebar) -->
            <div class="text-center d-none d-md-inline">
                <button class="rounded-circle border-0" id="sidebarToggle"></button>
            </div>

        </ul>
        <!-- End of Sidebar -->

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- Topbar -->
                <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                    <!-- Sidebar Toggle (Topbar) -->
                    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                        <i class="fa fa-bars"></i>
                    </button>

                    <!-- Topbar Navbar -->
                    <ul class="navbar-nav ml-auto">
                        <div class="topbar-divider d-none d-sm-block"></div>

                        <!-- Nav Item - User Information -->
                        <li class="nav-item dropdown no-arrow">
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="mr-2 d-none d-lg-inline text-gray-600 small">${user.name}</span>
                            </a>
                            <!-- Dropdown - User Information -->
                            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                                aria-labelledby="userDropdown">
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Logout
                                </a>
                            </div>
                        </li>

                    </ul>

                </nav>
                <!-- End of Topbar -->

                <!-- Begin Page Content -->
                <div class="container-fluid">
                    <!-- Content Row -->
                    <div class="row">
                        <!-- Dynamically include content based on the 'includePage' attribute -->
                        <c:if test="${not empty includePage}">
                            <jsp:include page="${includePage}" />
                        </c:if>
                        
                        
                    </div>
                </div>

                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

            <!-- Footer -->
            <footer class="sticky-footer bg-white">
                <div class="container my-auto">
                    <div class="copyright text-center my-auto">
                        <span>Copyright &copy;</span>
                    </div>
                </div>
            </footer>
            <!-- End of Footer -->

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <!-- Logout Modal-->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                <div class="modal-footer">
                    <button class="btn btn-danger" type="button" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-success" href="index">Logout</a>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap core JavaScript-->
    <script src="public/vendor/jquery/jquery.min.js"></script>
    <script src="public/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="public/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="public/js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="public/vendor/chart.js/Chart.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="public/js/demo/chart-area-demo.js"></script>
    <script src="public/js/demo/chart-pie-demo.js"></script>

    <!-- Include Bootstrap JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>

    <script>
        function loadProductList() {
            $.get("products?action=listProducts", function (data) {
                $('.container-fluid .row').html(data); // Assuming the servlet returns the content of ProductList.jsp
            });
        }

        function loadBranchList() {
            $.get("branches?action=list", function (data) {
                $('.container-fluid .row').html(data); // Assuming the servlet returns the content of BranchList.jsp
            });
        }

        function loadUserList() {
            $.get("users?action=list", function (data) {
                $('.container-fluid .row').html(data); // Assuming the servlet returns the content of UserList.jsp
            });
        }
        
        function loadPromotionList() {
            $.get("promotions?action=list", function (data) {
                $('.container-fluid .row').html(data); // Assuming the servlet returns the content of PromotionList.jsp
            });
        }
        function loadBookingList() {
            $.get("bookings?action=list", function (data) {
                $('.container-fluid .row').html(data); // Assuming the servlet returns the content of BookingList.jsp
            });
        }
        
        function loadHeadList() {
            $.get("heads?action=list", function (data) {
                $('.container-fluid .row').html(data); // Assuming the servlet returns the content of BookingList.jsp
            });
        }

        // Load the order list in the dashboard content
        function loadOrderList() {
            $.get("orders?action=list", function (data) {
                $('.container-fluid .row').html(data); // Load the content of the OrderList.jsp page
            });
        }
        function loadRatingList() {
            $.get("adminratings?action=list", function (data) {
                $('.container-fluid .row').html(data); // Assuming the servlet returns the content of RatingList.jsp
            });
        }
        
        function loadGalleryList() {
            $.get("galleryadmin?action=list", function (data) {
                $('.container-fluid .row').html(data); // Assuming the servlet returns the content of galleryList.jsp
            });
        }
        function loadServiceList() {
            $.get("serviceadmin?action=list", function (data) {
                $('.container-fluid .row').html(data); // Assuming the servlet returns the content of ServiceList.jsp
            });
        }



        setTimeout(() => {
            $(".alert-popup").alert('close');
        }, 3000);
    </script>

</body>

</html>
