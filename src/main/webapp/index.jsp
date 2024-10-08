<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
  <!-- Basic -->
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <!-- Mobile Metas -->
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <!-- Site Metas -->
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <meta name="author" content="" />
  <link rel="shortcut icon" href="images/favicon.png" type="">

  <title> ABC-Restaurant </title>

  <!-- bootstrap core css -->
  <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />

  <!--owl slider stylesheet -->
  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css" />
  <!-- nice select  -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/css/nice-select.min.css" integrity="sha512-CruCP+TD3yXzlvvijET8wV5WxxEh5H8P4cmz0RFbKK6FlZ2sYl3AEsKlLPHbniXKSrDdFewhbmBK5skbdsASbQ==" crossorigin="anonymous" />
  <!-- font awesome style -->
  <link href="css/font-awesome.min.css" rel="stylesheet" />

  <!-- Custom styles for this template -->
  <link href="css/style.css" rel="stylesheet" />
  <!-- responsive style -->
  <link href="css/responsive.css" rel="stylesheet" />
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .rating-item {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
            margin-bottom: 20px;
            text-align: center;
        }
        .rating-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
        }
        .rating-item .username {
            font-weight: bold;
            color: #333;
            margin-bottom: 10px;
        }
        .rating-item .text-muted {
            font-size: 14px;
            color: #6c757d;
        }
        .rating-item .rating-stars {
            color: #f1c40f;
            font-size: 20px;
        }
        .rating-container {
            margin-top: 40px;
        }
        .rating-container .col-md-4 {
            padding-left: 15px;
            padding-right: 15px;
        }
    </style>

</head>

<body>

  <div class="hero_area">
    <div class="bg-box">
      <img src="images/hero-bg.jpg" alt="">
    </div>
    <!-- header section strats -->
    <%@ include file="WEB-INF/views/navbar.jsp" %>
    <!-- end header section -->
    <!-- slider section -->
    <section class="slider_section ">
  <div id="customCarousel1" class="carousel slide" data-ride="carousel">
    <div class="carousel-inner">
      <c:forEach var="head" items="${heads}" varStatus="status">
        <div class="carousel-item ${status.first ? 'active' : ''}">
          <div class="container">
            <div class="row">
              <div class="col-md-7 col-lg-6">
                <div class="detail-box">
                  <h1>${head.title}</h1>
                  <p>${head.description}</p>
                  <div class="btn-box">
                    <a href="menu" class="btn1">
                      Order Now
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </c:forEach>
    </div>

    <div class="container">
      <ol class="carousel-indicators">
        <c:forEach var="head" items="${heads}" varStatus="status">
          <li data-target="#customCarousel1" data-slide-to="${status.index}" class="${status.first ? 'active' : ''}"></li>
        </c:forEach>
      </ol>
    </div>
  </div>
</section>

    <!-- end slider section -->
  </div>

  <!-- offer section -->

  <section class="offer_section layout_padding-bottom">
    <div class="offer_container">
      <div class="container">
        <!-- Bootstrap Carousel for Promotions -->
        <div id="promotionCarousel" class="carousel slide" data-ride="carousel">
          <!-- Indicators (optional, if you want dots below the slider) -->
          <ol class="carousel-indicators">
            <c:forEach var="promotion" items="${promotions}" varStatus="status">
              <li data-target="#promotionCarousel" data-slide-to="${status.index}" class="${status.first ? 'active' : ''}"></li>
            </c:forEach>
          </ol>

          <!-- Carousel items -->
          <div class="carousel-inner">
            <!-- Loop through promotions and group by two items per slide -->
            <c:forEach var="promotion" items="${promotions}" varStatus="status">
              <c:if test="${status.index % 2 == 0}">
                <div class="carousel-item ${status.first ? 'active' : ''}">
                  <div class="row">
              </c:if>
              
              <div class="col-md-6">
                <div class="box">
                  <div class="img-box">
                    <c:if test="${promotion.imagePath != null}">
                      <img src="${pageContext.request.contextPath}/${promotion.imagePath}" alt="${promotion.title}" width="100%">
                    </c:if>
                  </div>
                  <div class="detail-box">
                    <h5>${promotion.title}</h5>
                    <h6><span>${promotion.prestige}%</span> Off</h6>
                    <a href="menu">
                      Order Now
                      <svg version="1.1" xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" viewBox="0 0 456.029 456.029" style="enable-background:new 0 0 456.029 456.029;">
                        <g><g><path d="M345.6,338.862c-29.184,0-53.248,23.552-53.248,53.248c0,29.184,23.552,53.248,53.248,53.248
                        c29.184,0,53.248-23.552,53.248-53.248C398.336,362.926,374.784,338.862,345.6,338.862z"/></g></g>
                        <g><g><path d="M439.296,84.91c-1.024,0-2.56-0.512-4.096-0.512H112.64l-5.12-34.304C104.448,27.566,84.992,10.67,61.952,10.67H20.48
                        C9.216,10.67,0,19.886,0,31.15c0,11.264,9.216,20.48,20.48,20.48h41.472c2.56,0,4.608,2.048,5.12,4.608l31.744,216.064
                        c4.096,27.136,27.648,47.616,55.296,47.616h212.992c26.624,0,49.664-18.944,55.296-45.056l33.28-166.4
                        C457.728,97.71,450.56,86.958,439.296,84.91z"/></g></g>
                        <g><g><path d="M215.04,389.55c-1.024-28.16-24.576-50.688-52.736-50.688c-29.696,1.536-52.224,26.112-51.2,55.296
                        c1.024,28.16,24.064,50.688,52.224,50.688h1.024C193.536,443.31,216.576,418.734,215.04,389.55z"/></g></g>
                      </svg>
                    </a>
                  </div>
                </div>
              </div>

              <c:if test="${status.index % 2 == 1 || status.last}">
                  </div> <!-- End of row -->
                </div> <!-- End of carousel-item -->
              </c:if>
            </c:forEach>
          </div>

          <!-- Controls -->
          <a class="carousel-control-prev" href="#promotionCarousel" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
          </a>
          <a class="carousel-control-next" href="#promotionCarousel" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
          </a>
        </div>
      </div>
    </div>
  </section>


  <!-- end offer section -->

  <!-- food section -->

  <section class="food_section layout_padding-bottom">
    <div class="container">
      <div class="heading_container heading_center">
        <h2>
          Our Menu
        </h2>
      </div>
        <!-- Category Filter Menu -->
        <ul class="filters_menu">
            <li class="active" data-filter="*">All</li>
            <li data-filter=".burger">Burger</li>
            <li data-filter=".pizza">Pizza</li>
            <li data-filter=".pasta">Pasta</li>
            <li data-filter=".fries">Fries</li>
        </ul>

        <!-- Products Content Section -->
        <div class="filters-content">
    <div class="row grid">
        <!-- Initialize a counter -->
        <c:set var="counter" value="0" />
        
        <!-- Loop through products and dynamically assign the category class -->
        <c:forEach var="product" items="${products}">
            <!-- Only display the first 6 items -->
            <c:if test="${counter < 6}">
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
            </c:if>
            <!-- Increment the counter -->
            <c:set var="counter" value="${counter + 1}" />
        </c:forEach>
    </div>
</div>

        </div>
      
      <div class="btn-box">
        <a href="menu">
          View More
        </a>
      </div>
    
  </section>
  
  <section class="client_section layout_padding-bottom">
    <div class="container">
      <div class="heading_container heading_center psudo_white_primary mb_45">
        <h2>
          What Says Our Customers
        </h2>
      </div>
      <div class="carousel-wrap row ">
        <div class="owl-carousel client_owl-carousel">
          <c:forEach var="rating" items="${ratings}">
          <div class="item">
            <div class="box">
              <div class="detail-box">
                <p>
                   "${rating.feedback}"
                </p>
                <h6>
                 ${rating.username}
                </h6>
                <p>
                 -${rating.username}-
                </p>
                <p class="text-muted">
                                Rating: <span class="rating-stars">&#9733;&#9733;&#9733;&#9733;&#9733;</span>
                            </p>
              </div>
            </div>
          </div>
</c:forEach>
          
        </div>
      </div>
    </div>
  </section>

 

 

  <!-- end client section -->

  <!-- footer section -->
<%@ include file="WEB-INF/views/footer.jsp" %>
  <!-- footer section -->

  <!-- jQery -->
  <script src="js/jquery-3.4.1.min.js"></script>
  <!-- popper js -->
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous">
  </script>
  <!-- bootstrap js -->
  <script src="js/bootstrap.js"></script>
  <!-- owl slider -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js">
  </script>
  <!-- isotope js -->
  <script src="https://unpkg.com/isotope-layout@3.0.4/dist/isotope.pkgd.min.js"></script>
  <!-- nice select -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/js/jquery.nice-select.min.js"></script>
  <!-- custom js -->
  <script src="js/custom.js"></script>
  <!-- Google Map -->
  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCh39n5U-4IoWpsVGUHWdqB6puEkhRLdmI&callback=myMap">
  </script>
 
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    


</body>

</html>