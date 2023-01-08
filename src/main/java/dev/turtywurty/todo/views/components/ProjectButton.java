package dev.turtywurty.todo.views.components;

import dev.turtywurty.todo.models.Project;
import dev.turtywurty.todo.views.ProjectView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.text.Font;

public class ProjectButton extends MFXButton {
    private final Project project;
    private final ProjectView view;
    
    public ProjectButton(Project project) {
        super(project.getName(), 250, 100);
        this.project = project;
        
        setMinWidth(getPrefWidth());
        setMaxWidth(getPrefWidth());
        setMinHeight(getPrefHeight());
        setMinHeight(getPrefHeight());
        
        textProperty().bind(this.project.name);

        setFont(Font.font("JetBrains Mono SemiBold", 14));
        
        this.view = new ProjectView(this.project);
    }

    public Project getProject() {
        return this.project;
    }

    public ProjectView getView() {
        return this.view;
    }
}
