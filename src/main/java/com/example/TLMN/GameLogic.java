package com.example.TLMN;

import com.example.Deck.Card;
import com.example.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {

    private List<Player> players;
    private int currentPlayerIndex = 0;

    // Lưu bộ bài vừa đánh trên bàn (bài của lượt gần nhất)
    private List<Card> currentPlayedCards = new ArrayList<>();

    private boolean gameOver = false;
    private Player winner;

    public GameLogic(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    // Người chơi đánh bài, trả về true nếu hợp lệ, false nếu không
    public boolean playTurn(Player player, List<Card> playedCards) {
        if (gameOver) {
            return false;
        }
        if (!player.equals(getCurrentPlayer())) {
            return false;
        }
        if (!isValidPlay(player, playedCards)) {
            return false;
        }

        // Loại bỏ bài từ tay người chơi
        player.removeCardsFromHand(playedCards);

        // Cập nhật bài trên bàn
        currentPlayedCards.clear();
        currentPlayedCards.addAll(playedCards);

        // Kiểm tra nếu người chơi đã hết bài => kết thúc game
        if (player.getHand().isEmpty()) {
            gameOver = true;
            winner = player;
        }

        return true;
    }

    // Hàm kiểm tra bài đánh hợp lệ (bạn tự hiện thực chi tiết theo luật)
    private boolean isValidPlay(Player player, List<Card> playedCards) {
        // Ví dụ đơn giản: không cho đánh bài rỗng, và phải mạnh hơn bài hiện tại trên bàn
        if (playedCards.isEmpty()) return false;

        // Cần viết luật cụ thể ở đây

        return true;
    }

    // Người chơi bỏ lượt
    public void passTurn() {
        if (gameOver) return;

        // Chuyển lượt tiếp, bài trên bàn giữ nguyên
        nextTurn();
    }

    // Chuyển sang lượt kế tiếp
    public void nextTurn() {
        if (gameOver) return;

        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }
    }

    public List<Card> getCurrentPlayedCards() {
        return currentPlayedCards;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getWinner() {
        return winner;
    }
}
