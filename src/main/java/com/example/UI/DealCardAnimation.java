package com.example.UI;

import java.util.List;

import com.example.Deck.Card;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class DealCardAnimation {

    public static void play(HBox cardBox, List<Card> cards, boolean displayMode, Runnable onFinished) {
        cardBox.getChildren().clear(); // Xóa các lá cũ

        Timeline timeline = new Timeline();
        double delayBetweenCards = 0.2; // Giãn cách thời gian giữa các lá bài

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            CardView cardView = new CardView(card, 60, 90, displayMode);

            // Khởi tạo hiệu ứng ban đầu: mờ và nhỏ
            cardView.setOpacity(0);
            cardView.setScaleX(0.5);
            cardView.setScaleY(0.5);

            cardBox.getChildren().add(cardView);

            // Tạo frame bắt đầu
            KeyFrame startFrame = new KeyFrame(Duration.seconds(i * delayBetweenCards),
                    new KeyValue(cardView.opacityProperty(), 0),
                    new KeyValue(cardView.scaleXProperty(), 0.5),
                    new KeyValue(cardView.scaleYProperty(), 0.5)
            );

            // Tạo frame kết thúc
            KeyFrame endFrame = new KeyFrame(Duration.seconds(i * delayBetweenCards + 0.5),
                    new KeyValue(cardView.opacityProperty(), 1),
                    new KeyValue(cardView.scaleXProperty(), 1),
                    new KeyValue(cardView.scaleYProperty(), 1)
            );

            timeline.getKeyFrames().addAll(startFrame, endFrame);
        }

        timeline.setOnFinished(e -> {
            if (onFinished != null) onFinished.run();
        });

        timeline.play();
    }
}
