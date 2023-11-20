package planning;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Controller {
    @FXML private Label stop;
    @FXML private Button startStopButton;
    @FXML private Label greeting;
    private Timeline time;
    private long sec = 0;
    private long startTime;
    private boolean stopwatchRunning = false;
    private long TotalTime = 0;
    @FXML private Button endButton;
    @FXML private Label cardNum;
    @FXML private TextField UserPoker;
    @FXML private Label PokerNum;
    @FXML private Label UserStory;
    private String story;
    String filePath = "/Users/manfr/eclipse/PokerPlan/bin/planning/User_Story_test.txt";
    

    public void initialize() {
        time = new Timeline(new KeyFrame(Duration.seconds(1), evt -> up()));
        time.setCycleCount(Timeline.INDEFINITE);
        setGreeting("John", "Smith");
        setUserStory();
    }

    public void startTimer() { 
        if (time != null && (time.getStatus() != Timeline.Status.RUNNING)) {
            time.play();
        }
        startTime = System.currentTimeMillis();
    }

    public void stopTimer() { 
        if (time != null) {
            time.stop();
        }
    }

    public void up() {
        sec++;
        String tS = formatTime(sec);
        stop.setText(tS);
    }

    private String formatTime(long sec) {
        long minute = sec / 60;
        long seconds = sec  % 60;
        return String.format("Timer: %02d:%02d", minute, seconds);
    }
    @FXML
    public void toggleStopwatch() {
        if (!stopwatchRunning) {
            startTimer();
            startStopButton.setText("Stop");
        } else {
            stopTimer();
            startStopButton.setText("Start");
        }
        stopwatchRunning = !stopwatchRunning;
    }
    public void setGreeting(String firstName, String lastName) {
        greeting.setText("Hello! " + firstName + ", " + lastName);
    }
    public void EndButton()
    {
    	if(time != null)
    	{
    		time.stop();
    	}
    	TotalTime = System.currentTimeMillis() - startTime;
    	Reset();
    	
    }
    private void Reset()
    {
    	sec = 0;
    	stop.setText(formatTime(0));
    }
    @FXML
    private void UserInput()
    {
    	try {
    		int userChoice = Integer.parseInt(UserPoker.getText());
    		UserChoice(userChoice);
    	} catch(NumberFormatException e){
    		System.out.println("Please enter a valid number");
    	}
    }

    private void UserChoice(int userChoice)
    {
    	if(userChoice > 10 || userChoice < 1)
    	{
    		System.out.println("Pick a number 1-10");
    	}
    	else
    	{
    		cardNum.setText(String.valueOf(userChoice));
    	}
    }
    private void readStory(String filePath)
    {
    	try {
    	
    	byte[] file = Files.readAllBytes(Paths.get(filePath));
    	story = new String(file);
    	UserStory.setText("User Story: " + story);
    	} catch(IOException e) {
    	System.out.println("Error getting UserStory");}
    	
    }
    @FXML
    private void setUserStory()
    {
    	readStory(filePath);
    }
    
}
