<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Rate Your Order</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .rating-container {
            margin-top: 50px;
            max-width: 600px;
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .rating-stars {
            font-size: 40px;
            color: #ddd;
            cursor: pointer;
        }
        .rating-stars input {
            display: none;
        }
        .rating-stars label {
            color: #ddd;
            font-size: 40px;
            transition: color 0.3s ease-in-out;
        }
        .rating-stars input:checked ~ label,
        .rating-stars label:hover,
        .rating-stars label:hover ~ label {
            color: #f1c40f;
        }
        textarea {
            resize: none;
        }
    </style>
</head>
<body>
    <div class="container d-flex justify-content-center">
        <div class="rating-container">
            <h1 class="mb-4">Rate Your Order</h1>
            <form action="rate" method="post">
                <input type="hidden" name="orderId" value="${orderId}" />

              
                <div class="rating-stars mb-4">
                    <input type="radio" name="rating" id="star5" value="5"><label for="star5">&#9733;</label>
                    <input type="radio" name="rating" id="star4" value="4"><label for="star4">&#9733;</label>
                    <input type="radio" name="rating" id="star3" value="3"><label for="star3">&#9733;</label>
                    <input type="radio" name="rating" id="star2" value="2"><label for="star2">&#9733;</label>
                    <input type="radio" name="rating" id="star1" value="1"><label for="star1">&#9733;</label>
                </div>

                
                <div class="form-group mb-4">
                    <label for="feedback" class="form-label">Your Feedback (Optional)</label>
                    <textarea class="form-control" name="feedback" id="feedback" rows="4" placeholder="Write your feedback here..."></textarea>
                </div>

                
                <button type="submit" class="btn btn-warning btn-lg w-100">Submit Rating</button>
            </form>
        </div>
    </div>

    
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
