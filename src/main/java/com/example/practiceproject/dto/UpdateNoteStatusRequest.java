package com.example.practiceproject.dto;

import com.example.practiceproject.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateNoteStatusRequest {
    @NotNull(message = "Status must not be null")
    private Status status;
}
