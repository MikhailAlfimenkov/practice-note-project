package com.example.practiceproject.dao;

import com.example.practiceproject.entity.Author;
import com.example.practiceproject.entity.Note;
import com.example.practiceproject.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class NoteJdbcDaoImpl {
    private final DataSource dataSource;

    public Optional<Note> findById(UUID id) {
        String sql = "SELECT id, status, author_id, created_at, complete_at, text_value FROM notes WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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
                    return Optional.of(note);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("JDBC error", e);
        }
        return Optional.empty();
    }

}

