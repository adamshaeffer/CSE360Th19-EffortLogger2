package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/*
 * Controller for the login screen
 * Author: Jacob Lusenhop
 */
public class LoginScreenController {
	User currentUser;
	
	@FXML
	private TextField usernameInput;
	@FXML 
	private PasswordField passwordInput;
	@FXML
	private Label errorLabel;
	
	/*
	 * Checks if username and password are correct when login button is pressed
	 */
	public void loginButtonHandler(ActionEvent event) throws IOException{
		if(authenticate()) {
			
			String firstName = currentUser.getFirstName();
			String lastName = currentUser.getLastName();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("StartUpScreen.fxml"));
			Parent root = loader.load();
			StartUpScreenController controller = loader.getController();
			
			controller.setUser(firstName, lastName);
			
			Scene scene = new Scene(root);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
			
		} else
			errorLabel.setText("Log in failed");
	}
	
	/*
	 * If the register button is pressed, the screen is switched to the register screen
	 */
	public void registerButtonHandler(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterScreen.fxml"));
		Parent root = loader.load();
		
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/*
	 * Returns true if the username exists and the password matches, otherwise returns false
	 */
	private boolean authenticate() {
		// Placeholder for SQL Database
		ArrayList<User> users = UserDatabase.getUsers();
		for (User user: users) {
			if (user.getUsername().equals(usernameInput.getText())) {
				currentUser = user;
				return user.verifyPassword(passwordInput.getText());
			}
		}
		return false;
	}
}
