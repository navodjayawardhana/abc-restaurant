<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Error - ABC Restaurant</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f4f4f4;
        }
        .error-message {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.1);
            padding: 50px;
            margin-top: 100px;
            text-align: center;
        }
        .error-icon {
            font-size: 60px;
            color: #dc3545;
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="error-message">
                    <div class="error-icon">
                        <i class="bi bi-exclamation-triangle-fill"></i> 
                    </div>
                    <h1 class="display-4">Oops!</h1>
                    <p class="lead">Something went wrong.</p>
                    <p class="text-muted">We couldn't process your request. Please try again later.</p>
                    <a href="index.jsp" class="btn btn-primary">Go Back to Home</a>
                </div>
            </div>
        </div>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
