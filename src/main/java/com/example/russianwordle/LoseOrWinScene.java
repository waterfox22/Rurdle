package com.example.russianwordle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;

public class LoseOrWinScene {
    public void loseOrWinScene (Stage stage, String word, boolean won, int attempts) {
        BorderPane mainPane = new BorderPane();

        VBox vBox = new VBox();

        Text youLostLabel = new Text("Вы проиграли!");
        Text unguessedWord = new Text("Неугаданное слово: " + word);
        Text usedAttempts = new Text("Использовано попыток: " + attempts);

        if (won) {
            youLostLabel = new Text("Вы выиграли!");
            unguessedWord = new Text("Угаданное слово: " + word);
        }

        youLostLabel.setStyle("-fx-font-size: 20; -fx-fill: skyblue;");
        unguessedWord.setStyle("-fx-font-size: 20; -fx-fill: skyblue;");
        usedAttempts.setStyle("-fx-font-size: 20; -fx-fill: skyblue;");

        vBox.getChildren().addAll(youLostLabel, unguessedWord, usedAttempts);
        vBox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox();

        Button backToMenu = new Button("Вернуться в меню");
        backToMenu.setOnAction(_ -> {
            Main mainInstance = new Main();
            mainInstance.start(stage);
        });
        ButtonFade ButtonFadeInstance = new ButtonFade();
        ButtonFadeInstance.make(backToMenu);

        Button playAgain = new Button("Сыграть снова");
        playAgain.setOnAction(_ -> {
            PlayScene playSceneInstance = new PlayScene();
            playSceneInstance.playScene(stage);
        });
        ButtonFadeInstance.make(playAgain);

        playAgain.setMinSize(200, 30);
        backToMenu.setMinSize(200, 30);

        hBox.getChildren().addAll(playAgain, backToMenu);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(0, 0, 50, 0));
        hBox.setSpacing(20);

        mainPane.setCenter(vBox);
        mainPane.setBottom(hBox);
        mainPane.setStyle(Settings.backgroundColor);

        Scene scene = new Scene(mainPane, Constants.WIDTH, Constants.HEIGHT);
        URL cssPath = getClass().getResource("/styles.css");
        if (cssPath != null) scene.getStylesheets().add(cssPath.toExternalForm());
        stage.setScene(scene);
    }
}
