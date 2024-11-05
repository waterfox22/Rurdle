package com.example.russianwordle;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        BorderPane mainPane = new BorderPane();

        Text rurdleLabel = new Text("Rurdle");
        rurdleLabel.setStyle("-fx-font-size : 75; " +
                "-fx-fill: linear-gradient(from 0% 0% to 100% 100%, mintcream, blue, red);");

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(rurdleLabel);
        stackPane.setAlignment(Pos.TOP_CENTER);
        stackPane.setPadding(new Insets(10, 0, 0, 0));

        VBox vPane = new VBox();

        Button playButton = new Button("Играть");
        Button settingsButton = new Button("Настройки");
        Button exitButton = new Button("Выход из игры");
        playButton.setMinSize(300, 50);
        settingsButton.setMinSize(300, 50);
        exitButton.setMinSize(300, 50);

        ButtonFade ButtonFadeInstance = new ButtonFade();

        playButton.setOnAction(_ -> {
            PlayScene playSceneInstance = new PlayScene();
            playSceneInstance.playScene(stage);
        });
        ButtonFadeInstance.make(playButton);

        settingsButton.setOnAction(_ -> {
            SettingsScene settingsSceneInstance = new SettingsScene();
            settingsSceneInstance.settingsScene(stage);
        });
        ButtonFadeInstance.make(settingsButton);

        exitButton.setOnAction(_ -> System.exit(1));
        ButtonFadeInstance.make(exitButton);

        vPane.getChildren().addAll(playButton, settingsButton, exitButton);
        vPane.setAlignment(Pos.BASELINE_CENTER);
        vPane.setSpacing(10);
        vPane.setPadding(new Insets(165, 0, 0, 0));

        mainPane.setTop(stackPane);
        mainPane.setCenter(vPane);
        mainPane.setStyle(Settings.backgroundColor);

        VBox vPane2 = new VBox();
        Text githubLink = new Text("https://github.com/waterfox22");
        Text version = new Text("ver 1.0");
        githubLink.setStyle("-fx-font-size: 20; -fx-fill: skyblue;");
        version.setStyle("-fx-font-size: 20; -fx-fill: skyblue;");
        vPane2.getChildren().addAll(githubLink, version);
        vPane2.setAlignment(Pos.BASELINE_CENTER);
        vPane2.setPadding(new Insets(0, 0, 50, 0));
        mainPane.setBottom(vPane2);

        Scene scene = new Scene(mainPane, Constants.WIDTH, Constants.HEIGHT);

        URL cssPath = getClass().getResource("/styles.css");
        if (cssPath != null) scene.getStylesheets().add(cssPath.toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Rurdle");
        stage.show();
    }

    public static void main(String[] args) {
        String flaskServer = "main.py";
        ProcessBuilder processBuilder = new ProcessBuilder("python", flaskServer);
        processBuilder.directory(new File("src/main/resources"));
        try {
            Process process = processBuilder.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                process.destroy();
                System.out.println("flask stopped");
            }));
            System.out.println("flask started");
        } catch (IOException e) {
            System.out.printf("Cause: %s\n", e.getCause());
        }

        launch(args);
    }
}