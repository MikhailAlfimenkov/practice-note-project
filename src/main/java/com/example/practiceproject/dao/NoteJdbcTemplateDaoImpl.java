package com.example.practiceproject.dao;

import com.example.practiceproject.entity.Author;
import com.example.practiceproject.entity.Note;
import com.example.practiceproject.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class NoteJdbcTemplateDaoImpl {

    private final JdbcTemplate jdbcTemplate;

    public List<Note> findByStatusJdbcTemplate(Status status) {
        String sql = "SELECT id, status, author_id, created_at, complete_at, text_value FROM notes WHERE status = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Note note = new Note();
            note.setId(rs.getObject("id", UUID.class));
            note.setStatus(Status.valueOf(rs.getString("status")));

            UUID authorId = rs.getObject("author_id", UUID.class);
            if (authorId != null) {
                Author author = new Author();
                author.setId(authorId);
                note.setAuthor(author);
            }

            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt != null) {
                note.setCreatedAt(createdAt.toInstant());
            }

            Timestamp completeAt = rs.getTimestamp("complete_at");
            if (completeAt != null) {
                note.setCompletedAt(completeAt.toLocalDateTime());
            }

            note.setText(rs.getString("text_value"));
            return note;
        }, status.name());
    }


}
