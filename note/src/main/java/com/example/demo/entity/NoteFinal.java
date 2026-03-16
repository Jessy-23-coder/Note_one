package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "note_final")
public class NoteFinal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "etudiant", nullable = false)
    private Etudiant etudiant;
    
    @ManyToOne
    @JoinColumn(name = "matiere", nullable = false)
    private Matiere matiere;
    
    @Column(name = "note", nullable = false, precision = 4, scale = 2)
    private BigDecimal note;
    
    // Constructeurs
    public NoteFinal() {}
    
    public NoteFinal(Etudiant etudiant, Matiere matiere, BigDecimal note) {
        this.etudiant = etudiant;
        this.matiere = matiere;
        this.note = note;
    }
    
    // Getters et Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
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
    
    public BigDecimal getNote() {
        return note;
    }
    
    public void setNote(BigDecimal note) {
        if (note.compareTo(BigDecimal.ZERO) < 0 || note.compareTo(new BigDecimal("20")) > 0) {
            throw new IllegalArgumentException("La note doit être comprise entre 0 et 20");
        }
        this.note = note;
    }
    
    // Méthodes helpers
    public String getEtudiantNom() {
        return etudiant != null ? etudiant.getNom() + " " + etudiant.getPrenom() : "";
    }
    
    public String getMatiereNom() {
        return matiere != null ? matiere.getNom() : "";
    }
    
    @Override
    public String toString() {
        return "NoteFinal{" +
                "id=" + id +
                ", etudiant=" + (etudiant != null ? etudiant.getNom() : "null") +
                ", matiere=" + (matiere != null ? matiere.getNom() : "null") +
                ", note=" + note +
                '}';
    }
}