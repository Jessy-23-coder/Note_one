<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.example.demo.entity.Operateur" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Opérateurs</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/allCSS.css">
</head>
<body>
    <%@ include file="/assets/include/navbar.jsp" %>
    <div class="container">
        <h1>📚 Gestion des Opérateurs</h1>
        
        <div class="form-section">
            <%
                Operateur operateurToEdit = (Operateur) request.getAttribute("operateur");
                boolean isEditMode = operateurToEdit != null;
                String formTitle = isEditMode ? "✏️ Modifier l'opérateur" : "➕ Ajouter un opérateur";
                String buttonText = isEditMode ? "Mettre à jour" : "Enregistrer";
            %>
            <h2 class="form-title"><%= formTitle %></h2>
            
            <form action="${pageContext.request.contextPath}/operateur/save" method="post">
                <% if (isEditMode) { %>
                    <input type="hidden" name="id" value="<%= operateurToEdit.getId() %>">
                <% } %>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="operateur" class="required">Opérateur</label>
                        <input type="text" id="operateur" name="operateur" class="form-control" 
                               placeholder="Entrez l'opérateur (+, -, *, /)" required maxlength="50"
                               value="<%= isEditMode ? operateurToEdit.getOperateur() : "" %>">
                    </div>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn-submit"><%= buttonText %></button>
                    <% if (isEditMode) { %>
                        <a href="${pageContext.request.contextPath}/operateur/list" class="btn-cancel">Annuler</a>
                    <% } %>
                </div>
            </form>
        </div>
        
        <h2 class="form-title">📋 Liste des opérateurs</h2>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Opérateur</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Operateur> operateurs = (List<Operateur>) request.getAttribute("operateurs");
                    if (operateurs != null && !operateurs.isEmpty()) {
                        for (Operateur o : operateurs) {
                %>
                <tr>
                    <td><%= o.getId() %></td>
                    <td><%= o.getOperateur() %></td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/operateur/update?id=<%= o.getId() %>" class="btn-edit">Modifier</a>
                        <a href="${pageContext.request.contextPath}/operateur/delete?id=<%= o.getId() %>" 
                           class="btn-delete" 
                           onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet opérateur ?')">Supprimer</a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="3" class="empty-message">Aucun opérateur trouvé</td>
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