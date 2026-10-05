package com.example.practiceproject.dao;

import com.example.practiceproject.entity.Note;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class NoteNativeQueryDaoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Note> findRecentNotesNative() {
        String sql = "SELECT * FROM notes WHERE created_at >= NOW() - INTERVAL  '1 day' ";
        return entityManager.createNativeQuery(sql, Note.class)
                .getResultList();
    }

}
