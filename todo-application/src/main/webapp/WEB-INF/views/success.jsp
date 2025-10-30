<%-- File: /WEB-INF/views/success.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Success</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 40px auto;
            max-width: 600px;
            background-color: #f4f7f6;
            color: #333;
            text-align: center;
            padding-top: 50px;
        }
        
        .success-box {
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-bottom: 30px;
        }

        h1 {
            color: #155724;
            font-size: 2em;
            margin-top: 0;
            margin-bottom: 15px;
        }

        p {
            font-size: 1.1em;
            line-height: 1.5;
            margin-bottom: 0;
        }
        
        p strong {
            color: #0c3c1e;
            font-weight: 700;
        }

        hr {
            display: none;
        }

        a[href="tasks"] {
            display: inline-block;
            background-color: #007bff;
            color: white;
            padding: 12px 25px;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 600;
            margin-top: 20px;
            transition: background-color 0.2s, transform 0.1s;
        }

        a[href="tasks"]:hover {
            background-color: #0056b3;
            transform: translateY(-1px);
        }

    </style>
</head>
<body>
    <div class="success-box">
        <% 
            String action = (String) request.getAttribute("action");
            String title = (String) request.getAttribute("title");
        %>
        
        <% if ("Added".equals(action)) { %>
            <h1>Task Added Successfully! 🎉</h1>
            <p>The task titled "<strong><%= title %></strong>" has been successfully saved.</p>
        <% } else if ("Updated".equals(action)) { %>
            <h1>Task Updated Successfully! 🔄</h1>
            <p>The task titled "<strong><%= title %></strong>" has been successfully modified.</p>
        <% } else { %>
            <h1>Task Deleted Successful!</h1>
            <p>The task titled "<strong><%= title %></strong>" has been successfully modified.</p>
        <% } %>
    </div>
    
    <hr/>
    <a href="tasks">View All Tasks</a>
</body>
</html>