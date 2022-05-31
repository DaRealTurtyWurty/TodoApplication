package dev.turtywurty.todo;

import io.github.palexdev.materialfx.factories.InsetsFactory;
import io.github.palexdev.materialfx.font.FontResources;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TodoApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        final var left = new VBox(20);
        left.setAlignment(Pos.CENTER);
        left.setStyle("-fx-background-color: #414953;");
        left.setPrefWidth(250);
        
        final var title = new Label("Todo App");
        // title.setUnderline(true);
        title.setTextFill(Color.web("#009AFA"));
        title.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 32));
        left.getChildren().add(title);

        final var start = createItem("Start", FontResources.HOME, true);
        final var start1 = createItem("Start1", FontResources.CHART_PIE, false);
        final var start2 = createItem("Start2", FontResources.CSS, false);
        final var start3 = createItem("Start3", FontResources.DELETE, false);
        final var start4 = createItem("Start4", FontResources.FIT, false);
        final var start5 = createItem("Start5", FontResources.GOOGLE_DRIVE, false);
        final var start6 = createItem("Start6", FontResources.EYE, false);
        final var start7 = createItem("Start7", FontResources.MESSAGE, false);
        final var start8 = createItem("Start8", FontResources.BARS, false);
        final var start9 = createItem("Start9", FontResources.BELL, false);
        final var start10 = createItem("Start10", FontResources.BELL_ALT, false);
        final var start11 = createItem("Start11", FontResources.CALENDAR_ALT_DARK, false);
        final var start12 = createItem("Start12", FontResources.CALENDAR_ALT_LIGHT, false);

        final var sideContent = new VBox(20, start, start1, start2, start3, start4, start5, start6, start7, start8,
            start9, start10, start11, start12);
        sideContent.setFillWidth(true);
        sideContent.setMinWidth(190);
        sideContent.setPadding(InsetsFactory.all(20));
        
        sideContent.getChildren().stream().filter(Region.class::isInstance).map(Region.class::cast)
            .forEach(node -> node.minWidthProperty().bind(sideContent.minWidthProperty()));
        
        final var sideScroller = new ScrollPane(sideContent);
        sideScroller.setHbarPolicy(ScrollBarPolicy.NEVER);
        sideScroller.setPrefHeight(660);
        sideScroller.getStyleClass().add("edge-to-edge");
        sideScroller.setStyle("-fx-background: #6A737E;");
        
        left.getChildren().add(sideScroller);
        
        final var mainPane = new BorderPane();
        mainPane.setLeft(left);
        mainPane.setStyle("-fx-background-color: #1B232C;");
        
        final var scene = new Scene(mainPane, 1200, 800, Color.web("#1B232C"));
        primaryStage.setTitle("Todo");
        primaryStage.getIcons().add(new Image(TodoApp.class.getClassLoader().getResourceAsStream("icon.png")));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(false);
        primaryStage.centerOnScreen();
        
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
    }
    
    private static HBox createItem(String text, FontResources iconRes) {
        return createItem(text, iconRes, false);
    }
    
    private static HBox createItem(String text, FontResources iconRes, boolean disabled) {
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
