package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StartUpScreenController {
	@FXML 
	private Label userLabel;
	
	private String userFirstName;
	private String userLastName;
	
	public void setUser(String firstName, String lastName) {
		userFirstName = firstName;
		userLastName = lastName;
		userLabel.setText("Hello, " + firstName + " " + lastName);
	}
	
	/*
	 * If the effort logger console button is pressed, the screen is switched to the effort logger console
	 */
	public void effortLoggerConsoleButtonHandler(ActionEvent event) throws IOException {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		DefectConsoleController Controller = new DefectConsoleController();
		Controller.start(stage);
	}
	
	/*
	 * If the project definitions button is pressed, the screen is switched to the register screen
	 */
	public void planningPokerButtonHandler(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("ProjectDetails.fxml"));
    	Scene scene = new Scene(root, 1250, 900);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	/*
	 * If the project definitions button is pressed, the screen is switched to the register screen
	 */
	public void projectDefinitionsButtonHandler(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("ModifyProjects.fxml"));
		Scene scene = new Scene(root,600,400);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	
}
