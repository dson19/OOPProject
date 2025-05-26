package com.example.UI;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ShuffleEffect {
    private static final String CARD_BACK_IMAGE_PATH = "/com/example/CardImages/BACK.png";
    private static final int CARD_WIDTH = 84;
    private static final int CARD_HEIGHT = 120;

    private final List<ImageView> cardViews = new ArrayList<>();
    private final Pane root;
    private double centerX;
    private double centerY;

    public ShuffleEffect(Pane root, int cardCount) {
        this.root = root;
        // Tính toán centerX, centerY sau khi root layout xong
        root.layout(); // Bắt buộc cập nhật layout
        this.centerX = root.getWidth() / 2;
        this.centerY = root.getHeight() / 2;

        // Tạo các lá bài
        Image cardBackImage = new Image(getClass().getResourceAsStream(CARD_BACK_IMAGE_PATH));
        for (int i = 0; i < cardCount; i++) {
            ImageView cardView = new ImageView(cardBackImage);
            cardView.setFitWidth(CARD_WIDTH);
            cardView.setFitHeight(CARD_HEIGHT);
            cardView.setLayoutX(centerX - (CARD_WIDTH / 2));
            cardView.setLayoutY(centerY - (CARD_HEIGHT / 2));
            cardViews.add(cardView);
            root.getChildren().add(cardView);
        }
    }

    public void startShuffle(Runnable onFinished) {
        SequentialTransition shuffleSequence = new SequentialTransition();

        // Bước 1: Dàn bài ra hàng ngang
        ParallelTransition spreadEffect = new ParallelTransition();
        for (int i = 0; i < cardViews.size(); i++) {
            ImageView card = cardViews.get(i);
            TranslateTransition spread = new TranslateTransition(Duration.seconds(0.8), card);
            spread.setToX((i - cardViews.size() / 2.0) * (CARD_WIDTH + 10));
            spread.setToY(Math.sin(i * Math.PI / 6) * 50);
            spreadEffect.getChildren().add(spread);
        }
        shuffleSequence.getChildren().add(spreadEffect);

        // Bước 2: Gộp bài lại
        ParallelTransition gatherEffect = new ParallelTransition();
        for (ImageView card : cardViews) {
            TranslateTransition gather = new TranslateTransition(Duration.seconds(0.8), card);
            gather.setToX(0);
            gather.setToY(0);

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.8), card);
            rotate.setByAngle(360);

            gatherEffect.getChildren().addAll(gather, rotate);
        }
        shuffleSequence.getChildren().add(gatherEffect);

        shuffleSequence.setOnFinished(e -> {
            root.getChildren().removeAll(cardViews);
            if (onFinished != null) onFinished.run();
        });

        shuffleSequence.play();
    }
}
