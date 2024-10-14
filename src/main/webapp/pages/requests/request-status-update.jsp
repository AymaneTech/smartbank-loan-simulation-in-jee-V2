<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Loan Request Status</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
</head>
<body>
<h1>Change Loan Request Status</h1>
<form class="update-form" action="${pageContext.request.contextPath}/requests/change-status" method="POST">
    <label for="new-status">New Status:</label>
    <select name="statusId" id="new-status" required>
        <c:forEach var="status" items="${statuses}">
            <option value="${status.id().value()}">${status.name()}</option>
        </c:forEach>
    </select>

    <label for="description">Description:</label>
    <textarea id="description" name="description" rows="4" required></textarea>

    <input type="submit" value="Update Status"/>
</form>
</body>
</html>