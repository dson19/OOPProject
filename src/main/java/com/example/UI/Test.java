package com.example.UI;

import java.util.ArrayList;
import java.util.List;

import com.example.Deck.Card;
import com.example.Player.Player;
import com.example.TLMN.TurnOfGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Test extends Application {
    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        TurnOfGame turnOfGame = new TurnOfGame();
        List<Player> players = new ArrayList<>();
        Player player1 = new Player(0);
        Player player2 = new Player(1);
        Player player3 = new Player(2);
        Player player4 = new Player(3);
        Card card = new Card("S", "3"); // Ví dụ tạo một lá bài
        player1.addCard(card);
        player1.addCard(new Card("H", "2")); // Thêm một lá bài khác
        player2.addCard(new Card("D", "5"));
        player1.addCard(new Card("C", "8"));
        player1.addCard(new Card("H", "9"));
        player1.addCard(new Card("S", "10"));
        player3.addCard(new Card("C", "7"));
        player4.addCard(new Card("S", "10"));
        
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        turnOfGame.addGraphic(primaryStage, root,players);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
