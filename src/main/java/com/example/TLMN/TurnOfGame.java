package com.example.TLMN;

import java.util.ArrayList;
import java.util.List;

import com.example.Deck.Card;
import com.example.Deck.Deck;
import com.example.Player.Player;
import com.example.UI.CardView;
import com.example.UI.MainApplication;
import com.example.UI.ShuffleEffect;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TurnOfGame {
    private boolean[] checkTurn;
    private int startTurn;
    private List<Player> players;
    private Deck deck;
    private Rules rules;
    private ArrayList<Card> cardInTable = new ArrayList<>();
    private ArrayList<Integer> playerEndGame = new ArrayList<>();

    public void turnGame(Stage primaryStage, StackPane root, AnchorPane pane, List<Player> players, Rules rules) {
        this.players = players;
        this.rules = rules;
        deck = new Deck();
        deck.shuffle();
        setTurn();
        Boolean displayMode = MainApplication.displayMode;
        if (displayMode) {
            ShuffleEffect shuffleEffect = new ShuffleEffect(pane, 20);
            shuffleEffect.startShuffle(() -> {
                setTurn();
                turnOfGame(primaryStage, pane, players);
            });
        } else {
            turnOfGame(primaryStage, pane, players);
        }
    }

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
        checkTurn[0] = true; // Backup nếu không có 3♠
    }

    public void resetTurn() {
        for (int i = 0; i < players.size(); ++i) checkTurn[i] = true;
        for (Integer id : playerEndGame) checkTurn[id] = false;
    }

    public boolean checkEndTurn() {
        int counter = 0, index = 0;
        for (int i = 0; i < players.size(); ++i) {
            if (checkTurn[i]) {
                counter++;
                index = i;
            }
        }
        if (counter == 1) {
            startTurn = index;
            return true;
        }
        return false;
    }

    public boolean playCards(Player player, ArrayList<Card> listCardPlayed) {
        if (rules.checkCardPlayed(cardInTable, listCardPlayed)) {
            player.removeCardsFromHand(listCardPlayed);
            cardInTable = new ArrayList<>(listCardPlayed);
            if (player.getHand().isEmpty()) {
                playerEndGame.add(player.getId());
            }
            return true;
        }
        return false;
    }

    public void turnOfGame(Stage primaryStage, AnchorPane pane, List<Player> players) {
        addGraphic(primaryStage, pane, players);
        Timeline gameTimeline = new Timeline();
        int[] currentPlayerIndex = {startTurn};
        gameTimeline.setCycleCount(Timeline.INDEFINITE);

        KeyFrame turnFrame = new KeyFrame(Duration.seconds(1), event -> {
            if (playerEndGame.size() == players.size() - 1) {
                gameTimeline.stop();
                Player winner = players.get(startTurn);
                System.out.println("Winner: " + winner.getName());
                return;
            }

            if (checkEndTurn()) {
                resetTurn();
                currentPlayerIndex[0] = startTurn;
                cardInTable.clear();
            }

            Player currentPlayer = players.get(currentPlayerIndex[0]);
            if (!checkTurn[currentPlayerIndex[0]]) {
                currentPlayerIndex[0] = (currentPlayerIndex[0] + 1) % players.size();
                return;
            }
            // TODO: Thêm logic AI/AutoPlay cho bot
        });

        gameTimeline.getKeyFrames().add(turnFrame);
        gameTimeline.play();
    }

    public void addGraphic(Stage primaryStage, AnchorPane pane, List<Player> players) {
        int numberOfPlayers = players.size();
        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = players.get(i);
            HBox playerBox = new HBox(10);
            HBox cardBox = new HBox(5);
            VBox centerBox = new VBox(10);
            centerBox.setId("CenterBox");
            centerBox.setLayoutX(pane.getWidth() / 2 - 100);
            centerBox.setLayoutY(pane.getHeight() / 2 - 100);
            pane.getChildren().add(centerBox);

            for (Card card : player.getHand()) {
                CardView cardView = new CardView(card, 50, 100, true);
                cardBox.getChildren().add(cardView);
            }

            playerBox.setId("player" + i);
            switch (i) {
                case 0 -> {
                    AnchorPane.setBottomAnchor(playerBox, 20.0);
                    AnchorPane.setLeftAnchor(playerBox, 500.0);
                }
                case 1 -> {
                    AnchorPane.setRightAnchor(playerBox, 50.0);
                    AnchorPane.setTopAnchor(playerBox, 100.0);
                    playerBox.setRotate(-90);
                }
                case 2 -> {
                    AnchorPane.setTopAnchor(playerBox, 20.0);
                    AnchorPane.setLeftAnchor(playerBox, 500.0);
                    playerBox.setRotate(180);
                }
                case 3 -> {
                    AnchorPane.setBottomAnchor(playerBox, 100.0);
                    AnchorPane.setLeftAnchor(playerBox, 50.0);
                    playerBox.setRotate(90);
                }
            }

            Button playButton = MainApplication.createButton("Play");
            Button skipButton = MainApplication.createButton("Skip");
            Button sortButton = MainApplication.createButton("Sort");
            VBox controlBox = new VBox(5, playButton, skipButton, sortButton);

            sortButton.setOnAction(e -> {
                player.sortHand();
                cardBox.getChildren().clear();
                for (Card card : player.getHand()) {
                    CardView cardImage = new CardView(card, 50, 100, true);
                    cardBox.getChildren().add(cardImage);
                }
            });

            playButton.setOnAction(e -> {
                ArrayList<Card> cardsToPlay = new ArrayList<>();
                for (Node x : cardBox.getChildren()) {
                    if (x instanceof CardView cardView && cardView.isSelected()) {
                        cardsToPlay.add(cardView.getCard());
                    }
                }
                if (playCards(player, cardsToPlay)) {
                    cardBox.getChildren().removeIf(node -> node instanceof CardView && ((CardView) node).isSelected());
                    centerBox.getChildren().clear();
                    for (Card card : cardsToPlay) {
                        centerBox.getChildren().add(new CardView(card, 50, 100, true));
                    }
                } else {
                    System.out.println("Invalid move for Player " + player.getId());
                }
            });

            skipButton.setOnAction(e -> {
                checkTurn[player.getId()] = false;
            });

            playerBox.getChildren().addAll(cardBox, controlBox);
            pane.getChildren().add(playerBox);
        }
    }
}
