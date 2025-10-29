<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Management System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #fff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        h1 { color: #333; }
        ul { list-style-type: none; padding: 0; }
        li { margin: 10px 0; }
        a { text-decoration: none; color: #007bff; font-weight: bold; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome to the Employee Management System</h1>
        <p>Please select an action:</p>
        <ul>
            <!-- Use ${pageContext.request.contextPath} to build robust URLs -->
            <li><a href="${pageContext.request.contextPath}/employee?action=list">View All Employees</a></li>
            <li><a href="${pageContext.request.contextPath}/employee?action=create">Add New Employee</a></li>
        </ul>
    </div>
</body>
</html>
