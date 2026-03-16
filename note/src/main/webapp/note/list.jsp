<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.example.demo.entity.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Notes</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/allCSS.css">
    <style>
    .btn-filter{
         background: linear-gradient(135deg, #da0b0bff 0%, #650016ff 100%);
            color: white;
            padding: 12px 25px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-weight: 500;
            transition: transform 0.3s, box-shadow 0.3s;
    }
    </style>
</head>
<body>
    <%@ include file="/assets/include/navbar.jsp" %>
    <div class="container">
        <h1>📚 Gestion des Notes</h1>
        
        <div class="form-section">
            <%
                Note noteToEdit = (Note) request.getAttribute("note");
                boolean isEditMode = noteToEdit != null;
                String formTitle = isEditMode ? "✏️ Modifier la note" : "➕ Ajouter une note";
                String buttonText = isEditMode ? "Mettre à jour" : "Enregistrer";
            %>
            <h2 class="form-title"><%= formTitle %></h2>
            
            <form action="${pageContext.request.contextPath}/note/save" method="post">
                <% if (isEditMode) { %>
                    <input type="hidden" name="id" value="<%= noteToEdit.getId() %>">
                <% } %>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="etudiant" class="required">Étudiant</label>
                        <select id="etudiant" name="etudiantId" class="form-control" required>
                            <option value="">Sélectionnez un étudiant</option>
                            <%
                                List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");
                                if (etudiants != null) {
                                    for (Etudiant e : etudiants) {
                                        String selected = (isEditMode && noteToEdit.getEtudiant().getId().equals(e.getId())) ? "selected" : "";
                            %>
                            <option value="<%= e.getId() %>" <%= selected %>><%= e.getNom() %> <%= e.getPrenom() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="matiere" class="required">Matière</label>
                        <select id="matiere" name="matiereId" class="form-control" required>
                            <option value="">Sélectionnez une matière</option>
                            <%
                                List<Matiere> matieres = (List<Matiere>) request.getAttribute("matieres");
                                if (matieres != null) {
                                    for (Matiere m : matieres) {
                                        String selected = (isEditMode && noteToEdit.getMatiere().getId().equals(m.getId())) ? "selected" : "";
                            %>
                            <option value="<%= m.getId() %>" <%= selected %>><%= m.getNom() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="correcteur" class="required">Correcteur</label>
                        <select id="correcteur" name="correcteurId" class="form-control" required>
                            <option value="">Sélectionnez un correcteur</option>
                            <%
                                List<Correcteur> correcteurs = (List<Correcteur>) request.getAttribute("correcteurs");
                                if (correcteurs != null) {
                                    for (Correcteur c : correcteurs) {
                                        String selected = (isEditMode && noteToEdit.getCorrecteur().getId().equals(c.getId())) ? "selected" : "";
                            %>
                            <option value="<%= c.getId() %>" <%= selected %>><%= c.getNom() %> <%= c.getPrenom() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="note" class="required">Note</label>
                        <input type="number" id="note" name="note" class="form-control" 
                               placeholder="Entrez la note" required min="0" max="20" step="0.5"
                               value="<%= isEditMode ? noteToEdit.getNote() : "" %>">
                    </div>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn-submit"><%= buttonText %></button>
                    <% if (isEditMode) { %>
                        <a href="${pageContext.request.contextPath}/note/list" class="btn-cancel">Annuler</a>
                    <% } %>
                </div>
            </form>
        </div>
        
        <!-- Bouton pour calculer toutes les notes finales à partir de la page des notes -->
        <div style="margin:16px 0;">
            <button type="button" id="calcAllFromNotes" class="btn-filter" style="background: linear-gradient(135deg,#34e89e 0%,#0f3443 100%);">Calculer tout (notes finales)</button>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Etudiant</th>
                    <th>Matière</th>
                    <th>Correcteur</th>
                    <th>Note</th>
                    <th>Date saisie</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Note> notes = (List<Note>) request.getAttribute("notes");
                    if (notes != null && !notes.isEmpty()) {
                        for (Note n : notes) {
                %>
                <tr>
                    <td><%= n.getId() %></td>
                    <td><%= n.getEtudiant().getNom() %> <%= n.getEtudiant().getPrenom() %></td>
                    <td><%= n.getMatiere().getNom() %></td>
                    <td><%= n.getCorrecteur().getNom() %> <%= n.getCorrecteur().getPrenom() %></td>
                    <td><%= n.getNote() %></td>
                    <td><%= n.getDateSaisie() %></td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/note/update?id=<%= n.getId() %>" class="btn-edit">Modifier</a>
                        <a href="${pageContext.request.contextPath}/note/delete?id=<%= n.getId() %>" 
                           class="btn-delete" 
                           onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette note ?')">Supprimer</a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="7" class="empty-message">Aucune note trouvée</td>
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
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var btn = document.getElementById('calcAllFromNotes');
            if (btn) {
                btn.addEventListener('click', function() {
                    if (!confirm('Confirmer le calcul de toutes les notes finales pour tous les étudiants et matières ?')) {
                        return;
                    }
                    var url = '${pageContext.request.contextPath}/notefinal/calculateAll';
                    // redirect to controller endpoint which will run the calculation and redirect back
                    window.location.href = url;
                });
            }
        });
    </script>
</body>
</html>