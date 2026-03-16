package com.example.demo.services;

import com.example.demo.entity.Operateur;
import com.example.demo.repository.OperateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OperateurService {
    
    @Autowired
    private OperateurRepository operateurRepository;
    
    public List<Operateur> getAllOperateurs() {
        return operateurRepository.findAll();
    }
    
    public Optional<Operateur> getOperateurById(Integer id) {
        return operateurRepository.findById(id);
    }
    
    public Operateur save(Operateur operateur) {
        return operateurRepository.save(operateur);
    }
    
    public Operateur update(Integer id, Operateur updatedOperateur) {
        Operateur operateur = operateurRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Opérateur non trouvé avec l'id: " + id));
        
        operateur.setOperateur(updatedOperateur.getOperateur());
        
        return operateurRepository.save(operateur);
    }
    
    public void delete(Integer id) {
        if (!operateurRepository.existsById(id)) {
            throw new RuntimeException("Opérateur non trouvé avec l'id: " + id);
        }
        operateurRepository.deleteById(id);
    }
    
    public boolean existsById(Integer id) {
        return operateurRepository.existsById(id);
    }
}