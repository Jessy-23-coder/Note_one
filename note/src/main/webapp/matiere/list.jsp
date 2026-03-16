<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.example.demo.entity.Matiere" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Matières</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/allCSS.css">
</head>
<body>
    <%@ include file="/assets/include/navbar.jsp" %>
    <div class="container">
        <h1>📚 Gestion des Matières</h1>
        
        <div class="form-section">
            <%
                Matiere matiereToEdit = (Matiere) request.getAttribute("matiere");
                boolean isEditMode = matiereToEdit != null;
                String formTitle = isEditMode ? "✏️ Modifier la matière" : "➕ Ajouter une matière";
                String buttonText = isEditMode ? "Mettre à jour" : "Enregistrer";
            %>
            <h2 class="form-title"><%= formTitle %></h2>
            
            <form action="${pageContext.request.contextPath}/matiere/save" method="post">
                <% if (isEditMode) { %>
                    <input type="hidden" name="id" value="<%= matiereToEdit.getId() %>">
                <% } %>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="nom" class="required">Nom</label>
                        <input type="text" id="nom" name="nom" class="form-control" 
                               placeholder="Entrez le nom" required maxlength="100"
                               value="<%= isEditMode ? matiereToEdit.getNom() : "" %>">
                    </div>
                    
                    <div class="form-group">
                        <label for="coeff" class="required">Coefficient</label>
                        <input type="number" id="coeff" name="coeff" class="form-control" 
                               placeholder="Entrez le coefficient" required step="0.5" min="0.5" max="10"
                               value="<%= isEditMode ? matiereToEdit.getCoeff() : "" %>">
                    </div>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn-submit"><%= buttonText %></button>
                    <% if (isEditMode) { %>
                        <a href="${pageContext.request.contextPath}/matiere/list" class="btn-cancel">Annuler</a>
                    <% } %>
                </div>
            </form>
        </div>
        
        <h2 class="form-title">📋 Liste des matières</h2>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Coefficient</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Matiere> matieres = (List<Matiere>) request.getAttribute("matieres");
                    if (matieres != null && !matieres.isEmpty()) {
                        for (Matiere m : matieres) {
                %>
                <tr>
                    <td><%= m.getId() %></td>
                    <td><%= m.getNom() %></td>
                    <td><%= m.getCoeff() %></td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/matiere/update?id=<%= m.getId() %>" class="btn-edit">Modifier</a>
                        <a href="${pageContext.request.contextPath}/matiere/delete?id=<%= m.getId() %>" 
                           class="btn-delete" 
                           onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette matière ?')">Supprimer</a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="4" class="empty-message">Aucune matière trouvée</td>
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