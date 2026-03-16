<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.example.demo.entity.Resolution" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Résolutions</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/allCSS.css">
</head>
<body>
    <%@ include file="/assets/include/navbar.jsp" %>
    <div class="container">
        <h1>📚 Gestion des Résolutions</h1>
        
        <div class="form-section">
            <%
                Resolution resolutionToEdit = (Resolution) request.getAttribute("resolution");
                boolean isEditMode = resolutionToEdit != null;
                String formTitle = isEditMode ? "✏️ Modifier la résolution" : "➕ Ajouter une résolution";
                String buttonText = isEditMode ? "Mettre à jour" : "Enregistrer";
            %>
            <h2 class="form-title"><%= formTitle %></h2>
            
            <form action="${pageContext.request.contextPath}/resolution/save" method="post">
                <% if (isEditMode) { %>
                    <input type="hidden" name="id" value="<%= resolutionToEdit.getId() %>">
                <% } %>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="nom" class="required">Nom</label>
                        <input type="text" id="nom" name="nom" class="form-control" 
                               placeholder="Entrez le nom" required maxlength="100"
                               value="<%= isEditMode ? resolutionToEdit.getNom() : "" %>">
                    </div>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn-submit"><%= buttonText %></button>
                    <% if (isEditMode) { %>
                        <a href="${pageContext.request.contextPath}/resolution/list" class="btn-cancel">Annuler</a>
                    <% } %>
                </div>
            </form>
        </div>
        
        <h2 class="form-title">📋 Liste des résolutions</h2>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Resolution> resolutions = (List<Resolution>) request.getAttribute("resolutions");
                    if (resolutions != null && !resolutions.isEmpty()) {
                        for (Resolution r : resolutions) {
                %>
                <tr>
                    <td><%= r.getId() %></td>
                    <td><%= r.getNom() %></td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/resolution/update?id=<%= r.getId() %>" class="btn-edit">Modifier</a>
                        <a href="${pageContext.request.contextPath}/resolution/delete?id=<%= r.getId() %>" 
                           class="btn-delete" 
                           onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette résolution ?')">Supprimer</a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="3" class="empty-message">Aucune résolution trouvée</td>
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