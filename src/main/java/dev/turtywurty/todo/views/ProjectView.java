package dev.turtywurty.todo.views;

import dev.turtywurty.todo.models.Project;
import javafx.scene.layout.BorderPane;

public class ProjectView extends BorderPane {
    private final Project project;
    
    public ProjectView(Project project) {
        this.project = project;
    }
}
