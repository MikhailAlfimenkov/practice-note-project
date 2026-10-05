package com.example.practiceproject.entity.joined;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
@Table(name = "joined_todo_list_notes")
public class JoinedToDoListNote extends JoinedNote {

    @Column(name = "total_tasks")
    private int totalTasks;

}
