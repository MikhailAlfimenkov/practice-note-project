package com.example.practiceproject.entity;

import com.example.practiceproject.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "notes")
public class Note {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "complete_at")
    private LocalDateTime completedAt;

    @Column(name = "text_value", nullable = false, columnDefinition = "TEXT")
    private String text;


//    поля которые должны быть:
//    id
//    статус выполненного или невыполненного задания
//    когда добавлено задание
//    когда выполнено
//    автор задания
    // само задание текст


}
