package com.example.practiceproject.dao;


import com.example.practiceproject.entity.Note;
import com.example.practiceproject.enums.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class NoteJpqlDaoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Note> findByStatusJpql(Status status) {
        String jpql = "SELECT n FROM Note n WHERE n.status = :status";
        return entityManager.createQuery(jpql, Note.class)
                .setParameter("status", status)
                .getResultList();
    }
}
