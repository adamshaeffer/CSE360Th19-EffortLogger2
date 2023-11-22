package application;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/*
 * Controller for the register screen
 * Author: Jacob Lusenhop
 */
public class RegisterScreenController {
	private String accessPin = "1234";
	
	ObservableList<String> roleList = FXCollections.observableArrayList("Employee", "Supervisor");
	
	@FXML
	private TextField usernameInput;
	@FXML 
	private PasswordField passwordInput;
	@FXML
	private ChoiceBox<String> roleInput;
	@FXML
	private PasswordField accessPinInput;
	@FXML
	private Label accessPinLabel;
	@FXML 
	private Label errorLabel;
	@FXML
	private TextField firstNameInput;
	@FXML
	private TextField lastNameInput;
	
	@FXML
	private void initialize() {
		// Initialize choice box options
		roleInput.setItems(roleList);
		roleInput.setValue("Employee");
		
		// Hide access pin option when employee role is selected
		accessPinInput.setVisible(false);
		accessPinInput.setManaged(false);
		accessPinLabel.setVisible(false);
		accessPinLabel.setVisible(false);
	}
	
	/*
	 * If the register button is pressed, the user is added and returned to the login screen
	 */
	public void registerButtonHandler(ActionEvent event) throws IOException{
		String username = usernameInput.getText();
		String password = passwordInput.getText();
		String role = roleInput.getValue();
		String firstName = firstNameInput.getText();
		String lastName = lastNameInput.getText();
		
		boolean isValidInput = validateRole() & validatePassword() & validateUsername();
		
		if(isValidInput) {
			// Stand in for SQL database
			UserDatabase.addUser(username, password, role, firstName, lastName);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
			Parent root = loader.load();
			
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
	
	/*
	 * If the return to login button is pressed, the user is sent back to the login screen
	 */
	public void returnToLoginButtonHandler(ActionEvent event) throws IOException{
		// Return to login screen
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
		Parent root = loader.load();
		
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	/*
	 * Hides the access pin input when Employee is selected, shows it when supervisor is selected
	 */
	public void roleChoiceBoxHandler() {
		if(roleInput.getValue().equals("Employee")) {
			// Hide access pin
			accessPinInput.setVisible(false);
			accessPinInput.setManaged(false);
			accessPinLabel.setVisible(false);
			accessPinLabel.setVisible(false);
		} else {
			// Show access pin
			accessPinInput.setVisible(true);
			accessPinInput.setManaged(true);
			accessPinLabel.setVisible(true);
			accessPinLabel.setVisible(true);
		}
	}

	/*
	 * Validates the input for the username
	 */
	private boolean validateUsername() {
		String username = usernameInput.getText();
        
        if(username.isEmpty()) {
        	errorLabel.setText("Username field cannot be empty");
        	return false;
        } 
        
        if(Pattern.matches("[a-zA-Z0-9_.-]+", username) == false) {
        	errorLabel.setText("Username can only contain letters, numbers, underscores(-), periods(.), or hyphens(-)");
        	return false;
        }
        
        return true;
	}

	/*
	 * Validates the input for the password
	 */
	private boolean validatePassword() {
		String password = passwordInput.getText();
        
        if(password.length() < 8) {
        	errorLabel.setText("Password must be at least 8 characters");
        	return false;
        } 
        
        if(Pattern.matches("[a-zA-Z0-9!@#$%^&*]+", password) == false) {
        	errorLabel.setText("Password can only contain letters, numbers, and certain special characters(\"!@#$%^&*\")");
        	return false;
        }
        
        return true;
	}
	
	/*
	 * Validates if role and access pin agree
	 */
	private boolean validateRole() {
		// TODO Auto-generated method stub
		String role = roleInput.getValue();
		String accessPin = accessPinInput.getText();
		
		if(role == "Supervisor" && !accessPin.equals(this.accessPin)) {
			errorLabel.setText("Access pin incorrect");
			return false;
		}
		
		return true;
	}
	
	
}
