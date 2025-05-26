package com.example.TLMN;

import java.util.List;

import com.example.Deck.Card;

public class CardGroupChecker {

    // Kiểm tra 1 lá
    public static boolean isSingle(List<Card> cards) {
        return cards.size() == 1;
    }

    // Kiểm tra đôi
    public static boolean isPair(List<Card> cards) {
        return cards.size() == 2 && sameRank(cards);
    }

    // Kiểm tra bộ 3
    public static boolean isTriple(List<Card> cards) {
        return cards.size() == 3 && sameRank(cards);
    }

    // Kiểm tra tứ quý
    public static boolean isFourOfAKind(List<Card> cards) {
        return cards.size() == 4 && sameRank(cards);
    }

    // Kiểm tra sảnh (bài liên tiếp, ít nhất 3 lá)
    public static boolean isStraight(List<Card> cards) {
        if (cards.size() < 3) return false;
        sortCards(cards);
        for (int i = 0; i < cards.size() - 1; i++) {
            int curr = cards.get(i).getRankValue();
            int next = cards.get(i + 1).getRankValue();
            if (next != curr + 1 || curr >= 15 || curr <= 2) { // 2 và Joker không vào sảnh
                return false;
            }
        }
        return true;
    }

    // Kiểm tra đôi thông (ít nhất 3 đôi liên tiếp, tức 6 lá)
    public static boolean isConsecutivePairs(List<Card> cards) {
        if (cards.size() < 6 || cards.size() % 2 != 0) return false;
        sortCards(cards);

        for (int i = 0; i < cards.size(); i += 2) {
            // Check cặp
            if (!cards.get(i).getRank().equals(cards.get(i + 1).getRank())) return false;

            // Check liên tiếp
            if (i >= 2) {
                int prevRank = cards.get(i - 2).getRankValue();
                int currRank = cards.get(i).getRankValue();
                if (currRank != prevRank + 1 || currRank >= 15 || currRank <= 2) { // Không cho 2, Joker vào
                    return false;
                }
            }
        }
        return true;
    }


    public static void sortCards(List<Card> cards) {
        cards.sort(Card::compareByRank); // Đảm bảo Card có compareByRank (so sánh theo rankValue)
    }

    private static boolean sameRank(List<Card> cards) {
        if (cards.isEmpty()) return false;
        String rank = cards.get(0).getRank();
        return cards.stream().allMatch(c -> c.getRank().equals(rank));
    }
    public static String getPlayType(List<Card> cards) {
    if (isSingle(cards)) return "Single";
    if (isPair(cards)) return "Pair";
    if (isTriple(cards)) return "Triple";
    if (isFourOfAKind(cards)) return "FourOfAKind";
    if (isConsecutivePairs(cards)) return "ConsecutivePairs";
    if (isStraight(cards)) return "Straight";
    return "Invalid";
}

}
