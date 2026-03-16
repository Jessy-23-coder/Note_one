package com.example.demo.services;

import com.example.demo.entity.*;
import com.example.demo.repository.ParametreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParametreService {
    
    @Autowired
    private ParametreRepository parametreRepository;
    
    @Autowired
    private MatiereService matiereService;
    
    @Autowired
    private ResolutionService resolutionService;
    
    @Autowired
    private OperateurService operateurService;
    
    public List<Parametre> getAllParametres() {
        return parametreRepository.findAll();
    }
    
    public Optional<Parametre> getParametreById(Integer id) {
        return parametreRepository.findById(id);
    }
    
    public Parametre save(Parametre parametre) {
        return parametreRepository.save(parametre);
    }
    
    public Parametre saveWithIds(Integer matiereId, BigDecimal difference, 
                                  Integer resolutionId, Integer operateurId) {
        Matiere matiere = matiereService.getMatiereById(matiereId);
        Resolution resolution = resolutionService.getResolutionById(resolutionId)
            .orElseThrow(() -> new RuntimeException("Résolution non trouvée"));
        Operateur operateur = operateurService.getOperateurById(operateurId)
            .orElseThrow(() -> new RuntimeException("Opérateur non trouvé"));
        
        Parametre parametre = new Parametre(matiere, difference, resolution, operateur);
        return parametreRepository.save(parametre);
    }
    
    public Parametre update(Integer id, Parametre updatedParametre) {
        Parametre parametre = parametreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paramètre non trouvé avec l'id: " + id));
        
        if (updatedParametre.getMatiere() != null) {
            parametre.setMatiere(updatedParametre.getMatiere());
        }
        if (updatedParametre.getDifference() != null) {
            parametre.setDifference(updatedParametre.getDifference());
        }
        if (updatedParametre.getResolution() != null) {
            parametre.setResolution(updatedParametre.getResolution());
        }
        if (updatedParametre.getOperateur() != null) {
            parametre.setOperateur(updatedParametre.getOperateur());
        }
        
        return parametreRepository.save(parametre);
    }
    
    public void delete(Integer id) {
        if (!parametreRepository.existsById(id)) {
            throw new RuntimeException("Paramètre non trouvé avec l'id: " + id);
        }
        parametreRepository.deleteById(id);
    }
    
    public List<Parametre> getByMatiere(Integer matiereId) {
        return parametreRepository.findByMatiere_Id(matiereId);
    }
    
    public List<Parametre> getByResolution(Integer resolutionId) {
        return parametreRepository.findByResolution_Id(resolutionId);
    }
    
    public List<Parametre> getByOperateur(Integer operateurId) {
        return parametreRepository.findByOperateur_Id(operateurId);
    }
}