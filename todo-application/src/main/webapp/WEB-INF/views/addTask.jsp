```jsp
<%-- File: /WEB-INF/views/addTask.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>➕ Add Task</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 40px;
            background-color: #f4f7f6;
            color: #333;
            max-width: 400px; 
            margin: 20px auto;
            margin-top: 10px;
        }
        
        h1 {
            color: black;
            border-bottom: 3px solid #007bff;
            padding-bottom: 10px;
            margin-bottom: 25px;
            display: inline-block;
        }

        a[href="tasks"] {
            display: inline-block;
            color: #6c757d;
            text-decoration: none;
            margin-bottom: 15px;
            font-weight: 500;
            transition: color 0.2s;
        }

        a[href="tasks"]:hover {
            color: #0056b3;
        }
        
        hr {
            display: none;
        }

        form {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            margin-top: 20px;
        }
        
        label {
            display: block;
            font-weight: 600;
            color: #495057;
            margin-bottom: 5px;
            margin-top: 15px;
        }
        
        input[type="text"], 
        textarea {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ced4da;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
            transition: border-color 0.2s, box-shadow 0.2s;
        }
        
        input[type="text"]:focus, 
        textarea:focus {
            border-color: #007bff;
            box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
            outline: none;
        }
        
        textarea {
            resize: vertical;
        }

        input[type="submit"] {
            background-color: #28a745;
            color: white;
            padding: 12px 25px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 600;
            margin-top: 10px;
            transition: background-color 0.2s, box-shadow 0.2s;
        }

        input[type="submit"]:hover {
            background-color: #218838;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

    </style>
</head>
<body>
    <h1>Add New To-Do Task</h1>
    <a href="tasks">← Back to List</a>
    <hr/>
    
    <form action="saveTask" method="POST">
        
        <label for="title">Task Title:</label><br>
        <input type="text" id="title" name="title" required><br><br>
        
        <label for="description">Description:</label><br>
        <textarea id="description" name="description" rows="4" required></textarea><br><br>
        
        <input type="submit" value="Add Task">
    </form>
</body>
</html>
```