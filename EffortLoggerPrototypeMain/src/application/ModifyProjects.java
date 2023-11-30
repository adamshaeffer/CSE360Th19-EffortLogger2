package application;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    @FXML
    private Label instructionsLabel;

    private Alert warnings = new Alert(AlertType.WARNING);
    
    private ArrayList<ProjectClass> projects = new ArrayList<ProjectClass>();
    private ArrayList<LifeCycleStep> steps = new ArrayList<LifeCycleStep>();
    private ArrayList<String> efforts = new ArrayList<String>();
    private ArrayList<String> plans = new ArrayList<String>();
    private ArrayList<String> deliverables = new ArrayList<String>();
    private ArrayList<String> interruptions = new ArrayList<String>();
    private ArrayList<String> defects = new ArrayList<String>();
    
    // Initialize specific elements of the modify projects page
    public void initialize() throws IOException {
    	assert typeSelection != null : "fx:id=\"typeSelection\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert nameLabel != null : "fx:id=\"nameLabel\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert nameSelection != null : "fx:id=\"nameSelection\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert infoLabel != null : "fx:id=\"infoLabel\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert infoSelection != null : "fx:id=\"infoSelection\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert modifyLabel != null : "fx:id=\"modifyLabel\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert modifyField != null : "fx:id=\"modifyField\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert instructionsLabel != null : "fx:id=\"instructionsLabel\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert deleteBtn != null : "fx:id=\"deleteBtn\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert addBtn != null : "fx:id=\"addBtn\" was not injected: check your FXML file 'ModifyProjects.fxml'.";
    	assert updateBtn != null : "fx:id=\"updateBtn\" was not injected: check your FXML file 'ModifyProjects.fxml'.";

    	ArrayList<String> contents = readDefinitions("src/application/Definitions.txt");
//    	printStringArray(contents);
    	if(contents.get(0).charAt(0) == 'P') {
        	projects = getProjects(contents.get(0));
    	}
    	if(contents.get(1).charAt(0) == 'L') {
    		steps = getSteps(contents.get(1));
    	}
    	if(contents.get(2).charAt(0) == 'E') {
    		efforts = getNames(contents.get(2));
    	}
		plans = getNames(contents.get(3));
		String cat0 = efforts.get(0).substring(0,efforts.get(0).length()-1);
		String cat1 = efforts.get(1).substring(0,efforts.get(1).length()-1);
		String cat2 = efforts.get(2).substring(0,efforts.get(2).length()-1);
		String cat3 = efforts.get(3).substring(0,efforts.get(3).length()-1);
		deliverables = getNames(contents.get(4));
		interruptions = getNames(contents.get(5));
		defects = getNames(contents.get(6));
    	writeDefinitions("src/application/Definitions.txt");

    	// Add items to the combo box for modifying type of information
    	typeSelection.visibleProperty().setValue(true);
    	typeSelection.getItems().removeAll(typeSelection.getItems());
    	typeSelection.getItems().addAll("Project","User Story","Effort Category",cat0,cat1,cat2,cat3);
    	typeSelection.getSelectionModel().select("--Select type of information to be modified");
    	
    	// Make all other elements invisible until they can be edited
    	nameLabel.visibleProperty().setValue(false);
    	nameSelection.visibleProperty().setValue(false);
    	infoLabel.visibleProperty().setValue(false);
    	infoSelection.visibleProperty().setValue(false);
    	modifyLabel.visibleProperty().setValue(false);
    	modifyField.visibleProperty().setValue(false);
    	instructionsLabel.visibleProperty().setValue(false);
    	deleteBtn.visibleProperty().setValue(false);
    	addBtn.visibleProperty().setValue(false);
    	updateBtn.visibleProperty().setValue(false);
    }
    	
    
    // Code to be executed when the selection of typeSelection drop-down has changed
    @FXML
    private void handleTypeSelected(ActionEvent event) {
    	// Make everything else invisible
    	nameLabel.visibleProperty().setValue(false);
    	nameSelection.visibleProperty().setValue(false);
    	infoLabel.visibleProperty().setValue(false);
    	infoSelection.visibleProperty().setValue(false);
    	modifyLabel.visibleProperty().setValue(false);
    	modifyField.visibleProperty().setValue(false);
    	instructionsLabel.visibleProperty().setValue(false);
    	deleteBtn.visibleProperty().setValue(false);
    	addBtn.visibleProperty().setValue(false);
    	updateBtn.visibleProperty().setValue(false);
    	    	
    	// Make name options visible
    	nameLabel.visibleProperty().setValue(true);
    	nameSelection.visibleProperty().setValue(true);
    	nameSelection.getSelectionModel().clearSelection();
    	nameSelection.valueProperty().set(null);
    	nameSelection.getItems().removeAll(nameSelection.getItems());
    	
    	// Load the correct options for the names based on selection of typeSelection
       	ArrayList<String> nameOptions = new ArrayList<String>();
    	String typeSelected = typeSelection.getSelectionModel().getSelectedItem();
    	int typeIndex = typeSelection.getSelectionModel().getSelectedIndex();
    	nameLabel.setText("Select the " + typeSelected + " to edit:");
    	nameSelection.getItems().add("--Select a " + typeSelected + "--");
    	nameSelection.getSelectionModel().select(0);
    	getNameOptions(nameOptions,typeIndex);
    	nameSelection.getItems().addAll(nameOptions);
    	nameOptions.clear();

    	//Make info options visible
    	infoLabel.visibleProperty().setValue(true);
    	infoSelection.visibleProperty().setValue(true);
    	infoSelection.getSelectionModel().clearSelection();
    	infoSelection.getItems().removeAll(infoSelection.getItems());
    	
    	// Load the correct options for the info based on selection of typeSelection
    	ArrayList<String> infoOptions = new ArrayList<String>();
    	infoLabel.setText("Select the " + typeSelected + "'s info to modify:");
    	infoSelection.getItems().add("--Select the " + typeSelected + "'s info to modify--");
    	infoSelection.getSelectionModel().select(0);
    	getInfoOptions(infoOptions,typeIndex);
    	infoSelection.getItems().addAll(infoOptions);
    	if(infoOptions.size() < 2) {
			infoSelection.getItems().removeAll(infoSelection.getItems());
			infoSelection.getItems().add("Name");
			infoSelection.getSelectionModel().select(0);
    	}
    	infoOptions.clear();
    }
    
    // Code to be executed when the selection of nameSelection drop-down has changed
	@FXML
	private void handleNameSelected(ActionEvent event) {
		String typeSelected = typeSelection.getSelectionModel().getSelectedItem();
		int typeIndex = typeSelection.getSelectionModel().getSelectedIndex();
		String nameSelected = nameSelection.getSelectionModel().getSelectedItem();
		ArrayList<String> infoOptions = new ArrayList<String>();
    	getInfoOptions(infoOptions,typeIndex);
    	if(infoSelection.getItems().indexOf("Name") == 0) {
	    	infoSelection.getItems().removeAll(infoSelection.getItems());
	    	infoSelection.getItems().add("--Select the " + typeSelected + "'s info to modify--");
	    	infoSelection.getItems().addAll(infoOptions);
	    	infoSelection.getSelectionModel().select(0);
    	}
    	if(infoOptions.size() < 2) {
			infoSelection.getItems().removeAll(infoSelection.getItems());
			infoSelection.getItems().add("Name");
			infoSelection.getSelectionModel().select(0);
    	}
		String infoSelected = infoSelection.getSelectionModel().getSelectedItem();
		ArrayList<String> unremovableTypes = new ArrayList<String>();
		unremovableTypes.add("Effort Category");
		
		if(nameSelected != null && nameSelected.equals("Add new")) { 
			// Add new selected -> only add new button shows up / forces infoSelection to "Name"
	    	modifyLabel.visibleProperty().setValue(true);
	    	modifyField.visibleProperty().setValue(true);
	    	instructionsLabel.visibleProperty().setValue(true);
	    	addBtn.visibleProperty().setValue(true);
			updateBtn.visibleProperty().setValue(false);
			deleteBtn.visibleProperty().setValue(false);
			modifyLabel.setText("Add the name for the new " + typeSelected + " here:");
			instructionsLabel.setText("Must be at most 30 characters in length and contain only numbers, letters, spaces, and hyphens.\nCannot be empty.");
			String infoValue = "--New name--";
			modifyField.setPromptText(infoValue);
			addBtn.setText("Add new " + typeSelected);

			// Force infoSelection into the name field. When adding a new item, need to have name first, can modify other info later
			infoSelection.getItems().removeAll(infoSelection.getItems());
			infoSelection.getItems().add("Name");
			infoSelection.getSelectionModel().select(0);
		} else if((infoSelected != null && infoSelected.charAt(0) != '-') && (nameSelected != null && nameSelected.charAt(0) != '-' && !nameSelected.equals("Add new"))) {
			// Both info and name have been selected -> deleteBtn and updateBtn are available options
	    	modifyLabel.visibleProperty().setValue(true);
	    	modifyField.visibleProperty().setValue(true);
	    	instructionsLabel.visibleProperty().setValue(true);
	    	addBtn.visibleProperty().setValue(false);
			updateBtn.visibleProperty().setValue(true);
			deleteBtn.visibleProperty().setValue(true);
			if(infoSelected.equals("Life Cycle Step")) {
				modifyLabel.setText("Modify the Life Cycle Steps here:");
			} else if(infoSelected.equals("Default Effort Category")) {
				modifyLabel.setText("Modify the Default Effort Category here:");
			} else if(infoSelected.equals("Default Deliverable")) {
				modifyLabel.setText("Modify the Defualt Deliverable here:");
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
			if(infoSelected.equals("Name")) {
				instructionsLabel.setText("Must be at most 30 characters in length and contain only numbers, letters, spaces, hyphens, and underscores.\nCannot be empty.");
			} else if(infoSelected.equals("Life Cycle Steps")) {
				instructionsLabel.setText("Must contain only numbers and commas, with each number separated by a comma.\nNumbers cannot exceed 100 and there must be at most 30 numbers. Cannot be empty.");
			} else {
				instructionsLabel.setText("Must contain a single number less than or equal to 20. Cannot be empty or 0.");
			}
		} else if((infoSelected == null || infoSelected.charAt(0) == '-') && (nameSelected != null && nameSelected.charAt(0) != '-')) { 
			// Only name has been selected -> only deleteBtn is available to press
	    	modifyLabel.visibleProperty().setValue(false);
	    	modifyField.visibleProperty().setValue(false);
	    	instructionsLabel.visibleProperty().setValue(false);
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
			// Nothing should change after using infoSelection (it should always be "Name" and nothing else)
		} else if((infoSelected != null && infoSelected.charAt(0) != '-') && (nameSelected != null && nameSelected.charAt(0) != '-' && !nameSelected.equals("Add new"))) { 
			// Both info and name have been selected -> deleteBtn and updateBtn are available options
			modifyLabel.visibleProperty().setValue(true);
	    	modifyField.visibleProperty().setValue(true);
	    	instructionsLabel.visibleProperty().setValue(true);
	    	addBtn.visibleProperty().setValue(false);
			updateBtn.visibleProperty().setValue(true);
			deleteBtn.visibleProperty().setValue(true);
			if(infoSelected.equals("Life Cycle Step")) {
				modifyLabel.setText("Modify the Life Cycle Steps here:");
			} else if(infoSelected.equals("Default Effort Category")) {
				modifyLabel.setText("Modify the Default Effort Category here:");
			} else if(infoSelected.equals("Default Deliverable")) {
				modifyLabel.setText("Modify the Default Deliverable here:");
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
			if(infoSelected.equals("Name")) {
				instructionsLabel.setText("Must be at most 30 characters in length and contain only numbers, letters, spaces, hyphens, and underscores.\nCannot be empty.");
			} else if(infoSelected.equals("Life Cycle Steps")) {
				instructionsLabel.setText("Must contain only numbers and commas, with each number separated by a comma.\nNumbers cannot exceed 100 and there must be at most 30 numbers. Cannot be empty.");
			} else if(infoSelected.equals("Default Effort Category")) {
				instructionsLabel.setText("Must contain a single number between 1 and 4 (inclusive).");
			} else if(infoSelected.equals("Default Deliverable")) {
				instructionsLabel.setText("Must contain a single number between 1 and 20 (inclusive).");
			}
		} else if((infoSelected == null || infoSelected.charAt(0) == '-') && (nameSelected != null && nameSelected.charAt(0) != '-')) { 
			// Only name has been selected -> only deleteBtn is available to press
			modifyLabel.visibleProperty().setValue(false);
	    	modifyField.visibleProperty().setValue(false);
	    	instructionsLabel.visibleProperty().setValue(false);
	    	addBtn.visibleProperty().setValue(false);
			updateBtn.visibleProperty().setValue(false);
			deleteBtn.visibleProperty().setValue(true);
			deleteBtn.setText("Delete " + typeSelected);
			if(unremovableTypes.contains(typeSelected)) {
				deleteBtn.visibleProperty().setValue(false);
			}
		} else {
	    	modifyLabel.visibleProperty().setValue(false);
	    	modifyField.visibleProperty().setValue(false);
	    	instructionsLabel.visibleProperty().setValue(false);
	    	addBtn.visibleProperty().setValue(false);
			updateBtn.visibleProperty().setValue(false);
			deleteBtn.visibleProperty().setValue(false);
		}
		
	}

	// Code to be executed when deleteBtn is clicked
    @FXML
    private void handleDeleteClick(ActionEvent event) throws IOException {
		String typeSelected = typeSelection.getSelectionModel().getSelectedItem();
		int typeIndex = typeSelection.getSelectionModel().getSelectedIndex();
		String nameSelected = nameSelection.getSelectionModel().getSelectedItem();
		int nameIndex = nameSelection.getSelectionModel().getSelectedIndex();
    	System.out.println("delete button pressed");
    	System.out.printf("%s(%d): '%s'(%d) will be deleted.\n",typeSelected,typeIndex,nameSelected,nameIndex);
    	switch(typeIndex) {
    	case 0:
    		projects.remove(nameIndex-1);
//        		printProjectArray(projects);
    		break;
    	case 1:
    		steps.remove(nameIndex-1);
    		shiftSteps(nameIndex);
//        		printStepArray(steps);
    		break;
    	case 2:
			warnings.setHeaderText("WARNING: unreachable code");
			warnings.setContentText("Somehow got a different item type than expected.");
    		warnings.show();
    		break;
    	case 3:
    		plans.remove(nameIndex-1);
//        		printNameArray(plans);
    		break;
    	case 4:
    		deliverables.remove(nameIndex-1);
//        		printNameArray(deliverables);
    		break;
    	case 5:
    		interruptions.remove(nameIndex-1);
//        		printNameArray(interruptions);
    		break;
    	case 6:
    		defects.remove(nameIndex-1);
//        		printNameArray(defects);
    		break;
    	default:
			warnings.setHeaderText("WARNING: unreachable code");
			warnings.setContentText("Somehow got a different item type than expected.");
    		warnings.show();
    	}
       	ArrayList<String> nameOptions = new ArrayList<String>();
       	nameSelection.getItems().removeAll(nameSelection.getItems());
    	nameSelection.getItems().add("--Select a " + typeSelected + "--");
    	nameSelection.getSelectionModel().select(0);
    	getNameOptions(nameOptions,typeIndex);
    	nameSelection.getItems().addAll(nameOptions);
    	nameOptions.clear();
    	infoSelection.getSelectionModel().select(0);
    	modifyField.setText("");
    	writeDefinitions("src/application/Definitions.txt");
    }

	// Code to be executed when addBtn is clicked
    @FXML
    private void handleAddClick(ActionEvent event) throws IOException {
		String typeSelected = typeSelection.getSelectionModel().getSelectedItem();
		int typeIndex = typeSelection.getSelectionModel().getSelectedIndex();
		String modifyInput = modifyField.getText();
    	if(checkContents(modifyInput,1)) { // method to enforce restrictions for names here (ie must start with letter, can only consist of letters, numbers, and spaces, etc)
    		boolean repeat = false;
    		switch(typeIndex) {
    		case 0:
    			for(int i=0; i<projects.size(); i++) {
    				if(modifyInput.equals(projects.get(i).getName())) {
    					repeat = true;
    				}
    			}
    			if(!repeat) {
    				ArrayList<Integer> list = new ArrayList<Integer>();
    				ProjectClass proj = new ProjectClass(modifyInput,list);
    				projects.add(proj);
    			}
    			break;
    		case 1:
    			for(int i=0; i<steps.size(); i++) {
    				if(modifyInput.equals(steps.get(i).getName())) {
    					repeat = true;
    				}
    			}
    			if(!repeat) {
    				LifeCycleStep step = new LifeCycleStep(modifyInput,0,0);
    				steps.add(step);
    			}
    			break;
    		case 2:
				warnings.setHeaderText("WARNING: unreachable code");
				warnings.setContentText("Somehow got a different item type than expected.");
	    		warnings.show();
    			break;
    		case 3:
    			for(int i=0; i<plans.size(); i++) {
    				if(modifyInput.equals(plans.get(i))) {
    					repeat = true;
    				}
    			}
    			if(!repeat) {
    				plans.add(modifyInput);
    			}
    			break;
    		case 4:
    			for(int i=0; i<deliverables.size(); i++) {
    				if(modifyInput.equals(deliverables.get(i))) {
    					repeat = true;
    				}
    			}
    			if(!repeat) {
    				deliverables.add(modifyInput);
    			}
    			break;
    		case 5:
    			for(int i=0; i<interruptions.size(); i++) {
    				if(modifyInput.equals(interruptions.get(i))) {
    					repeat = true;
    				}
    			}
    			if(!repeat) {
    				interruptions.add(modifyInput);
    			}
    			break;
    		case 6:
    			for(int i=0; i<defects.size(); i++) {
    				if(modifyInput.equals(defects.get(i))) {
    					repeat = true;
    				}
    			}
    			if(!repeat) {
    				defects.add(modifyInput);
    			}
    			break;
        	default:
				warnings.setHeaderText("WARNING: unreachable code");
				warnings.setContentText("Somehow got a different item type than expected.");
	    		warnings.show();
    		}
        	System.out.println("add button pressed");
        	System.out.printf("New %s(%d) named '%s' will be added.\n",typeSelected,typeIndex,modifyInput);
           	ArrayList<String> nameOptions = new ArrayList<String>();
           	nameSelection.getItems().removeAll(nameSelection.getItems());
        	nameSelection.getItems().add("--Select a " + typeSelected + "--");
        	getNameOptions(nameOptions,typeIndex);
        	nameSelection.getItems().addAll(nameOptions);
        	nameSelection.getSelectionModel().select(modifyInput);
        	nameOptions.clear();
        	infoSelection.getItems().removeAll(infoSelection.getItems());
        	ArrayList<String> infoOptions = new ArrayList<String>();
        	infoLabel.setText("Select the " + typeSelected + "'s info to modify:");
        	infoSelection.getItems().add("--Select the " + typeSelected + "'s info to modify--");
        	infoSelection.getSelectionModel().select(0);
        	getInfoOptions(infoOptions,typeIndex);
        	infoSelection.getItems().addAll(infoOptions);
        	if(infoOptions.size() < 2) {
        		infoSelection.getSelectionModel().select(0);
        	}
        	infoOptions.clear();
        	modifyField.setText("");
        	writeDefinitions("src/application/Definitions.txt");
    	}
    	else {
    		warnings.setHeaderText("WARNING: input does not meet requirements");
    		warnings.setContentText("The name you have entered does not meet requirements. Please try a different name that meets requirements.");
    		warnings.show();
    		modifyField.setText("");
    	}
    }
    
	// Code to be executed when update2Btn is clicked
    @FXML
    private void handleUpdateClick(ActionEvent event) throws IOException {
		String typeSelected = typeSelection.getSelectionModel().getSelectedItem();
		int typeIndex = typeSelection.getSelectionModel().getSelectedIndex();
		String nameSelected = nameSelection.getSelectionModel().getSelectedItem();
		int nameIndex = nameSelection.getSelectionModel().getSelectedIndex();
		String infoSelected = infoSelection.getSelectionModel().getSelectedItem();
		int infoIndex = 0;
		String modifyInput = modifyField.getText();
		if(infoSelected.equals("Name")) {
			infoIndex = 1;
		} else if(infoSelected.equals("Life Cycle Steps")) {
			infoIndex = 2;
		} else if(infoSelected.equals("Default Effort Category")) {
			infoIndex = 3;
		} else if(infoSelected.equals("Default Deliverable")) {
			infoIndex = 4;
		}
		if(checkContents(modifyInput,infoIndex)) {
			System.out.println("update button pressed");
			System.out.printf("Modifying %s(%d) '%s'(%d). Setting %s's %s(%d) to '%s'\n",typeSelected,typeIndex,nameSelected,nameIndex,typeSelected,infoSelected,infoIndex,modifyInput);			
			switch(infoIndex) {
			case 1:
	           	ArrayList<String> nameOptions = new ArrayList<String>();
	        	getNameOptions(nameOptions,typeIndex);
	        	if(nameOptions.contains(modifyInput)) {
	        		warnings.setHeaderText("WARNING: name already exists as " + typeSelected);
	        		warnings.setContentText("This name is already being used by another item of this type. Please input a different name.");
	        		warnings.show();
	        		break;
	        	}
				switch(typeIndex) {
				case 0:
					// Check repeat names
					projects.get(nameIndex-1).setName(modifyInput);
					break;
				case 1:
					steps.get(nameIndex-1).setName(modifyInput);
					break;
				case 2:
					efforts.set(nameIndex-1, modifyInput);
					break;
				case 3:
					plans.set(nameIndex-1, modifyInput);
					break;
				case 4:
					deliverables.set(nameIndex-1, modifyInput);
					break;
				case 5:
					interruptions.set(nameIndex-1, modifyInput);
					break;
				case 6:
					defects.set(nameIndex-1, modifyInput);
					break;
				default:
					warnings.setHeaderText("WARNING: unreachable code");
					warnings.setContentText("Somehow got a different item type than expected.");
		    		warnings.show();
				}
				break;
			case 2: // Breaking down the life cycle steps list
				if(typeIndex != 0) {
					warnings.setHeaderText("WARNING: unreachable code");
					warnings.setContentText("Somehow got a different item type than expected.");					
		    		warnings.show();
		    		break;
				}
				// First convert the list into an actual list
				ArrayList<Integer> pSteps = new ArrayList<Integer>();
				String temp = modifyInput;
				boolean good = true;
				while(temp.indexOf(',') >= 0) {
					int s = Integer.parseInt(temp.substring(0,temp.indexOf(',')));
					if(s > 100) {
						good = false;
						break;
					}
					pSteps.add(s);
					temp = temp.substring(temp.indexOf(',')+1);
				}
				int s = Integer.parseInt(temp);
				if(s > 100) {
					good = false;
				}
				pSteps.add(s);
				if(pSteps.size() > 30) {
					warnings.setHeaderText("WARNING: too many values");
					warnings.setContentText("Input contains more values of life cycle steps than is allowed. Please reduce the number of life cycle steps.");
					warnings.show();
					break;
				}
				if(!good) {
					warnings.setHeaderText("WARNING: values out of range");
					warnings.setContentText("Inupt contains one or more values outised of the range allowed. Please review instructions and try again.");
				}
				projects.get(nameIndex-1).setSteps(pSteps);
				break;
			case 3:
				if(typeIndex != 1) {
					warnings.setHeaderText("WARNING: unreachable code");
					warnings.setContentText("Somehow got a different item type than expected.");					
		    		warnings.show();
		    		break;
				}
				int mod = Integer.parseInt(modifyInput);
				if(mod <= 0 || mod > 4) {
		    		warnings.setHeaderText("WARNING: input does not meet requirements");
	        		warnings.setContentText("The value you have entered does not meet requirements. Please try again.");
		    		warnings.show();
		    		break;
				}
				steps.get(nameIndex-1).setEffort(mod);
				break;
			case 4:
				if(typeIndex != 1) {
					warnings.setHeaderText("WARNING: unreachable code");
					warnings.setContentText("Somehow got a different item type than expected.");					
		    		warnings.show();
		    		break;
				}
				mod = Integer.parseInt(modifyInput);
				if(mod <= 0 || mod > 20) {
		    		warnings.setHeaderText("WARNING: input does not meet requirements");
	        		warnings.setContentText("The value you have entered does not meet requirements. Please try again.");
		    		warnings.show();
		    		break;
				}
				steps.get(nameIndex-1).setDeliverable(mod);
				break;
			default:
				warnings.setHeaderText("WARNING: unreachable code");
				warnings.setContentText("Somehow got a different info type than expected.");
	    		warnings.show();
			}
    		String cat0 = efforts.get(0).substring(0,efforts.get(0).length()-1);
    		String cat1 = efforts.get(1).substring(0,efforts.get(1).length()-1);
    		String cat2 = efforts.get(2).substring(0,efforts.get(2).length()-1);
    		String cat3 = efforts.get(3).substring(0,efforts.get(3).length()-1);
        	typeSelection.getItems().removeAll(typeSelection.getItems());
        	typeSelection.getItems().addAll("Project","Life Cycle Step","Effort Category",cat0,cat1,cat2,cat3);
        	typeSelection.getSelectionModel().select(typeIndex);
           	ArrayList<String> nameOptions = new ArrayList<String>();
           	nameSelection.getItems().removeAll(nameSelection.getItems());
        	nameSelection.getItems().add("--Select a " + typeSelected + "--");
        	getNameOptions(nameOptions,typeIndex);
        	nameSelection.getItems().addAll(nameOptions);
        	nameSelection.getSelectionModel().select(nameIndex);
        	nameOptions.clear();
        	infoSelection.getItems().removeAll(infoSelection.getItems());
        	ArrayList<String> infoOptions = new ArrayList<String>();
        	infoLabel.setText("Select the " + typeSelected + "'s info to modify:");
        	infoSelection.getItems().add("--Select the " + typeSelected + "'s info to modify--");
        	getInfoOptions(infoOptions,typeIndex);
        	infoSelection.getItems().addAll(infoOptions);
        	infoSelection.getSelectionModel().select(infoSelected);
        	if(infoOptions.size() < 2) {
        		infoSelection.getSelectionModel().select(1);
        	}
        	infoOptions.clear();
        	modifyField.setText("");
        	
        	writeDefinitions("src/application/Definitions.txt");
		} else {
    		warnings.setHeaderText("WARNING: input does not meet requirements");
    		switch(infoIndex) {
    		case 1:
        		warnings.setContentText("The name you have entered does not meet requirements. Please try a different name that meets requirements.");
    			break;
    		case 2:
        		warnings.setContentText("The set of life cycle steps you have entered does not meet requirements. Please try again.");
    			break;
    		default:
        		warnings.setContentText("The value you have entered does not meet requirements. Please try again.");
    		}
    		warnings.show();
    		modifyField.setText("");
		}
    }
        
    // Method for getting all name options for the selected item type
    private void getNameOptions(ArrayList<String> nameOptions, int typeIndex) {
    	switch(typeIndex) {
    	case 0:
        	for(int i=0; i<projects.size(); i++) {
    			nameOptions.add(projects.get(i).getName());
    		}
    		break;
    	case 1:
    		for(int i=0; i<steps.size(); i++) {
    			nameOptions.add(steps.get(i).getName());
    		}
    		break;
    	case 2:
    		for(int i=0; i<efforts.size(); i++) {
    			nameOptions.add(efforts.get(i));
    		}
    		break;
    	case 3:
    		for(int i=0; i<plans.size(); i++) {
    			nameOptions.add(plans.get(i));
    		}
    		break;
    	case 4:
    		for(int i=0; i<deliverables.size(); i++) {
    			nameOptions.add(deliverables.get(i));
    		}
    		break;
    	case 5:
    		for(int i=0; i<interruptions.size(); i++) {
    			nameOptions.add(interruptions.get(i));
    		}
    		break;
    	case 6:
    		for(int i=0; i<defects.size(); i++) {
    			nameOptions.add(defects.get(i));
    		}
    		break;
    	default:
    		// Warning
    	}
    	if(typeIndex != 2) { 
    		// Effort Categories are crucial to how the code works, so can only "add new" to the other item types
    		nameOptions.add("Add new");
    	}
    }
    
    // Method for getting all info options for the selected item type
    private void getInfoOptions(ArrayList<String> infoOptions, int typeIndex) {
    	switch(typeIndex) {
    	case 0:
    		infoOptions.add("Name");
    		infoOptions.add("Life Cycle Steps");
    		break;
    	case 1:
    		infoOptions.add("Name");
    		infoOptions.add("Default Effort Category");
    		infoOptions.add("Default Deliverable");
    		break;
    	default:
    		infoOptions.add("Name");
    	}
    }
    
    // Method to read in the entire definitions database as a .txt file and store the contents
    private ArrayList<String> readDefinitions(String fileName) throws FileNotFoundException  {
    	ArrayList<String> contents = new ArrayList<String>();
    	File file = new File(fileName);
    	Scanner scan = new Scanner(file);
    	while(scan.hasNextLine()) {
    		contents.add(scan.nextLine());
    	}
    	scan.close();
    	return contents;
    }
    
    // Method to write all of the items as strings and store them in a .txt file
    private void writeDefinitions(String fileName) throws IOException {
    	ArrayList<String> items = new ArrayList<String>();
    	String line = "Projects={";
    	for(int i=0; i<projects.size(); i++) {
    		line = line + "{\"" + projects.get(i).getName() + "\",[";
    		for(int j=0; j<projects.get(i).getSteps().size(); j++) {
    			line = line + "" + projects.get(i).getSteps().get(j);
    			if(j < projects.get(i).getSteps().size()-1) {
    				line += ",";
    			}
    		}
    		line += "]}";
    		if(i < projects.size()-1) {
    			line += ",";
    		}
    	}
    	line += "}";
    	items.add(line);
    	// Adding the lifecyclesteps
    	line = "LifeCycleSteps={";
    	for(int i=0; i<steps.size(); i++) {
    		line = line + "{\"" + steps.get(i).getName() + "\"," + steps.get(i).getEffort() + "," + steps.get(i).getDeliverable() + "}";
    		if(i < steps.size()-1) {
    			line += ",";
    		}
    	}
    	line += "}";
    	items.add(line);
    	// Adding the effort categories
    	line = "EffortCategories={";
    	line = addNames(line,efforts);
    	line += "}";
    	items.add(line);
    	// Adding the plans
    	line = efforts.get(0) + "={";
    	line = addNames(line,plans);
    	line += "}";
    	items.add(line);
    	// Adding the deliverables
    	line = efforts.get(1) + "={";
    	line = addNames(line,deliverables);
    	line += "}";
    	items.add(line);
    	// Adding the interruptions
    	line = efforts.get(2) + "={";
    	line = addNames(line,interruptions);
    	line += "}";
    	items.add(line);
    	// Adding the defect categories
    	line = efforts.get(3) + "={";
    	line = addNames(line,defects);
    	line += "}";
    	items.add(line);
    	FileWriter writer = new FileWriter(fileName);
    	for(int i=0; i<items.size(); i++) {
    		writer.write(items.get(i));
    		if(i < items.size()-1) {
    			writer.write("\n");
    		}
    	}
    	writer.close();
    }
        
    private ArrayList<ProjectClass> getProjects(String line) {
    	ArrayList<ProjectClass> projects = new ArrayList<ProjectClass>();
    	line = line.substring(line.indexOf('{')+1);
    	while(line.charAt(0) == '{') {
    		line = line.substring(2);
    		String name = line.substring(0,line.indexOf('"'));
    		line = line.substring(line.indexOf('[')+1);
    		ArrayList<Integer> list = new ArrayList<Integer>();
    		while(line.charAt(0) != ']') {
    			if(line.indexOf(',') > 0 && line.indexOf(',') < line.indexOf(']')) {
    				list.add(Integer.parseInt(line.substring(0,line.indexOf(','))));
    				if(line.indexOf(',') < 0) {
    					line = line.substring(line.indexOf('}')+1);
    					break;
    				}
    				else if(line.indexOf(']') < line.indexOf(',')) {
    					line = line.substring(line.indexOf('{'));
    					break;
    				}
    				line = line.substring(line.indexOf(',')+1);
    			}
    			else {
    				list.add(Integer.parseInt(line.substring(0,line.indexOf(']'))));
    				line = line.substring(line.indexOf(']'));
    			}
    		}
    		ProjectClass proj = new ProjectClass(name,list);
    		projects.add(proj);
    		if(line.indexOf('{') < 0) {
    			break;
    		}
    		while(line.charAt(0) != '{') {
    			line = line.substring(1);
    		}
    	}
    	return projects;
    }
    
    private ArrayList<LifeCycleStep> getSteps(String line) {
    	ArrayList<LifeCycleStep> steps = new ArrayList<LifeCycleStep>();
    	line = line.substring(line.indexOf('{')+1);
    	while(line.charAt(0) == '{') {
    		line = line.substring(2);
    		String name = line.substring(0,line.indexOf('"'));
    		line = line.substring(line.indexOf(',')+1);
    		int effort = Integer.parseInt(line.substring(0,line.indexOf(',')));
    		int deliverable = Integer.parseInt(line.substring(line.indexOf(',')+1,line.indexOf('}')));
    		line = line.substring(line.indexOf('}'));
    		LifeCycleStep step = new LifeCycleStep(name,effort,deliverable);
    		steps.add(step);
//    		printStepArray(steps);
    		if(line.indexOf('{') < 0) {
    			break;
    		}
    		while(line.charAt(0) != '{') {
    			line = line.substring(1);
    		}
    	}
    	return steps;
    }

    private ArrayList<String> getNames(String line) {
    	ArrayList<String> names = new ArrayList<String>();
    	line = line.substring(line.indexOf('{')+1);
    	while(line.charAt(0) == '"') {
    		line = line.substring(1);
    		String name = line.substring(0,line.indexOf('"'));
    		line = line.substring(line.indexOf('"')+1);
    		names.add(name);
    		if(line.indexOf('"') < 0) {
    			break;
    		}
    		while(line.charAt(0) != '"') {
    			line = line.substring(1);
    		}
    	}
    	return names;
    }
    
    private String addNames(String line, ArrayList<String> arr) {
    	for(int i=0; i<arr.size(); i++) {
    		line = line + "\"" + arr.get(i) + "\"";
    		if(i < arr.size()-1) {
    			line += ",";
    		}
    	}
    	return line;
    }
    
    // Method to check that the contents of an input value fits the parameters set.
    private boolean checkContents(String input, int type) {
    	int len = input.length();
    	switch(type) {
    	case 1: // checking the contents for a name (0 < length <= 30; only numbers, letters, spaces, hyphens, and underscores 
    		if(len <= 0 || len > 30) {
    			return false;
    		}
    		for(int i = 0; i < len; i++) {
    			char c = input.charAt(i);
    			if(!(c == 32 || c == 45 || (c >= 65 && c <= 90) || c == 95 || (c >= 97 && c <= 122))) {
    				return false;
    			}
    		}
    		return true;
    	case 2: // checking the contents for life cycle step (only numbers and commas, numbers: 1-100, len(numbers): 1-30, no repeating numbers, begin and end with numbers
    		if(len <= 0) {
    			return false;
    		}
    		if(input.charAt(0) == 44 || input.charAt(len-1) == 44) {
    			return false;
    		}
    		for(int i = 0; i < len; i++) {
    			char c = input.charAt(i);
    			if(!(c == 44 || (c >= 48 && c <= 57))) {
    				return false;
    			}
    			if(i < len-1 && c == 44 && input.charAt(i+1) == 44) {
    				return false;
    			}
    		}
    		return true;
    	case 3: // checking the contents for default effort category (only a single number, number: 1-4
    		if(len > 1 || len <= 0) {
    			return false;
    		} else {
	    		char c = input.charAt(0);
	    		if(!(c >= 49 && c <= 52)) {
	    			return false;
	    		}
    		}
    		return true;
    	case 4: // checking the contents for default deliverable (only a single number, number: 1-20
    		if(len > 2 || len <= 0) {
    			return false;
    		}
    		for(int i = 0; i < len; i++) {
    			char c = input.charAt(i);
    			if(!(c >= 48 && c <= 57)) {
    				return false;
    			}
    		}
    		return true;
    	default: // should have no other option, but just in case...
    		return false;
    	}
//		default effort category: Must contain a single number between 1 and 4 (inclusive).
//		default deliverable: Must contain a single number between 1 and 20 (inclusive).
    }
    
    public void shiftSteps(int index) {
    	for(int i=0; i<projects.size(); i++) {
    		ArrayList<Integer> projSteps = projects.get(i).getSteps();
    		for(int j=0; j<projSteps.size(); j++) {
    			if(projSteps.get(j) == index) {
    				System.out.printf("Removed step: %d / %d\n",projSteps.get(j),index);
    				projSteps.remove(j);
    			}
    			if(projSteps.get(j) > index) {
    				System.out.printf("Shifted step: %d to %d\n",projSteps.get(j),projSteps.get(j)-1);
    				projSteps.set(j, projSteps.get(j)-1);
    			}
    		}
    		projects.get(i).setSteps(projSteps);
    	}
    }

    public void printStringArray(ArrayList<String> arr) {
    	for(int i=0; i<arr.size(); i++) {
    		System.out.println(arr.get(i));
    	}
    	System.out.println("");
    }
    
    public void printNameArray(ArrayList<String> arr) {
    	for(int i=0; i<arr.size(); i++) {
    		System.out.printf("Name: '%s'\n",arr.get(i));
    	}
    }

    public void printIntegerArray(ArrayList<Integer> arr) {
    	System.out.print("[");
    	for(int i=0; i<arr.size()-1; i++) {
    		System.out.printf("%d,",arr.get(i));
    	}
    	System.out.printf("%d]\n",arr.get(arr.size()-1));
    }
    
    public void printProjectArray(ArrayList<ProjectClass> arr) {
    	System.out.println("PROJECTS:");
    	for(int i=0; i<arr.size(); i++) {
    		System.out.printf("Name: '%s', steps: ",arr.get(i).getName());
    		printIntegerArray(arr.get(i).getSteps());
    	}
    	System.out.println("");
    }
    
    public void printStepArray(ArrayList<LifeCycleStep> arr) {
    	System.out.println("LIFE CYCLE STEPS:");
    	for(int i=0; i<arr.size(); i++) {
    		System.out.printf("Name: '%s', effort: %d, deliverable: %d\n",arr.get(i).getName(),arr.get(i).getEffort(),arr.get(i).getDeliverable());
    	}
    	System.out.println("");
    }
 }