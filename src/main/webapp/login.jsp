<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #fff3cd;
            height: 100vh;
        }
        .login-container {
            margin-top: 50px;
            max-width: 550px;
            padding: 40px;
            background-color: #ffffff;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        }
        .login-heading {
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
        .btn-social {
            width: 100%;
            margin-bottom: 10px;
        }
        .social-buttons {
            display: flex;
            justify-content: space-between;
        }
        .remember-me {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .password-toggle {
            cursor: pointer;
        }
    </style>
</head>
<body>

<div class="container d-flex justify-content-center align-items-center">
    <div class="login-container">
        <h2 class="login-heading">Login to Your Account</h2>

      
        <c:if test="${not empty message}">
            <div class="alert alert-${messageType} alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>


        <form action="login" method="post">
            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="Enter your email" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <div class="input-group">
                    <input type="password" class="form-control" id="password" name="password" placeholder="Enter your password" required>
                    <span class="input-group-text password-toggle" onclick="togglePassword()">👁</span>
                </div>
            </div>
            
         
            <div class="remember-me mb-3">
                <div>
                    <input type="checkbox" id="rememberMe" name="rememberMe">
                    <label for="rememberMe" class="form-check-label">Remember me</label>
                </div>
                <a href="#" class="text-muted">Forgot password?</a>
            </div>

       
            <div class="d-grid">
                <button type="submit" class="btn btn-warning btn-lg">Login</button>
            </div>
        </form>


        <div class="text-center my-4">
            <span>OR</span>
        </div>

     
        <div class="mt-3 text-center">
            <p>Don't have an account? <a href="register.jsp" class="text-muted">Sign up here</a></p>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    
    function togglePassword() {
        var passwordField = document.getElementById("password");
        if (passwordField.type === "password") {
            passwordField.type = "text";
        } else {
            passwordField.type = "password";
        }
    }
</script>
</body>
</html>
