package com.example.practiceproject.controllers;

import com.example.practiceproject.dto.CreationNoteRequest;
import com.example.practiceproject.dto.NoteResponse;
import com.example.practiceproject.dto.UpdateNoteStatusRequest;
import com.example.practiceproject.dto.UpdateNoteTextRequest;
import com.example.practiceproject.enums.Status;
import com.example.practiceproject.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NoteService noteService;

    private UUID sampleId;
    private UUID sampleAuthorId;
    private NoteResponse sampleResponse;

    @BeforeEach
    void setUp() {
        sampleId = UUID.randomUUID();
        sampleAuthorId = UUID.randomUUID();

        sampleResponse = new NoteResponse();
        sampleResponse.setId(sampleId);
        sampleResponse.setText("Тестовая заметка");
        sampleResponse.setStatus(Status.IN_PROGRESS);
    }

    @Test
    @DisplayName("POST /api/notes Создание заметки")
    void createNote() throws Exception {
        CreationNoteRequest request = new CreationNoteRequest();
        request.setText("Тестовая заметка");
        request.setAuthorName("Иван");
        request.setAuthorSurname("Иванов");

        given(noteService.createNote(any(CreationNoteRequest.class))).willReturn(sampleResponse);

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleId.toString()))
                .andExpect(jsonPath("$.text").value("Тестовая заметка"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));

        verify(noteService).createNote(any(CreationNoteRequest.class));
    }

    @Test
    @DisplayName("GET /api/notes/all Получение всех заметок")
    void getAllNotes() throws Exception {
        given(noteService.getAllNotes()).willReturn(List.of(sampleResponse));

        mockMvc.perform(get("/api/notes/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleId.toString()))
                .andExpect(jsonPath("$[0].text").value("Тестовая заметка"));

        verify(noteService).getAllNotes();
    }

    @Test
    @DisplayName("GET /api/notes/{id} Получение заметки по ID")
    void getNoteById() throws Exception {
        given(noteService.getNoteById(sampleId)).willReturn(sampleResponse);

        mockMvc.perform(get("/api/notes/" + sampleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleId.toString()))
                .andExpect(jsonPath("$.text").value("Тестовая заметка"));

        verify(noteService).getNoteById(sampleId);
    }

    @Test
    @DisplayName("PATCH /api/notes/{id}/status Обновление статуса заметки")
    void updateNoteStatus() throws Exception {
        UpdateNoteStatusRequest request = new UpdateNoteStatusRequest();
        request.setStatus(Status.COMPLETED);

        sampleResponse.setStatus(Status.COMPLETED);
        given(noteService.updateNoteStatus(eq(sampleId), any(UpdateNoteStatusRequest.class))).willReturn(sampleResponse);

        mockMvc.perform(patch("/api/notes/" + sampleId + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));

        verify(noteService).updateNoteStatus(eq(sampleId), any(UpdateNoteStatusRequest.class));
    }

    @Test
    @DisplayName("PATCH /api/notes/{id}/text Обновление текста заметки")
    void updateNoteText() throws Exception {
        UpdateNoteTextRequest request = new UpdateNoteTextRequest();
        request.setText("Обновленный текст заметки");

        sampleResponse.setText("Обновленный текст заметки");
        given(noteService.updateNoteText(eq(sampleId), any(UpdateNoteTextRequest.class))).willReturn(sampleResponse);

        mockMvc.perform(patch("/api/notes/" + sampleId + "/text")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Обновленный текст заметки"));

        verify(noteService).updateNoteText(eq(sampleId), any(UpdateNoteTextRequest.class));
    }

    @Test
    @DisplayName("DELETE /api/notes/{id} Удаление заметки")
    void deleteNote() throws Exception {
        doNothing().when(noteService).deleteNote(sampleId);

        mockMvc.perform(delete("/api/notes/" + sampleId))
                .andExpect(status().isOk());

        verify(noteService).deleteNote(sampleId);
    }

    @Test
    @DisplayName("GET /api/notes/author/{authorId} Получение заметок автора")
    void getNotesByAuthorId() throws Exception {
        given(noteService.getNotesByAuthorId(sampleAuthorId)).willReturn(List.of(sampleResponse));

        mockMvc.perform(get("/api/notes/author/" + sampleAuthorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleId.toString()));

        verify(noteService).getNotesByAuthorId(sampleAuthorId);
    }

    @Test
    @DisplayName("GET /api/notes Поиск/Фильтрация заметок с пагинацией")
    void getNotes() throws Exception {
        PageImpl<NoteResponse> page = new PageImpl<>(List.of(sampleResponse), PageRequest.of(0, 10), 1);
        given(noteService.getFilteredNotes(any(), any(), any(), anyInt(), anyInt(), anyString(), anyString())).willReturn(page);

        mockMvc.perform(get("/api/notes")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(sampleId.toString()));

        verify(noteService).getFilteredNotes(any(), any(), any(), anyInt(), anyInt(), anyString(), anyString());
    }
}