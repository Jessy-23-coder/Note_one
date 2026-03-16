package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Etudiant;
import com.example.demo.repository.EtudiantRepository;

@Service
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    public Etudiant save(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    public Optional<Etudiant> getEtudiantById(Integer id) {
        return etudiantRepository.findById(id);
    }

    public Etudiant update(Integer id, Etudiant updatedEtudiant) {
        Etudiant etudiant = getEtudiantById(id).orElseThrow(() -> new RuntimeException("Etudiant not found with id: " + id)); 
        etudiant.setNom(updatedEtudiant.getNom());
        etudiant.setPrenom(updatedEtudiant.getPrenom());
        return etudiantRepository.save(etudiant); 
    }

    public void delete(Integer id) {
        etudiantRepository.deleteById(id);
    }

}
