package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.example.demo.entity.*;
import com.example.demo.services.*;


@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @Autowired
    private MatiereService matiereService;

    @Autowired
    private CorrecteurService correcteurService;

    @Autowired
    private EtudiantService etudiantService;


    @GetMapping("/list")
    public String listNotes(Model model) {
        model.addAttribute("notes", noteService.getAllNotes());
        model.addAttribute("etudiants", etudiantService.getAllEtudiants());
        model.addAttribute("matieres", matiereService.getAllMatieres());
        model.addAttribute("correcteurs", correcteurService.getAllCorrecteur());
        return "note/list";
    }


    @PostMapping("/save")
    public String saveNote(@ModelAttribute("noteForm") Note note) {
        if (note.getId() != null) {
            noteService.update(note.getId(), note);

        } else {
            noteService.saveNote(note);

        }
        return "redirect:/note/list";
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam("id") Integer id) {
        noteService.delete(id);
        return "redirect:/note/list";
    }

   @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") Integer id, Model model) {
        Note note = noteService.getNoteById(id)
            .orElseThrow(() -> new RuntimeException("Note non trouvée avec l'id: " + id));
        
        model.addAttribute("note", note);
        
        model.addAttribute("etudiants", etudiantService.getAllEtudiants());
        model.addAttribute("matieres", matiereService.getAllMatieres());
        model.addAttribute("correcteurs", correcteurService.getAllCorrecteur()); 
        
        model.addAttribute("notes", noteService.getAllNotes());
        
        return "note/list";
    }

}
