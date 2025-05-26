package com.example.UI;


import java.util.Stack;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameSelectionMenu {
    public Scene createGameSelectionMenu(Stage primaryStage) {
        StackPane root = new StackPane();
        AnchorPane Pane = new AnchorPane();
        Scene scene = new Scene(root, 1200, 800);

        // Background
        ImageView background = new ImageView(new Image(getClass().getResource("/com/example/Application/Table.png").toExternalForm()));
        background.fitWidthProperty().bind(scene.widthProperty());
        background.fitHeightProperty().bind(scene.heightProperty());
        root.getChildren().add(background);
        // Close Button (Back)
        Button closeButton = MainApplication.createButton("Back");
        closeButton.setOnAction(e -> primaryStage.setScene(new MainMenu().CreateMainMenu(primaryStage)));
        AnchorPane.setTopAnchor(closeButton,10.0);
        AnchorPane.setRightAnchor(closeButton,10.0);
        Pane.getChildren().add(closeButton);

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
        AnchorPane.setLeftAnchor(gameButtons, 250.0);
        AnchorPane.setTopAnchor(gameButtons, 150.0);
        Pane.getChildren().add(gameButtons);
        root.getChildren().add(Pane);
        // Game Button Action
        /*tlmnButton.setOnAction(e -> {
            RadioButton selectedPlayer = (RadioButton) playerToggleGroup.getSelectedToggle();
            int selectedPlayers = Integer.parseInt(selectedPlayer.getText().substring(0, 1));
            Scene tlmnScene = new TLMNGameScene().createTLMNGameScene(primaryStage, selectedPlayers, MainApplication.displayMode);
            primaryStage.setScene(tlmnScene);
        });*/
        baCayButton.setOnAction(e -> { 
            Scene addPlayerScene = new AddPlayer().createAddPlayerScene(primaryStage, "Ba Cây");
            primaryStage.setScene(addPlayerScene);
        });
        return scene;
    }
}
