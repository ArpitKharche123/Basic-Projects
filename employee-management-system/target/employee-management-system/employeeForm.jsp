<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Title dynamically changes based on add or edit -->
<title>${empty employee ? 'Add New Employee' : 'Edit Employee'}</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
	background-color: #f4f4f4;
}

.container {
	max-width: 600px;
	margin: 0 auto;
	padding: 20px;
	background-color: #fff;
	border-radius: 8px;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

h2 {
	color: #333;
	border-bottom: 2px solid #007bff;
	padding-bottom: 10px;
}

.form-group {
	margin-bottom: 15px;
}

.form-group label {
	display: block;
	margin-bottom: 5px;
	font-weight: bold;
}

.form-group input {
	width: calc(100% - 20px);
	padding: 8px 10px;
	border: 1px solid #ccc;
	border-radius: 4px;
}

.btn-submit {
	background-color: #007bff;
	color: white;
	padding: 10px 15px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 16px;
}

.btn-submit:hover {
	background-color: #0056b3;
}

.btn-cancel {
	text-decoration: none;
	color: #555;
	margin-left: 10px;
}
</style>
</head>
<body>
	<div class="container">
		<!-- Heading dynamically changes -->
		<h2>${empty employee ? 'Add New Employee' : 'Edit Employee'}</h2>

		<!-- The form posts to the same servlet URL -->
		<form action="${pageContext.request.contextPath}/employee"
			method="POST">

			<!-- We use JSTL <c:if> to check if we are in 'edit' mode -->
			<!-- If 'employee' object exists, we are editing, so we include the hidden ID field -->
			<c:if test="${not empty employee}">
				<input type="hidden" name="id" value="${employee.id}" />
			</c:if>

			<div class="form-group">
				<label for="name">Name:</label> <input type="text" id="name"
					name="name" value="${employee.name}" required>
			</div>

			<div class="form-group">
				<label for="email">Email:</label> <input type="email" id="email"
					name="email" value="${employee.email}" required>
			</div>

			<div class="form-group">
				<label for="department">Department:</label> <input type="text"
					id="department" name="department"
					value="${not empty employee ? employee.department : defaultDepartment}">
			</div>

			<button type="submit" class="btn-submit">Save Employee</button>
			<a href="${pageContext.request.contextPath}/employee"
				class="btn-cancel">Cancel</a>
		</form>
	</div>
</body>
</html>
