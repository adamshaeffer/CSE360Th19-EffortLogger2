package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Entry Point for effor logger prototype
 * Author: Jacob Lusenhop
 */
public class EffortLoggerPrototype extends Application{
	
	public static void main(String[] args) {					
		launch(args);											
	}	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
			Scene scene = new Scene(root, 600, 400);
			primaryStage.setTitle("EffortLogger Console"); 
	        primaryStage.setScene(scene);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

