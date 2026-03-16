<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.example.demo.entity.Etudiant" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Étudiants</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/allCSS.css">
</head>
<body>
    <%@ include file="/assets/include/navbar.jsp" %>
    <div class="container">
        <h1>📚 Gestion des Étudiants</h1>
        
       
        <div class="form-section">
            <%
                Etudiant etudiantToEdit = (Etudiant) request.getAttribute("etudiant");
                boolean isEditMode = etudiantToEdit != null;
                String buttonText = isEditMode ? "Mettre à jour" : "Enregistrer";
            %>
            
            <form action="${pageContext.request.contextPath}/etudiant/save" method="post">
                <% if (isEditMode) { %>
                    <input type="hidden" name="id" value="<%= etudiantToEdit.getId() %>">
                <% } %>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="nom" class="required">Nom</label>
                        <input type="text" id="nom" name="nom" class="form-control" 
                               placeholder="Entrez le nom" required maxlength="100"
                               value="<%= isEditMode ? etudiantToEdit.getNom() : "" %>">
                    </div>
                    
                    <div class="form-group">
                        <label for="prenom" class="required">Prénom</label>
                        <input type="text" id="prenom" name="prenom" class="form-control" 
                               placeholder="Entrez le prénom" required maxlength="100"
                               value="<%= isEditMode ? etudiantToEdit.getPrenom() : "" %>">
                    </div>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn-submit"><%= buttonText %></button>
                    <% if (isEditMode) { %>
                        <a href="${pageContext.request.contextPath}/etudiant/list" class="btn-cancel">Annuler</a>
                    <% } %>
                </div>
            </form>
        </div>
        
        <!-- Liste des étudiants -->
        <h2 class="form-title">📋 Liste des étudiants</h2>
        
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
                    List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");
                    if (etudiants != null && !etudiants.isEmpty()) {
                        for (Etudiant e : etudiants) {
                %>
                <tr>
                    <td><%= e.getId() %></td>
                    <td><%= e.getNom() %></td>
                    <td><%= e.getPrenom() %></td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/etudiant/update?id=<%= e.getId() %>" class="btn-edit">Modifier</a>
                        <a href="${pageContext.request.contextPath}/etudiant/delete?id=<%= e.getId() %>" 
                           class="btn-delete" 
                           onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet étudiant ?')">Supprimer</a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="4" class="empty-message">Aucun étudiant trouvé</td>
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