package com.example.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for (String suit : new String[]{"H", "D", "C", "S"}) {
            for (String rank : new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"}) {
                cards.add(new Card(suit, rank));
            }
        }
    }
    public void shuffle(){
        Collections.shuffle(cards);
    }
    public void filterwithBaCay(){
        cards.removeIf(card -> card.getRank().equals("10") || card.getRank().equals("J") || card.getRank().equals("Q") || card.getRank().equals("K"));
    }
    public Card drawCard() {
        if (cards.isEmpty()) return null;
        return cards.remove(cards.size() - 1);
    }
}