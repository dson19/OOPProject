package com.example.logic;

import java.util.ArrayList;
import java.util.List;

import com.example.Deck.Card;
import com.example.Deck.Deck;

public class BaCayGame {
    private Deck deck;
    private List<List<Card>> playersCards;
    private int numberOfPlayers;

    public BaCayGame(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.deck = new Deck();          // Create a new deck
        this.deck.filterwithBaCay();     // Filter for Ba Cây rules
        this.deck.shuffle();             // Shuffle

        playersCards = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            playersCards.add(new ArrayList<>()); // Initalize each player's cards
        }
    }

    public void dealCards() {
        for (int i = 0; i < 3; i++) { // mỗi người 3 lá
            for (int p = 0; p < numberOfPlayers; p++) {
                Card card = deck.drawCard();
                playersCards.get(p).add(card);
            }
        }
    }

    public List<Card> getPlayerCards(int playerIndex) {
        return playersCards.get(playerIndex);
    }

    // Tính điểm tổng 3 lá, theo luật Ba Cây
    public int calculatePoints(List<Card> cards) {
        int sum = 0;
        for (Card card : cards) {
            String rank = card.getRank();
            int value;
            switch (rank) {
                case "A" -> value = 1;
                case "2", "3", "4", "5", "6", "7", "8", "9" -> value = Integer.parseInt(rank);
                default -> value = 0; // Trường hợp không hợp lệ
            }
            sum += value;
        }
        if (sum % 10 ==0) return 10; // Nếu tổng là bội số của 10 thì điểm là 10
        return sum % 10;
    }
    public int valueforCard(Card card){
        String suit = card.getSuit();
        String rank = card.getRank();
        int suitVal = suitRank(suit);
        int rankVal = switch (rank) {
            case "A" -> 9;
            case "2" -> 1;
            case "3" -> 2;
            case "4" -> 3;
            case "5" -> 4;
            case "6" -> 5;
            case "7" -> 6;
            case "8" -> 7;
            case "9" -> 8;
            default -> 0; // Trường hợp không hợp lệ
        };
        return suitVal * 10 + rankVal; // Trả về giá trị của lá bài theo chất và giá trị
    }
    // So sánh chất bài D > H > C > S
    private int suitRank(String suit) {
        return switch (suit) {
            case "D" -> 4;
            case "H" -> 3;
            case "C" -> 2;
            case "S" -> 1;
            default -> 0;
        };
    }

    // So sánh 2 người chơi, trả về index người thắng
    public int compareHands(int player1, int player2) {
        List<Card> cards1 = playersCards.get(player1);
        List<Card> cards2 = playersCards.get(player2);

        int points1 = calculatePoints(cards1);
        int points2 = calculatePoints(cards2);

        if (points1 > points2) return player1;
        if (points2 > points1) return player2;

        // Điểm bằng nhau thì so sánh lá bài có chất lớn nhất
        int val1  = valueforCard(getMaxCard(cards1));
        int val2  = valueforCard(getMaxCard(cards2));
        if (val1 > val2) return player1;
        if (val2 > val1) return player2;
        return player1; 
    }

    public Card getMaxCard(List<Card> cards) {
        Card maxCard = null;
        for (Card card : cards) {
            if (maxCard == null || valueforCard(card) > valueforCard(maxCard)) {
                maxCard = card;
            }
        }
        return maxCard;
    }
    public int numberOfPlayers() {
        return numberOfPlayers;
    }
}