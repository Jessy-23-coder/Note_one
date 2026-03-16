package com.example.demo.repository;

import com.example.demo.entity.NoteFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoteFinalRepository extends JpaRepository<NoteFinal, Integer> {
    // NoteFinal stores relations (etudiant, matiere) as entities; use nested property names
    List<NoteFinal> findByEtudiant_Id(Integer etudiantId);
    List<NoteFinal> findByMatiere_Id(Integer matiereId);
    Optional<NoteFinal> findByEtudiant_IdAndMatiere_Id(Integer etudiantId, Integer matiereId);
}