package com.example.russianwordle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.LinkedHashMap;

public class SettingsScene {
    public void settingsScene(Stage stage) {
        BorderPane mainPane = new BorderPane();

        LinkedHashMap<String, String> backgroundColors = new LinkedHashMap<>(); // to keep an order

        backgroundColors.put("Обычный", "-fx-background-color : #131e26;");
        backgroundColors.put("Чёрный", "-fx-background-color : black;");
        backgroundColors.put("Оливковый", "-fx-background-color : olive;");

        VBox vBox = new VBox();

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(backgroundColors.keySet());
        comboBox.setValue(Settings.backgroundColorNamed);
        comboBox.setOnAction(_ -> {
            String selectedItem = comboBox.getSelectionModel().getSelectedItem();
            Settings.backgroundColor = backgroundColors.get(selectedItem);
            Settings.backgroundColorNamed = selectedItem;
        });

        Text backgroundColorSetting = new Text("Цвет заднего фона");
        backgroundColorSetting.setStyle("-fx-font-size: 20; -fx-fill: skyblue;");

        comboBox.setMinWidth(backgroundColorSetting.getLayoutBounds().getWidth());

        vBox.getChildren().addAll(backgroundColorSetting, comboBox);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

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

        mainPane.setCenter(vBox);
        mainPane.setBottom(hBox);
        mainPane.setStyle(Settings.backgroundColor);

        Scene scene = new Scene(mainPane, Constants.WIDTH, Constants.HEIGHT);
        mainPane.requestFocus(); // blue border on comboBox looks ugly
        URL cssPath = getClass().getResource("/styles.css");
        if (cssPath != null) scene.getStylesheets().add(cssPath.toExternalForm());
        stage.setScene(scene);
    }
}
