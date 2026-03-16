package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "note")
public class Note {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "etudiant", nullable = false)
    private Integer etudiantId;
    
    @Column(name = "matiere", nullable = false)
    private Integer matiereId;
    
    @Column(name = "correcteur", nullable = false)
    private Integer correcteurId;
    
    @Column(name = "note", nullable = false, precision = 4, scale = 2)
    private BigDecimal note;
    
    @Column(name = "date_saisie", nullable = false, updatable = false)
    private LocalDateTime dateSaisie;
    
    // Relations (optionnelles - si vous voulez charger les objets complets)
    @ManyToOne
    @JoinColumn(name = "etudiant", insertable = false, updatable = false)
    private Etudiant etudiant;
    
    @ManyToOne
    @JoinColumn(name = "matiere", insertable = false, updatable = false)
    private Matiere matiere;
    
    @ManyToOne
    @JoinColumn(name = "correcteur", insertable = false, updatable = false)
    private Correcteur correcteur;
    
    // Constructeurs
    public Note() {
        this.dateSaisie = LocalDateTime.now();
    }
    
    public Note(Integer etudiantId, Integer matiereId, Integer correcteurId, BigDecimal note) {
        this.etudiantId = etudiantId;
        this.matiereId = matiereId;
        this.correcteurId = correcteurId;
        this.note = note;
        this.dateSaisie = LocalDateTime.now();
    }
    
    // Getters et Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getEtudiantId() {
        return etudiantId;
    }
    
    public void setEtudiantId(Integer etudiantId) {
        this.etudiantId = etudiantId;
    }
    
    public Integer getMatiereId() {
        return matiereId;
    }
    
    public void setMatiereId(Integer matiereId) {
        this.matiereId = matiereId;
    }
    
    public Integer getCorrecteurId() {
        return correcteurId;
    }
    
    public void setCorrecteurId(Integer correcteurId) {
        this.correcteurId = correcteurId;
    }
    
    public BigDecimal getNote() {
        return note;
    }
    
    public void setNote(BigDecimal note) {
        if (note.compareTo(BigDecimal.ZERO) < 0 || note.compareTo(new BigDecimal("20")) > 0) {
            throw new IllegalArgumentException("La note doit être comprise entre 0 et 20");
        }
        this.note = note;
    }
    
    public LocalDateTime getDateSaisie() {
        return dateSaisie;
    }
    
    public void setDateSaisie(LocalDateTime dateSaisie) {
        this.dateSaisie = dateSaisie;
    }
    
    // Getters et Setters pour les relations
    public Etudiant getEtudiant() {
        return etudiant;
    }
    
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
    
    public Matiere getMatiere() {
        return matiere;
    }
    
    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }
    
    public Correcteur getCorrecteur() {
        return correcteur;
    }
    
    public void setCorrecteur(Correcteur correcteur) {
        this.correcteur = correcteur;
    }
    
    // Méthodes utilitaires
    @PrePersist
    protected void onCreate() {
        dateSaisie = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", etudiantId=" + etudiantId +
                ", matiereId=" + matiereId +
                ", correcteurId=" + correcteurId +
                ", note=" + note +
                ", dateSaisie=" + dateSaisie +
                '}';
    }
}