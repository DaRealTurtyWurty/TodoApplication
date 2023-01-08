package dev.turtywurty.todo.models;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.paint.Color;

public class Workspace {
    private String name = "Untitled";
    private String description = "This is a workspace";
    private String icon = "";
    private Color color = Color.BLACK;
    private final Set<Project> projects = new HashSet<>();

    public boolean addProject(Project project) {
        return this.projects.add(project);
    }

    public Color getColor() {
        return this.color;
    }

    public String getDescription() {
        return this.description;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getName() {
        return this.name;
    }

    public Set<Project> getProjects() {
        return Set.copyOf(this.projects);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
