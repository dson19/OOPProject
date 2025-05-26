package com.example.Deck;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand; // Các lá bài trên tay
    private int points;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Card> getHand() {
        return hand;
    }
    public void setHand(List<Card> hand) {
        this.hand = hand;
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    // Getter, setter, constructor...
}