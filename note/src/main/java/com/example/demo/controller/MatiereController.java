package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.example.demo.entity.Matiere;
import com.example.demo.services.MatiereService;


@Controller
@RequestMapping("/matiere")
public class MatiereController {
    @Autowired
    private MatiereService matiereService;

    @GetMapping("/list")
    public String listematiere(Model model) {
        model.addAttribute("matieres", matiereService.getAllMatieres());
        model.addAttribute("matiereform", new Matiere());
        return "matiere/list";
    }

    @PostMapping("/save")
    public String saveMatiere(@ModelAttribute("matiereForm") Matiere matiere, Model model) {
        if (matiere.getId() != null) {
            matiereService.update(matiere.getId(), matiere);

        } else {
            matiereService.save(matiere);

        }
        return "redirect:/matiere/list";
    }

    @GetMapping("/delete")
    public String deleteMatiere(@RequestParam("id") Integer id) {
        matiereService.delete(id);
        return "redirect:/matiere/list";
    }

     @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") Integer id, Model model) {
        Matiere matiere = matiereService.getMatiereById(id);
        
        model.addAttribute("matiere", matiere);
        model.addAttribute("matieres", matiereService.getAllMatieres());
        
        return "matiere/list";
    }
}
