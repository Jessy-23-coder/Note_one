package com.example.demo.repository;

import com.example.demo.entity.Note;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findByEtudiantId(Integer etudiantId);
    
    List<Note> findByMatiereId(Integer matiereId);
    
    List<Note> findByEtudiantIdAndMatiereId(Integer etudiantId, Integer matiereId);
    
    List<Note> findByCorrecteurId(Integer correcteurId);
    
    long countByEtudiantIdAndMatiereId(Integer etudiantId, Integer matiereId);
    
}