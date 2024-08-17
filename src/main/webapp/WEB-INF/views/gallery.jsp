<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Gallery</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="css/style.css" rel="stylesheet" />
  <style>
    .gallery-section {
      padding: 60px 0;
      background-color: #f8f9fa;
    }
    .gallery-title {
      font-size: 36px;
      font-weight: bold;
      margin-bottom: 20px;
      text-align: center;
    }
    .gallery-item img {
      width: 100%;
      border-radius: 10px;
      transition: all 0.3s ease;
    }
    .gallery-item img:hover {
      transform: scale(1.05);
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

<div class="container gallery-section">
  <div class="row">
    <div class="col-lg-12">
      <h2 class="gallery-title">Our Gallery</h2>
    </div>

    <div class="col-md-4">
      <div class="gallery-item">
        <img src="images/gallery1.jpg" alt="Gallery Image 1">
      </div>
    </div>

    <div class="col-md-4">
      <div class="gallery-item">
        <img src="images/gallery2.jpg" alt="Gallery Image 2">
      </div>
    </div>

    <div class="col-md-4">
      <div class="gallery-item">
        <img src="images/gallery3.jpg" alt="Gallery Image 3">
      </div>
    </div>

    <div class="col-md-4">
      <div class="gallery-item">
        <img src="images/gallery4.jpg" alt="Gallery Image 4">
      </div>
    </div>

    <div class="col-md-4">
      <div class="gallery-item">
        <img src="images/gallery5.jpg" alt="Gallery Image 5">
      </div>
    </div>

    <div class="col-md-4">
      <div class="gallery-item">
        <img src="images/gallery6.jpg" alt="Gallery Image 6">
      </div>
    </div>
  </div>
</div>

<%@ include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
