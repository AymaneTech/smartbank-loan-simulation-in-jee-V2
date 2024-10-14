<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Loan Request Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            color: #333;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }

        h1 {
            color: #2c3e50;
            border-bottom: 2px solid #3498db;
            padding-bottom: 10px;
        }

        .section {
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 15px;
            margin-bottom: 20px;
        }

        .section h2 {
            color: #2980b9;
            margin-top: 0;
        }

        .detail {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }

        .label {
            font-weight: bold;
            flex-basis: 40%;
        }

        .value {
            flex-basis: 60%;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #3498db;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<h1>Loan Request Details</h1>

<div class="section">
    <h2>Request Information</h2>
    <div class="detail">
        <span class="label">Request ID:</span>
        <span class="value">${request.request().id().value()}</span>
    </div>
    <div class="detail">
        <span class="label">Project:</span>
        <span class="value">${request.request().project()}</span>
    </div>
    <div class="detail">
        <span class="label">Amount:</span>
        <span class="value">
            <fmt:formatNumber value="${request.request().amount()}" type="currency" currencySymbol="$"/>
        </span>
    </div>
    <div class="detail">
        <span class="label">Duration:</span>
        <span class="value">${request.request().duration()} months</span>
    </div>
    <div class="detail">
        <span class="label">Monthly Payment:</span>
        <span class="value">
            <fmt:formatNumber value="${request.request().monthly()}" type="currency" currencySymbol="$"/>
        </span>
    </div>
</div>

<div class="section">
    <h2>Personal Information</h2>
    <div class="detail">
        <span class="label">Title:</span>
        <span class="value">${request.request().title()}</span>
    </div>
    <div class="detail">
        <span class="label">First Name:</span>
        <span class="value">${request.request().firstName()}</span>
    </div>
    <div class="detail">
        <span class="label">Last Name:</span>
        <span class="value">${request.request().lastName()}</span>
    </div>
    <div class="detail">
        <span class="label">Email:</span>
        <span class="value">${request.request().email()}</span>
    </div>
    <div class="detail">
        <span class="label">Phone:</span>
        <span class="value">${request.request().phone()}</span>
    </div>
    <div class="detail">
        <span class="label">Profession:</span>
        <span class="value">${request.request().profession()}</span>
    </div>
    <div class="detail">
        <span class="label">CIN:</span>
        <span class="value">${request.request().cin()}</span>
    </div>
    <div class="detail">
        <span class="label">Date of Birth:</span>
        <span class="value">${request.request().dateOfBirth()}</span>
    </div>
</div>

<div class="section">
    <h2>Employment Information</h2>
    <div class="detail">
        <span class="label">Employment Start Date:</span>
        <span class="value">${request.request().employmentStartDate()}</span>
    </div>
    <div class="detail">
        <span class="label">Monthly Income:</span>
        <span class="value">
            <fmt:formatNumber value="${request.request().monthlyIncome()}" type="currency" currencySymbol="$"/>
        </span>
    </div>
    <div class="detail">
        <span class="label">Existing Loans:</span>
        <span class="value">${request.request().hasExistingLoans() ? 'Yes' : 'No'}</span>
    </div>
</div>

<div class="section">
    <h2>Status History</h2>
    <table>
        <thead>
        <tr>
            <th>Date</th>
            <th>Status</th>
            <th>Comments</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${statusHistory}" var="item">
            <tr>
                <td>${item.createdAt()}</td>
                <td>${item.status().name()}</td>
                <td>${item.description()}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
