<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Demander mon crédit en ligne</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/index.css">
</head>

<body>
<div class="container">
    <h1 class="page-title">Demander mon crédit en ligne</h1>

    <div class="credit-container">
        <div class="process">
            <div class="steps">
                <div class="step active">1<br>Simuler mon crédit</div>
                <div class="step">2<br>Mes coordonnées</div>
                <div class="step">3<br>Mes infos personnelles</div>
            </div>

            <form class="form" action="${pageContext.request.contextPath}/requests/create/contact-info" method="post">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input class="text-input" id="email" name="email" type="email" required/>
                </div>
                <div class="form-group">
                    <label for="phone">Téléphone</label>
                    <input class="text-input" id="phone" name="phone" type="tel" required/>
                </div>
                <input type="hidden" name="currentStep" value="contact-info">

                <div class="submit-btn-parent">
                    <button id="submit-step-two" type="submit" class="submit-btn">Continuer <br> sans engagement
                    </button>
                </div>
            </form>
        </div>

        <div class="recap">
            <h3>Mon récapitulatif</h3>
            <p>Mon projet: Prêt Personnel</p>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/main.js"></script>
</body>

</html>
