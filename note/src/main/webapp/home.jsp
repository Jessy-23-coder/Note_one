<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.example.demo.entity.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Notes</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/allCSS.css">
</head>
<body>
    <%@ include file="/assets/include/navbar.jsp" %>
    <div class="container">
        <h1>🏠 Accueil - Système de Gestion des Notes</h1>
        
        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 20px; margin-top: 30px;">
            
            <!-- Carte Étudiants -->
            <div class="form-section" style="text-align: center;">
                <h2 class="form-title">👨‍🎓 Étudiants</h2>
                <p>Gérer les étudiants</p>
                <a href="${pageContext.request.contextPath}/etudiant/list" class="btn-submit" style="display: inline-block; width: auto; padding: 10px 20px;">Voir les étudiants</a>
            </div>
            
            <!-- Carte Matières -->
            <div class="form-section" style="text-align: center;">
                <h2 class="form-title">📚 Matières</h2>
                <p>Gérer les matières et coefficients</p>
                <a href="${pageContext.request.contextPath}/matiere/list" class="btn-submit" style="display: inline-block; width: auto; padding: 10px 20px;">Voir les matières</a>
            </div>
            
            <!-- Carte Correcteurs -->
            <div class="form-section" style="text-align: center;">
                <h2 class="form-title">👨‍🏫 Correcteurs</h2>
                <p>Gérer les correcteurs</p>
                <a href="${pageContext.request.contextPath}/correcteur/list" class="btn-submit" style="display: inline-block; width: auto; padding: 10px 20px;">Voir les correcteurs</a>
            </div>
            
            <!-- Carte Notes classiques -->
            <div class="form-section" style="text-align: center;">
                <h2 class="form-title">📝 Notes classiques</h2>
                <p>Gérer les notes avec correcteurs</p>
                <a href="${pageContext.request.contextPath}/note/list" class="btn-submit" style="display: inline-block; width: auto; padding: 10px 20px;">Voir les notes</a>
            </div>
            
            <!-- Carte Notes finales -->
            <div class="form-section" style="text-align: center;">
                <h2 class="form-title">🎯 Notes finales</h2>
                <p>Gérer les notes finales par étudiant et matière</p>
                <a href="${pageContext.request.contextPath}/notefinal/list" class="btn-submit" style="display: inline-block; width: auto; padding: 10px 20px;">Voir les notes finales</a>
            </div>
            
            <!-- Carte Paramètres -->
            <div class="form-section" style="text-align: center;">
                <h2 class="form-title">⚙️ Paramètres</h2>
                <p>Résolutions, Opérateurs et Paramètres</p>
                <a href="${pageContext.request.contextPath}/resolution/list" class="btn-edit" style="display: inline-block; margin: 5px;">Résolutions</a>
                <a href="${pageContext.request.contextPath}/operateur/list" class="btn-edit" style="display: inline-block; margin: 5px;">Opérateurs</a>
                <a href="${pageContext.request.contextPath}/parametre/list" class="btn-edit" style="display: inline-block; margin: 5px;">Paramètres</a>
            </div>
        </div>
        
        <%
            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
        <div class="message <%= request.getAttribute("type") %>">
            <%= message %>
        </div>
        <%
            }
        %>
    </div>
</body>
</html>