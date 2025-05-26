package com.example.TLMN;

import com.example.Deck.Card;

public class CardComparator {

    public static int compareSuit(Card c1, Card c2) {
        int suit1 = switch (c1.getSuit()) {
            case "H" -> 4;
            case "D" -> 3;
            case "C" -> 2;
            case "S" -> 1;
            default -> 0;
        };
        int suit2 = switch (c2.getSuit()) {
            case "H" -> 4;
            case "D" -> 3;
            case "C" -> 2;
            case "S" -> 1;
            default -> 0;
        };
        return Integer.compare(suit1, suit2);
    }
}
