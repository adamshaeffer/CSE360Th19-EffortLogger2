package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class TimerController {

	@FXML
	private Label stop;
	@FXML
	private Button startStopButton;
	@FXML
    private Label greeting;
	private Timeline time;
	private long sec = 0L;
	private long startTime;
	private boolean stopwatchRunning = false;
	private long TotalTime = 0L;
	@FXML
	private Button endButton;
	   
	   
	public void initialize() {
	      this.time = new Timeline(new KeyFrame[]{new KeyFrame(Duration.seconds(1.0), (evt) -> {
	         this.up();
	      }, new KeyValue[0])});
	      this.time.setCycleCount(-1);
	   }
	   
	   
	
	public void startTimer() {
	      if (this.time != null && this.time.getStatus() != Status.RUNNING) {
	         this.time.play();
	      }

	      this.startTime = System.currentTimeMillis();
	   }

	   public void stopTimer() {
	      if (this.time != null) {
	         this.time.stop();
	      }

	   }

	   public void up() {
	      ++this.sec;
	      String tS = this.formatTime(this.sec);
	      this.stop.setText(tS);
	   }

	   private String formatTime(long sec) {
	      long minute = sec / 60L;
	      long seconds = sec % 60L;
	      return String.format("%02d:%02d", minute, seconds);
	   }

	   @FXML
	   public void toggleStopwatch() {
	      if (!this.stopwatchRunning) {
	         this.startTimer();
	         this.startStopButton.setText("Stop");
	      } else {
	         this.stopTimer();
	         this.startStopButton.setText("Start");
	      }

	      this.stopwatchRunning = !this.stopwatchRunning;
	   }
	
	   public void EndButton() {
		      if (this.time != null) {
		         this.time.stop();
		      }

		      this.TotalTime = System.currentTimeMillis() - this.startTime;
		      this.Reset();
		   }

		   private void Reset() {
		      this.sec = 0L;
		      this.stop.setText(this.formatTime(0L));
		   }  
	   
}
