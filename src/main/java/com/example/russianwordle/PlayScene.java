package com.example.russianwordle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;

import org.apache.commons.text.StringEscapeUtils;

public class PlayScene {
    int currAttempts = 0;
    TextField[] textFields;
    Stage stage;
    String wordToGuess;

    public void playScene(Stage stage) {
        this.stage = stage;
        BorderPane mainPane = new BorderPane();

        try {
            URL url = new URI("http://localhost:5000/generate-word").toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int response = conn.getResponseCode();
            if (response == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                wordToGuess = StringEscapeUtils.unescapeJava(in.readLine()).substring(1, 6);
            }
        } catch (Exception e) {
            System.out.printf("Cause: %s", e.getCause());
        }

        textFields = new TextField[5];
        TextFieldGenerator myGen = new TextFieldGenerator();

        VBox vBox = new VBox();
        GridPane gridPane = myGen.generate(textFields, this, vBox);
        vBox.getChildren().add(gridPane);

        HBox hBox = new HBox();

        Button backToMenu = new Button("Вернуться в меню");
        backToMenu.setOnAction(_ -> {
            Main mainInstance = new Main();
            mainInstance.start(stage);
        });
        ButtonFade ButtonFadeInstance = new ButtonFade();
        ButtonFadeInstance.make(backToMenu);

        hBox.getChildren().add(backToMenu);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setPadding(new Insets(0, 0, 50, 0));

        mainPane.setTop(vBox);
        mainPane.setBottom(hBox);
        mainPane.setStyle(Settings.backgroundColor);

        Scene scene = new Scene(mainPane, Constants.WIDTH, Constants.HEIGHT);
        URL cssPath = getClass().getResource("/styles.css");
        if (cssPath != null) scene.getStylesheets().add(cssPath.toExternalForm());
        stage.setScene(scene);
    }
}
