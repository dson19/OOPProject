package com.example.UI;

import java.util.List;

import com.example.Deck.Card;
import com.example.Player.Player;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class DisplayPlayerBox extends HBox {
    private HBox cardBox = new HBox(-30);
    private Player player;
    private Button playButton = MainApplication.createButton("Play");
    private Button skipButton = MainApplication.createButton("Skip");
    private Button sortButton = MainApplication.createButton("Sort");
    private Circle avatar;
    private Label playerName;

    public DisplayPlayerBox(Player player) {
        super(20); // spacing 20px
        this.player = player;
        this.setAlignment(Pos.CENTER_LEFT);

        // Avatar
        avatar = createAvatar(40);

        // Player name label
        playerName = new Label(player.getName());
        playerName.setStyle("-fx-font-weight:bold; -fx-text-fill:white; -fx-font-size:14px;");

        // Card box
        cardBox.setAlignment(Pos.CENTER_LEFT);
        List<Card> hand = player.getHand();
        for (Card card : hand) {
            CardView cardView = new CardView(card, 60, 90, true);
            cardBox.getChildren().add(cardView);
        }

        // Hide action buttons initially
        playButton.setVisible(true);
        skipButton.setVisible(true);
        sortButton.setVisible(true);

        // Sort button action
        sortButton.setOnAction(e -> {
            player.sortHand();
            cardBox.getChildren().clear();
            for (Card c : player.getHand()) {
                cardBox.getChildren().add(new CardView(c, 60, 90, true));
            }
        });

        // Add all elements to this HBox
        this.getChildren().addAll(
            avatar,
            playerName,
            cardBox,
            playButton,
            skipButton,
            sortButton
        );
    }

    private Circle createAvatar(double radius) {
        Image img = new Image(getClass().getResource("/com/example/Application/avatar.jpg").toExternalForm());
        Circle circle = new Circle(radius);
        circle.setFill(new ImagePattern(img));
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(2);
        return circle;
    }

    /**
     * Hiển thị hoặc ẩn các nút điều khiển tùy theo lượt người chơi.
     */
    public void setActiveTurn(boolean active) {
        playButton.setVisible(active);
        skipButton.setVisible(active);
        sortButton.setVisible(active);
    }
}
