
-- Procédure pour calculer automatiquement les notes finales
CREATE OR REPLACE FUNCTION calculer_notes_finales()
RETURNS void AS $$
BEGIN
    -- Supprimer les anciennes notes finales
    DELETE FROM note_final;
    
    -- Insérer les nouvelles notes finales (moyenne par étudiant/matière)
    INSERT INTO note_final (etudiant, matiere, note, date_calcul)
    SELECT 
        etudiant,
        matiere,
        AVG(note) AS note_finale,
        CURRENT_TIMESTAMP
    FROM note
    GROUP BY etudiant, matiere
    ORDER BY etudiant, matiere;
END;
$$ LANGUAGE plpgsql;

-- Exemples de requêtes utiles

-- 1. Comparaison notes intermédiaires vs notes finales
/*
SELECT 
    e.nom || ' ' || e.prenom AS etudiant,
    m.nom AS matiere,
    COUNT(n.id) AS nb_notes_intermediaires,
    AVG(n.note) AS moyenne_intermediaire,
    nf.note AS note_finale,
    CASE 
        WHEN AVG(n.note) = nf.note THEN 'Identique'
        ELSE 'Différente'
    END AS comparaison
FROM etudiant e
JOIN note n ON e.id = n.etudiant
JOIN matiere m ON n.matiere = m.id
JOIN note_final nf ON e.id = nf.etudiant AND m.id = nf.matiere
GROUP BY e.id, e.nom, e.prenom, m.id, m.nom, nf.note
ORDER BY e.nom, m.nom;
*/

-- 2. Relevé de notes final par étudiant
/*
SELECT 
    e.nom || ' ' || e.prenom AS etudiant,
    m.nom AS matiere,
    nf.note,
    m.coeff,
    (nf.note * m.coeff) AS points_ponderes
FROM etudiant e
CROSS JOIN matiere m
LEFT JOIN note_final nf ON e.id = nf.etudiant AND m.id = nf.matiere
ORDER BY e.id, m.id;
*/

-- 3. Calcul de la moyenne générale pondérée à partir des notes finales
/*
SELECT 
    e.nom || ' ' || e.prenom AS etudiant,
    ROUND(AVG(nf.note), 2) AS moyenne_simple,
    ROUND(SUM(nf.note * m.coeff) / SUM(m.coeff), 2) AS moyenne_ponderee,
    RANK() OVER (ORDER BY SUM(nf.note * m.coeff) / SUM(m.coeff) DESC) AS classement
FROM etudiant e
JOIN note_final nf ON e.id = nf.etudiant
JOIN matiere m ON nf.matiere = m.id
GROUP BY e.id, e.nom, e.prenom
ORDER BY moyenne_ponderee DESC;
*/

-- 4. Trigger pour mettre à jour automatiquement les notes finales
-- (optionnel - à décommenter si besoin)
/*
CREATE OR REPLACE FUNCTION trigger_maj_notes_finales()
RETURNS TRIGGER AS $$
BEGIN
    PERFORM calculer_notes_finales();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_maj_notes_finales
AFTER INSERT OR UPDATE OR DELETE ON note
FOR EACH STATEMENT
EXECUTE FUNCTION trigger_maj_notes_finales();
*/