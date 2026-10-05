package com.example.practiceproject.service;

import com.example.practiceproject.dto.CreationNoteRequest;
import com.example.practiceproject.dto.NoteResponse;
import com.example.practiceproject.dto.UpdateNoteStatusRequest;
import com.example.practiceproject.dto.UpdateNoteTextRequest;
import com.example.practiceproject.enums.Status;
import org.springframework.data.domain.Page;

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

    Page<NoteResponse> getFilteredNotes(
            Status status,
            String textSearch,
            String authorQuery,
            int page,
            int size,
            String sortBy,
            String sortDir
    );
}
