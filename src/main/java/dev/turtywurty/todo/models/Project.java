package dev.turtywurty.todo.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

public class Project {
    public final StringProperty name = new SimpleStringProperty("Untitled");
    public final StringProperty description = new SimpleStringProperty("This is a project");
    public final StringProperty thumbnail = new SimpleStringProperty();
    public final ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.BLACK);

    public Color getColor() {
        return this.color.getValue();
    }

    public String getDescription() {
        return this.description.getValue();
    }

    public String getName() {
        return this.name.getValue();
    }

    public String getThumbnail() {
        return this.thumbnail.getValue();
    }

    public void setColor(Color color) {
        this.color.setValue(color);
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }

    public void setName(String name) {
        this.name.setValue(name);
    }
    
    public void setThumbnail(String thumbnail) {
        this.thumbnail.setValue(thumbnail);
    }
}
