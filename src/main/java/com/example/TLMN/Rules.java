package com.example.TLMN;

import java.util.List;

import com.example.Deck.Card;
import com.example.Player.*;

public class Rules {
    protected int numOfPlayers;
    protected CardGroupChecker cardGroupChecker = new CardGroupChecker();
    protected List<Player> players;
    protected void setPlayers(List<Player> players) {
        this.players = players;
    }
    protected void setCardGroupChecker(CardGroupChecker cardGroupChecker) {
        this.cardGroupChecker = cardGroupChecker;
    }
    public boolean compareCards(List<Card> group1, List<Card> group2) {
        group1 = cardGroupChecker.sortCards(group1);
        group2 = cardGroupChecker.sortCards(group2);
        if (group1.isEmpty()){
            return true; // lượt đầu tiên của vòng chơi
        }
        if (group2.isEmpty()) {
            return false; // không được đánh bài trống?
        }
        if ( group1.get(0).getRankValue() != 15 &&  group1.size() != group2.size() && !cardGroupChecker.isConsecutivePairs(group1)) {
            return false; // loại trừ các trường hợp không cùng loại bài vẫn hợp lệ -> chặt 2, và đôi thông
        }
        if (group1.size() == group2.size()) {
            String type1 = cardGroupChecker.getCardType(group1);
            String type2 = cardGroupChecker.getCardType(group2);
            if (type1.equals(type2)) {
                Card highestCard1 = group1.get(group1.size()-1);
                Card highestCard2 = group2.get(group2.size() -1);
                if (highestCard2.getRankValue() > highestCard1.getRankValue()) {
                    return true; // group2 thắng
                } else if (highestCard1.getRankValue() > highestCard2.getRankValue()) {
                    return false; // group1 thắng
                } else {
                    String suit1 = highestCard1.getSuit();
                    String suit2 = highestCard2.getSuit();
                    return Card.compareSuit(suit1,suit2)>0; 
                }
            } 
        }
        return false; // không cùng loại bài
    }
    protected Player Winner(){
        for (Player player : players){
            if (player.getHand().isEmpty()) {
                return player; 
            }
        }
        return null; // Chưa ai thắng
    }
}
