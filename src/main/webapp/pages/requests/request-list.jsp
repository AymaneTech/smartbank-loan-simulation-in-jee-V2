<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Loan Request List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
</head>
<body>
<h1>Loan Request List</h1>

<table class="content-table">
    <thead>
    <tr>
        <th>Project</th>
        <th>Amount</th>
        <th>Duration</th>
        <th>Monthly</th>
        <th>Name</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Profession</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="request" items="${requests}">
        <tr>
            <td>${request.project()}</td>
            <td><fmt:formatNumber value="${request.amount()}" type="currency" currencySymbol="$"/></td>
            <td>${request.duration()} months</td>
            <td><fmt:formatNumber value="${request.monthly()}" type="currency" currencySymbol="$"/></td>
            <td>${request.title()} ${request.firstName()} ${request.lastName()}</td>
            <td>${request.email()}</td>
            <td>${request.phone()}</td>
            <td>${request.profession()}</td>
            <td class="actions">
                <a href="${pageContext.request.contextPath}/requests/delete/${request.id().value()}">Delete</a>
                <a href="${pageContext.request.contextPath}/requests/update/${request.id().value()}">Update</a>
                <a href="${pageContext.request.contextPath}/requests/change-status/${request.id().value()}">Change Status</a>
                <a href="${pageContext.request.contextPath}/requests/show/${request.id().value()}">Details</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>