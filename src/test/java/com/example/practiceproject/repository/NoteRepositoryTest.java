package com.example.practiceproject.repository;

import com.example.practiceproject.entity.Author;
import com.example.practiceproject.entity.Note;
import com.example.practiceproject.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Author savedAuthor;
    private Note savedNote;

    @BeforeEach
    void setUp() {
        noteRepository.deleteAll();
        authorRepository.deleteAll();

        Author author = new Author();
        author.setName("Иван");
        author.setSurname("Иванов");
        savedAuthor = authorRepository.save(author);

        Note note = new Note();
        note.setText("Интеграционный тест");
        note.setStatus(Status.IN_PROGRESS);
        note.setAuthor(savedAuthor);
        savedNote = noteRepository.save(note);
    }

    @Test
    @DisplayName("DAO: Поиск заметок по ID автора")
    void findByAuthorId() {
        List<Note> notes = noteRepository.findByAuthorId(savedAuthor.getId());

        assertNotNull(notes);
        assertEquals(1, notes.size());
        assertEquals("Иван", notes.get(0).getAuthor().getName());
    }

    @Test
    @DisplayName("DAO: Фильтрация через Specification")
    void findAll_WithSpecification() {
        Specification<Note> spec = Specification
                .where(NoteSpecification.hasStatus(Status.IN_PROGRESS))
                .and(NoteSpecification.textContains("Интеграционный"));

        Page<Note> page = noteRepository.findAll(spec, PageRequest.of(0, 10));

        assertNotNull(page);
        assertEquals(1, page.getTotalElements());
        assertEquals(savedNote.getId(), page.getContent().get(0).getId());
    }
}