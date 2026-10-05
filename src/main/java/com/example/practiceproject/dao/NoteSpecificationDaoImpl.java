package com.example.practiceproject.dao;

import com.example.practiceproject.entity.Note;
import com.example.practiceproject.enums.Status;
import com.example.practiceproject.repository.NoteRepository;
import com.example.practiceproject.repository.NoteSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoteSpecificationDaoImpl {

    private final NoteRepository noteRepository;

    public List<Note> findBystatusAndText(Status status, String keyword) {
        Specification<Note> spec = Specification
                .where(NoteSpecification.hasStatus(status))
                .and(NoteSpecification.textContains(keyword));
        return noteRepository.findAll(spec);
    }
}
