package com.example.practiceproject.repository;

import com.example.practiceproject.entity.Note;
import com.example.practiceproject.enums.Status;
import org.springframework.data.jpa.domain.Specification;

public class NoteSpecification {
    public static Specification<Note> hasStatus(Status status) {
        return (root, query, criteriaBuilder) -> status == null ? null
                : criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Note> textContains(String keyword) {
        return (root, query, criteriaBuilder) -> (keyword == null || keyword.isBlank()) ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("text")), "%" + keyword.toLowerCase() + "%");
    }

    public static Specification<Note> hasAuthorNameOrSurname(String authorQuery) {
        return (root, query, criteriaBuilder) -> {
            if (authorQuery == null) {
                return criteriaBuilder.conjunction();
            }
            String pattern = "%" + authorQuery.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("author").get("name")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("author").get("surname")), pattern)
            );
        };
    }
}
