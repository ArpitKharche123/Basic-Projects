<%-- File: /WEB-INF/views/home.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Collection" %>
<%@ page import="model.Task" %>
<!DOCTYPE html>
<html>
<head>
    <title>✅ To-Do List - Home</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f7f6; 
            color: #333;
            max-width: 1000px; 
            margin: 40px auto;
            margin-top: 10px;
        }
        
        h1 {
            color: black;
            border-bottom: 3px solid #007bff;
            padding-bottom: 10px;
            margin-bottom: 25px;
            display: inline-block;
        }

        a[href="addTask"] {
            display: inline-block;
            background-color: #28a745; 
            color: white;
            padding: 10px 18px;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 600;
            margin-bottom: 20px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: background-color 0.2s;
        }

        a[href="addTask"]:hover {
            background-color: #218838;
        }
        
        hr {
            display: none;
        }

        table {
            width: 100%;
            border-collapse: collapse; 
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            background-color: white;
            border-radius: 8px;
            overflow: hidden; 
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #e0e0e0;
            width: auto; 
        }

        thead tr {
            background-color: #007bff; 
            color: white;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }
        
        tbody tr:nth-child(even) {
            background-color: #f8f8f8;
        }
        
        tbody tr:hover {
            background-color: #e9ecef; 
        }

        td a {
            text-decoration: none;
            font-weight: 500;
            padding: 4px 8px;
            border-radius: 4px;
            transition: opacity 0.2s;
        }

        td a[href*="editTask"] {
            color: #ffc107; 
            border: 1px solid #ffc107;
        }
        
        td a[href*="deleteTask"] {
            color: #dc3545; 
            border: 1px solid #dc3545;
            margin-left: 5px; 
        }
        
        td a:hover {
            opacity: 0.8;
        }
        
        td[colspan="4"] {
            text-align: center;
            font-style: italic;
            color: #6c757d;
            background-color: #fff;
            padding: 20px;
        }
    </style>
</head>
<body>
    <h1>My To-Do Tasks</h1><br>
    <a href="addTask">➕ Add New Task</a>
    <hr/>
    
    <table border="1" cellpadding="5" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Description</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <%
            @SuppressWarnings("unchecked")
            Collection<Task> tasksCollection = (Collection<Task>) request.getAttribute("tasksMap");
            
            if (tasksCollection != null && !tasksCollection.isEmpty()) {
                for (Task task : tasksCollection) {
        %>
            <tr>
                <td><%= task.getId() %></td>
                <td><%= task.getTitle() %></td>
                <td><%= task.getDescription() %></td>
                <td>
                    <a href="editTask?id=<%= task.getId() %>">✏️ Edit</a> 
                    <a href="deleteTask?id=<%= task.getId() %>">🗑️ Delete</a>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="4">No tasks found. Add a new one!</td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
</body>
</html>