package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/parametre")
public class ParametreController {
    
    @Autowired
    private ParametreService parametreService;
    
    @Autowired
    private MatiereService matiereService;
    
    @Autowired
    private ResolutionService resolutionService;
    
    @Autowired
    private OperateurService operateurService;
    
    @GetMapping("/list")
    public String listParametres(Model model) {
        model.addAttribute("parametres", parametreService.getAllParametres());
        model.addAttribute("matieres", matiereService.getAllMatieres());
        model.addAttribute("resolutions", resolutionService.getAllResolutions());
        model.addAttribute("operateurs", operateurService.getAllOperateurs());
        return "parametre/list";
    }
    
    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") Integer id, Model model) {
        Parametre parametre = parametreService.getParametreById(id)
            .orElseThrow(() -> new RuntimeException("Paramètre non trouvé avec l'id: " + id));
        
        model.addAttribute("parametre", parametre);
        model.addAttribute("parametres", parametreService.getAllParametres());
        model.addAttribute("matieres", matiereService.getAllMatieres());
        model.addAttribute("resolutions", resolutionService.getAllResolutions());
        model.addAttribute("operateurs", operateurService.getAllOperateurs());
        
        return "parametre/list";
    }
    
    @PostMapping("/save")
    public String saveParametre(@RequestParam(value = "id", required = false) Integer id,
                                @RequestParam("matiereId") Integer matiereId,
                                @RequestParam("difference") BigDecimal difference,
                                @RequestParam("resolutionId") Integer resolutionId,
                                @RequestParam("operateurId") Integer operateurId) {
        
        if (id != null) {
            // Mode modification
            Parametre parametre = parametreService.getParametreById(id)
                .orElseThrow(() -> new RuntimeException("Paramètre non trouvé"));
            
            Matiere matiere = matiereService.getMatiereById(matiereId);
            Resolution resolution = resolutionService.getResolutionById(resolutionId)
                .orElseThrow(() -> new RuntimeException("Résolution non trouvée"));
            Operateur operateur = operateurService.getOperateurById(operateurId)
                .orElseThrow(() -> new RuntimeException("Opérateur non trouvé"));
            
            parametre.setMatiere(matiere);
            parametre.setDifference(difference);
            parametre.setResolution(resolution);
            parametre.setOperateur(operateur);
            
            parametreService.save(parametre);
        } else {
            // Mode ajout
            parametreService.saveWithIds(matiereId, difference, resolutionId, operateurId);
        }
        
        return "redirect:/parametre/list";
    }
    
    @GetMapping("/delete")
    public String deleteParametre(@RequestParam("id") Integer id) {
        parametreService.delete(id);
        return "redirect:/parametre/list";
    }
}