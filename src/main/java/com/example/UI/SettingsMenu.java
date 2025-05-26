package com.example.UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SettingsMenu {
    public static StackPane createSettingsMenu(Scene scene) {
        // Create VBox for Settings
        VBox settingsMenu = new VBox(20);
        settingsMenu.setAlignment(Pos.CENTER);
        settingsMenu.setVisible(true);
        settingsMenu.setStyle(
            "-fx-background-color: rgba(255,0,0,0.8);" +
            "-fx-padding: 25;" +
            "-fx-border-color: gray;" +
            "-fx-border-width: 2;"
        );
        settingsMenu.maxWidthProperty().bind(scene.widthProperty().divide(2));
        settingsMenu.maxHeightProperty().bind(scene.heightProperty().divide(2));

        // Create CloseButton
        Button CloseButton = new Button("X");
        CloseButton.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: transparent;");
        CloseButton.setPrefSize(30, 30);
        CloseButton.setFont(javafx.scene.text.Font.font("System", javafx.scene.text.FontWeight.BOLD, 16));

        // Create Volume Slider Box with red background, no border-radius or background-radius
        HBox volumeBox = new HBox(15);
        volumeBox.setAlignment(Pos.CENTER);
        volumeBox.setStyle(
            "-fx-background-color: rgba(255,0,0,1);" +
            "-fx-padding: 10;" +
            "-fx-border-color: gray;" +
            "-fx-border-width: 2;"
        );
        volumeBox.setPrefHeight(50);

        Label volumeText = new Label("Volume");
        volumeText.setTextFill(javafx.scene.paint.Color.WHITE);
        volumeText.setStyle("-fx-font-weight: bold;");
        volumeText.setPrefWidth(70);
        volumeText.setFont(javafx.scene.text.Font.font("System", javafx.scene.text.FontWeight.BOLD, 14));

        Slider soundSlider = new Slider(0, 100, 50);
        soundSlider.setShowTickLabels(false);
        soundSlider.setShowTickMarks(false);
        soundSlider.setBlockIncrement(10);
        soundSlider.setPrefWidth(200);
        soundSlider.setStyle(
            "-fx-control-inner-background: white;" +
            "-fx-background-radius: 5;" +
            "-fx-border-radius: 5;" +
            "-fx-border-color: gray;" +
            "-fx-border-width: 1;"
        );

        Label volumeValue = new Label(String.valueOf((int) soundSlider.getValue()));
        volumeValue.setTextFill(javafx.scene.paint.Color.WHITE);
        volumeValue.setStyle("-fx-font-weight: bold;");
        volumeValue.setPrefWidth(30);
        volumeValue.setFont(javafx.scene.text.Font.font("System", javafx.scene.text.FontWeight.BOLD, 14));

        soundSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            volumeValue.setText(String.valueOf(newValue.intValue()));
        });
        soundSlider.setOnMouseDragged(e -> MainApplication.mediaPlayer.setVolume(soundSlider.getValue() / 100.0));
        volumeBox.getChildren().addAll(volumeText, soundSlider, volumeValue);
        settingsMenu.getChildren().add(volumeBox);
        Button DisplayMode = MainApplication.createButton("Image Mode");
        DisplayMode.setOnAction(e -> {
            MainApplication.displayMode = !MainApplication.displayMode;
            DisplayMode.setText(MainApplication.displayMode ? "Image Mode" : "Text Mode");
        });
        settingsMenu.getChildren().add(DisplayMode);
        // Create StackPane chứa settingsMenu và CloseButton
        StackPane settingsPane = new StackPane(settingsMenu, CloseButton);
        settingsPane.maxWidthProperty().bind(scene.widthProperty().divide(2));
        settingsPane.maxHeightProperty().bind(scene.heightProperty().divide(2));
        settingsPane.setAlignment(Pos.CENTER);

        StackPane.setAlignment(CloseButton, Pos.TOP_RIGHT);
        StackPane.setAlignment(volumeBox, Pos.TOP_CENTER);

        // Set CloseButton Action
        CloseButton.setOnAction(e -> settingsPane.setVisible(false));

        return settingsPane;
    }
}
