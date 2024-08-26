<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <meta name="author" content="" />
  <link rel="shortcut icon" href="images/favicon.png" type="">

  <title>ABC Restaurant</title>

  <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />

  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/css/nice-select.min.css" />
  <link href="css/font-awesome.min.css" rel="stylesheet" />

  <link href="css/style.css" rel="stylesheet" />
  <link href="css/responsive.css" rel="stylesheet" />

</head>

<body class="sub_page">

  <div class="hero_area">
    <div class="bg-box">
      <img src="images/hero-bg.jpg" alt="">
    </div>

    <%@ include file="navbar.jsp" %>

  </div>

  <section class="about_section layout_padding">
    <div class="container">
      <div class="row">
        <div class="col-md-6">
          <div class="img-box">
            <img src="images/about-img.png" alt="About ABC Restaurant">
          </div>
        </div>
        <div class="col-md-6">
          <div class="detail-box">
            <div class="heading_container">
              <h2>
                About ABC Restaurant
              </h2>
            </div>
            <p>
              Welcome to ABC Restaurant, where flavor meets tradition. Since our inception, we have strived to provide a dining experience that combines culinary excellence with a warm, family-friendly atmosphere. We are passionate about serving fresh, high-quality meals made from locally sourced ingredients.
            </p>
            <p>
              Our menu showcases a perfect blend of classic dishes and contemporary cuisine, created with love and care by our dedicated chefs. From signature dishes to daily specials, every plate is a testament to our commitment to excellence.
            </p>
            <a href="menu">
              Explore Our Menu
            </a>
          </div>
        </div>
      </div>
    </div>
  </section>

  
  <%@ include file="footer.jsp" %>

  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>
