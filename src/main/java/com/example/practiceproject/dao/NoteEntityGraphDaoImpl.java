package com.example.practiceproject.dao;

import com.example.practiceproject.entity.Note;
import com.example.practiceproject.enums.Status;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class NoteEntityGraphDaoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Note> findByStatusWithAuthorEager(Status status) {
        EntityGraph<Note> entityGraph = entityManager.createEntityGraph(Note.class);
        entityGraph.addAttributeNodes("author");

        return entityManager.createQuery("SELECT n FROM Note n WHERE n.status = :status", Note.class)
                .setParameter("status", status)
                .setHint("jakarta.persistence.fetchgraph", entityGraph)
                .getResultList();
    }
}
