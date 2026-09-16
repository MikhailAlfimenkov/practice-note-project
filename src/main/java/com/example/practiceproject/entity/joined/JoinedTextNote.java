package com.example.practiceproject.entity.joined;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
@Table(name = "joined_text_notes")
public class JoinedTextNote extends JoinedNote {

    @Column(name = "words_count", nullable = false)
    private int wordsCount;

}
