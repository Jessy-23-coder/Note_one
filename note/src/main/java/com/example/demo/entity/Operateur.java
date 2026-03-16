package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "operateur")
public class Operateur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "operateur", nullable = false, length = 50, unique = true)
    private String operateur;
    
    @OneToMany(mappedBy = "operateur")
    private List<Parametre> parametres;
    
    // Constructeurs
    public Operateur() {}
    
    public Operateur(String operateur) {
        this.operateur = operateur;
    }
    
    // Getters et Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getOperateur() {
        return operateur;
    }
    
    public void setOperateur(String operateur) {
        this.operateur = operateur;
    }
    
    public List<Parametre> getParametres() {
        return parametres;
    }
    
    public void setParametres(List<Parametre> parametres) {
        this.parametres = parametres;
    }
    
    @Override
    public String toString() {
        return "Operateur{" +
                "id=" + id +
                ", operateur='" + operateur + '\'' +
                '}';
    }
}