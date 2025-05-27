package com.example.UI;

import java.util.List;

import com.example.Deck.Card;
import com.example.Deck.Deck;
import com.example.Player.Player;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Test application for DisplayPlayerBox component.
 */
public class Test extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Layout container
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: #228B22; -fx-padding: 20;"); // green background

        // Prepare a deck and shuffle
        Deck deck = new Deck();
        deck.shuffle();

        // Create sample players and their DisplayPlayerBox
        for (int i = 1; i <= 4; i++) {
            Player player = new Player(i);
            // Deal 5 cards to each player for demonstration
            for (int j = 0; j < 5; j++) {
                Card card = deck.drawCard();
                player.getHand().add(card);
            }
            // Create UI box and set active turn for the first player
            DisplayPlayerBox playerBox = new DisplayPlayerBox(player);
            playerBox.setActiveTurn(i == 1); // only first player has action buttons visible initially
            root.getChildren().add(playerBox);
        }

        // Scene setup
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DisplayPlayerBox Test");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
