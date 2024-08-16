<!-- Navbar with Cart Icon -->
<header class="header_section">
    <div class="container">
        <nav class="navbar navbar-expand-lg custom_nav-container ">
            <a class="navbar-brand" href="index.html">
                <span>ABC-Restaurant</span>
            </a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class=""> </span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mx-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="index">Home <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="menu">Menu</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="about">About</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="booking">Book Table</a>
                    </li>
                     <li class="nav-item">
                        <a class="nav-link" href="">Gallery</a>
                    </li>
                </ul>
                <div class="user_option">
                    <a href="login.jsp" class="user_link">
                        <i class="fa fa-user" aria-hidden="true"></i>
                    </a>
                    <!-- Cart Icon with Offcanvas Trigger -->
                   <a class="cart_link" href="#" data-bs-toggle="offcanvas" data-bs-target="#cartOffcanvas">
					    <i class="fa fa-shopping-cart" style="color: white;"></i>
					    <span id="cart-item-count" class="badge bg-warning">
					        <c:out value="${cart.size()}" /> <!-- Display the current number of items in the cart -->
					    </span> <!-- Dynamic cart item count -->
					</a>
                </div>
            </div>
        </nav>
    </div>
</header>

<!-- Offcanvas for Cart -->
<!-- Offcanvas for Cart -->
<div class="offcanvas offcanvas-end" tabindex="-1" id="cartOffcanvas" aria-labelledby="cartOffcanvasLabel">
    <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="cartOffcanvasLabel">Shopping Cart</h5>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>
    <div class="offcanvas-body d-flex flex-column justify-content-between">
        
        <div class="cart-items">
           
            <div class="cart-item card mb-3">
                
            </div>
        </div>

       
    </div>
</div>


<style>
  
    .offcanvas-end {
        width: 70vw !important;
    }
</style>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const cartLink = document.querySelector(".cart_link");
        const cartItemsContainer = document.querySelector(".cart-items");
        const cartTotalElement = document.querySelector("#cart-total");

        cartLink.addEventListener("click", function () {
            fetchCartItems();
        });

        function fetchCartItems() {
            fetch("cart?action=viewCart")
                .then(response => response.text())
                .then(html => {
                    cartItemsContainer.innerHTML = html;
                    updateCartTotal();
                })
                .catch(error => console.error("Error fetching cart items:", error));
        }

        function updateCartTotal() {
            const cartItems = document.querySelectorAll(".cart-item");
            let total = 0;

            cartItems.forEach(item => {
                const itemTotalText = item.querySelector(".item-total").textContent;
                const itemTotal = parseFloat(itemTotalText.replace("LKR", "").trim());
                total += itemTotal;
            });

            cartTotalElement.textContent = total.toFixed(2);
        }
    });
</script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const cartItemCountElement = document.querySelector("#cart-item-count");

        // Function to update the cart item count dynamically
        function updateCartItemCount() {
            fetch('cart?action=getCartCount')
                .then(response => response.json())
                .then(data => {
                    cartItemCountElement.textContent = data.count;
                })
                .catch(error => console.error("Error fetching cart count:", error));
        }

        // Update the cart item count when the page loads
        updateCartItemCount();

        // Example: If you add/remove items in the cart, call this function again to refresh the count
        // updateCartItemCount();
    });
</script>



<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>

