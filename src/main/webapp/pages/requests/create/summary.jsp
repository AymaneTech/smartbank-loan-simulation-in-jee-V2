<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Demande de Crédit</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/page-two.css">
</head>
<body>
<div class="container">
    <div class="header">
        <a href="#">← Retour</a>
    </div>
    <div class="tabs">
        <div class="tab">1<br>Simuler mon crédit</div>
        <div class="tab">2<br>Mes coordonnées</div>
        <div class="tab active">3<br>Mes infos personnelles</div>
    </div>
    <div class="form-content">
        <form class="form" action="${pageContext.request.contextPath}/requests/create/summary" method="post">
            <div class="form-group">
                <label>Civilité</label>
                <div class="radio-group">
                    <label><input type="radio" name="title" value="MRS" checked> Madame</label>
                    <label><input type="radio" name="title" value="MR"> Mademoiselle</label>
                </div>
            </div>
            <div class="form-group">
                <label for="lastName">Nom*</label>
                <input type="text" id="lastName" name="lastName" required>
            </div>
            <div class="form-group">
                <label for="firstName">Prénom*</label>
                <input type="text" id="firstName" name="firstName" required>
            </div>
            <div class="form-group">
                <label for="cin">Numéro CIN / Carte de séjour*</label>
                <input type="text" id="cin" name="cin" required>
            </div>
            <div class="form-group">
                <label for="dateOfBirth">Date de naissance*</label>
                <input type="date" id="dateOfBirth" name="dateOfBirth" required>
            </div>
            <div class="form-group">
                <label for="employmentStartDate">Date d'embauche/début de l'activité*</label>
                <input type="date" id="employmentStartDate" name="employmentStartDate" required>
            </div>
            <div class="form-group">
                <label for="monthlyIncome">Total revenus mensuels (net en DH)*</label>
                <input type="number" id="monthlyIncome" name="monthlyIncome" required>
            </div>
            <div class="form-group">
                <label>Avez-vous des crédits en cours ?</label>
                <div class="radio-group">
                    <label><input type="radio" name="hasExistingLoans" value="true"> Oui</label>
                    <label><input type="radio" name="hasExistingLoans" value="false" checked> Non</label>
                </div>
            </div>
            <div class="checkbox-group">
                <input type="checkbox" id="conditions" name="conditions" required>
                <label for="conditions">J'ai lu et j'accepte les conditions générales d'utilisation figurant sur les
                    informations légales, notamment la mention relative à la protection des données personnelles</label>
            </div>
            <input type="hidden" name="currentStep" value="summary">

            <div class="button-group">
                <button type="submit" class="btn">Demander ce crédit</button>
                <button type="button" class="btn">Voir mon récapitulatif</button>
            </div>
        </form>
    </div>
    <footer>
        <p>Simulation à titre indicatif et non contractuelle. La mensualité minimale est de 180 dirhams. Un client
            Wafasalaf peut bénéficier d'une tarification plus avantageuse en fonction de ses conditions
            préférentielles.</p>
        <p>Conformément à la loi 09-08, vous disposez d'un droit d'accès, de rectification et d'opposition au traitement
            de vos données personnelles. Ce traitement est autorisé par la CNDP sous le numéro A-GC-206/2014.</p>
    </footer>
</div>
</body>
</html>