<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.example.demo.entity.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Paramètres</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/allCSS.css">
</head>
<body>
    <%@ include file="/assets/include/navbar.jsp" %>
    <div class="container">
        <h1>📚 Gestion des Paramètres</h1>
        
        <div class="form-section">
            <%
                Parametre parametreToEdit = (Parametre) request.getAttribute("parametre");
                boolean isEditMode = parametreToEdit != null;
                String formTitle = isEditMode ? "✏️ Modifier le paramètre" : "➕ Ajouter un paramètre";
                String buttonText = isEditMode ? "Mettre à jour" : "Enregistrer";
            %>
            <h2 class="form-title"><%= formTitle %></h2>
            
            <form action="${pageContext.request.contextPath}/parametre/save" method="post">
                <% if (isEditMode) { %>
                    <input type="hidden" name="id" value="<%= parametreToEdit.getId() %>">
                <% } %>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="matiereId" class="required">Matière</label>
                        <select id="matiereId" name="matiereId" class="form-control" required>
                            <option value="">Sélectionnez une matière</option>
                            <%
                                List<Matiere> matieres = (List<Matiere>) request.getAttribute("matieres");
                                if (matieres != null) {
                                    for (Matiere m : matieres) {
                                        String selected = (isEditMode && parametreToEdit.getMatiere().getId().equals(m.getId())) ? "selected" : "";
                            %>
                            <option value="<%= m.getId() %>" <%= selected %>><%= m.getNom() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="difference" class="required">Différence</label>
                        <input type="number" id="difference" name="difference" class="form-control" 
                               placeholder="Entrez la différence" required step="0.01" min="0"
                               value="<%= isEditMode ? parametreToEdit.getDifference() : "" %>">
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="resolutionId" class="required">Résolution</label>
                        <select id="resolutionId" name="resolutionId" class="form-control" required>
                            <option value="">Sélectionnez une résolution</option>
                            <%
                                List<Resolution> resolutions = (List<Resolution>) request.getAttribute("resolutions");
                                if (resolutions != null) {
                                    for (Resolution r : resolutions) {
                                        String selected = (isEditMode && parametreToEdit.getResolution().getId().equals(r.getId())) ? "selected" : "";
                            %>
                            <option value="<%= r.getId() %>" <%= selected %>><%= r.getNom() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="operateurId" class="required">Opérateur</label>
                        <select id="operateurId" name="operateurId" class="form-control" required>
                            <option value="">Sélectionnez un opérateur</option>
                            <%
                                List<Operateur> operateurs = (List<Operateur>) request.getAttribute("operateurs");
                                if (operateurs != null) {
                                    for (Operateur o : operateurs) {
                                        String selected = (isEditMode && parametreToEdit.getOperateur().getId().equals(o.getId())) ? "selected" : "";
                            %>
                            <option value="<%= o.getId() %>" <%= selected %>><%= o.getOperateur() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn-submit"><%= buttonText %></button>
                    <% if (isEditMode) { %>
                        <a href="${pageContext.request.contextPath}/parametre/list" class="btn-cancel">Annuler</a>
                    <% } %>
                </div>
            </form>
        </div>
        
        <h2 class="form-title">📋 Liste des paramètres</h2>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Matière</th>
                    <th>Différence</th>
                    <th>Résolution</th>
                    <th>Opérateur</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Parametre> parametres = (List<Parametre>) request.getAttribute("parametres");
                    if (parametres != null && !parametres.isEmpty()) {
                        for (Parametre p : parametres) {
                %>
                <tr>
                    <td><%= p.getId() %></td>
                    <td><%= p.getMatiere().getNom() %></td>
                    <td><%= p.getDifference() %></td>
                    <td><%= p.getResolution().getNom() %></td>
                    <td><%= p.getOperateur().getOperateur() %></td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/parametre/update?id=<%= p.getId() %>" class="btn-edit">Modifier</a>
                        <a href="${pageContext.request.contextPath}/parametre/delete?id=<%= p.getId() %>" 
                           class="btn-delete" 
                           onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce paramètre ?')">Supprimer</a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6" class="empty-message">Aucun paramètre trouvé</td>
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