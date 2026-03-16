package com.example.demo.services;

import com.example.demo.entity.*;
import com.example.demo.repository.NoteFinalRepository;
import com.example.demo.repository.NoteRepository;
import com.example.demo.repository.ParametreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class NoteFinalService {
    
    @Autowired
    private NoteFinalRepository noteFinalRepository;
    
    @Autowired
    private EtudiantService etudiantService;
    
    @Autowired
    private MatiereService matiereService;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ParametreRepository parametreRepository;

    
    public List<NoteFinal> getAllNotesFinales() {
        return noteFinalRepository.findAll();
    }
    
    public Optional<NoteFinal> getNoteFinaleById(Integer id) {
        return noteFinalRepository.findById(id);
    }
    
    public NoteFinal save(NoteFinal noteFinal) {
        return noteFinalRepository.save(noteFinal);
    }
    
    public NoteFinal saveWithIds(Integer etudiantId, Integer matiereId, BigDecimal note) {
        Etudiant etudiant = etudiantService.getEtudiantById(etudiantId)
            .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
        Matiere matiere = matiereService.getMatiereById(matiereId);
        
        NoteFinal noteFinal = new NoteFinal(etudiant, matiere, note);
        return noteFinalRepository.save(noteFinal);
    }
    
    public NoteFinal update(Integer id, NoteFinal updatedNoteFinal) {
        NoteFinal noteFinal = noteFinalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Note finale non trouvée avec l'id: " + id));
        
        if (updatedNoteFinal.getEtudiant() != null) {
            noteFinal.setEtudiant(updatedNoteFinal.getEtudiant());
        }
        if (updatedNoteFinal.getMatiere() != null) {
            noteFinal.setMatiere(updatedNoteFinal.getMatiere());
        }
        if (updatedNoteFinal.getNote() != null) {
            noteFinal.setNote(updatedNoteFinal.getNote());
        }
        
        return noteFinalRepository.save(noteFinal);
    }
    
    public void delete(Integer id) {
        if (!noteFinalRepository.existsById(id)) {
            throw new RuntimeException("Note finale non trouvée avec l'id: " + id);
        }
        noteFinalRepository.deleteById(id);
    }
    
    public List<NoteFinal> getByEtudiant(Integer etudiantId) {
        return noteFinalRepository.findByEtudiant_Id(etudiantId);
    }
    
    public List<NoteFinal> getByMatiere(Integer matiereId) {
        return noteFinalRepository.findByMatiere_Id(matiereId);
    }
    
    public Optional<NoteFinal> getByEtudiantAndMatiere(Integer etudiantId, Integer matiereId) {
        return noteFinalRepository.findByEtudiant_IdAndMatiere_Id(etudiantId, matiereId);
    }



    



    public static double calculerDifferenceTotaleOptimisee(List<Double> nombres) {
        if (nombres == null || nombres.size() < 2) {
            return 0.0;
        }
        
        List<Double> nombresTries = new ArrayList<>(nombres);
        Collections.sort(nombresTries);
        
        double sommeDifferences = 0.0;
        int taille = nombresTries.size();
        
        for (int i = 0; i < taille; i++) {
            for (int j = i + 1; j < taille; j++) {
                sommeDifferences += (nombresTries.get(j) - nombresTries.get(i));
            }
        }
        
        return sommeDifferences;
    }
    
    public double notefinal(Etudiant etudiant, Matiere matiere) {
        double note_final = 0.00;
        
        // Récupérer toutes les notes de l'étudiant pour cette matière
        List<Note> notesEtudiant = noteRepository.findByEtudiantIdAndMatiereId(etudiant.getId(), matiere.getId());
        
        if (notesEtudiant != null && !notesEtudiant.isEmpty()) {
            
            // Si une seule note, c'est la note finale
            if (notesEtudiant.size() == 1) {
                note_final = notesEtudiant.get(0).getNote().doubleValue();
            } 
            // Si plusieurs notes, appliquer la règle de résolution
            else {
                // Trouver les paramètres pour cette matière
                List<Parametre> parametre = parametreRepository.findByMatiere_Id(matiere.getId());

                // Si pas de paramètre défini (null ou vide) : moyenne simple par défaut
                if (parametre == null || parametre.isEmpty()) {
                    note_final = calculerMoyenne(notesEtudiant);
                } else {
                    // Calculer l'écart entre la note max et min
                    double noteMax = notesEtudiant.stream()
                        .mapToDouble(n -> n.getNote().doubleValue())
                        .max()
                        .orElse(0.0);
                        
                    double noteMin = notesEtudiant.stream()
                        .mapToDouble(n -> n.getNote().doubleValue())
                        .min()
                        .orElse(0.0);
                    
                        
                    double difference = calculerDifferenceTotaleOptimisee(notesEtudiant.stream().map(n -> n.getNote().doubleValue()).collect(Collectors.toList()));

                    Parametre parametreSelectionne = null;

                    if (parametre.size() > 0) {
                        // Initialiser avec le premier paramètre
                        parametreSelectionne = parametre.get(0);
                        double differenceMinimale = Math.abs(parametreSelectionne.getDifference().doubleValue() - difference);
                        
                        // Parcourir tous les paramètres pour trouver celui avec la différence la plus proche
                        for (int i = 1; i < parametre.size(); i++) {
                            Parametre paramCourant = parametre.get(i);
                            double ecartCourant = Math.abs(paramCourant.getDifference().doubleValue() - difference);
                            
                            // Si l'écart est plus petit, on garde ce paramètre
                            if (ecartCourant < differenceMinimale) {
                                parametreSelectionne = paramCourant;
                                differenceMinimale = ecartCourant;
                            }
                            // Si l'écart est égal, on garde celui avec la plus petite différence
                            else if (ecartCourant == differenceMinimale) {
                                if (paramCourant.getDifference().doubleValue() < parametreSelectionne.getDifference().doubleValue()) {
                                    parametreSelectionne = paramCourant;
                                    // differenceMinimale reste la même
                                }
                            }
                        }
                    }

                    // Utilisation du paramètre sélectionné
                    if (parametreSelectionne != null) {
                        System.out.println("Paramètre sélectionné avec différence: " + parametreSelectionne.getDifference().doubleValue());
                        // Faire quelque chose avec le paramètre
                    } else {
                        System.out.println("Aucun paramètre disponible");
                    }


                    Resolution resolution = parametreSelectionne.getResolution();
                    Operateur operateur = parametreSelectionne.getOperateur();
                    
                    // Vérifier si l'écart correspond au critère
                    boolean conditionRemplie = false;
                    
                    if (operateur.getOperateur().equals(">")) {
                        conditionRemplie = difference > parametreSelectionne.getDifference().doubleValue();
                    } else if (operateur.getOperateur().equals("<")) {
                        conditionRemplie = difference < parametreSelectionne.getDifference().doubleValue();
                    } else if (operateur.getOperateur().equals(">=")) {
                        conditionRemplie = difference >= parametreSelectionne.getDifference().doubleValue();
                    } else if (operateur.getOperateur().equals("<=")) {
                        conditionRemplie = difference <= parametreSelectionne.getDifference().doubleValue();
                    } else if (operateur.getOperateur().equals("=")) {
                        conditionRemplie = Math.abs(difference - parametreSelectionne.getDifference().doubleValue()) < 0.001;
                    }
                    
                    // Appliquer la règle de résolution si la condition est remplie
                    if (conditionRemplie) {
                        switch(resolution.getNom().toLowerCase()) {
                            case "haute":
                                note_final = noteMax;
                                break;
                                
                            case "moyenne":
                                note_final = calculerMoyenne(notesEtudiant);
                                break;
                                
                            case "basse":
                                note_final = noteMin;
                                break;
                                
                            default:
                                // Par défaut : moyenne
                                note_final = calculerMoyenne(notesEtudiant);
                        }
                    } else {
                        // Condition non remplie : moyenne simple par défaut
                        note_final = calculerMoyenne(notesEtudiant);
                    }
                }
            }
        }
        
        return note_final;
    }

    private double calculerMoyenne(List<Note> notes) {
        double moyenne = 0.00;
        BigDecimal somme = BigDecimal.ZERO;
        
        for (Note note : notes) {
            somme = somme.add(note.getNote());
        }
        
        double size = notes.size();
        
        if (size > 0) {
            moyenne = somme.doubleValue() / size;
        }
        
        return moyenne;
    }

   
    public int calculateAndSaveAll() {
        List<Etudiant> etudiants = etudiantService.getAllEtudiants();
        List<Matiere> matieres = matiereService.getAllMatieres();
        int count = 0;

        if (etudiants == null || matieres == null) {
            return count;
        }

        for (Etudiant e : etudiants) {
            for (Matiere m : matieres) {
                List<Note> notes = noteRepository.findByEtudiantIdAndMatiereId(e.getId(), m.getId());
                if (notes == null || notes.isEmpty()) {
                    continue; 
                }

                double noteCalc = notefinal(e, m);
                BigDecimal noteBd = BigDecimal.valueOf(noteCalc);

                Optional<NoteFinal> existing = getByEtudiantAndMatiere(e.getId(), m.getId());
                if (existing.isPresent()) {
                    NoteFinal nf = existing.get();
                    nf.setNote(noteBd);
                    noteFinalRepository.save(nf);
                } else {
                    NoteFinal nf = new NoteFinal(e, m, noteBd);
                    noteFinalRepository.save(nf);
                }
                count++;
            }
        }

        return count;
    }

    
    public void deleteAllNotesFinales() {
        noteFinalRepository.deleteAll();
    }




}