<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ page import="com.employee.timetrack.bean.Project" %>

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
    <h1>Associate Task add Form</h1>

    <!-- Display error message if it exists -->
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" role="alert">
            ${errorMessage}
        </div>
    </c:if>
    <form action="addtaskpost" method="post">
       

        <div class="form-group">
            <label for="taskName">Task Name</label>
            <input type="text" class="form-control" id="taskName" name="taskName" required>
        </div>

        <div class="form-group">
            <label for="starTime">Start Time</label>
            <input type="time" class="form-control" id="starTime" name="starTime" required>
        </div>

        <div class="form-group">
            <label for="endTime">End Time</label>
            <input type="time" class="form-control" id="endTime" name="endTime" required>
        </div>

        <div class="form-group">
    		<label for="associateId">Associate ID</label>
    		<input type="text" class="form-control" id="associateId" name="associateId" value="${associateId}" readonly>
		</div>


        <div class="form-group">
            <label for="projectId"><b>Select Project Id:</b></label>
            <select name="projectId" id="projectId">
            
            <c:forEach items="${projectList}" var="project">
  				<option value="${project.projectId}">${project.projectId} ${project.projectName}</option>
  			</c:forEach>
  			</select>
        </div>


        <div class="form-group">
            <label for="description">Task Description</label>
            <input type="text" class="form-control" id="description" name="description" required>
        </div>

        <input type="submit" value="Add Task" class="btn btn-primary">
        <a href="associateList" class="btn btn-secondary">Back</a>
    </form>
</div>
</body>
</html>
