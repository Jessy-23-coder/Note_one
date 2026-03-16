package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "resolution")
public class Resolution {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;
    
    @OneToMany(mappedBy = "resolution")
    private List<Parametre> parametres;
    
    // Constructeurs
    public Resolution() {}
    
    public Resolution(String nom) {
        this.nom = nom;
    }
    
    // Getters et Setters
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
    
    public List<Parametre> getParametres() {
        return parametres;
    }
    
    public void setParametres(List<Parametre> parametres) {
        this.parametres = parametres;
    }
    
    @Override
    public String toString() {
        return "Resolution{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}