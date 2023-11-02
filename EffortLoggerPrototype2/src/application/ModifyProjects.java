package application;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

/*
 * Modify Project Controller
 * Controller for the ModifyProjects page
 * Author: Adam Shaeffer
 */


public class ModifyProjects {

	//FXML injections
    @FXML
    private ComboBox<String> typeSelection;
    @FXML
    private ComboBox<String> nameSelection;
    @FXML
    private ComboBox<String> infoSelection;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private TextField modifyField;
    @FXML
    private Label nameLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private Label modifyLabel;
    
    
    // Initialize specific elements of the modify projects page
    public void initialize() {
    	assert typeSelection != null : "fx:id=\"typeSelection\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert nameLabel != null : "fx:id=\"nameLabel\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert nameSelection != null : "fx:id=\"nameSelection\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert infoLabel != null : "fx:id=\"infoLabel\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert infoSelection != null : "fx:id=\"infoSelection\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert modifyLabel != null : "fx:id=\"modifyLabel\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert modifyField != null : "fx:id=\"modifyField\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert deleteBtn != null : "fx:id=\"deleteBtn\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert addBtn != null : "fx:id=\"addBtn\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert updateBtn != null : "fx:id=\"updateBtn\" was not injected: check your FXML file 'ModifyProjects.fxml'.";

    	// Add items to the combo box for modifying type of information
    	typeSelection.getItems().removeAll(typeSelection.getItems());
    	typeSelection.getItems().addAll("Project","Life Cycle Step","Effort Category","Plan","Deliverable","Interruption","Defect Category");
    	typeSelection.getSelectionModel().select("--Select type of information to be modified");
    	
    	// Make all other elements invisible until they can be edited
    	nameLabel.visibleProperty().setValue(false);
    	nameSelection.visibleProperty().setValue(false);
    	infoLabel.visibleProperty().setValue(false);
    	infoSelection.visibleProperty().setValue(false);
    	modifyLabel.visibleProperty().setValue(false);
    	modifyField.visibleProperty().setValue(false);
    	deleteBtn.visibleProperty().setValue(false);
    	addBtn.visibleProperty().setValue(false);
    	updateBtn.visibleProperty().setValue(false);
    }
    
    // Code to be executed when the selection of typeSelection drop-down has changed
    @FXML
    private void handleTypeSelected(ActionEvent event) {
    	// Make name options visible
    	nameLabel.visibleProperty().setValue(true);
    	nameSelection.visibleProperty().setValue(true);
    	nameSelection.getSelectionModel().clearSelection();
    	
    	// Load the correct options for the names based on selection of typeSelection
       	ArrayList<String> nameOptions = new ArrayList<String>();
    	String typeSelected = typeSelection.getSelectionModel().getSelectedItem();
    	nameLabel.setText("Select the " + typeSelected + " to edit:");
    	nameSelection.getSelectionModel().select("--Select a " + typeSelected);
    	if(typeSelected.equals("Project")) {
    		nameOptions.add("Business Project");
    		nameOptions.add("Development Project");
    	} else if(typeSelected.equals("Life Cycle Step")) {
    		nameOptions.add("Life Cycle Step 1");
    		nameOptions.add("Life Cycle Step 2");
    	} else if(typeSelected.equals("Effort Category")) {
    		nameOptions.add("Effort Category 1");
    		nameOptions.add("Effort Category 2");
    	} else if(typeSelected.equals("Plan")) {
    		nameOptions.add("Plan 1");
    		nameOptions.add("Plan 2");
    	} else if(typeSelected.equals("Deliverable")) {
    		nameOptions.add("Deliverable 1");
    		nameOptions.add("Deliverable 2");
    	} else if(typeSelected.equals("Interruption")) {
    		nameOptions.add("Interruption 1");
    		nameOptions.add("Interruption 2");
    	} else if(typeSelected.equals("Defect Category")) {
    		nameOptions.add("Defect Category 1");
    		nameOptions.add("Defect Category 2");
    	}
		nameOptions.add("Add new");
    	nameSelection.getItems().removeAll(nameSelection.getItems());
    	nameSelection.getItems().addAll(nameOptions);
    	nameOptions.clear();

    	//Make info options visible
    	infoLabel.visibleProperty().setValue(true);
    	infoSelection.visibleProperty().setValue(true);
    	infoSelection.getSelectionModel().clearSelection();
    	
    	// Load the correct options for the info based on selection of typeSelection
    	ArrayList<String> infoOptions = new ArrayList<String>();
    	infoLabel.setText("Select the " + typeSelected + "'s info to modify:");
    	infoSelection.getSelectionModel().select("--Select the " + typeSelected + "'s info to modify");
    	if(typeSelected.equals("Project")) {
    		infoOptions.add("Name");
    		infoOptions.add("Life Cycle Steps");
    	} else if(typeSelected.equals("Life Cycle Step")) {
    		infoOptions.add("Name");
    		infoOptions.add("Default Effort Category");
    		infoOptions.add("Default Deliverable");
    	} else if(typeSelected.equals("Effort Category")) {
    		infoOptions.add("Name");
    	} else if(typeSelected.equals("Plan")) {
    		infoOptions.add("Name");
    	} else if(typeSelected.equals("Deliverable")) {
    		infoOptions.add("Name");
    	} else if(typeSelected.equals("Interruption")) {
    		infoOptions.add("Name");
    	} else if(typeSelected.equals("Defect Category")) {
    		infoOptions.add("Name");
    	}
    	infoSelection.getItems().removeAll(infoSelection.getItems());
    	infoSelection.getItems().addAll(infoOptions);
    	if(infoOptions.size() < 2) {
    		infoSelection.getSelectionModel().select(0);
    	}
    	infoOptions.clear();
    }
    
    // Code to be executed when the selection of nameSelection drop-down has changed
	@FXML
	private void handleNameSelected(ActionEvent event) {
		// Make deleteBtn visible
		String typeSelected = typeSelection.getSelectionModel().getSelectedItem();
		String nameSelected = nameSelection.getSelectionModel().getSelectedItem();
		String infoSelected = infoSelection.getSelectionModel().getSelectedItem();
		ArrayList<String> unremovableTypes = new ArrayList<String>();
		unremovableTypes.add("Effort Category");
		
		if(nameSelected != null && nameSelected.equals("Add new")) {
	    	modifyLabel.visibleProperty().setValue(true);
	    	modifyField.visibleProperty().setValue(true);
	    	addBtn.visibleProperty().setValue(true);
			updateBtn.visibleProperty().setValue(false);
			deleteBtn.visibleProperty().setValue(false);
			modifyLabel.setText("Add the name for the new " + typeSelected + " here:");
			String infoValue = "--New name--";
			modifyField.setPromptText(infoValue);
			addBtn.setText("Add new " + typeSelected);
		} else if((infoSelected != null && infoSelected.charAt(0) != '-') && (nameSelected != null && nameSelected.charAt(0) != '-' && !nameSelected.equals("Add new"))) { // Both info and name have been selected
	    	modifyLabel.visibleProperty().setValue(true);
	    	modifyField.visibleProperty().setValue(true);
			updateBtn.visibleProperty().setValue(true);
			deleteBtn.visibleProperty().setValue(true);
			if(infoSelected.equals("Life Cycle Step")) {
				modifyLabel.setText("Modify the Life Cycle Steps here: (type the numbers of the desired life cycles separated by commas)");
			} else if(infoSelected.equals("Default Effort Category")) {
				modifyLabel.setText("Modify the Default Effort Category here: (type the number of the desired effort category)");
			} else if(infoSelected.equals("Default Deliverable")) {
				modifyLabel.setText("Modify the Defualt Deliverable here: (type the number of the desired deliverable)");
			} else {
				modifyLabel.setText("Modify the " + infoSelected + " here:");
			}
			String infoValue = "";
			if(infoSelected.equals("Name")) {
				infoValue = nameSelected;
			} else {
				infoValue = "Data (ex: '1' or '1,2')";
			}
			modifyField.setPromptText(infoValue);
			updateBtn.setText("Update " + infoSelected);
			deleteBtn.setText("Delete " + typeSelected);
			if(unremovableTypes.contains(typeSelected)) {
				deleteBtn.visibleProperty().setValue(false);
			}
		} else if((infoSelected == null || infoSelected.charAt(0) == '-') && (nameSelected != null && nameSelected.charAt(0) != '-')) { // Only name has been selected
	    	modifyLabel.visibleProperty().setValue(false);
	    	modifyField.visibleProperty().setValue(false);
			updateBtn.visibleProperty().setValue(false);
			deleteBtn.visibleProperty().setValue(true);
	    	addBtn.visibleProperty().setValue(false);
			deleteBtn.setText("Delete " + typeSelected);
			if(unremovableTypes.contains(typeSelected)) {
				deleteBtn.visibleProperty().setValue(false);
			}
		} else {
	    	modifyLabel.visibleProperty().setValue(false);
	    	modifyField.visibleProperty().setValue(false);
			updateBtn.visibleProperty().setValue(false);
			deleteBtn.visibleProperty().setValue(false);
		}
	}
	
	// Code to be executed when the selection of infoSelection drop-down has changed
	@FXML
	private void handleInfoSelected(ActionEvent event) {
		// Make modify options visible accordingly
		String typeSelected = typeSelection.getSelectionModel().getSelectedItem();
		String nameSelected = nameSelection.getSelectionModel().getSelectedItem();
		String infoSelected = infoSelection.getSelectionModel().getSelectedItem();
		ArrayList<String> unremovableTypes = new ArrayList<String>();
		unremovableTypes.add("Effort Category");
		
		if(nameSelected != null && nameSelected.equals("Add new")) {
	    	modifyLabel.visibleProperty().setValue(true);
	    	modifyField.visibleProperty().setValue(true);
	    	addBtn.visibleProperty().setValue(true);
			updateBtn.visibleProperty().setValue(false);
			deleteBtn.visibleProperty().setValue(false);
			modifyLabel.setText("Add the name for the new " + typeSelected + " here:");
			String infoValue = "--New name--";
			modifyField.setPromptText(infoValue);
			addBtn.setText("Add new " + typeSelected);
		} else if((infoSelected != null && infoSelected.charAt(0) != '-') && (nameSelected != null && nameSelected.charAt(0) != '-' && !nameSelected.equals("Add new"))) { // Both info and name have been selected
	    	modifyLabel.visibleProperty().setValue(true);
	    	modifyField.visibleProperty().setValue(true);
			updateBtn.visibleProperty().setValue(true);
			deleteBtn.visibleProperty().setValue(true);
			if(infoSelected.equals("Life Cycle Step")) {
				modifyLabel.setText("Modify the Life Cycle Steps here: (type the numbers of the desired life cycles separated by commas)");
			} else if(infoSelected.equals("Default Effort Category")) {
				modifyLabel.setText("Modify the Default Effort Category here: (type the number of the desired effort category)");
			} else if(infoSelected.equals("Default Deliverable")) {
				modifyLabel.setText("Modify the Defualt Deliverable here: (type the number of the desired deliverable)");
			} else {
				modifyLabel.setText("Modify the " + infoSelected + " here:");
			}
			String infoValue = "";
			if(infoSelected.equals("Name")) {
				infoValue = nameSelected;
			} else {
				infoValue = "Data (ex: '1' or '1,2')";
			}
			modifyField.setPromptText(infoValue);
			updateBtn.setText("Update " + infoSelected);
			deleteBtn.setText("Delete " + typeSelected);
			if(unremovableTypes.contains(typeSelected)) {
				deleteBtn.visibleProperty().setValue(false);
			}
		} else if((infoSelected == null || infoSelected.charAt(0) == '-') && (nameSelected != null && nameSelected.charAt(0) != '-')) { // Only name has been selected
	    	modifyLabel.visibleProperty().setValue(false);
	    	modifyField.visibleProperty().setValue(false);
			updateBtn.visibleProperty().setValue(false);
			deleteBtn.visibleProperty().setValue(true);
			deleteBtn.setText("Delete " + typeSelected);
			if(unremovableTypes.contains(typeSelected)) {
				deleteBtn.visibleProperty().setValue(false);
			}
		} else {
	    	modifyLabel.visibleProperty().setValue(false);
	    	modifyField.visibleProperty().setValue(false);
			updateBtn.visibleProperty().setValue(false);
			deleteBtn.visibleProperty().setValue(false);
		}
		
    	// Make add new button visible only when nameSelected is on "Add a new _"
	}


    @FXML
    private void handleButtonClick(ActionEvent event) {
        // I really don't recommend using a single handler like this,
        // but it will work
        if (event.getSource() == deleteBtn) {
        	
        }
        // etc...
    }
 }