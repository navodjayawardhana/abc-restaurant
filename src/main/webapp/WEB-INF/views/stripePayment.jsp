
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Stripe Payment</title>
    <!-- Bootstrap CSS for styling -->
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
</head>
<body>

    <div class="container mt-5">
        <h2 class="mb-4">Complete Your Payment</h2>
        <p>You are about to make a payment. Click the button below to proceed to Stripe’s secure payment gateway.</p>
        
        <!-- Stripe Payment Button -->
        <form action="https://buy.stripe.com/test_4gw0209kPf7iexG3cc" method="GET">
            <button type="submit" class="btn btn-primary btn-lg">Proceed to Payment</button>
        </form>
        
        <div class="mt-3">
            <a href="checkout" class="btn btn-secondary">Back to Checkout</a>
        </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
</body>
</html>
