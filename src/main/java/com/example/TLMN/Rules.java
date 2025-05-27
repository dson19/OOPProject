package com.example.TLMN;

import java.util.List;

import com.example.Deck.Card;
import com.example.Player.Player;

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
            return true;
        }
        if (group2.isEmpty()) {
            return false; 
        }
        if (group1.size() != group2.size()) {
            return false; // Không cùng số lượng bài
        }
        return group1.getLast().compareCard(group2.getLast()) > 0; // So sánh lá bài cuối cùng
    }

    public Boolean checkCardPlayed(List<Card> cardsInTable,List<Card> cardsInHand) {
        if (cardsInHand.isEmpty()) return false;
        String typePreTurn = cardGroupChecker.getCardType(cardsInTable);
        String typeInHand = cardGroupChecker.getCardType(cardsInHand);
        if (typeInHand.equals("Invalid")) return false;
        if (cardsInTable.isEmpty())  return true;
        if (!typePreTurn.equals(typeInHand)) {
            if (typePreTurn.equals("Single"))
            {
                if (cardsInTable.getFirst().getRankValue() != 15)
                {
                    return false; 
                }
                else
                {
                    return (typeInHand.equals("ConsecutivePairs")  || typeInHand.equals("FourOfAKind"));
                }
            }
            if (typePreTurn.equals("Pair")|| typePreTurn.equals("Triple")) //chặt đôi 2  hoặc tam 2 cần 4 đôi thông hoặc tứ quý
            {
                if (cardsInTable.getFirst().getRankValue() != 15)
                {
                    return false; 
                }
                else
                {
                    return ( (typeInHand.equals("ConsecutivePairs") && cardsInHand.size()>=8) || typeInHand.equals("FourOfAKind"));
                }
            }
            if (typePreTurn.equals("ConsecutivePairs") && cardsInTable.size() == 6)
            {
                return (typeInHand.equals("FourOfAKind"));
            }
            if (typePreTurn.equals("FourOfAKind")) //chặt tứ quý cần 4 đôi thông
            {
                return (typeInHand.equals("ConsecutivePairs") && cardsInHand.size() >= 8);
            }
            return false;
        }
        return compareCards(cardsInTable, cardsInHand);
    }

    protected int checkWinnerBeforePlayed(){
        for (int i=0;i< numOfPlayers;i++)
        {
            players.get(i).sortHand();
            List<Card> hand = players.get(i).getHand();
            int numberOfTwos =0;
            for (Card card : hand){
                if (card.getRankValue() == 15) {
                    numberOfTwos++; 
                }
            }
            if (numberOfTwos >= 4) {
                return i; 
            }
        }
        return -1;
    }
    protected Player winner(){
        for (Player player : players){
            if (player.getHand().isEmpty()) {
                return player; 
            }
        }
        return null; // Chưa ai thắng
    }
}
