package com.example.Player;

import java.util.ArrayList;
import java.util.List;

import com.example.Deck.Card;

public class Player {
    private int id;
    private List<Card> hand = new ArrayList<>();
    private String name;
    private boolean skipped = false;
    private boolean isBot = false; // Biến để xác định người chơi có phải là bot hay không

    public Player(int id) {
        this.id = id;
        this.isBot = false;
        this.name = "Player " + id; // Tên mặc định nếu không có tên cụ thể
    }
    public Player(int id,String name){
        this.id = id;
        this.name = name;
        this.isBot = false;
    }
    public Player(int id, String name, boolean isBot) {
        this.id = id;
        this.name = name;
        this.isBot = isBot; // Xác định người chơi có phải là bot hay không
    }
    
    public int getId() {
        return id;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void sortHand() {
        hand.sort(Card::compareByRank); // Sắp xếp bài theo giá trị
    }

    // Loại bỏ bài đã đánh khỏi tay
    public void removeCardsFromHand(List<Card> playedCards) {
        hand.removeAll(playedCards);
    }

    // Người chơi pass lượt
    public void passTurn() {
        skipped = true;
    }

    public void addCard(Card card) {
        hand.add(card);
    }
    public String getName() {
        return this.name;
    }
}
