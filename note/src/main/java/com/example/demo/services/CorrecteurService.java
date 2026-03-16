package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Correcteur;
import com.example.demo.repository.CorrecteurRepository;

@Service
public class CorrecteurService {
  
    @Autowired
    private CorrecteurRepository correcteurRepository;

    public Correcteur save(Correcteur correcteur) {
        return correcteurRepository.save(correcteur);
    }

    public List<Correcteur> getAllCorrecteur() {
        return correcteurRepository.findAll();
    }

    public Optional<Correcteur> getCorrecteurById(Integer id) {
        return correcteurRepository.findById(id);
    }

    public Correcteur update(Integer id, Correcteur updatedCorrecteur) {
        Correcteur correcteur = getCorrecteurById(id).orElseThrow(() -> new RuntimeException("Correcteur not found with id: " + id)); 

        correcteur.setNom(updatedCorrecteur.getNom());
        correcteur.setPrenom(updatedCorrecteur.getPrenom());

        return correcteurRepository.save(correcteur); 
    }

     public void delete(Integer id) {
        correcteurRepository.deleteById(id);
    }

}
