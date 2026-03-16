package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/notefinal")
public class NoteFinalController {
    
    @Autowired
    private NoteFinalService noteFinalService;
    
    @Autowired
    private EtudiantService etudiantService;
    
    @Autowired
    private MatiereService matiereService;


    @GetMapping("/list")
    public String listNotesFinales(@RequestParam(required = false) Integer etudiantId,
                                @RequestParam(required = false) Integer matiereId,
                                @RequestParam(required = false) BigDecimal noteMin,
                                @RequestParam(required = false) BigDecimal noteMax,
                                Model model) {
        
        List<NoteFinal> notesFinales;
        
        if (etudiantId != null) {
            notesFinales = noteFinalService.getByEtudiant(etudiantId);
        } else if (matiereId != null) {
            notesFinales = noteFinalService.getByMatiere(matiereId);
        } else if (noteMin != null || noteMax != null) {
            notesFinales = noteFinalService.getAllNotesFinales(); 
        } else {
            notesFinales = noteFinalService.getAllNotesFinales();
        }

        model.addAttribute("notesFinales", notesFinales);
        model.addAttribute("etudiants", etudiantService.getAllEtudiants());
        model.addAttribute("matieres", matiereService.getAllMatieres());
        
        // Pour conserver les valeurs des filtres
        model.addAttribute("selectedEtudiant", etudiantId);
        model.addAttribute("selectedMatiere", matiereId);
        model.addAttribute("noteMin", noteMin);
        model.addAttribute("noteMax", noteMax);
        
        return "notefinal/list";
    }
    
    
    @GetMapping("/delete")
    public String deleteNoteFinale(@RequestParam("id") Integer id) {
        noteFinalService.delete(id);
        return "redirect:/notefinal/list";
    }


    @GetMapping("/calculate")
    public String calculateAndSave(@RequestParam("etudiantId") Integer etudiantId,
                                   @RequestParam("matiereId") Integer matiereId,
                                   RedirectAttributes redirectAttributes) {

        // Récupérer l'étudiant et la matière
        Optional<Etudiant> etOpt = etudiantService.getEtudiantById(etudiantId);
        Matiere matiere = matiereService.getMatiereById(matiereId);

        if (!etOpt.isPresent() || matiere == null) {
            redirectAttributes.addFlashAttribute("message", "Étudiant ou matière introuvable.");
            redirectAttributes.addFlashAttribute("type", "error");
            return "redirect:/notefinal/list";
        }

        Etudiant etudiant = etOpt.get();

        // Calculer la note finale via le service
        double noteCalc = noteFinalService.notefinal(etudiant, matiere);
        BigDecimal noteBd = BigDecimal.valueOf(noteCalc);

        // Vérifier si une note finale existe déjà pour cette paire et la mettre à jour ou créer
        Optional<NoteFinal> existing = noteFinalService.getByEtudiantAndMatiere(etudiantId, matiereId);
        if (existing.isPresent()) {
            NoteFinal nf = existing.get();
            nf.setNote(noteBd);
            noteFinalService.save(nf);
        } else {
            noteFinalService.saveWithIds(etudiantId, matiereId, noteBd);
        }

        redirectAttributes.addFlashAttribute("message", "Note finale calculée et enregistrée : " + noteBd);
        redirectAttributes.addFlashAttribute("type", "success");

        // Retourner vers la liste en conservant les filtres sur l'étudiant et la matière
        return "redirect:/notefinal/list?etudiantId=" + etudiantId + "&matiereId=" + matiereId;
    }


    @GetMapping("/calculateAll")
    public String calculateAllAndSave(RedirectAttributes redirectAttributes) {
        int saved = noteFinalService.calculateAndSaveAll();
        redirectAttributes.addFlashAttribute("message", "Calcul terminé. Notes finales créées/mises à jour : " + saved);
        redirectAttributes.addFlashAttribute("type", "success");
        return "redirect:/notefinal/list";
    }


    @GetMapping("/deleteAll")
    public String deleteAllFinals() {
        noteFinalService.deleteAllNotesFinales();
        return "redirect:/notefinal/list";
    }

}