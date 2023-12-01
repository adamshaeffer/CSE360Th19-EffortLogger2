package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class MyManfredSceneController {

	@FXML
    private TableColumn<Projects, Integer> weightsColumn;

	public void switchToProjectDetails(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ProjectDetails.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 1250, 800);
		stage.setScene(scene);
		stage.show();
	}
    
    public void setWeightsColumn(TableColumn<Projects, Integer> weightsColumn) {
        this.weightsColumn = weightsColumn;
    }
    
    
    
    
    // Part involving the data being collected and transferred to new scene
    //******************************************************
    @FXML
    private TextArea userStoryTextArea;
    
    public void displayUserStoryText(String collectedUserStory) {
		userStoryTextArea.setText(collectedUserStory);
    	userStoryTextArea.setWrapText(true);
	}
    
    // Function used to put all the data in a textArea if you need it in a new scene
 	@FXML
     private TextArea dataTextArea;
 	
 	public void displayData(String projectName, double averageWeights, ObservableList<Projects> projectsList) {
        // Displaying the collected data in the text areas
 		dataTextArea.appendText("Project Name: " + projectName + "\n");
 		dataTextArea.appendText("Average Weight: " + averageWeights + "\n");

        StringBuilder projectsText = new StringBuilder("Projects List:\n");
        for (Projects project : projectsList) {
            projectsText.append(project.toString()).append("\n");
        }
        dataTextArea.appendText(projectsText.toString());
    }
 	
}
 	

