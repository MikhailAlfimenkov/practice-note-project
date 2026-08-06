package com.example.practiceproject.dto;

import com.example.practiceproject.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class NoteResponse {

    private UUID id;
    private String text;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private String authorName;
    private String authorSurname;
}
