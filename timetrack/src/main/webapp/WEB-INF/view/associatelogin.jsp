<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Associate Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <style>
        body {
            background-color: #f8f9fa;
        }

        .container {
            margin-top: 50px;
        }

        /* Add some simple animation */
        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }

        .animated {
            animation: fadeIn 1s ease-out;
        }
    </style>
</head>
<body>
<div class="container col-md-8 col-md-offset-3 animated">
    <h1>Associate Login Form</h1>
    <form action="/assLogin" method="POST">
        <div class="form-group">
            <label for="associateId">Associate Id:</label>
            <input type="text" class="form-control" id="associateId" name="associateId" required>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>

        <input type="submit" value="Login" class="btn btn-primary">
        <a href="home" class="btn btn-secondary">Home</a>
    </form>
</div>
    <script>
    
    function showSuccessAlert() {
        
        setTimeout(function () {
            alert("Login successful");
        }, 1); // Delayed execution to ensure correct order
        return true; // Return true to submit the form
    }
    
        

        // Check for login success or error in the URL parameters and display corresponding pop-ups
        document.addEventListener('DOMContentLoaded', function () {
            const params = new URLSearchParams(window.location.search);
            if (params.get('loginSuccess')) {
                alert("Login successful");
                window.location.href = 'associate';
            } else if (params.get('loginError')) {
                alert("Wrong password");
            }
        });
    </script>
</body>
</html>
