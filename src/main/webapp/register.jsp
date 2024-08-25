<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
   
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #fff3cd;
            height: 100vh;
        }
        .register-container {
            margin-top: 50px;
            max-width: 550px;
            padding: 40px;
            background-color: #ffffff;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        }
        .register-heading {
            text-align: center;
            margin-bottom: 20px;
            font-size: 30px;
            font-weight: bold;
            color: #856404;
        }
        .form-control:focus {
            border-color: #ffc107;
            box-shadow: none;
        }
        .btn-warning {
            background-color: #ffc107;
            border-color: #ffc107;
        }
        .btn-warning:hover {
            background-color: #e0a800;
            border-color: #d39e00;
        }
    </style>
</head>
<body>

<div class="container d-flex justify-content-center align-items-center">
    <div class="register-container">
        <h2 class="register-heading">Create Your Account</h2>

     
        <form action="register" method="post">
            <div class="mb-3">
                <label for="name" class="form-label">Name:</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Enter your name" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email:</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="Enter your email" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password:</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Enter your password" required>
            </div>

          
            <div class="mb-3">
                <select class="form-select" id="role" name="role" required hidden>
                    <option value="CUSTOMER" selected>Customer</option>
                </select>
            </div>

          
            <div class="d-grid">
                <button type="submit" class="btn btn-warning btn-lg">Register</button>
            </div>
        </form>

     
        <div class="mt-3 text-center">
            <p>Already have an account? <a href="login.jsp" class="text-muted">Login here</a></p>
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
</body>
</html>
