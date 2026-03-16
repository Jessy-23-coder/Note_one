<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.example.demo.entity.Correcteur" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Correcteurs</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/allCSS.css">
</head>
<body>
    <%@ include file="/assets/include/navbar.jsp" %>
    <div class="container">
        <h1>📚 Gestion des Correcteurs</h1>
        
        <div class="form-section">
            <%
                Correcteur correcteurToEdit = (Correcteur) request.getAttribute("correcteur");
                boolean isEditMode = correcteurToEdit != null;
                String formTitle = isEditMode ? "✏️ Modifier le correcteur" : "➕ Ajouter un correcteur";
                String buttonText = isEditMode ? "Mettre à jour" : "Enregistrer";
            %>
            <h2 class="form-title"><%= formTitle %></h2>
            
            <form action="${pageContext.request.contextPath}/correcteur/save" method="post">
                <% if (isEditMode) { %>
                    <input type="hidden" name="id" value="<%= correcteurToEdit.getId() %>">
                <% } %>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="nom" class="required">Nom</label>
                        <input type="text" id="nom" name="nom" class="form-control" 
                               placeholder="Entrez le nom" required maxlength="100"
                               value="<%= isEditMode ? correcteurToEdit.getNom() : "" %>">
                    </div>
                    
                    <div class="form-group">
                        <label for="prenom" class="required">Prénom</label>
                        <input type="text" id="prenom" name="prenom" class="form-control" 
                               placeholder="Entrez le prénom" required maxlength="100"
                               value="<%= isEditMode ? correcteurToEdit.getPrenom() : "" %>">
                    </div>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn-submit"><%= buttonText %></button>
                    <% if (isEditMode) { %>
                        <a href="${pageContext.request.contextPath}/correcteur/list" class="btn-cancel">Annuler</a>
                    <% } %>
                </div>
            </form>
        </div>
        
        <h2 class="form-title">📋 Liste des correcteurs</h2>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Correcteur> correcteurs = (List<Correcteur>) request.getAttribute("correcteurs");
                    if (correcteurs != null && !correcteurs.isEmpty()) {
                        for (Correcteur c : correcteurs) {
                %>
                <tr>
                    <td><%= c.getId() %></td>
                    <td><%= c.getNom() %></td>
                    <td><%= c.getPrenom() %></td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/correcteur/update?id=<%= c.getId() %>" class="btn-edit">Modifier</a>
                        <a href="${pageContext.request.contextPath}/correcteur/delete?id=<%= c.getId() %>" 
                           class="btn-delete" 
                           onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce correcteur ?')">Supprimer</a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="4" class="empty-message">Aucun correcteur trouvé</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        
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