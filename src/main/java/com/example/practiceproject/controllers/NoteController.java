package com.example.practiceproject.controllers;

import com.example.practiceproject.dto.CreationNoteRequest;
import com.example.practiceproject.dto.NoteResponse;
import com.example.practiceproject.dto.UpdateNoteStatusRequest;
import com.example.practiceproject.dto.UpdateNoteTextRequest;
import com.example.practiceproject.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public NoteResponse createNote(@Valid @RequestBody CreationNoteRequest request) {
        return noteService.createNote(request);
    }

    @GetMapping
    public List<NoteResponse> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public NoteResponse getNoteById(@PathVariable UUID id) {
        return noteService.getNoteById(id);
    }

    @PatchMapping("/{id}/status")
    public NoteResponse updateNoteStatus(@PathVariable UUID id, @Valid @RequestBody UpdateNoteStatusRequest request) {
        return noteService.updateNoteStatus(id, request);
    }

    @PatchMapping("/{id}/text")
    public NoteResponse updateNoteText(@PathVariable UUID id, @Valid @RequestBody UpdateNoteTextRequest request) {
        return noteService.updateNoteText(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable UUID id) {
        noteService.deleteNote(id);
    }

    @GetMapping("/author/{authorId}")
    public List<NoteResponse> getNotesByAuthorId(@PathVariable UUID authorId) {
        return noteService.getNotesByAuthorId(authorId);
    }
}