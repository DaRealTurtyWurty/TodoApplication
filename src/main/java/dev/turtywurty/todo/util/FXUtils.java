package dev.turtywurty.todo.util;

import org.jetbrains.annotations.Nullable;

import io.github.palexdev.materialfx.font.FontResources;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public final class FXUtils {
    private FXUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }
    
    /**
     * Creates items that can be used for a sidebar.
     *
     * @param  text    - The text to display in the label
     * @param  iconRes - The icon that should be displayed
     * @return         A {@link HBox} that contains the label and font icon
     * @see            FXUtils#createItem(String, FontResources, boolean)
     */
    public static HBox createItem(final String text, final FontResources iconRes) {
        return createItem(text, iconRes, false);
    }
    
    /**
     * Creates items that can be used for a sidebar.
     *
     * @param  text     - The text to display in the label
     * @param  iconRes  - The icon that should be displayed
     * @param  disabled - Whether or not this item should start disabled
     * @return          A {@link HBox} that contains the label and font icon
     * @see             FXUtils#createItem(String, FontResources)
     */
    public static HBox createItem(final String text, @Nullable final FontResources iconRes, final boolean disabled) {
        final var box = new HBox(20);
        
        final var label = new Label(text);
        label.setTextFill(Color.web("#009AFA"));
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 16pt;");
        label.setDisable(disabled);
        
        final var icon = new MFXFontIcon(iconRes.getDescription(), 32, Color.web("#FFB940"));
        icon.setDisable(disabled);
        
        box.getChildren().addAll(icon, label);
        
        return box;
    }
}
