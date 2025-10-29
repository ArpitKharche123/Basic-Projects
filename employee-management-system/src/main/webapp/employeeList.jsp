<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee List</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background: #f2f4f7;
        padding: 30px;
    }
    table {
        border-collapse: collapse;
        width: 90%;
        margin: auto;
        background: white;
        box-shadow: 0 0 8px rgba(0,0,0,0.1);
    }
    th, td {
        padding: 12px;
        border-bottom: 1px solid #ddd;
        text-align: left;
    }
    th {
        background: #007bff;
        color: white;
    }
    tr:hover {
        background: #f1f1f1;
    }
    .top-bar {
        width: 90%;
        margin: 10px auto;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .top-bar a {
        text-decoration: none;
        background: #28a745;
        color: white;
        padding: 8px 14px;
        border-radius: 5px;
    }
    .top-bar a:hover {
        background: #218838;
    }
    .info {
        margin: 10px auto;
        width: 90%;
        color: #555;
    }
</style>
</head>
<body>

<div class="top-bar">
    <h2>Employee List</h2>
    <a href="employee?action=create">+ Add Employee</a>
</div>

<div class="info">
<%
    String lastUpdatedId = (String) request.getAttribute("lastUpdatedId");
    String lastUpdatedName = (String) request.getAttribute("lastUpdatedName");

    if (lastUpdatedId != null) {
        out.println("✅ Last updated ID: <strong>" + lastUpdatedId + "</strong><br>");
    }
    if (lastUpdatedName != null) {
        out.println("👤 Last updated Name: <strong>" + lastUpdatedName + "</strong>");
    }
%>
</div>

<table border="2px">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Department</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
    <%
        java.util.Collection<Model.Employee> employeeList = 
            (java.util.Collection<Model.Employee>) request.getAttribute("employeeList");

        if (employeeList != null && !employeeList.isEmpty()) {
            for (Model.Employee e : employeeList) {
    %>
                <tr>
                    <td><%= e.getId() %></td>
                    <td><%= e.getName() %></td>
                    <td><%= e.getEmail() %></td>
                    <td><%= e.getDepartment() %></td>
                    <td>
                        <a href="employee?action=edit&id=<%= e.getId() %>">Edit</a> |
                        <a href="employee?action=delete&id=<%= e.getId() %>" onclick="return confirm('Delete this employee?');">Delete</a>
                    </td>
                </tr>
    <%
            }
        } else {
    %>
            <tr><td colspan="5" style="text-align:center;">No employees found.</td></tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>
