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
@DiscriminatorValue("TODOLIST")
public class ToDoListNote extends SingleTableNote {

    @Column(name = "total_tasks")
    private int totalTasks;

}
