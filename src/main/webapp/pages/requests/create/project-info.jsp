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

            <form class="form" action="${pageContext.request.contextPath}/requests/create/project-info" method="post">
                <div class="form-group">
                    <label for="project">Mon projet</label>
                    <select id="project" name="project" required>
                        <option>Je Gère mes imprévus</option>
                        <option>J'ai besoin d'argent</option>
                        <option>je finance mon vehicule neuf</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="profession">Je suis</label>
                    <select id="profession" name="profession" required>
                        <option>Fonctionnaire</option>
                        <option>Salarie - secteur privé</option>
                        <option>Commerçant</option>
                        <option>Artisan</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="amount-input">Montant (en DH)</label>
                    <input type="number" id="amount-input" name="amount" value="10000" required>
                    <input id="amount-range" type="range" class="slider" min="0" max="20000" value="10000">
                </div>
                <div class="form-group">
                    <label for="duration-input">Durée (en mois)</label>
                    <input type="number" id="duration-input" name="duration" value="24" required>
                    <input id="duration-range" type="range" class="slider" min="0" max="72" value="24">
                </div>
                <div class="form-group">
                    <label for="monthly-input">Mensualités (en DH)</label>
                    <input type="number" id="monthly-input" name="monthly" value="469.4" readonly required>
                    <input id="monthly-range" type="range" class="slider" min="0" max="1000" value="469.4">
                </div>
                <input type="hidden" name="currentStep" value="project-info">

                <div class="submit-btn-parent">
                    <button id="submit-step-one" type="submit" class="submit-btn">Continuer <br> sans engagement
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
