package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller extends Application {
    @FXML
    private Label stop;
    private Timeline time;
    private long sec = 0;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PokerPlanning.fxml"));
            loader.setController(this);
            BorderPane root = loader.load();
            Scene scene = new Scene(root, 400, 400);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
            init();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public void init() {
        time = new Timeline(new KeyFrame(Duration.seconds(1), evt -> up()));
        time.setCycleCount(Timeline.INDEFINITE);
    }

    public void startTimer() { 
        if (time != null && (time.getStatus() != Timeline.Status.RUNNING)) {
            time.play();
        }
    }

    public void stopTimer() { 
        if (time != null) {
            time.stop();
        }
    }

    public void up() {
        sec++;
        stop.setText(formatT(sec));
    }

    private String formatT(long sec) {
        long minute = (sec / 60000) % 60;
        long seconds = (sec / 1000) % 60;
        long mil = sec % 1000;
        return String.format("%02d:%02d.%03d", minute, seconds, mil);
    }
}
