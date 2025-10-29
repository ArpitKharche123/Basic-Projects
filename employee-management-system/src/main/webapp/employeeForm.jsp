<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= (request.getAttribute("employee") == null) ? "Add New Employee" : "Edit Employee" %></title>
<style>
    body {
        font-family: Arial, sans-serif;
        background: #f6f8fa;
        padding: 30px;
    }
    form {
        max-width: 400px;
        margin: auto;
        background: white;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 0 8px rgba(0,0,0,0.1);
    }
    h2 {
        text-align: center;
        color: #333;
    }
    label {
        font-weight: bold;
        display: block;
        margin-top: 10px;
    }
    input[type=text], input[type=email] {
        width: 100%;
        padding: 8px;
        margin-top: 5px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }
    button {
        margin-top: 15px;
        width: 100%;
        padding: 10px;
        background: #007bff;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }
    button:hover {
        background: #0056b3;
    }
    a {
        text-decoration: none;
        display: block;
        text-align: center;
        margin-top: 15px;
        color: #555;
    }
</style>
</head>
<body>

<%
    Model.Employee emp = (Model.Employee) request.getAttribute("employee");
    String defaultDept = (String) request.getAttribute("defaultDepartment");
%>

<form action="employee" method="post">
    <h2><%= (emp == null) ? "Add Employee" : "Edit Employee" %></h2>

    <% if (emp != null) { %>
        <input type="hidden" name="id" value="<%= emp.getId() %>">
    <% } %>

    <label>Name:</label>
    <input type="text" name="name" value="<%= (emp != null) ? emp.getName() : "" %>" required>

    <label>Email:</label>
    <input type="email" name="email" value="<%= (emp != null) ? emp.getEmail() : "" %>" required>

    <label>Department:</label>
    <input type="text" name="department" value="<%= (emp != null && emp.getDepartment() != null) ? emp.getDepartment() : defaultDept %>">

    <button type="submit"><%= (emp == null) ? "Save" : "Update" %></button>

    <a href="employee?action=list">Back to List</a>
</form>

</body>
</html>
