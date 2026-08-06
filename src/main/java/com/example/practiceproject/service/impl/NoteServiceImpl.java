package com.example.practiceproject.service.impl;

import com.example.practiceproject.dto.CreationNoteRequest;
import com.example.practiceproject.dto.NoteResponse;
import com.example.practiceproject.dto.UpdateNoteStatusRequest;
import com.example.practiceproject.dto.UpdateNoteTextRequest;
import com.example.practiceproject.entity.Author;
import com.example.practiceproject.entity.Note;
import com.example.practiceproject.enums.Status;
import com.example.practiceproject.exception.NoteNotFoundException;
import com.example.practiceproject.exception.AuthorNotFound;
import com.example.practiceproject.mapper.NoteMapper;
import com.example.practiceproject.repository.AuthorRepository;
import com.example.practiceproject.repository.NoteRepository;
import com.example.practiceproject.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final AuthorRepository authorRepository;
    private final NoteMapper noteMapper;

    @Override
    @Transactional
    public NoteResponse createNote(CreationNoteRequest request){
        Author author = authorRepository
                .findByNameAndSurname(request.getAuthorName(), request.getAuthorSurname())
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setId(UUID.randomUUID());
                    newAuthor.setName(request.getAuthorName());
                    newAuthor.setSurname(request.getAuthorSurname());
                    return authorRepository.save(newAuthor);
                });
        Note note = noteMapper.toEntity(request);
        note.setId(UUID.randomUUID());
        note.setStatus(Status.IN_PROGRESS);
        note.setCreatedAt(LocalDateTime.now());
        note.setAuthor(author);

        Note savedNote = noteRepository.save(note);
        return noteMapper.toResponse(savedNote);
    }

    @Override
    @Transactional
    public List<NoteResponse> getAllNotes() {
        return noteMapper.toResponseList(noteRepository.findAll());
    }

    @Override
    @Transactional
    public NoteResponse getNoteById(UUID id){
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
                return noteMapper.toResponse(note);
    }

    @Override
    @Transactional
    public NoteResponse updateNoteStatus(UUID id, UpdateNoteStatusRequest request){
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
        note.setStatus(request.getStatus());
        if(request.getStatus() == Status.COMPLETED){
            note.setCompletedAt(LocalDateTime.now());
        }else{
            note.setCompletedAt(null);
        }
        Note updatedNote = noteRepository.save(note);
        return noteMapper.toResponse(updatedNote);
    }

    @Override
    @Transactional
    public NoteResponse updateNoteText(UUID id, UpdateNoteTextRequest request){
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
        note.setText(request.getText());

        Note updatedNote = noteRepository.save(note);
        return noteMapper.toResponse(updatedNote);
    }

    @Override
    @Transactional
    public void deleteNote(UUID id){
        if(!noteRepository.existsById(id)){
            throw new RuntimeException("Note not found with id: " + id);
        }
        noteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoteResponse> getNotesByAuthorId(UUID authorId){
        if (!authorRepository.existsById(authorId)) {
            throw new AuthorNotFound(authorId);
        }
        List<Note> notes = noteRepository.findByAuthorId(authorId);
        return noteMapper.toResponseList(notes);
    }


}
