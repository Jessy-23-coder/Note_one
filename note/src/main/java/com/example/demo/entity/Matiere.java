package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "matiere")
public class Matiere {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;
    
    @Column(name = "coeff", nullable = false, precision = 3, scale = 2)
    private BigDecimal coeff;
    
    // Constructeurs
    public Matiere() {}
    
    public Matiere(String nom, BigDecimal coeff) {
        this.nom = nom;
        this.coeff = coeff;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public BigDecimal getCoeff() {
        return coeff;
    }
    
    public void setCoeff(BigDecimal coeff) {
        this.coeff = coeff;
    }
    
    @Override
    public String toString() {
        return "Matiere{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", coeff=" + coeff +
                '}';
    }
}