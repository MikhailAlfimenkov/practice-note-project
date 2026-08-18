package com.example.practiceproject.service;

import com.example.practiceproject.dto.CreationNoteRequest;
import com.example.practiceproject.dto.NoteResponse;
import com.example.practiceproject.dto.UpdateNoteStatusRequest;
import com.example.practiceproject.dto.UpdateNoteTextRequest;

import java.util.List;
import java.util.UUID;

public interface NoteService {

    NoteResponse createNote(CreationNoteRequest request);

    List<NoteResponse> getAllNotes();

    NoteResponse getNoteById(UUID id);

    NoteResponse updateNoteStatus(UUID id, UpdateNoteStatusRequest request);

    NoteResponse updateNoteText(UUID id, UpdateNoteTextRequest request);

    List<NoteResponse> getNotesByAuthorId(UUID authorId);

    void deleteNote(UUID id);
}
