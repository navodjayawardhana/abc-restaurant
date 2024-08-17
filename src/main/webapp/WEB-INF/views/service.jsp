<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- Importing JSTL Core Tag Library -->
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Our Services</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="css/style.css" rel="stylesheet" />
  <style>
    .service-section {
      background-color: #f8f9fa;
      padding: 60px 0;
    }
    .service-title {
      font-size: 36px;
      font-weight: bold;
      margin-bottom: 20px;
      text-align: center;
    }
    .service-item {
      padding: 30px;
      border-radius: 10px;
      background-color: #fff;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }
    .service-item h5 {
      margin-top: 15px;
      font-size: 20px;
      font-weight: bold;
    }
  </style>
</head>
<body class="sub_page">

  <div class="hero_area">
    <div class="bg-box">
      <img src="images/hero-bg.jpg" alt="">
    </div>

    <%@ include file="navbar.jsp" %>

  </div>

<div class="container service-section">
  <div class="row">
    <div class="col-lg-12">
      <h2 class="service-title">Our Services</h2>
    </div>

    <!-- Loop through the services from the backend -->
    <c:forEach var="service" items="${services}">
      <div class="col-md-4">
        <div class="service-item text-center">
          <i class="fa fa-cutlery fa-3x"></i> <!-- You can change icons dynamically if needed -->
          <h5>${service.title}</h5> <!-- Dynamically display the service title -->
          <p>${service.description}</p> <!-- Dynamically display the service description -->
        </div>
      </div>
    </c:forEach>
    
  </div>
</div>

<%@ include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
