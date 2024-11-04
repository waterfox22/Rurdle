package com.example.russianwordle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TextFieldGenerator {
    GridPane generate(TextField[] textFields, PlayScene playScene, VBox vBox) {
        if (playScene.currAttempts == Constants.MAX_ATTEMPTS) {
            LoseOrWinScene LoseSceneInstance = new LoseOrWinScene();
            LoseSceneInstance.loseOrWinScene(playScene.stage, playScene.wordToGuess, false, playScene.currAttempts);
        }

        GridPane gridPane = new GridPane();

        for (int i = 0; i < 5; i++) {
            if (textFields[i] != null) textFields[i].setEditable(false);
            textFields[i] = new TextField();
            textFields[i].setMinSize(80, 80);
            textFields[i].setStyle("-fx-font-size: 20; -fx-background-color: #0078D7; -fx-text-fill: yellow; -fx-font-weight: bold;");
            textFields[i].setAlignment(Pos.CENTER);

            int finalI = i;

            textFields[i].textProperty().addListener((_, oldValue, newValue) -> {
                if (newValue.length() > 1) {
                    textFields[finalI].setText(oldValue);
                } else if (!newValue.isEmpty()) {
                    if (newValue.matches("[А-Яа-яЁё]+")) {
                        textFields[finalI].setText(newValue.toUpperCase());
                        if (finalI < 4) textFields[finalI+1].requestFocus();
                    } else {
                        textFields[finalI].setText(oldValue);
                    }
                } else {
                    if (finalI != 0) textFields[finalI-1].requestFocus();
                }
            });

            textFields[i].addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if ((event.getCode() == KeyCode.LEFT ||
                        (event.getCode() == KeyCode.BACK_SPACE && textFields[finalI].getText().isEmpty()))
                                && finalI != 0) {
                    textFields[finalI-1].requestFocus();
                }
                if (event.getCode() == KeyCode.RIGHT && finalI < 4) textFields[finalI+1].requestFocus();
            });

            gridPane.add(textFields[i], i, 0);
        }

        gridPane.setPadding(new Insets(10, 10, 0, 10));
        gridPane.setHgap(20);

        WordChecker wordChecker = new WordChecker();
        wordChecker.setActionListener(textFields, vBox, playScene);

        playScene.currAttempts += 1;

        return gridPane;
    }
}
