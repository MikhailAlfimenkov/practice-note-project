package com.example.practiceproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "authors")
public class Author {

    @Id
    private UUID id;

    @Column(name = "author_name", nullable = false)
    private String name;

    @Column(name = "author_surname", nullable = false)
    private String surname;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Note> note = new ArrayList<>();

//    должны быть колонки:
//    id
//    имя
//    фамилия
//    список notes one to many
//


}
