package com.example.practiceproject.entity.tableperclass;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
@Table(name = "table_per_class_todo_list_notes")
public class TablePerClassToDoListNote extends TablePerClassNote {

    @Column(name = "total_tasks", nullable = false)
    private int totalTasks;

}
