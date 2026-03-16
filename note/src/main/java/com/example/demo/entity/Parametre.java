package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "parametre")
public class Parametre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "matiere", nullable = false)
    private Matiere matiere;
    
    @Column(name = "difference", nullable = false, precision = 10, scale = 2)
    private BigDecimal difference;
    
    @ManyToOne
    @JoinColumn(name = "resolution", nullable = false)
    private Resolution resolution;
    
    @ManyToOne
    @JoinColumn(name = "operateur", nullable = false)
    private Operateur operateur;
    
    // Constructeurs
    public Parametre() {}
    
    public Parametre(Matiere matiere, BigDecimal difference, Resolution resolution, Operateur operateur) {
        this.matiere = matiere;
        this.difference = difference;
        this.resolution = resolution;
        this.operateur = operateur;
    }
    
    // Getters et Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Matiere getMatiere() {
        return matiere;
    }
    
    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }
    
    public BigDecimal getDifference() {
        return difference;
    }
    
    public void setDifference(BigDecimal difference) {
        this.difference = difference;
    }
    
    public Resolution getResolution() {
        return resolution;
    }
    
    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }
    
    public Operateur getOperateur() {
        return operateur;
    }
    
    public void setOperateur(Operateur operateur) {
        this.operateur = operateur;
    }
    
    // Méthodes helpers
    public Integer getMatiereId() {
        return matiere != null ? matiere.getId() : null;
    }
    
    public String getMatiereNom() {
        return matiere != null ? matiere.getNom() : "";
    }
    
    public Integer getResolutionId() {
        return resolution != null ? resolution.getId() : null;
    }
    
    public String getResolutionNom() {
        return resolution != null ? resolution.getNom() : "";
    }
    
    public Integer getOperateurId() {
        return operateur != null ? operateur.getId() : null;
    }
    
    public String getOperateurNom() {
        return operateur != null ? operateur.getOperateur() : "";
    }
    
    @Override
    public String toString() {
        return "Parametre{" +
                "id=" + id +
                ", matiere=" + (matiere != null ? matiere.getNom() : "null") +
                ", difference=" + difference +
                ", resolution=" + (resolution != null ? resolution.getNom() : "null") +
                ", operateur=" + (operateur != null ? operateur.getOperateur() : "null") +
                '}';
    }
}