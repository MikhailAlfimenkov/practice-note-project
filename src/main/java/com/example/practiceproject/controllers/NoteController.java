package com.example.practiceproject.controllers;

import com.example.practiceproject.dto.CreationNoteRequest;
import com.example.practiceproject.dto.NoteResponse;
import com.example.practiceproject.dto.UpdateNoteStatusRequest;
import com.example.practiceproject.dto.UpdateNoteTextRequest;
import com.example.practiceproject.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody CreationNoteRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.createNote(request));
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable UUID id){
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<NoteResponse> updateNoteStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateNoteStatusRequest request){
        return ResponseEntity.ok(noteService.updateNoteStatus(id, request));
    }

    @PatchMapping("/{id}/text")
    public ResponseEntity<NoteResponse> updateNoteText(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateNoteTextRequest request){
        return ResponseEntity.ok(noteService.updateNoteText(id,request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable UUID id){
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }


}
