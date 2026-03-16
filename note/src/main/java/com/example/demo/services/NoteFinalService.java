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



    // public double notefinal(Etudiant etudiant, Matiere matiere, Resolution resolution,Operateur operateur) {
    //     double note_final = 0.00;
    //     List<Note> notesetudant = noteRepository.findByEtudiantIdAndMatiereId(etudiant.getId(), matiere.getId());
    //     if(notesetudant != null && !notesetudant.isEmpty()) {
    //         if(notesetudant.size() > 1) {
                
    //             double notemax = 0.00;
    //             double notemin = notemax;

    //             for(Note note : notesetudant) {
    //                 if(note.getNote().doubleValue() > notemax) {
    //                     notemax = note.getNote().doubleValue();
    //                 }
                    
    //                 if(note.getNote().doubleValue() < notemin) {
    //                     notemin = note.getNote().doubleValue();
    //                 }
    //             }

    //             double difference = notemax - notemin;

    //             if (difference > 10 && difference < 12) {
    //                 note_final = operateur.apply((note_final - notemin - notemax) / (notesetudant.size() - 2),);
    //             }else if(difference > 12 && difference < 15) {
    //                 note_final = operateur.apply((note_final - notemin - notemax) / (notesetudant.size() - 2), resolution.getDifference().doubleValue() * 0.8);
    //             }else if(difference > 15) {
    //                 note_final = operateur.apply((note_final - notemin - notemax) / (notesetudant.size() - 2), resolution.getDifference().doubleValue() * 0.6);
    //             }

    //         } else {
    //             note_final = notesetudant.get(0).getNote().doubleValue();
    //         }
    //     }
    //     return note_final;
    // }




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
        
        List<Note> notesEtudiant = noteRepository.findByEtudiantIdAndMatiereId(etudiant.getId(), matiere.getId());
        
        if (notesEtudiant != null && !notesEtudiant.isEmpty()) {
            
            if (notesEtudiant.size() == 1) {
                note_final = notesEtudiant.get(0).getNote().doubleValue();
            } 
            else {
                List<Parametre> parametre = parametreRepository.findByMatiere_Id(matiere.getId());

                if (parametre == null || parametre.isEmpty()) {
                    note_final = calculerMoyenne(notesEtudiant);
                } else {
                    double noteMax = notesEtudiant.stream()
                        .mapToDouble(n -> n.getNote().doubleValue())
                        .max()
                        .orElse(0.0);
                        
                    double noteMin = notesEtudiant.stream()
                        .mapToDouble(n -> n.getNote().doubleValue())
                        .min()
                        .orElse(0.0);
                    
                        
                    double difference = calculerDifferenceTotaleOptimisee(notesEtudiant.stream().map(n -> n.getNote().doubleValue()).collect(Collectors.toList()));
                    
                    Resolution resolution = parametre.get(0).getResolution();
                    Operateur operateur = parametre.get(0).getOperateur();
                    
                    boolean conditionRemplie = false;
                    
                    if (operateur.getOperateur().equals(">")) {
                        conditionRemplie = difference > parametre.get(0).getDifference().doubleValue();
                    } else if (operateur.getOperateur().equals("<")) {
                        conditionRemplie = difference < parametre.get(0).getDifference().doubleValue();
                    } else if (operateur.getOperateur().equals(">=")) {
                        conditionRemplie = difference >= parametre.get(0).getDifference().doubleValue();
                    } else if (operateur.getOperateur().equals("<=")) {
                        conditionRemplie = difference <= parametre.get(0).getDifference().doubleValue();
                    } else if (operateur.getOperateur().equals("=")) {
                        conditionRemplie = Math.abs(difference - parametre.get(0).getDifference().doubleValue()) < 0.001;
                    }
                    
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
                                note_final = calculerMoyenne(notesEtudiant);
                        }
                    } else {
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