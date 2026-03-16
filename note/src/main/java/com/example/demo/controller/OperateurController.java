package com.example.demo.controller;

import com.example.demo.entity.Operateur;
import com.example.demo.services.OperateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/operateur")
public class OperateurController {
    
    @Autowired
    private OperateurService operateurService;
    
    @GetMapping("/list")
    public String listOperateurs(Model model) {
        model.addAttribute("operateurs", operateurService.getAllOperateurs());
        return "operateur/list";
    }
    
    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") Integer id, Model model) {
        Operateur operateur = operateurService.getOperateurById(id)
            .orElseThrow(() -> new RuntimeException("Opérateur non trouvé avec l'id: " + id));
        
        model.addAttribute("operateur", operateur);
        model.addAttribute("operateurs", operateurService.getAllOperateurs());
        
        return "operateur/list";
    }
    
    @PostMapping("/save")
    public String saveOperateur(@ModelAttribute("operateur") Operateur operateur) {
        if (operateur.getId() != null) {
            operateurService.update(operateur.getId(), operateur);
        } else {
            operateurService.save(operateur);
        }
        return "redirect:/operateur/list";
    }
    
    @GetMapping("/delete")
    public String deleteOperateur(@RequestParam("id") Integer id) {
        operateurService.delete(id);
        return "redirect:/operateur/list";
    }
}