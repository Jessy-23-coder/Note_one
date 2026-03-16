package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.example.demo.entity.Etudiant;
import com.example.demo.services.EtudiantService;

@Controller
@RequestMapping("/etudiant")
public class EtudiantController {
    
    @Autowired
    private EtudiantService etudiantService;

    @GetMapping("/list")
    public String listEtudiants(Model model) {
        model.addAttribute("etudiants", etudiantService.getAllEtudiants());
        model.addAttribute("etudiantForm", new Etudiant());
        return "etudiant/list"; // Retourne la vue JSP
    }

    @PostMapping("/save")
    public String saveEtudiant(@ModelAttribute("etudiantForm") Etudiant etudiant) {
        if (etudiant.getId() != null) {
            etudiantService.update(etudiant.getId(), etudiant);
        } else {
            etudiantService.save(etudiant);
        }
        return "redirect:/etudiant/list";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") Integer id, Model model) {
        Etudiant etudiant = etudiantService.getEtudiantById(id)
            .orElseThrow(() -> new RuntimeException("Étudiant non trouvé avec l'id: " + id));
        
        model.addAttribute("etudiant", etudiant);
        
        model.addAttribute("etudiants", etudiantService.getAllEtudiants());
        
        return "etudiant/list";
    }

    @GetMapping("/delete")
    public String deleteEtudiant(@RequestParam("id") Integer id) {
        etudiantService.delete(id);
        return "redirect:/etudiant/list";
    }
}