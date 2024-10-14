<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Loan Request System - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
</head>
<body>
<div class="home-container">
    <h1 class="home-title">Welcome to the Loan Request System</h1>
    <p class="home-description">Manage and track loan requests efficiently. Choose an option below to get started.</p>

    <div class="button-container">
        <a href="${pageContext.request.contextPath}/requests/all" class="home-button">View All Requests</a>
        <a href="${pageContext.request.contextPath}/requests/create" class="home-button create">Create New Request</a>
    </div>
</div>
</body>
</html>