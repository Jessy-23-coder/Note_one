-- Insertion des données de test
INSERT INTO operateur (operateur) VALUES 
    ('<'),
    ('<='),
    ('>'),
    ('>=');

INSERT INTO resolution (nom) VALUES 
    ('Haute'),
    ('Moyenne'),
    ('Basse');

INSERT INTO matiere (nom, coeff) VALUES 
    ('Mathématiques', 3.0),
    ('Physique', 2.5),
    ('Français', 2.0),
    ('Anglais', 1.5);

INSERT INTO correcteur (nom, prenom) VALUES 
    ('Dupont', 'Jean'),
    ('Martin', 'Marie'),
    ('Bernard', 'Pierre');

INSERT INTO etudiant (nom, prenom) VALUES 
    ('Durand', 'Paul'),
    ('Petit', 'Sophie'),
    ('Moreau', 'Thomas');

INSERT INTO parametre (matiere, difference, resolution, operateur) VALUES 
    (1, 2.0, 1, 1), -- > Haute
    (1, 2.0, 2, 2), -- > Moyenne
    (2, 3.0, 1, 2), -- < Haute
    (2, 3.0, 3, 2),  -- < Moyenne
    (3, 3.0, 1, 1), -- > Haute
    (3, 2.0, 3, 1), -- > Moyenne
    (4, 3.0, 1, 1), -- > Haute
    (4, 3.0, 3, 1);


