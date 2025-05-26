package com.example.Deck;

public class Card {
    private String suit;
    private String rank;
    private boolean faceUp = false; // Mặc định úp bài
    private boolean selected = false; // Để dùng trong UI (click chọn bài)

    // Constructor
    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    // ==================== Getters ====================
    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getName() {
        return rank + "-" + suit;
    }

    // ==================== Setters ====================
    public void flip() {
        faceUp = !faceUp;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    // ==================== Rank Value (Theo TLMN) ====================
    public int getRankValue() {
        return switch (rank) {
            case "2" -> 15;
            case "A" -> 14;
            case "K" -> 13;
            case "Q" -> 12;
            case "J" -> 11;
            default -> Integer.parseInt(rank);
        };
    }

    // ==================== So sánh theo Rank ====================
    public static int compareByRank(Card c1, Card c2) {
        return Integer.compare(c1.getRankValue(), c2.getRankValue());
    }

    // ==================== So sánh theo cả Rank và Suit ====================
    // Át bích > át rô > át chuồn > át cơ
    public static int compareByRankAndSuit(Card c1, Card c2) {
        int rankCompare = compareByRank(c1, c2);
        if (rankCompare != 0) return rankCompare;
        return compareSuit(c1.getSuit(), c2.getSuit());
    }

    private static int compareSuit(String suit1, String suit2) {
    // Quy định thứ tự chất (H > D > C > S)
        int value1 = switch (suit1) {
            case "H" -> 4; // Hearts
            case "D" -> 3; // Diamonds
            case "C" -> 2; // Clubs
            case "S" -> 1; // Spades
            default -> 0;
        };
        int value2 = switch (suit2) {
            case "H" -> 4;
            case "D" -> 3;
            case "C" -> 2;
            case "S" -> 1;
            default -> 0;
        };
        return Integer.compare(value1, value2);
    }

}
