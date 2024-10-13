<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Update Request</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/updateTable.css">
</head>
<body>
<h1>Update Request</h1>
<form class="update-form" action="${pageContext.request.contextPath}/requests/change-status"
      method="POST">
    <label for="new-status"></label>
    <select name="statusId" id="new-status">
        <c:forEach var="status" items="${statuses}">
            <option value="${status.id().value()}">${status.name()}</option>
        </c:forEach>
    </select>

    <input type="text" name="description">

    <input type="submit" value="Update"/>
</form>
</body>
</html>
