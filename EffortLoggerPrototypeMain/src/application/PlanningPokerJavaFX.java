package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Random;
 
public class PlanningPokerJavaFX extends Application {
	
    public static void main(String[] args) {
       launch(args);
    }
    
    public void start(Stage primaryStage) {
    	//System.out.println("Planning Poker Session");
    	//System.out.println("It started!");
        //primaryStage.setTitle("EffortLogger Editor");		// Title of the current page
        
        try {
        	//Parent root = FXMLLoader.load(getClass().getResource("BeforeStartPressed.fxml"));
        	Parent root = FXMLLoader.load(getClass().getResource("ProjectDetails.fxml"));
        	Scene scene = new Scene(root, 1250, 800);
        	//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        	primaryStage.setScene(scene);
        	primaryStage.show();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }  
    }
  
    
}

