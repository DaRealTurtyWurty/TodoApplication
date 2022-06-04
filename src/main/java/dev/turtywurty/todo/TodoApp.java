package dev.turtywurty.todo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;

import io.github.palexdev.materialfx.font.FontResources;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TodoApp extends Application {
	private static void createCrashLog(final Throwable... throwables) {
		try {
			var calendar = Calendar.getInstance();
			var year = calendar.get(Calendar.YEAR);
			var month = calendar.get(Calendar.MONTH) + 1;
			var day = dayToRealDay(calendar.get(Calendar.DAY_OF_WEEK));
			var hour = calendar.get(Calendar.HOUR_OF_DAY);
			var minute = calendar.get(Calendar.MINUTE);
			var second = calendar.get(Calendar.SECOND);
			var millisecond = calendar.get(Calendar.MILLISECOND);
			var location = Path.of("crash_log-" + year + "-" + month + "-" + day + "." + hour + "-" + minute + "-"
					+ second + "." + millisecond + ".log");
			var file = Files.createFile(location);
			for (Throwable throwable : throwables) {
				var asStr = ExceptionUtils.getMessage(throwable) + "\n" + ExceptionUtils.getStackTrace(throwable)
						+ "\n\n";
				Files.write(file, asStr.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	private static HBox createItem(final String text, final FontResources iconRes) {
		return createItem(text, iconRes, false);
	}

	private static HBox createItem(final String text, final FontResources iconRes, final boolean disabled) {
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

	private static int dayToRealDay(final int day) {
		if (day == 0)
			return 12;

		return day;
	}

	private static void restartApplication(@NotNull final Parameters params) throws IOException {
		Platform.exit();
		new ProcessBuilder(ProcessHandle.current().info().commandLine().orElse("")).start();
		System.exit(0);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		var params = getParameters();
		var fileMenu = new Menu("File");
		var helpMenu = new Menu("Help");

		var restartItem = new MenuItem("Restart");
		restartItem.setOnAction(event -> {
			try {
				restartApplication(params);
			} catch (IOException exception) {
				createCrashLog(exception);
				System.exit(-1);
			}
		});

		fileMenu.getItems().add(restartItem);

		var menu = new MenuBar();
		menu.getMenus().addAll(fileMenu, helpMenu);

		var grid = new FlowPane(Orientation.HORIZONTAL);
		for (var i = 0; i < 100; i++) {
			var btn = new Button("bruh");
			btn.setMinWidth(250);
			btn.setMinHeight(150);
			grid.getChildren().add(btn);
		}

		var mainScroller = new ScrollPane(grid);
		mainScroller.setFitToWidth(true);
		mainScroller.setFitToHeight(true);

		final var mainPane = new BorderPane();
		mainPane.setTop(menu);
		mainPane.setCenter(mainScroller);
		mainPane.setStyle("-fx-background-color: #1B232C;");

		final var scene = new Scene(mainPane, 1200, 600, Color.web("#1B232C"));
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
}
