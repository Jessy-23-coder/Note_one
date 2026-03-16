package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.example.demo.entity.Correcteur;
import com.example.demo.services.CorrecteurService;


@Controller
@RequestMapping("/correcteur")
public class CorrecteurController {
    @Autowired
    private CorrecteurService correcteurService;

    @GetMapping("/list")
    public String listCorrecteurs(Model model) {
        model.addAttribute("correcteurs", correcteurService.getAllCorrecteur());
        model.addAttribute("correcteurForm", new Correcteur());
        return "correcteur/list";
    }

    @PostMapping("/save")
    public String saveCorrecteur(@ModelAttribute("correcteurForm") Correcteur correcteur) {
        if (correcteur.getId() != null) {
            correcteurService.update(correcteur.getId(), correcteur);

        } else {
            correcteurService.save(correcteur);

        }
        return "redirect:/correcteur/list";
    }

   @GetMapping("/delete")
    public String deleteCorrecteur(@RequestParam("id") Integer id) {
        correcteurService.delete(id);
        return "redirect:/correcteur/list";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") Integer id, Model model) {
        Correcteur correcteur = correcteurService.getCorrecteurById(id)
            .orElseThrow(() -> new RuntimeException("Correcteur non trouvé avec l'id: " + id));
        
        model.addAttribute("correcteur", correcteur);
        
        model.addAttribute("correcteurs", correcteurService.getAllCorrecteur());
        
        return "correcteur/list";
    }

}
