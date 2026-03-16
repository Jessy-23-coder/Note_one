package com.example.demo.repository;

import com.example.demo.entity.Parametre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParametreRepository extends JpaRepository<Parametre, Integer> {
    // Use nested property path (entity relationship) for Parametre.matiere.id, resolution.id and operateur.id
    List<Parametre> findByMatiere_Id(Integer matiereId);
    List<Parametre> findByResolution_Id(Integer resolutionId);
    List<Parametre> findByOperateur_Id(Integer operateurId);
}