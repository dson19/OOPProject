package com.example.UI;

import com.example.Deck.Card;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class CardView extends StackPane {
    private Card card;
    private boolean selected = false;
    private final double jumpAmount = 20;
    public enum SelectDirection {
        UP, DOWN, LEFT, RIGHT
    }

    private ImageView imageView;
    private Label textLabel;
    private boolean displayMode; // true = image, false = text

    public CardView(Card card, double width, double height, boolean displayMode) {
        this.card = card;
        this.displayMode = displayMode;

        imageView = new ImageView();
        textLabel = new Label();

        // Setup imageView
        String cardName = card.getName();
        Image image = new Image(getClass().getResourceAsStream("/com/example/CardImages/" + cardName + ".png"));
        imageView.setImage(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        // Setup textLabel
        textLabel.setText(cardName);
        textLabel.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-font-size: 18; -fx-padding: 10;");
        textLabel.setPrefSize(width, height);
        textLabel.setAlignment(javafx.geometry.Pos.CENTER);

        // Show image or text depending on mode
        if (displayMode) {
            getChildren().add(imageView);
        } else {
            getChildren().add(textLabel);
        }

        setOnMouseClicked(e -> toggleSelected());
    }

    private void toggleSelected() {
        if (!selected) {
            setTranslateY(getTranslateY() - jumpAmount);
            selected = true;
        } 
        else {
            setTranslateY(getTranslateY() + jumpAmount);
            selected = false;
        }
    }
    public void setDisplayMode(boolean displayMode) {
        if (this.displayMode == displayMode) return;

        this.displayMode = displayMode;
        getChildren().clear();

        if (displayMode) {
            getChildren().add(imageView);
        } else {
            getChildren().add(textLabel);
        }
    }
    public Card getCard() {
        return card;
    }
    public Boolean isSelected() {
        return selected;
    }
}
