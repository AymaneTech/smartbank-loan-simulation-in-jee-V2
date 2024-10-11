<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Update Request</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/updateTable.css">
</head>
<body>
<h1>Update Request</h1>
<form class="update-form" action="${pageContext.request.contextPath}/requests/update/${request.id()}" method="POST">
    <input type="hidden" name="id" value="${loanRequest.id()}"/>

    <label for="project">Project Name:</label>
    <input type="text" id="project" name="project" value="${loanRequest.project()}" required/><br/>

    <label for="profession">Profession:</label>
    <input type="text" id="profession" name="profession" value="${loanRequest.profession()}" required/><br/>

    <label for="amount">Loan Amount:</label>
    <input type="number" step="0.01" id="amount" name="amount" value="${loanRequest.amount()}" required/><br/>

    <label for="duration">Loan Duration (in months):</label>
    <input type="number" step="1" id="duration" name="duration" value="${loanRequest.duration()}" required/><br/>

    <label for="monthly">Monthly Payment:</label>
    <input type="number" step="0.01" id="monthly" name="monthly" value="${loanRequest.monthly()}" required/><br/>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" value="${loanRequest.email()}" required/><br/>

    <label for="phone">Phone:</label>
    <input type="tel" id="phone" name="phone" value="${loanRequest.phone()}" required/><br/>

    <label for="title">Civility:</label>
    <select id="title" name="title" required>
        <option value="MR" <c:if test="${loanRequest.title() == 'MR'}">selected</c:if>>MR</option>
        <option value="MRS" <c:if test="${loanRequest.title() == 'MRS'}">selected</c:if>>MRS</option>
        <option value="SIR" <c:if test="${loanRequest.title() == 'SIR'}">selected</c:if>>SIR</option>
    </select><br/>

    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" value="${loanRequest.firstName()}" required/><br/>

    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" value="${loanRequest.lastName()}" required/><br/>

    <label for="cin">CIN:</label>
    <input type="text" id="cin" name="cin" value="${loanRequest.cin()}" required/><br/>

    <label for="dateOfBirth">Birth Date:</label>
    <input type="date" id="dateOfBirth" name="dateOfBirth" value="${loanRequest.dateOfBirth()}" required/><br/>

    <label for="employmentStartDate">Employment Start Date:</label>
    <input type="date" id="employmentStartDate" name="employmentStartDate" value="${loanRequest.employmentStartDate()}"
           required/><br/>

    <label for="monthlyIncome">Monthly Income:</label>
    <input type="number" step="0.01" id="monthlyIncome" name="monthlyIncome" value="${loanRequest.monthlyIncome()}"
           required/><br/>

    <h4>Avez-vous des crédits en cours ?</h4>
    <div class="radio-group">
        <label class="radio-label">
            <input type="radio" name="hasExistingLoans" value="true"
                   <c:if test="${loanRequest.hasExistingLoans()}">checked</c:if>/>
            <span class="radio-custom"></span>
            Oui
        </label>
        <label class="radio-label">
            <input type="radio" name="hasExistingLoans" value="false"
                   <c:if test="${!loanRequest.hasExistingLoans()}">checked</c:if>/>
            <span class="radio-custom"></span>
            Non
        </label>
    </div>

    <input type="submit" value="Update"/>
</form>
</body>
</html>
