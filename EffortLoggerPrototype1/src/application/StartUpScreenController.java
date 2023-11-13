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

/*
 * Controller for the start up screen 
 * Author: Jacob Lusenhop
 */
public class StartUpScreenController {
	@FXML
	private Label usernameLabel;
	@FXML 
	private Label roleLabel;
	
	/*
	 * Handles when the log out button is pressed
	 */
	public void logOutButtonHandler(ActionEvent event) throws IOException{ 
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
		Parent root = loader.load();
		
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/*
	 * Sets the text for the username and role labels
	 */
	public void setUser(String username, String role) {
		usernameLabel.setText(username);
		roleLabel.setText(role);
	}
}
