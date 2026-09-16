package com.example.practiceproject.entity.singletable;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("TEXT")
public class TextNote extends SingleTableNote {

    @Column(name = "words_count")
    private int wordsCount;

}

