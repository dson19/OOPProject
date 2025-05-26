package com.example.UI;

import com.example.Deck.Card;
import com.example.Player.Player;
import com.example.TLMN.GameLogic;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TLMNGameScene {

    private GameLogic gameLogic;
    private Label infoLabel = new Label();
    private VBox player1Box;
    private HBox player1CardBox;

    // Hiển thị bài đã đánh trên bàn
    private HBox playedCardsBox = new HBox(10);

    private List<CardView> selectableCards = new ArrayList<>();
    private List<Card> selectedCards = new ArrayList<>();

    public TLMNGameScene(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public Scene createTLMNGameScene(Stage primaryStage, int numberOfPlayers, boolean displayMode) {
        StackPane root = new StackPane();
        root.setPrefSize(1920, 1200);

        BorderPane gameLayout = new BorderPane();

        // Back Button
        Button backButton = MainApplication.createButton("Back");
        backButton.setOnAction(e -> primaryStage.setScene(new MainMenu().CreateMainMenu(primaryStage)));

        // Info label (hiển thị trạng thái)
        infoLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        infoLabel.setAlignment(Pos.CENTER);
        gameLayout.setTop(infoLabel);

        // Player 1 (bạn) - Bottom
        player1Box = new VBox(5);
        player1Box.setAlignment(Pos.CENTER);
        Label player1Label = new Label("Player 1 (You)");
        player1Label.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        player1CardBox = new HBox(5);
        player1CardBox.setAlignment(Pos.CENTER);
        player1Box.getChildren().addAll(player1Label, player1CardBox);
        gameLayout.setBottom(player1Box);

        // Player 2 - Right
        VBox player2Box = new VBox(5);
        player2Box.setAlignment(Pos.CENTER);
        player2Box.getChildren().addAll(new Label("Player 2"), createCardBox(2, displayMode));
        player2Box.setRotate(-90);
        gameLayout.setRight(player2Box);

        // Player 3 - Top (nếu có)
        if (numberOfPlayers >= 3) {
            VBox player3Box = new VBox(5);
            player3Box.setAlignment(Pos.CENTER);
            player3Box.getChildren().addAll(new Label("Player 3"), createCardBox(3, displayMode));
            player3Box.setRotate(180);
            gameLayout.setTop(player3Box);
        }

        // Player 4 - Left (nếu có)
        if (numberOfPlayers == 4) {
            VBox player4Box = new VBox(5);
            player4Box.setAlignment(Pos.CENTER);
            player4Box.getChildren().addAll(new Label("Player 4"), createCardBox(4, displayMode));
            player4Box.setRotate(90);
            gameLayout.setLeft(player4Box);
        }

        // Bàn chơi - Center
        StackPane centerPane = new StackPane();
        ImageView tableBackground = new ImageView(getClass().getResource("/com/example/Application/TableInGame.png").toExternalForm());
        tableBackground.fitWidthProperty().bind(root.widthProperty());
        tableBackground.fitHeightProperty().bind(root.heightProperty());

        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);

        // Hộp chứa bài đã đánh
        playedCardsBox.setAlignment(Pos.CENTER);

        // Nút Đánh bài và Pass
        HBox actionBox = new HBox(20);
        actionBox.setAlignment(Pos.CENTER);
        Button playButton = new Button("Đánh bài");
        Button passButton = new Button("Pass");
        actionBox.getChildren().addAll(playButton, passButton);

        centerBox.getChildren().addAll(playedCardsBox, actionBox);

        centerPane.getChildren().addAll(tableBackground, centerBox);

        gameLayout.setCenter(centerPane);

        root.getChildren().addAll(gameLayout, backButton);
        StackPane.setAlignment(backButton, Pos.TOP_RIGHT);

        // Thiết lập sự kiện nút
        playButton.setOnAction(e -> onPlayClicked());
        passButton.setOnAction(e -> onPassClicked());

        // Load bài người chơi 1 vào UI
        updatePlayer1Cards(displayMode);

        updatePlayedCardsOnTable();
        updateInfoLabel();

        return new Scene(root, 1200, 800);
    }

    private void updatePlayer1Cards(boolean displayMode) {
        player1CardBox.getChildren().clear();
        selectableCards.clear();
        selectedCards.clear();

        Player player1 = gameLogic.getPlayers().get(0);
        List<Card> hand = player1.getHand();

        for (Card card : hand) {
            CardView cardView = new CardView(card, 40, 60, displayMode);
            cardView.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    toggleCardSelection(cardView);
                }
            });
            selectableCards.add(cardView);
            player1CardBox.getChildren().add(cardView);
        }
    }

    private void toggleCardSelection(CardView cardView) {
        Card card = cardView.getCard();
        if (selectedCards.contains(card)) {
            selectedCards.remove(card);
            cardView.setSelected(false);
        } else {
            selectedCards.add(card);
            cardView.setSelected(true);
        }
    }

    private void onPlayClicked() {
        Player currentPlayer = gameLogic.getCurrentPlayer();
        if (!currentPlayer.equals(gameLogic.getPlayers().get(0))) {
            showMessage("Không phải lượt bạn!");
            return;
        }
        if (selectedCards.isEmpty()) {
            showMessage("Vui lòng chọn bài để đánh.");
            return;
        }

        boolean success = gameLogic.playTurn(currentPlayer, selectedCards);
        if (success) {
            showMessage("Bạn đã đánh bài thành công.");
            updatePlayer1Cards(true);
            updatePlayedCardsOnTable();
            if (gameLogic.isGameOver()) {
                showMessage("Game kết thúc! Người chiến thắng: Player " + gameLogic.getWinner().getId());
                // Có thể disable nút hoặc làm gì đó thêm ở đây
            } else {
                gameLogic.nextTurn();
                updateInfoLabel();
                selectedCards.clear();
            }
        } else {
            showMessage("Bài đánh không hợp lệ hoặc không đủ mạnh để chặt.");
        }
    }

    private void onPassClicked() {
        Player currentPlayer = gameLogic.getCurrentPlayer();
        if (!currentPlayer.equals(gameLogic.getPlayers().get(0))) {
            showMessage("Không phải lượt bạn!");
            return;
        }
        currentPlayer.passTurn();
        showMessage("Bạn đã bỏ lượt.");
        gameLogic.nextTurn();
        updateInfoLabel();
        selectedCards.clear();
        updatePlayedCardsOnTable();
    }

    private void updateInfoLabel() {
        if (!gameLogic.isGameOver()) {
            Player current = gameLogic.getCurrentPlayer();
            infoLabel.setText("Lượt đánh: Player " + current.getId());
        }
    }

    private void showMessage(String msg) {
        infoLabel.setText(msg);
    }

    // Hiển thị bài đã đánh trên bàn
    private void updatePlayedCardsOnTable() {
        playedCardsBox.getChildren().clear();

        List<Card> cardsOnTable = gameLogic.getCurrentPlayedCards();
        if (cardsOnTable == null || cardsOnTable.isEmpty()) {
            Label noCardsLabel = new Label("Chưa có bài đánh trên bàn");
            noCardsLabel.setStyle("-fx-text-fill: white; -fx-font-style: italic;");
            playedCardsBox.getChildren().add(noCardsLabel);
        } else {
            for (Card card : cardsOnTable) {
                CardView cardView = new CardView(card, 50, 75, true);
                playedCardsBox.getChildren().add(cardView);
            }
        }
    }

    // Tạo card box giả cho các người chơi khác (bài úp)
    private HBox createCardBox(int playerNumber, boolean displayMode) {
        HBox box = new HBox(5);
        box.setAlignment(Pos.CENTER);
        for (int i = 0; i < 13; i++) {
            Card card = new Card("H", "X"); // Bài úp
            CardView cardView = new CardView(card, 40, 60, displayMode);
            box.getChildren().add(cardView);
        }
        return box;
    }

}
