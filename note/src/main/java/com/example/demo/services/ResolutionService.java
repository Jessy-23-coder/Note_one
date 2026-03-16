package com.example.demo.services;

import com.example.demo.entity.Resolution;
import com.example.demo.repository.ResolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ResolutionService {
    
    @Autowired
    private ResolutionRepository resolutionRepository;
    
    public List<Resolution> getAllResolutions() {
        return resolutionRepository.findAll();
    }
    
    public Optional<Resolution> getResolutionById(Integer id) {
        return resolutionRepository.findById(id);
    }
    
    public Resolution save(Resolution resolution) {
        return resolutionRepository.save(resolution);
    }
    
    public Resolution update(Integer id, Resolution updatedResolution) {
        Resolution resolution = resolutionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Resolution non trouvée avec l'id: " + id));
        
        resolution.setNom(updatedResolution.getNom());
        
        return resolutionRepository.save(resolution);
    }
    
    public void delete(Integer id) {
        if (!resolutionRepository.existsById(id)) {
            throw new RuntimeException("Resolution non trouvée avec l'id: " + id);
        }
        resolutionRepository.deleteById(id);
    }
    
    public boolean existsById(Integer id) {
        return resolutionRepository.existsById(id);
    }
}