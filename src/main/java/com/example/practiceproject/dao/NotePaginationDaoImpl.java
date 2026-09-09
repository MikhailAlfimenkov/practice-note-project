package com.example.practiceproject.dao;

import com.example.practiceproject.entity.Note;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class NotePaginationDaoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Note> findWithPagination(int pageNumber, int pageSize) {
        TypedQuery<Note> query = entityManager.createQuery("SELECT n FROM Note n ORDER BY n.createdAt DESC", Note.class);
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

}
