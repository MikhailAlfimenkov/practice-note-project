package com.example.practiceproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateNoteTextRequest {
    @NotBlank(message = "Text must not be blank")
    @Size(min = 3, message = "Text must contain at least 3 characters.")
    private String text;
}
