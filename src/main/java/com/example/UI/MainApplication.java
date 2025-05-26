package com.example.UI;


import javafx.scene.control.Button;
import javafx.scene.effect.InnerShadow;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

public class MainApplication extends javafx.application.Application {
    public static MediaPlayer mediaPlayer;
    public static Boolean displayMode = true;
    
    public static Button createButton(String text) {
        Button button = new Button(text);
        // Gradient nền đỏ
        String RedGradient = "-fx-background-color: linear-gradient(to bottom,rgb(255, 0, 0),rgb(255, 0, 0),rgb(230, 0, 0));" +
                " -fx-background-radius: 15;" +
                " -fx-border-color: white;" +
                " -fx-border-width: 2;" +
                " -fx-border-radius: 15;" +
                " -fx-text-fill: white;" +
                " -fx-font-weight: bold;" +
                " -fx-font-size: 16;";

        button.setStyle(RedGradient);
        // Hiệu ứng bóng bên trong (InnerShadow)
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.rgb(255, 0, 0, 0.8));
        innerShadow.setRadius(5);
        innerShadow.setChoke(0.4);

        // Thêm sự kiện tương tác chuột
        button.setOnMouseEntered(event -> button.setEffect(innerShadow));
        button.setOnMouseExited(event -> button.setEffect(null));
        return button;
        }
    public void playMusic() {
        try {
            Media media = new Media(getClass().getResource("/com/example/Music/BackgroundMusic.mp3").toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.5);
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error loading music: " + e.getMessage());
        }
    }
    @Override
    public void start(javafx.stage.Stage primaryStage) {
        // Create Main Menu
        MainMenu mainMenu = new MainMenu();
        javafx.scene.Scene MainMenuScene = mainMenu.CreateMainMenu(primaryStage);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(MainMenuScene);
        primaryStage.show();
        playMusic();
    }
  
    public static void main(String[] args) {
        launch(args);
    }
}    
