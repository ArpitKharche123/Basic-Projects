<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Employees</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { max-width: 900px; margin: 0 auto; padding: 20px; background-color: #fff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        h2 { color: #333; border-bottom: 2px solid #007bff; padding-bottom: 10px; }
        .btn-add { background-color: #28a745; color: white; padding: 10px 15px; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; font-size: 16px; display: inline-block; margin-bottom: 20px; }
        .btn-add:hover { background-color: #218838; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #f8f8f8; }
        tr:nth-child(even) { background-color: #fdfdfd; }
        .actions a { text-decoration: none; margin-right: 10px; }
        .actions .edit { color: #007bff; }
        .actions .delete { color: #dc3545; }
        .info-box { margin-top: 20px; padding: 15px; background-color: #e6f7ff; border: 1px solid #b3e0ff; border-radius: 4px; }
    </style>
    <script>
        // JavaScript confirmation for delete
        function confirmDelete(id) {
            if (confirm('Are you sure you want to delete employee ' + id + '?')) {
                // If confirmed, redirect to the delete URL
                window.location.href = '${pageContext.request.contextPath}/employee?action=delete&id=' + id;
            }
            // If cancelled, do nothing
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Employee List</h2>
        <a href="${pageContext.request.contextPath}/employee?action=create" class="btn-add">Add New Employee</a>

        <table>
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
                <!-- Use JSTL <c:forEach> to loop over the employee list -->
                <c:forEach var="emp" items="${employeeList}">
                    <tr>
                        <td><c:out value="${emp.id}" /></td>
                        <td><c:out value="${emp.name}" /></td>
                        <td><c:out value="${emp.email}" /></td>
                        <td><c:out value="${emp.department}" /></td>
                        <td class="actions">
                            <!-- Edit Link -->
                            <a href="${pageContext.request.contextPath}/employee?action=edit&id=${emp.id}" class="edit">Edit</a>
                            
                            <!-- Delete Link with JavaScript confirmation -->
                            <a href="#" onclick="confirmDelete('<c:out value="${emp.id}"/>'); return false;" class="delete">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                
                <!-- Handle case where list is empty -->
                <c:if test="${empty employeeList}">
                    <tr>
                        <td colspan="5" style="text-align: center;">No employees found.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <!-- 
          Display HttpSession and Cookie data as required by the PDF
        -->
        <div class="info-box">
            <c:if test="${not empty lastUpdatedId}">
                <p><strong>Last Updated (from Session):</strong> Employee ID <c:out value="${lastUpdatedId}" /></p>
            </c:if>
            <c:if test="${not empty lastUpdatedName}">
                <p><strong>Last Updated (from Cookie):</strong> Employee Name <c:out value="${lastUpdatedName}" /></p>
            </c:if>
            <c:if test="${empty lastUpdatedId && empty lastUpdatedName}">
                <p>No recent updates to display.</p>
            </c:if>
        </div>
    </div>
</body>
</html>
