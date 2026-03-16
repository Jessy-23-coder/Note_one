<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .navbar-simple {
        background: #333;
        color: white;
        padding: 15px;
        text-align: center;
        margin-bottom: 20px;
    }
    .navbar-simple a {
        color: white;
        text-decoration: none;
        margin: 0 15px;
        padding: 5px 10px;
    }
    .navbar-simple a:hover {
        background: #555;
        border-radius: 5px;
    }
    .dropdown {
        display: inline-block;
        position: relative;
    }
    .dropdown-content {
        display: none;
        position: absolute;
        background-color: #333;
        min-width: 160px;
        box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
        z-index: 1;
    }
    .dropdown:hover .dropdown-content {
        display: block;
    }
    .dropdown-content a {
        color: white;
        padding: 12px 16px;
        text-decoration: none;
        display: block;
        text-align: left;
        margin: 0;
    }
    .dropdown-content a:hover {
        background-color: #555;
    }
</style>

<div class="navbar-simple">
    <a href="${pageContext.request.contextPath}/">Accueil</a>
    <a href="${pageContext.request.contextPath}/etudiant/list">Étudiants</a>
    <a href="${pageContext.request.contextPath}/matiere/list">Matières</a>
    <a href="${pageContext.request.contextPath}/correcteur/list">Correcteurs</a>
    
    <div class="dropdown">
        <a href="#">Paramètres ▼</a>
        <div class="dropdown-content">
            <a href="${pageContext.request.contextPath}/resolution/list">Résolutions</a>
            <a href="${pageContext.request.contextPath}/operateur/list">Opérateurs</a>
            <a href="${pageContext.request.contextPath}/parametre/list">Paramètres</a>
        </div>
    </div>
    
    <div class="dropdown">
        <a href="#">Notes ▼</a>
        <div class="dropdown-content">
            <a href="${pageContext.request.contextPath}/note/list">Notes classiques</a>
            <a href="${pageContext.request.contextPath}/notefinal/list">Notes finales</a>
        </div>
    </div>
</div>