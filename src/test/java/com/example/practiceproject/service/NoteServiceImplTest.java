package com.example.practiceproject.service;

import com.example.practiceproject.dto.CreationNoteRequest;
import com.example.practiceproject.dto.NoteResponse;
import com.example.practiceproject.dto.UpdateNoteStatusRequest;
import com.example.practiceproject.dto.UpdateNoteTextRequest;
import com.example.practiceproject.entity.Author;
import com.example.practiceproject.entity.Note;
import com.example.practiceproject.enums.Status;
import com.example.practiceproject.mapper.NoteMapper;
import com.example.practiceproject.repository.AuthorRepository;
import com.example.practiceproject.repository.NoteRepository;
import com.example.practiceproject.service.impl.NoteServiceImpl;
import com.example.practiceproject.starter.service.AuditLoggerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private NoteMapper noteMapper;

    @Mock
    private AuditLoggerService auditLoggerService;

    @InjectMocks
    private NoteServiceImpl noteService;

    private Note testNote;
    private Author testAuthor;
    private NoteResponse testResponse;
    private UUID noteId;
    private UUID authorId;

    @BeforeEach
    void setUp() {
        noteId = UUID.randomUUID();
        authorId = UUID.randomUUID();

        testAuthor = new Author();
        testAuthor.setId(authorId);
        testAuthor.setName("Борис");
        testAuthor.setSurname("Петров");

        testNote = new Note();
        testNote.setId(noteId);
        testNote.setText("Тестовый текст");
        testNote.setStatus(Status.IN_PROGRESS);
        testNote.setAuthor(testAuthor);

        testResponse = new NoteResponse();
        testResponse.setId(noteId);
        testResponse.setText("Тестовый текст");

        lenient().when(noteMapper.toResponse(any(Note.class))).thenReturn(testResponse);
        lenient().when(noteMapper.toResponseList(anyList())).thenReturn(List.of(testResponse));
        lenient().when(noteMapper.toEntity(any(CreationNoteRequest.class))).thenReturn(testNote);

        lenient().doNothing().when(auditLoggerService).logAction(any(), any());
    }

    @Test
    @DisplayName("Создание заметки")
    void createNote() {
        CreationNoteRequest request = new CreationNoteRequest();
        request.setText("Тестовый текст");
        request.setAuthorName("Борис");
        request.setAuthorSurname("Петров");

        when(authorRepository.findByNameAndSurname(any(), any())).thenReturn(Optional.of(testAuthor));
        when(noteRepository.save(any(Note.class))).thenReturn(testNote);

        NoteResponse response = noteService.createNote(request);

        assertNotNull(response);
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    @DisplayName("Получение всех заметок")
    void getAllNotes() {
        when(noteRepository.findAll()).thenReturn(List.of(testNote));

        List<NoteResponse> responses = noteService.getAllNotes();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Получение заметки по ID")
    void getNoteById() {
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(testNote));

        NoteResponse response = noteService.getNoteById(noteId);

        assertNotNull(response);
        assertEquals(noteId, response.getId());
        verify(noteRepository, times(1)).findById(noteId);
    }

    @Test
    @DisplayName("Обновление статуса заметки")
    void updateNoteStatus() {
        UpdateNoteStatusRequest request = new UpdateNoteStatusRequest();
        request.setStatus(Status.COMPLETED);

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(testNote));
        when(noteRepository.save(any(Note.class))).thenReturn(testNote);

        NoteResponse response = noteService.updateNoteStatus(noteId, request);

        assertNotNull(response);
        verify(noteRepository, times(1)).save(testNote);
    }

    @Test
    @DisplayName("Обновление текста заметки")
    void updateNoteText() {
        UpdateNoteTextRequest request = new UpdateNoteTextRequest();
        request.setText("Новый текст");

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(testNote));
        when(noteRepository.save(any(Note.class))).thenReturn(testNote);

        NoteResponse response = noteService.updateNoteText(noteId, request);

        assertNotNull(response);
        verify(noteRepository, times(1)).save(testNote);
    }

    @Test
    @DisplayName("Удаление заметки")
    void deleteNote() {
        when(noteRepository.existsById(noteId)).thenReturn(true);
        doNothing().when(noteRepository).deleteById(noteId);

        assertDoesNotThrow(() -> noteService.deleteNote(noteId));
        verify(noteRepository, times(1)).deleteById(noteId);
    }

    @Test
    @DisplayName("Получение заметок по ID автора")
    void getNotesByAuthorId() {
        when(authorRepository.existsById(authorId)).thenReturn(true);
        when(noteRepository.findByAuthorId(authorId)).thenReturn(List.of(testNote));

        List<NoteResponse> responses = noteService.getNotesByAuthorId(authorId);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(noteRepository, times(1)).findByAuthorId(authorId);
    }

    @Test
    @DisplayName("Получение фильтрованных заметок")
    @SuppressWarnings("unchecked")
    void getFilteredNotes() {
        Page<Note> notePage = new PageImpl<>(List.of(testNote));
        when(noteRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(notePage);

        Page<NoteResponse> result = noteService.getFilteredNotes(
                Status.IN_PROGRESS, "поиск", "автор", 0, 10, "createdAt", "DESC"
        );

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(noteRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }
}