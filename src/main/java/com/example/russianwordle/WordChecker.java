package com.example.russianwordle;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WordChecker {
    void setActionListener(TextField[] textFields, VBox vBox, PlayScene playScene) {
        for (int i = 0; i < 5; i++) {
            textFields[i].setOnAction(_ -> {
                StringBuilder result = new StringBuilder();
                for (TextField textField : textFields) {
                    result.append(textField.getText());
                }

                if (result.length() == 5) {
                    if (result.toString().equals(playScene.wordToGuess)) {
                        LoseOrWinScene LoseSceneInstance = new LoseOrWinScene();
                        LoseSceneInstance.loseOrWinScene(playScene.stage, playScene.wordToGuess, true, playScene.currAttempts);
                    } else {
                        boolean wordExists = false;
                        try {
                            String encodedUserInput = URLEncoder.encode(result.toString(), StandardCharsets.UTF_8);
                            URL url = new URI("http://localhost:5000/word-exists/" + encodedUserInput).toURL();
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            int response = conn.getResponseCode();
                            if (response == 200) {
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String jsonString = in.readLine();
                                wordExists = Boolean.parseBoolean(jsonString);
                            }
                        } catch (Exception e) {
                            System.out.printf("Cause: %s", e.getCause());
                        }
                        if (wordExists) {
                            setFillOnTiles(getFillings(result.toString(), playScene), textFields);
                            TextFieldGenerator myGen = new TextFieldGenerator();
                            vBox.getChildren().add(myGen.generate(textFields, playScene, vBox));
                            playScene.textFields[0].requestFocus();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Такого слова нет!");
                            alert.show();
                        }
                    }
                }
            });
        }
    }

    int[] getFillings(String result, PlayScene playScene) {
        int[] filling = new int[5];
        try {
            String encodedUserInput = URLEncoder.encode(result, StandardCharsets.UTF_8);
            String encodedWord = URLEncoder.encode(playScene.wordToGuess, StandardCharsets.UTF_8);
            URL url = new URI("http://localhost:5000/check-java-input/" + encodedUserInput + "/" + encodedWord).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int response = conn.getResponseCode();
            if (response == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String jsonString = in.readLine();
                int i = 0;
                for (char x : jsonString.toCharArray()) {
                    if (Character.isDigit(x)) {
                        filling[i] = Integer.parseInt(Character.toString(x));
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.printf("Cause: %s", e.getCause());
        }
        return filling;
    }

    void setFillOnTiles(int[] fillings, TextField[] textFields) {

        for (int i = 0; i < 5; i++) {
            int result = fillings[i];
            String colorName = switch (result) {
                case 0 -> "dimgray";
                case 1 -> "darkorange";
                case 2 -> "limegreen";
                default -> "";
            };

            textFields[i].setStyle("-fx-background-color: " + colorName +
                    "; -fx-text-fill: yellow; -fx-font-size: 20; -fx-font-weight: bold;");

        }
    }
}
