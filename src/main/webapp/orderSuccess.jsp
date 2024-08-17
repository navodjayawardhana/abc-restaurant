<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Success</title>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
  <style>
    body {
      background-color: #f4f4f4;
    }
    .success-message {
      background-color: white;
      border-radius: 8px;
      box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.1);
      padding: 50px;
      margin-top: 100px;
      text-align: center;
    }
    .success-icon {
      font-size: 60px;
      color: #28a745;
    }
  </style>
</head>
<body>

  <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="success-message">
          <div class="success-icon">
            <i class="bi bi-check-circle"></i> <!-- Use bootstrap icon -->
          </div>
          <h1 class="display-4">Success!</h1>
          <p class="lead">Your operation was completed successfully.</p>
          <p class="text-muted">You will be redirected to the homepage in 2 seconds.</p>
        </div>
      </div>
    </div>
  </div>

  <!-- Redirect to index after 2 seconds -->
  <script>
    setTimeout(function() {
      window.location.href = 'index'; // Adjust the path as needed
    }, 2000);
  </script>

  <!-- Bootstrap JS and dependencies -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
