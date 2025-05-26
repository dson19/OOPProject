package com.example.UI;

import com.example.BaCay.BaCayGame;
import com.example.Deck.Card;
import com.example.Player.Player;
import com.example.TLMN.SetGame;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TLMNGameScene {
    private List<HBox> playerCardBoxes = new ArrayList<>();
    private int numberOfPlayers;  // lưu số người chơi
    public Scene createTLMNGameScene(Stage primaryStage, int numberOfPlayers, boolean displayMode) {
        this.numberOfPlayers = numberOfPlayers; // gán cho biến thành viên

        StackPane root = new StackPane();
        root.setPrefSize(1920, 1080);

        BorderPane gameLayout = new BorderPane();

        // Background bàn chơi
        ImageView tableBackground = new ImageView(getClass().getResource("/com/example/Application/TableInGame.png").toExternalForm());
        tableBackground.fitWidthProperty().bind(root.widthProperty());
        tableBackground.fitHeightProperty().bind(root.heightProperty());

        // Nút Back
        Button backButton = MainApplication.createButton("Back");
        backButton.setOnAction(e -> primaryStage.setScene(new com.example.UI.MainMenu().CreateMainMenu(primaryStage)));

        // StartGame Button
        Button startButton = MainApplication.createButton("Start Game");

        // Initialize player boxes
        for (int i = 0; i < numberOfPlayers; i++) {
        HBox playerBox = new HBox(10);
        playerBox.setAlignment(Pos.CENTER);

        
        // Tên người chơi
        Label playerName = new Label("Player " + (i + 1));
        playerName.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 14px;");

        // VBox chứa avatar + tên

        // HBox chứa các lá bài
        HBox cardBox = new HBox(5);
        cardBox.setAlignment(Pos.CENTER);
        playerCardBoxes.add(cardBox);

        // Đặt vị trí các player
        switch (i) {
            case 0 -> gameLayout.setBottom(playerBox);
            case 1 -> {
                gameLayout.setRight(playerBox);
                playerBox.setRotate(90); // Xoay 90 độ để hiển thị đúng hướng
            }
            case 2 -> {
                gameLayout.setTop(playerBox);
                playerBox.setRotate(180); // Xoay 180 độ để hiển thị đúng hướng
            }
            case 3 -> {
                gameLayout.setLeft(playerBox);
                playerBox.setRotate(-90); // Xoay -90 độ để hiển thị đúng hướng
            }
        }
    }

        // Start Game
        startButton.setOnAction(e -> {
            startButton.setVisible(false);
            // Tạo hiệu ứng shuffle với số lá bài là tổng số bài (ví dụ 21 lá)
            ShuffleEffect shuffleEffect = new ShuffleEffect(root, 21); 

            shuffleEffect.startShuffle(() -> {
                // Sau khi hiệu ứng shuffle kết thúc mới chạy game
                SetGame tlmnGame = new SetGame();
                tlmnGame.dealCards();
                for (int i=0;i<this.numberOfPlayers;i++){
                    Player player = tlmnGame.getPlayers().get(i);
                    player.sortHand();
                    updatePlayerCards(i, player.getHand(), displayMode);
                }
            });
        });
        root.getChildren().addAll(tableBackground, gameLayout, startButton, backButton);
        StackPane.setAlignment(backButton, Pos.TOP_RIGHT);
        return new Scene(root, 1200, 800);
    }

    private void updatePlayerCards(int playerIndex, List<Card> cards, boolean displayMode) {
        HBox cardBox = playerCardBoxes.get(playerIndex);
        cardBox.getChildren().clear();
        for (Card card : cards) {
            CardView cardView = new CardView(card, 60, 90, displayMode);
            cardBox.getChildren().add(cardView);
        }
        DealCardAnimation.play(cardBox, cards, displayMode, null);
    }
}
