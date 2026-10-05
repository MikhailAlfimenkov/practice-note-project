package com.example.practiceproject.entity.tableperclass;


import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
@Table(name = "table_per_class_text_notes")
public class TablePerClassTextNote extends TablePerClassNote {

    @Column(name = "words_count", nullable = false)
    private int wordsCount;

}
