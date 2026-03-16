package com.example.demo.services;

import com.example.demo.entity.Matiere;
import com.example.demo.repository.MatiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MatiereService {
    
    @Autowired
    private MatiereRepository matiereRepository;
    
    public List<Matiere> getAllMatieres() {
        return matiereRepository.findAll();
    }
    
    public Matiere getMatiereById(Integer id) {
        Optional<Matiere> optionalMatiere = matiereRepository.findById(id);
        return optionalMatiere.orElse(null); // ou lancez une exception
    }

    public Matiere save(Matiere matiere) {
        return matiereRepository.save(matiere);
    }
    

    public Matiere update(Integer id, Matiere updatedMatiere) {
        Matiere matiere = getMatiereById(id); 

        matiere.setNom(updatedMatiere.getNom());
        matiere.setCoeff(updatedMatiere.getCoeff());

        return matiereRepository.save(matiere); 
    }

    public void delete(Integer id) {
        matiereRepository.deleteById(id);
    }
   
}