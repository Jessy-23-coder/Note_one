-- Table etudiant
CREATE TABLE etudiant (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL
);

-- Table matiere
CREATE TABLE matiere (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    coeff DECIMAL(3,2) NOT NULL CHECK (coeff > 0)
);

-- Table correcteur
CREATE TABLE correcteur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL
);

-- Table resolution
CREATE TABLE resolution (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

-- Table operateur
CREATE TABLE operateur (
    id SERIAL PRIMARY KEY,
    operateur VARCHAR(50) NOT NULL UNIQUE
);

-- Table parametre
CREATE TABLE parametre (
    id SERIAL PRIMARY KEY,
    matiere INTEGER NOT NULL,
    difference DECIMAL(10,2) NOT NULL,
    resolution INTEGER NOT NULL,
    operateur INTEGER NOT NULL,
    FOREIGN KEY (matiere) REFERENCES matiere(id) ON DELETE CASCADE,
    FOREIGN KEY (resolution) REFERENCES resolution(id) ON DELETE CASCADE,
    FOREIGN KEY (operateur) REFERENCES operateur(id) ON DELETE CASCADE
);

-- Table note (notes intermédiaires)
CREATE TABLE note (
    id SERIAL PRIMARY KEY,
    etudiant INTEGER NOT NULL,
    matiere INTEGER NOT NULL,
    correcteur INTEGER NOT NULL,
    note DECIMAL(4,2) NOT NULL CHECK (note >= 0 AND note <= 20),
    date_saisie TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (etudiant) REFERENCES etudiant(id) ON DELETE CASCADE,
    FOREIGN KEY (matiere) REFERENCES matiere(id) ON DELETE CASCADE,
    FOREIGN KEY (correcteur) REFERENCES correcteur(id) ON DELETE CASCADE
);

-- Table note_final (notes finales)
CREATE TABLE note_final (
    id SERIAL PRIMARY KEY,
    etudiant INTEGER NOT NULL,
    matiere INTEGER NOT NULL,
    note DECIMAL(4,2) NOT NULL CHECK (note >= 0 AND note <= 20),
    FOREIGN KEY (etudiant) REFERENCES etudiant(id) ON DELETE CASCADE,
    FOREIGN KEY (matiere) REFERENCES matiere(id) ON DELETE CASCADE,
    UNIQUE(etudiant, matiere) -- Un étudiant ne peut avoir qu'une note finale par matière
);



-- Création d'index pour améliorer les performances
CREATE INDEX idx_parametre_matiere ON parametre(matiere);
CREATE INDEX idx_parametre_resolution ON parametre(resolution);
CREATE INDEX idx_parametre_operateur ON parametre(operateur);

CREATE INDEX idx_note_etudiant ON note(etudiant);
CREATE INDEX idx_note_matiere ON note(matiere);
CREATE INDEX idx_note_correcteur ON note(correcteur);
CREATE INDEX idx_note_date ON note(date_saisie);
CREATE INDEX idx_note_etudiant_matiere ON note(etudiant, matiere);

CREATE INDEX idx_note_final_etudiant ON note_final(etudiant);
CREATE INDEX idx_note_final_matiere ON note_final(matiere);
CREATE INDEX idx_note_final_date ON note_final(date_calcul);
CREATE INDEX idx_note_final_etudiant_matiere ON note_final(etudiant, matiere);

-- Commentaires sur les tables
COMMENT ON TABLE etudiant IS 'Table des étudiants';
COMMENT ON TABLE matiere IS 'Table des matières avec leurs coefficients';
COMMENT ON TABLE correcteur IS 'Table des correcteurs';
COMMENT ON TABLE resolution IS 'Table des résolutions possibles';
COMMENT ON TABLE operateur IS 'Table des opérateurs disponibles';
COMMENT ON TABLE parametre IS 'Table de liaison des paramètres par matière';
COMMENT ON TABLE note IS 'Table des notes intermédiaires des étudiants par matière et correcteur';
COMMENT ON TABLE note_final IS 'Table des notes finales des étudiants par matière';