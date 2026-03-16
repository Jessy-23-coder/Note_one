package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.services.*;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private MatiereService matiereService;

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private NoteFinalService noteFinalService;

    @GetMapping("/")
    public String listNotesFinales(Model model) {
        model.addAttribute("notesFinales", noteFinalService.getAllNotesFinales());
        model.addAttribute("etudiants", etudiantService.getAllEtudiants());
        model.addAttribute("matieres", matiereService.getAllMatieres());
        return "/home";
    }
}
