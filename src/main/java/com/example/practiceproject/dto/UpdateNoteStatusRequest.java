package com.example.practiceproject.dto;

import com.example.practiceproject.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateNoteStatusRequest {
    @NotBlank(message = "Status must not be blank")
    private Status status;
}
