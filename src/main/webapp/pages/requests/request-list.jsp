<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<html>
<head>
    <title>Request Response List</title>
    <style>
        * {
            font-family: sans-serif;
        }

        .content-table {
            border-collapse: collapse;
            margin: 25px 0;
            font-size: 0.9em;
            min-width: 400px;
            border-radius: 5px 5px 0 0;
            overflow: hidden;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
        }

        .content-table thead tr {
            background-color: #009879;
            color: #ffffff;
            text-align: left;
            font-weight: bold;
        }

        .content-table th,
        .content-table td {
            padding: 12px 15px;
        }

        .content-table tbody tr {
            border-bottom: 1px solid #dddddd;
        }

        .content-table tbody tr:nth-of-type(even) {
            background-color: #f3f3f3;
        }

        .content-table tbody tr:last-of-type {
            border-bottom: 2px solid #009879;
        }

        .content-table tbody tr.active-row {
            font-weight: bold;
            color: #009879;
        }
    </style>
</head>
<body>
<table class="content-table">
    <thead>
    <tr>
        <th>Project</th>
        <th>Amount</th>
        <th>Duration</th>
        <th>Monthly</th>
        <th>Title</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Profession</th>
        <th>CIN</th>
        <th>Date of Birth</th>
        <th>Employment Start Date</th>
        <th>Monthly Income</th>
        <th>Has Existing Loans</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="request" items="${requests}">
        <tr>
            <td>${request.project()}</td>
            <td><fmt:formatNumber value="${request.amount()}" type="currency" currencySymbol="$"/></td>
            <td>${request.duration()}</td>
            <td><fmt:formatNumber value="${request.monthly()}" type="currency" currencySymbol="$"/></td>
            <td>${request.title()}</td>
            <td>${request.firstName()}</td>
            <td>${request.lastName()}</td>
            <td>${request.email()}</td>
            <td>${request.phone()}</td>
            <td>${request.profession()}</td>
            <td>${request.cin()}</td>
            <td>${request.dateOfBirth()}</td>
            <td>${request.employmentStartDate()}</td>
            <td><fmt:formatNumber value="${request.monthlyIncome()}" type="currency" currencySymbol="$"/></td>
            <td>${request.hasExistingLoans()}</td>
            <td><a href="${pageContext.request.contextPath}/requests/delete/${request.id().value()}">delete</a></td>
            <td><a href="${pageContext.request.contextPath}/requests/update/${request.id().value()}">update</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>