package com.example.practiceproject.dao;


import com.example.practiceproject.entity.Author;
import com.example.practiceproject.entity.Note;
import com.example.practiceproject.enums.Status;
import com.example.practiceproject.repository.AuthorRepository;
import com.example.practiceproject.repository.NoteRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class DaoPatternsTest {
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private NoteJpqlDaoImpl noteJpqlDao;
    @Autowired
    private NoteJdbcDaoImpl noteJdbcDao;
    @Autowired
    private NoteJdbcTemplateDaoImpl noteJdbcTemplateDao;
    @Autowired
    private NoteNativeQueryDaoImpl noteNativeQueryDao;
    @Autowired
    private NoteEntityGraphDaoImpl noteEntityGraphDao;
    @Autowired
    private NoteSpecificationDaoImpl noteSpecificationDao;
    @Autowired
    private NotePaginationDaoImpl notePaginationDao;

    @AfterEach
    void tearDown() {
        noteRepository.deleteAll();
        authorRepository.deleteAll();
    }

    private Note savedNote;

    @BeforeEach
    void setUp() {
        Author author = new Author();
        author.setName("тестовое имя автора");
        author.setSurname("тестовая фамилия автора");
        authorRepository.save(author);

        Note note = new Note();
        note.setStatus(Status.IN_PROGRESS);
        note.setText("тестовая заметка");
        note.setCreatedAt(Instant.now());
        note.setAuthor(author);

        this.savedNote = noteRepository.save(note);
    }

    @Test
    @DisplayName("1 plain JDBC")
    void testJdbc() {
        Optional<Note> note = noteJdbcDao.findById(savedNote.getId());
        assertThat(note).isPresent();
        assertThat(note.get().getText()).isEqualTo(savedNote.getText());
    }

    @Test
    @DisplayName("2 JDBC template")
    void testJdbcTemplate() {
        List<Note> notes = noteJdbcTemplateDao.findByStatusJdbcTemplate(Status.IN_PROGRESS);
        assertThat(notes).isNotEmpty();
    }

    @Test
    @DisplayName("3 JPQL")
    void testJpql() {
        List<Note> notes = noteJpqlDao.findByStatusJpql(Status.IN_PROGRESS);
        assertThat(notes).extracting(Note::getId).contains(savedNote.getId());
    }

    @Test
    @Transactional
    @DisplayName("4 native query")
    void testNativeQuery() {
        List<Note> notes = noteNativeQueryDao.findRecentNotesNative();
        assertThat(notes).isNotEmpty();
        Note foundNote = notes.stream()
                .filter(n -> n.getId().equals(savedNote.getId()))
                .findFirst()
                .orElseThrow();
        assertThat(foundNote.getAuthor().getName()).isEqualTo("тестовое имя автора");
    }

    @Test
    @DisplayName("5 specification DAO")
    void testSpecification() {
        List<Note> notes = noteSpecificationDao.findBystatusAndText(Status.IN_PROGRESS, "заметка");
        assertThat(notes).hasSize(1);
    }

    @Test
    @DisplayName("6 pagination DAO")
    void testPagination() {
        List<Note> page = notePaginationDao.findWithPagination(0, 10);
        assertThat(page).isNotEmpty();
    }

    @Test
    @DisplayName("7 EntityGraph DAO")
    void testEntityGraph() {
        List<Note> notes = noteEntityGraphDao.findByStatusWithAuthorEager(Status.IN_PROGRESS);
        assertThat(notes).isNotEmpty();
        assertThat(notes.get(0).getAuthor().getName()).isEqualTo("тестовое имя автора");
    }
}
