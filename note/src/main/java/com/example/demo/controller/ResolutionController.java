package com.example.demo.controller;

import com.example.demo.entity.Resolution;
import com.example.demo.services.ResolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/resolution")
public class ResolutionController {
    
    @Autowired
    private ResolutionService resolutionService;
    
    @GetMapping("/list")
    public String listResolutions(Model model) {
        model.addAttribute("resolutions", resolutionService.getAllResolutions());
        return "resolution/list";
    }
    
    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") Integer id, Model model) {
        Resolution resolution = resolutionService.getResolutionById(id)
            .orElseThrow(() -> new RuntimeException("Résolution non trouvée avec l'id: " + id));
        
        model.addAttribute("resolution", resolution);
        model.addAttribute("resolutions", resolutionService.getAllResolutions());
        
        return "resolution/list";
    }
    
    @PostMapping("/save")
    public String saveResolution(@ModelAttribute("resolution") Resolution resolution) {
        if (resolution.getId() != null) {
            resolutionService.update(resolution.getId(), resolution);
        } else {
            resolutionService.save(resolution);
        }
        return "redirect:/resolution/list";
    }
    
    @GetMapping("/delete")
    public String deleteResolution(@RequestParam("id") Integer id) {
        resolutionService.delete(id);
        return "redirect:/resolution/list";
    }
}