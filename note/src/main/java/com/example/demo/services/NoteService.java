package com.example.demo.services;

import com.example.demo.entity.Note;
import com.example.demo.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    
    @Autowired
    private NoteRepository noteRepository;
    
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }
    
    public Optional<Note> getNoteById(Integer id) {
        return noteRepository.findById(id);
    }
   
    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }
    
    public void delete(Integer id) {
        noteRepository.deleteById(id);
    }

    public Note update(Integer id, Note updatedNote) {
        Note note = getNoteById(id).orElse(null); 
        note.setEtudiantId(updatedNote.getEtudiantId());
        note.setMatiereId(updatedNote.getMatiereId());
        note.setCorrecteurId(updatedNote.getCorrecteurId());
        note.setNote(updatedNote.getNote());
        return noteRepository.save(note); 

    }

    public List<Note> getAllNoteByEtudiantAndMatiere(Integer etudiantId, Integer matiereId) {
        return noteRepository.findByEtudiantIdAndMatiereId(etudiantId, matiereId);
    }

    public List<Note> getNotesByEtudiant(Integer etudiantId) {
        return noteRepository.findByEtudiantId(etudiantId);
    }

    public List<Note> getNotesByMatiere(Integer matiereId) {
        return noteRepository.findByMatiereId(matiereId);
    }
    


}