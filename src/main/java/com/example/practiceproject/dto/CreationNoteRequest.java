package com.example.practiceproject.dto;

import com.example.practiceproject.entity.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreationNoteRequest {


    @NotBlank(message = "Text must not be blank")
    @Size(min = 3, message = "Text must contain at least 3 characters.")
    private String text;

    @NotBlank(message = "Authors name must not be blank")
    private String authorName;

    @NotBlank(message = "Authors surname must not be blank")
    private String authorSurname;

}


