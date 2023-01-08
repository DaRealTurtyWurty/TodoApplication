package dev.turtywurty.todo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.text.WordUtils;

import com.github.dhiraj072.randomwordgenerator.RandomWordGenerator;
import com.github.dhiraj072.randomwordgenerator.datamuse.DataMuseRequest;

import dev.turtywurty.todo.models.Project;
import dev.turtywurty.todo.models.Workspace;
import dev.turtywurty.todo.util.FileUtils;
import dev.turtywurty.todo.util.JavaUtils;
import dev.turtywurty.todo.views.components.ProjectButton;
import io.github.palexdev.materialfx.factories.InsetsFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

@SuppressWarnings("deprecation")
public class TodoApp extends Application {
    @Override
    public void start(final Stage primaryStage) throws Exception {
        Font.loadFont(TodoApp.class.getClassLoader().getResourceAsStream("fonts/JetBrainsMono-SemiBold.ttf"), 16);

        final var mainPane = new BorderPane();

        final var params = getParameters();
        final var fileMenu = new Menu("File");
        final var helpMenu = new Menu("Help");

        final var restartItem = new MenuItem("Restart");
        restartItem.setOnAction(event -> {
            try {
                JavaUtils.restartApplication(params);
            } catch (final IOException exception) {
                FileUtils.createCrashLog(exception);
                System.exit(-1);
            }
        });

        fileMenu.getItems().add(restartItem);

        final var menu = new MenuBar();
        menu.getMenus().addAll(fileMenu, helpMenu);

        final var area = new VBox();

        final List<Workspace> workspaces = new ArrayList<>();

        // TODO: Testing Purposes only
        final var adjectiveRequest = new DataMuseRequest();
        adjectiveRequest.topics("Adjectives");

        final var nounRequest = new DataMuseRequest();
        nounRequest.topics("Nouns");

        for (int i = 0; i < 5; i++) {
            final var workspace = new Workspace();
            workspace.setColor(Color.rgb(ThreadLocalRandom.current().nextInt(0, 255),
                ThreadLocalRandom.current().nextInt(0, 255), ThreadLocalRandom.current().nextInt(0, 255)));

            final String adjective = WordUtils.capitalize(RandomWordGenerator.getRandomWord(adjectiveRequest));
            final String noun = WordUtils.capitalize(RandomWordGenerator.getRandomWord(nounRequest));
            workspace.setName(adjective + noun);

            workspaces.add(workspace);
        }

        for (int index = 0; index < workspaces.size(); index++) {
            final Workspace workspace = workspaces.get(index);

            final var stack = new StackPane();
            final var flow = new FlowPane(Orientation.HORIZONTAL);
            flow.setHgap(10);
            flow.setVgap(10);
            flow.setAlignment(Pos.CENTER);

            // TODO: Testing Purposes only
            for (int i = 0; i < ThreadLocalRandom.current().nextInt(1, 10); i++) {
                final var project = new Project();
                project.setColor(Color.rgb(ThreadLocalRandom.current().nextInt(0, 255),
                    ThreadLocalRandom.current().nextInt(0, 255), ThreadLocalRandom.current().nextInt(0, 255)));

                final String adjective = WordUtils.capitalize(RandomWordGenerator.getRandomWord(adjectiveRequest));
                final String noun = WordUtils.capitalize(RandomWordGenerator.getRandomWord(nounRequest));
                project.setName(adjective + noun);

                workspace.addProject(project);
            }

            workspace.getProjects().forEach(project -> {
                final var btn = new ProjectButton(project);
                btn.setOnAction(event -> mainPane.setCenter(btn.getView()));

                flow.getChildren().add(btn);
            });

            final var name = new Label(workspace.getName());
            name.setUnderline(true);
            name.setStyle("-fx-font-family: 'JetBrains Mono SemiBold';");
            name.setId("workspaceName");
            stack.getChildren().add(name);
            StackPane.setAlignment(name, Pos.TOP_CENTER);
            StackPane.setMargin(name, InsetsFactory.all(5));

            stack.getChildren().add(flow);
            StackPane.setAlignment(flow, Pos.CENTER_LEFT);
            StackPane.setMargin(flow, InsetsFactory.of(46, 20, 20, 20));

            area.getChildren().add(stack);

            if (index < workspaces.size() - 1) {
                area.getChildren().add(new Separator(Orientation.HORIZONTAL));
            }
        }

        final var mainScroller = new ScrollPane(area);
        mainScroller.setFitToWidth(true);
        mainScroller.setFitToHeight(true);

        mainPane.setTop(menu);
        mainPane.setCenter(mainScroller);
        mainPane.setId("#mainPane");

        final var scene = new Scene(mainPane, 1200, 600, Color.web("#1B232C"));
        scene.getStylesheets().add(TodoApp.class.getClassLoader().getResource("styles/home.css").toExternalForm());
        primaryStage.setTitle("Todo");
        primaryStage.getIcons().add(new Image(TodoApp.class.getClassLoader().getResourceAsStream("icon.png")));
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setAlwaysOnTop(false);
        primaryStage.centerOnScreen();

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
    }
}
