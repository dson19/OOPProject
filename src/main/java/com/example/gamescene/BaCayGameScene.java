package com.example.gamescene;

import java.util.ArrayList;
import java.util.List;

import com.example.Deck.Card;
import com.example.Player.Player;
import com.example.UI.CardView;
import com.example.UI.DealCardAnimation;
import com.example.UI.MainApplication;
import com.example.UI.MainMenu;
import com.example.UI.ShuffleEffect;
import com.example.logic.BaCayGame;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BaCayGameScene {

    private List<HBox> playerCardBoxes = new ArrayList<>();
    private int numberOfPlayers;  // lưu số người chơi

    public Scene createBaCayGameScene(Stage primaryStage, List<Player> players, boolean displayMode) {
        this.numberOfPlayers = players.size(); // gán cho biến thành viên

        StackPane root = new StackPane();
        root.setPrefSize(1920, 1080);

        AnchorPane gameLayout = new AnchorPane();

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

        // Avatar (logo hình tròn)
       Circle avatar = createAvatar(40);
        // Tên người chơi
        Label playerName = new Label(players.get(i).getName());
        playerName.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 14px;");

        // VBox chứa avatar + tên
        VBox leftBox = new VBox(5, avatar, playerName);
        leftBox.setAlignment(Pos.CENTER);

        // HBox chứa các lá bài
        HBox cardBox = new HBox( -40);
        cardBox.setAlignment(Pos.CENTER);
        playerCardBoxes.add(cardBox);

        // Thêm vào playerBox (bên trái là avatar, bên phải là bài)
        playerBox.getChildren().addAll(leftBox, cardBox);
        playerBox.setId("PlayerBox"); // Đặt ID cho playerBox để dễ dàng định dạng CSS
        // Đặt vị trí các player
        switch (i) {
            case 0 -> {
                AnchorPane.setBottomAnchor(playerBox, 20.0);
                AnchorPane.setLeftAnchor(playerBox,500.0);
                
            }
            case 1 -> {
                AnchorPane.setRightAnchor(playerBox,20.0);
                AnchorPane.setBottomAnchor(playerBox, 250.0);
                playerBox.setRotate(-90); // Xoay 90 độ để hiển thị đúng hướng
                
            }
            case 2 -> {
                AnchorPane.setTopAnchor(playerBox,20.0);
                AnchorPane.setRightAnchor(playerBox, 500.0);
                playerBox.setRotate(180); // Xoay 180 độ để hiển thị đúng hướng
            }
            case 3 -> {
                AnchorPane.setLeftAnchor(playerBox,20.0);
                AnchorPane.setTopAnchor(playerBox, 400.0);
                playerBox.setRotate(90); // Xoay -90 độ để hiển thị đúng hướng
            }
        }
        gameLayout.getChildren().add(playerBox);
    }

        // Start Game
        startButton.setOnAction(e -> {
            
            startButton.setVisible(false);
            // Tạo hiệu ứng shuffle với số lá bài là tổng số bài 
            ShuffleEffect shuffleEffect = new ShuffleEffect(root, 54); 

            shuffleEffect.startShuffle(() -> {
                // Sau khi hiệu ứng shuffle kết thúc mới chạy game
                BaCayGame game = new BaCayGame(this.numberOfPlayers);
                game.dealCards();
                
                
                DealCardAnimation dealCardAnimation = new DealCardAnimation(gameLayout,this.numberOfPlayers,3,null);
                PauseTransition pause = new PauseTransition(Duration.seconds(numberOfPlayers * 0.5));
                pause.play();
                pause.setOnFinished(eh-> {
                    for (int i = 0; i < this.numberOfPlayers; i++) {
                        List<Card> playerCards = game.getPlayerCards(i);
                        updatePlayerCards(i, playerCards, displayMode);
                    }
                    int winner = findWinner(game);
                    VBox winnerAvatar = createWinnerAvatar();
                    Label winnerLabel = new Label("Winner:  " + players.get(winner).getName());
                    winnerLabel.setStyle(
                        "-fx-background-color: linear-gradient(to bottom,rgb(255, 0, 0),rgb(255, 0, 0),rgb(230, 0, 0));" +
                        " -fx-background-radius: 15;" +
                        " -fx-border-color: white;" +
                        " -fx-border-width: 2;" +
                        " -fx-border-radius: 15;" +
                        " -fx-text-fill: white;" +
                        " -fx-font-weight: bold;" +
                        " -fx-font-size: 16;"
                    );
                    VBox winnerBox = new VBox(5, winnerAvatar, winnerLabel);
                    winnerBox.setAlignment(Pos.CENTER);
                    startButton.setText("Play Again");
                    startButton.setVisible(true);
                    AnchorPane.setBottomAnchor(winnerBox,400.0);
                    AnchorPane.setLeftAnchor(winnerBox, 500.0);
                    gameLayout.getChildren().add(winnerBox);
                    StackPane.setAlignment(startButton, Pos.BOTTOM_RIGHT);
                });
            });
        });


        root.getChildren().addAll(tableBackground, gameLayout, startButton, backButton);
        StackPane.setAlignment(backButton, Pos.TOP_RIGHT);

        return new Scene(root, 1920, 1080);
    }

    private int findWinner(BaCayGame game) {
        int winner = 0;
        for (int i = 1; i < this.numberOfPlayers; i++) {
            winner = game.compareHands(winner, i);
        }
        return winner;
    }

    private void updatePlayerCards(int playerIndex, List<Card> cards, boolean displayMode) {
        HBox cardBox = playerCardBoxes.get(playerIndex);
        cardBox.getChildren().clear();
        for (Card card : cards) {
            CardView cardView = new CardView(card, 90, 120, displayMode);
            cardBox.getChildren().add(cardView);
        }
    }
    public Circle createAvatar(float radius) {
        Image avatarImage = new Image(getClass().getResource("/com/example/Application/avatar.jpg").toExternalForm());
        Circle avatar = new Circle(radius); 
        avatar.setFill(new ImagePattern(avatarImage));
        avatar.setStroke(Color.RED); // Viền đỏ
        avatar.setStrokeWidth(3); // Gán tên người chơi vào avatar
        return avatar;
    }
    public VBox createWinnerAvatar() {
        ImageView winnerImage = new ImageView(getClass().getResource("/com/example/Application/crown.png").toExternalForm());
        winnerImage.setFitWidth(60);
        winnerImage.setFitHeight(60);
        Circle winnerAvatar = createAvatar(50);
        VBox winnerBox = new VBox(0,winnerImage,winnerAvatar);
        winnerBox.setAlignment(Pos.CENTER);
        return winnerBox;
    }   
}
