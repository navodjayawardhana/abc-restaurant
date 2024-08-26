<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <meta name="author" content="" />
  <link rel="shortcut icon" href="images/favicon.png" type="">

  <title> ABC Restaurant </title>

  <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />

  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css" />
  
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/css/nice-select.min.css" integrity="sha512-CruCP+TD3yXzlvvijET8wV5WxxEh5H8P4cmz0RFbKK6FlZ2sYl3AEsKlLPHbniXKSrDdFewhbmBK5skbdsASbQ==" crossorigin="anonymous" />
  
  <link href="css/font-awesome.min.css" rel="stylesheet" />

  <link href="css/style.css" rel="stylesheet" />
  
  <link href="css/responsive.css" rel="stylesheet" />

  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.isotope/3.0.6/isotope.pkgd.min.js"></script>
</head>

<body class="sub_page">

  <div class="hero_area">
    <div class="bg-box">
      <img src="images/hero-bg.jpg" alt="">
    </div>
    <%@ include file="navbar.jsp" %>
  </div>

  
  <section class="food_section layout_padding">
    <div class="container ">
        

        
        <ul class="filters_menu">
            <li class="active" data-filter="*">All</li>
            <li data-filter=".burger">Burger</li>
            <li data-filter=".pizza">Pizza</li>
            <li data-filter=".pasta">Pasta</li>
            <li data-filter=".fries">Fries</li>
        </ul>

        
        <div class="filters-content">
            <div class="row grid">
              
                <c:forEach var="product" items="${products}">
                    <div class="col-sm-6 col-lg-4 grid-item all ${product.category.toLowerCase()}">
                        <div class="box">
                            <div>
                                <div class="img-box">
                                    <img src="${pageContext.request.contextPath}/${product.imagePath}" alt="${product.name}">
                                </div>
                                <div class="detail-box">
                                    <h5>${product.name}</h5>
                                    <p>${product.description}</p>
                                    <div class="options">
                                        <h6>LKR: ${product.price}</h6>
                                        <h6 hidden>Category: ${product.category}</h6>
									<a href="cart?action=addToCart&productId=${product.id}&quantity=1" class="btn btn-warning">
									    <i class="fa fa-shopping-cart" style="color: white;"></i>
									</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
  </section>

 
  <script>
    $(document).ready(function(){
     
        var $grid = $('.grid').isotope({
            itemSelector: '.grid-item',
            layoutMode: 'fitRows'
        });

       
        $('.filters_menu li').click(function() {
            $('.filters_menu li').removeClass('active');
            $(this).addClass('active');

            var filterValue = $(this).attr('data-filter');
            $grid.isotope({ filter: filterValue });
        });
    });
  </script>

  <%@ include file="footer.jsp" %>

</body>
</html>
