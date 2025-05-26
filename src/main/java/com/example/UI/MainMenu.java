package com.example.UI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainMenu {
    public Scene CreateMainMenu(Stage primaryStage) {
            //Create BackGroundBackGround
            Image BackGround = new Image(getClass().getResource("/com/example/Application/Start.png").toExternalForm());
            ImageView BackGroundView = new ImageView(BackGround);
            //Create Button Container
            Button button1 = MainApplication.createButton("Play");
            Button button2 = MainApplication.createButton("Settings");
            Button button3 = MainApplication.createButton("Quit");
            HBox hBox = new HBox(15,button1, button2, button3);
            hBox.setAlignment(Pos.CENTER);
            //Create StackPane and Scene
            StackPane pane = new StackPane(BackGroundView, hBox);
            Scene scene = new Scene(pane, 1200, 800);
            primaryStage.setScene(scene);
            
            //Set Background
            BackGroundView.fitWidthProperty().bind(scene.widthProperty());
            BackGroundView.fitHeightProperty().bind(scene.heightProperty());
            //Set Button Position
            hBox.spacingProperty().bind(scene.widthProperty().divide(30));
            //Set Button Size
            button1.prefWidthProperty().bind(scene.widthProperty().divide(5));
            button1.prefHeightProperty().bind(scene.heightProperty().divide(12));
            button2.prefWidthProperty().bind(scene.widthProperty().divide(5));
            button2.prefHeightProperty().bind(scene.heightProperty().divide(12));
            button3.prefWidthProperty().bind(scene.widthProperty().divide(5));
            button3.prefHeightProperty().bind(scene.heightProperty().divide(12));
            //Set Play Button Action
            button1.setOnAction(e -> primaryStage.setScene(new GameSelectionMenu().createGameSelectionMenu(primaryStage)));
            //Set Settings Button Action
            button2.setOnAction(e -> {
                StackPane settingsMenu = com.example.UI.SettingsMenu.createSettingsMenu(scene);
                pane.getChildren().add(settingsMenu);
            });
            //Set Quit Button Action
            button3.setOnAction(e -> primaryStage.close());
            return scene;
    }
}
