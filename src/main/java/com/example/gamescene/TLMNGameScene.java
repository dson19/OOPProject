package com.example.gamescene;

import java.util.ArrayList;
import java.util.List;

import com.example.Deck.Card;
import com.example.Deck.Deck;
import com.example.Player.Player;
import com.example.TLMN.Rules;
import com.example.UI.CardView;
import com.example.UI.DealCardAnimation;
import com.example.UI.DisplayPlayerBox;
import com.example.UI.MainApplication;
import com.example.UI.MainMenu;
import com.example.UI.ShuffleEffect;
import com.example.logic.BaCayGame;
import com.example.logic.TLMNGame;

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
public class TLMNGameScene {
    private List<HBox> playerCardBoxes = new ArrayList<>();
    private int numberOfPlayers;  // lưu số người chơi
    private boolean[] checkTurn;
    private int startTurn;
    private List<Player> players;
    private Deck deck;
    private Rules rules;
    private ArrayList<Card> cardInTable = new ArrayList<>();
    private ArrayList<Integer> playerEndGame = new ArrayList<>();
    private DisplayPlayerBox player0, player1, player2, player3;
    public Scene createTLMNGameScene(Stage primaryStage, List<Player> players, boolean displayMode) {
        this.numberOfPlayers = players.size();

        StackPane root = new StackPane();
        root.setPrefSize(1920, 1080);

        AnchorPane gameLayout = new AnchorPane();

        ImageView tableBackground = new ImageView(getClass().getResource("/com/example/Application/TableInGame.png").toExternalForm());
        tableBackground.fitWidthProperty().bind(root.widthProperty());
        tableBackground.fitHeightProperty().bind(root.heightProperty());

        Button backButton = MainApplication.createButton("Back");
        backButton.setOnAction(e -> primaryStage.setScene(new MainMenu().CreateMainMenu(primaryStage)));

        Button startButton = MainApplication.createButton("Start Game");
        
        startButton.setOnAction(e -> {
            startButton.setVisible(false);
            ShuffleEffect shuffleEffect = new ShuffleEffect(root, 52);
            
            shuffleEffect.startShuffle(() -> {
                TLMNGame game = new TLMNGame(players);
                game.dealCards();
                DealCardAnimation dealCardAnimation = new DealCardAnimation(gameLayout,this.numberOfPlayers,13,null);
                PauseTransition pause = new PauseTransition(Duration.seconds(numberOfPlayers * 2));
                pause.play();
                pause.setOnFinished(event -> {
                            player0 = new DisplayPlayerBox(players.get(0));
                            player1 = new DisplayPlayerBox(players.get(1));
                            player2 = new DisplayPlayerBox(players.get(2));
                            player3 = new DisplayPlayerBox(players.get(3));
                            AnchorPane.setBottomAnchor(player0, 20.0);
                            AnchorPane.setLeftAnchor(player0, 500.0);
                            gameLayout.getChildren().add(player0);
                            AnchorPane.setBottomAnchor(player1, 500.0);
                            AnchorPane.setRightAnchor(player1, 20.0);
                            player1.setRotate(-90);
                            gameLayout.getChildren().add(player1);
                            AnchorPane.setTopAnchor(player2, 20.0);
                            AnchorPane.setLeftAnchor(player2, 500.0);
                            player2.setRotate(180);
                            gameLayout.getChildren().add(player2); 
                            AnchorPane.setTopAnchor(player3, 500.0);
                            AnchorPane.setLeftAnchor(player3, 20.0);
                            player3.setRotate(90);
                            gameLayout.getChildren().add(player3);
                        });
                    });
                    

                });


        // Thêm các thành phần giao diện sau khi vòng lặp đã kết thúc
        root.getChildren().addAll(tableBackground, gameLayout, startButton, backButton);
        StackPane.setAlignment(backButton, Pos.TOP_RIGHT);

        return new Scene(root, 1920, 1080); 
    }


    
    // Thiết lập lượt chơi đầu tiên
    public void setTurn() {
        checkTurn = new boolean[players.size()];
        for (int i = 0; i < players.size(); i++) {
            checkTurn[i] = false;
        }
        for (Player player : players) {
            for (Card card : player.getHand()) {
                if (card.getRankValue() == 3 && card.getSuit().equals("S")) {
                    startTurn = player.getId();
                    checkTurn[startTurn] = true;
                }
            }
        }
    }
}