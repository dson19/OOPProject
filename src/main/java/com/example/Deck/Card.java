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

    public void flip() {
        faceUp = !faceUp;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

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

    public static int compareByRank(Card c1, Card c2) {
        return Integer.compare(c1.getRankValue(), c2.getRankValue());
    }
    public static int compareSuit(String suit1, String suit2) {
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
    public int compareCard(Card other) {
        int rankComparison = compareByRank(this, other);
        if (rankComparison != 0) {
            return rankComparison; // So sánh theo giá trị trước
        }
        return compareSuit(this.getSuit(), other.getSuit()); // Nếu giá trị bằng nhau, so sánh theo chất
    }
}
