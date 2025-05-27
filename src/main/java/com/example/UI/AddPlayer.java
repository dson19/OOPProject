package com.example.UI;

import java.util.ArrayList;
import java.util.List;

import com.example.Player.Player;
import com.example.gamescene.BaCayGameScene;
import com.example.gamescene.TLMNGameScene;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddPlayer {
    private VBox playerInputBox;
    public Scene createAddPlayerScene(Stage primaryStage,String gameName)
    {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 1200, 800);
        // Background
        ImageView background = new ImageView(getClass().getResource("/com/example/Application/Table.png").toExternalForm());
        background.fitHeightProperty().bind(scene.heightProperty());
        background.fitWidthProperty().bind(scene.widthProperty());
        root.getChildren().add(background);

        //Back Button
        Button backButton = MainApplication.createButton("Back");
        backButton.setOnAction(e -> primaryStage.setScene(new GameSelectionMenu().createGameSelectionMenu(primaryStage)));
        AnchorPane.setTopAnchor(backButton, 10.0);
        AnchorPane.setRightAnchor(backButton, 10.0);
        root.getChildren().add(backButton);

        // Choose number of players
        Button twoPlayers = MainApplication.createButton("2 Players");
        Button threePlayers = MainApplication.createButton("3 Players");
        Button fourPlayers = MainApplication.createButton("4 Players");
        twoPlayers.setPrefSize(100, 20);
        threePlayers.setPrefSize(100, 20);
        fourPlayers.setPrefSize(100, 20);
        HBox playerSelection = new HBox(20, twoPlayers, threePlayers, fourPlayers);
        AnchorPane.setTopAnchor(playerSelection, 50.0);
        AnchorPane.setLeftAnchor(playerSelection, 400.0);
        root.getChildren().add(playerSelection);
        
        // Set action for player selection buttons
        twoPlayers.setOnAction(e -> showPlayerInput(root, 2, primaryStage, gameName));
        threePlayers.setOnAction(e -> showPlayerInput(root, 3, primaryStage, gameName));
        fourPlayers.setOnAction(e -> showPlayerInput(root, 4, primaryStage, gameName));

        //Start Game Button
        Button startGameButton = MainApplication.createButton("Start Game");
        startGameButton.setOnAction(e -> {
        List<Player> players = new ArrayList<>();
        int playerID = 0;

        int humanCount = 0;
        if (playerInputBox != null) {
            humanCount = playerInputBox.getChildren().size();
            for (int i = 0; i < humanCount; i++) {
                HBox row = (HBox) playerInputBox.getChildren().get(i);
                TextField nameField = (TextField) row.getChildren().get(0);
                String playerName = nameField.getText();
                players.add(new Player(playerID++, playerName));
            }
        } else {
            // Không có input box (mặc định 2 người)
            humanCount = 2;
            for (int i = 0; i < humanCount; i++) {
                players.add(new Player(playerID++));
            }
        }

        if (gameName.equals("Ba Cây")) {
            // Không thêm bot
            Scene gameScene = new BaCayGameScene().createBaCayGameScene(primaryStage, players, MainApplication.displayMode);
            primaryStage.setScene(gameScene);
        } else if (gameName.equals("TLMN")) {
            // Thêm bot nếu chưa đủ 4 người
            int botCount = 4 - humanCount;
            for (int i = 0; i < botCount; i++) {
                players.add(new Player(playerID++, "Bot " + (i), true));
            }
            Scene gameScene = new TLMNGameScene().createTLMNGameScene(primaryStage, players, MainApplication.displayMode);
            primaryStage.setScene(gameScene);
        }
        });
        AnchorPane.setBottomAnchor(startGameButton, 20.0);
        AnchorPane.setRightAnchor(startGameButton, 50.0);
        root.getChildren().add(startGameButton);
        return scene;
    }

    private void showPlayerInput(AnchorPane root, int playerCount, Stage primaryStage,String gameName) {
        if (playerInputBox != null) {
            root.getChildren().remove(playerInputBox);
        }
        playerInputBox = new VBox(15);
        String style = "-fx-background-color: rgb(255, 0, 0);" +
                " -fx-background-radius: 15;" +
                " -fx-border-color: white;" +
                " -fx-border-width: 2;" +
                " -fx-border-radius: 15;" +
                " -fx-text-fill: white;" +
                " -fx-font-weight: bold;" +
                " -fx-font-size: 16;";
    
        playerInputBox.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(playerInputBox, 150.0);
        AnchorPane.setLeftAnchor(playerInputBox, 450.0);
        root.getChildren().add(playerInputBox);

        List<TextField> playerNameFields = new ArrayList<>();
        for (int i = 1; i <= playerCount; i++) {
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);
            TextField nameField = new TextField("Player " + i);
            nameField.setStyle(style);
            playerNameFields.add(nameField);
            if (gameName.equals("TLMN"))
            {
                Button setAsBot = MainApplication.createButton("Set as Bot");
            }
            row.getChildren().add(nameField);
            playerInputBox.getChildren().add(row);
        }
    }
}
