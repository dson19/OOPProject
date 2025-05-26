package com.example.UI;

import com.example.TLMN.GameLogic;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameSelectionMenu {
    public Scene createGameSelectionMenu(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1200, 800);

        // Background
        ImageView background = new ImageView(new Image(getClass().getResource("/com/example/Application/Table.png").toExternalForm()));
        background.fitWidthProperty().bind(scene.widthProperty());
        background.fitHeightProperty().bind(scene.heightProperty());
        root.getChildren().add(background);

        // Close Button (Back)
        Button closeButton = MainApplication.createButton("Back");
        closeButton.setOnAction(e -> primaryStage.setScene(new MainMenu().CreateMainMenu(primaryStage)));
        BorderPane.setAlignment(closeButton, javafx.geometry.Pos.TOP_RIGHT);
        root.setTop(closeButton);

        // Game Buttons
        Button tlmnButton = MainApplication.createButton("TLMN");
        tlmnButton.setContentDisplay(ContentDisplay.TOP);
        ImageView tlmnImage = new ImageView(new Image(getClass().getResource("/com/example/Application/TLMN.png").toExternalForm()));
        tlmnImage.fitWidthProperty().bind(scene.widthProperty().divide(4));
        tlmnImage.fitHeightProperty().bind(scene.heightProperty().divide(2));
        tlmnButton.setGraphic(tlmnImage);

        Button baCayButton = MainApplication.createButton("Ba Cây");
        baCayButton.setContentDisplay(ContentDisplay.TOP);
        ImageView baCayImage = new ImageView(new Image(getClass().getResource("/com/example/Application/TLMN.png").toExternalForm()));
        baCayImage.fitWidthProperty().bind(scene.widthProperty().divide(4));
        baCayImage.fitHeightProperty().bind(scene.heightProperty().divide(2));
        baCayButton.setGraphic(baCayImage);

        HBox gameButtons = new HBox(20, baCayButton, tlmnButton);
        gameButtons.setAlignment(javafx.geometry.Pos.CENTER);
        root.setCenter(gameButtons);

        // Player Selection
        Label playerNumberLabel = new Label("Choose Number of Players:");
        playerNumberLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        RadioButton twoPlayers = new RadioButton("2 Players");
        RadioButton threePlayers = new RadioButton("3 Players");
        RadioButton fourPlayers = new RadioButton("4 Players");

        String radioStyle = "-fx-font-size: 16px; " +
                            "-fx-text-fill: white; " +
                            "-fx-background-color: #C62828; " +
                            "-fx-background-radius: 10; " +
                            "-fx-border-radius: 10; " +
                            "-fx-padding: 8 16; " +
                            "-fx-border-color: white; " +
                            "-fx-border-width: 2;";
        twoPlayers.setStyle(radioStyle);
        threePlayers.setStyle(radioStyle);
        fourPlayers.setStyle(radioStyle);

        ToggleGroup playerToggleGroup = new ToggleGroup();
        twoPlayers.setToggleGroup(playerToggleGroup);
        threePlayers.setToggleGroup(playerToggleGroup);
        fourPlayers.setToggleGroup(playerToggleGroup);
        twoPlayers.setSelected(true);

        HBox playerOptions = new HBox(15, twoPlayers, threePlayers, fourPlayers);
        playerOptions.setAlignment(javafx.geometry.Pos.CENTER);

        VBox playerSelectionBox = new VBox(10, playerNumberLabel, playerOptions);
        playerSelectionBox.setAlignment(javafx.geometry.Pos.CENTER);
        BorderPane.setAlignment(playerSelectionBox, javafx.geometry.Pos.CENTER);
        root.setBottom(playerSelectionBox);

        // Game Button Action
        tlmnButton.setOnAction(e -> {
            RadioButton selectedPlayer = (RadioButton) playerToggleGroup.getSelectedToggle();
            int selectedPlayers = Integer.parseInt(selectedPlayer.getText().substring(0, 1));
            GameLogic gameLogic = new GameLogic(selectedPlayers);
            gameLogic.startGame();
            TLMNGameScene tlmnGameScene = new TLMNGameScene(gameLogic);
            Scene tlmnScene = tlmnGameScene.createTLMNGameScene(primaryStage, selectedPlayers, MainApplication.displayMode);
            primaryStage.setScene(tlmnScene);
        });
        baCayButton.setOnAction(e -> {
            RadioButton selectedPlayer = (RadioButton) playerToggleGroup.getSelectedToggle();
            int selectedPlayers = Integer.parseInt(selectedPlayer.getText().substring(0, 1)); // Parse number
            Scene baCayScene = new BaCayGameScene().createBaCayGameScene(primaryStage, selectedPlayers, MainApplication.displayMode);
            primaryStage.setScene(baCayScene);
        });
        return scene;
    }
}
