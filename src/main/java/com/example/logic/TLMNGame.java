package com.example.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import com.example.Deck.Card;
import com.example.Deck.Deck;
import com.example.Player.Player;

public class TLMNGame {
    private Deck deck;
    private List<Player> players = new ArrayList<>();
    private int numberOfPlayers;
    public TLMNGame(List<Player> players) {
        this.numberOfPlayers = players.size();
        this.deck = new Deck(); 
        this.players = players;         // Create a new deck
        this.deck.shuffle();             // Shuffle
    }

    public void dealCards() {
    int cardsPerPlayer = 13;
        for (int i = 0; i < cardsPerPlayer; i++) {
            for (int p = 0; p < numberOfPlayers; p++) {
                players.get(p).addCard(deck.drawCard());
            }
        }
    }

    // Sắp theo giá trị, rồi đến chất

}


