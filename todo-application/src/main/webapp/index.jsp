<%-- File: /WEB-INF/views/index.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome to the To-Do App</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
            background-color: #f4f7f6;
            color: #333;
        }
        .container {
            text-align: center;
            padding: 40px;
            background-color: #fff;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #4CAF50;
            margin-bottom: 10px;
        }
        p {
            font-size: 1.1em;
            margin-bottom: 30px;
            color: #555;
        }
        .tasks-button {
            display: inline-block;
            padding: 12px 25px;
            font-size: 1.1em;
            color: #fff;
            background-color: #4CAF50;
            border: none;
            border-radius: 8px;
            text-decoration: none;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.1s;
            box-shadow: 0 4px #388E3C; 
        }
        .tasks-button:hover {
            background-color: #43A047;
        }
        .tasks-button:active {
            background-color: #388E3C;
            box-shadow: 0 2px #388E3C;
            transform: translateY(2px);
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome to Your To-Do Application!</h1>
        <p>
            This simple application is built using Spring MVC, demonstrating the core principles of
            Create, Read, Update, and Delete (CRUD) operations for managing your tasks.
        </p>
        
        <%-- The button links to the controller's mapping for viewing all tasks (/tasks) --%>
        <a href="tasks" class="tasks-button">
            View All Tasks
        </a>
    </div>
</body>
</html>