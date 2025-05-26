package com.example.Player;

import java.util.ArrayList;
import java.util.List;

import com.example.Deck.Card;

public class Player {
    private int id;
    private List<Card> hand = new ArrayList<>();
    private String name;

    public Player(int id) {
        this.id = id;
        this.name = "Player " + id; // Tên mặc định nếu không có tên cụ thể
    }
    public Player(int id,String name){
        this.id = id;
        this.name = name;
        // Có thể lưu tên người chơi nếu cần
    }
    public int getId() {
        return id;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void sortHand() {
        // Sắp xếp bài tùy bạn triển khai
    }

    // Loại bỏ bài đã đánh khỏi tay
    public void removeCardsFromHand(List<Card> playedCards) {
        hand.removeAll(playedCards);
    }

    // Người chơi pass lượt
    public void passTurn() {
        // Có thể cập nhật trạng thái gì đó nếu cần
    }

    public void addCard(Card card) {
        hand.add(card);
    }
    public String getName() {
        return this.name;
    }
}
