package com.example.russianwordle;

import javafx.scene.control.Button;

public class ButtonFade {
    void make(Button btn) {
        btn.setOnMouseEntered(_ -> btn.getStyleClass().add("button-fading"));
        btn.setOnMouseExited(_ -> {
            btn.getStyleClass().clear();
            btn.getStyleClass().add("button");
        });
    }
}
