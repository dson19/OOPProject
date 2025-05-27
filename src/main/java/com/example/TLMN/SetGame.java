package com.example.TLMN;

import java.util.ArrayList;
import java.util.List;

import com.example.Deck.Card;
import com.example.Deck.Deck;
import com.example.Player.BotTLMN;
import com.example.Player.Player;

public class SetGame {
    protected int numberOfPlayer;
    protected Deck deck = new Deck();
    public List<Player> players = new ArrayList<>();
    public void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }
    public List<Player> getPlayers() {
        return this.players;
    }

    public void addPlayer(Boolean isPlayer,Player player,Rules rules) {
        if (isPlayer){
            players.add(player);
        }
        else{

            players.add(new BotTLMN(player.getId()));
        }
    }
    public void dealCards() {
        for (int i = 0; i < 13; i++) { // mỗi người 3 lá
            for (Player player : players) {
                Card card = deck.drawCard();
                if (card != null) {
                    player.addCard(card);
                } else {
                    break;
                }
            }
        }
    }
}
