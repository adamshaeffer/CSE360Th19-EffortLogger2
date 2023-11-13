package application;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

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
    @FXML
    private Label instructionsLabel;
    
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

    	// Add items to the combo box for modifying type of information
    	typeSelection.visibleProperty().setValue(true);
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
    	instructionsLabel.visibleProperty().setValue(false);
    	deleteBtn.visibleProperty().setValue(false);
    	addBtn.visibleProperty().setValue(false);
    	updateBtn.visibleProperty().setValue(false);
    	
    	ArrayList<String> contents = readDefinitions("src/application/Definitions.txt");
    	printStringArray(contents);
    	if(contents.get(0).charAt(0) == 'P') {
        	projects = getProjects(contents.get(0));
//        	printProjectArray(projects);
    	}
    	if(contents.get(1).charAt(0) == 'L') {
    		steps = getSteps(contents.get(1));
//        	printStepArray(steps);
    	}
    	if(contents.get(2).charAt(0) == 'E') {
    		efforts = getNames(contents.get(2));
//        	System.out.println("EFFORT CATEGORIES");
//        	printNameArray(efforts);
    	}
    	if(contents.get(3).charAt(0) == 'P') {
    		plans = getNames(contents.get(3));
//        	System.out.println("\nPLANS");
//        	printNameArray(plans);
    	}
    	if(contents.get(4).charAt(0) == 'D') {
    		deliverables = getNames(contents.get(4));
//        	System.out.println("\nDELIVERABLES");
//        	printNameArray(deliverables);
    	}
    	if(contents.get(5).charAt(0) == 'I') {
    		interruptions = getNames(contents.get(5));
//        	System.out.println("\nINTERRUPTIONS");
//        	printNameArray(interruptions);
    	}
    	if(contents.get(6).charAt(0) == 'D') {
    		defects = getNames(contents.get(6));
//        	System.out.println("\nDEFECT CATEGORIES");
//        	printNameArray(defects);
    	}
    	
//    	ArrayList<Integer> list = new ArrayList<Integer>();
//    	list.add(69);
//    	ProjectClass proj = new ProjectClass("Name",list);
//    	projects.add(proj);
//    	printProjectArray(projects);
    	writeDefinitions("src/application/Definitions.txt");
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
    	nameLabel.setText("Select the " + typeSelected + " to edit:");
    	nameSelection.getItems().add("--Select a " + typeSelected + "--");
    	nameSelection.getSelectionModel().select(0);
    	getNameOptions(nameOptions,typeSelected);
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
    	getInfoOptions(infoOptions,typeSelected);
    	infoSelection.getItems().addAll(infoOptions);
    	if(infoOptions.size() < 2) {
    		infoSelection.getSelectionModel().select(0);
    	}
    	infoOptions.clear();
    }
    
    // Code to be executed when the selection of nameSelection drop-down has changed
	@FXML
	private void handleNameSelected(ActionEvent event) {
		String typeSelected = typeSelection.getSelectionModel().getSelectedItem();
		String nameSelected = nameSelection.getSelectionModel().getSelectedItem();
		ArrayList<String> infoOptions = new ArrayList<String>();
    	getInfoOptions(infoOptions,typeSelected);
    	if(infoSelection.getItems().indexOf("Name") == 0) {
	    	infoSelection.getItems().removeAll(infoSelection.getItems());
	    	infoSelection.getItems().add("--Select the " + typeSelected + "'s info to modify--");
	    	infoSelection.getItems().addAll(infoOptions);
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
				modifyLabel.setText("Modify the Life Cycle Steps here: (type the numbers of the desired life cycles separated by commas)");
			} else if(infoSelected.equals("Default Effort Category")) {
				modifyLabel.setText("Modify the Default Effort Category here: (type the number of the desired effort category)");
			} else if(infoSelected.equals("Default Deliverable")) {
				modifyLabel.setText("Modify the Default Deliverable here: (type the number of the desired deliverable)");
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
    private void handleDeleteClick(ActionEvent event) {
		String typeSelected = typeSelection.getSelectionModel().getSelectedItem();
		int typeIndex = typeSelection.getSelectionModel().getSelectedIndex();
		String nameSelected = nameSelection.getSelectionModel().getSelectedItem();
		int nameIndex = nameSelection.getSelectionModel().getSelectedIndex();
        if (event.getSource() == deleteBtn) {
        	System.out.println("delete button pressed");
        	System.out.printf("%s(%d): '%s'(%d) will be deleted.\n",typeSelected,typeIndex,nameSelected,nameIndex);
        	switch(typeIndex) {
        	case 0:
        		projects.remove(nameIndex-1);
//        		printProjectArray(projects);
        		break;
        	case 1:
        		steps.remove(nameIndex-1);
//        		printStepArray(steps);
        		break;
        	case 2:
        		System.out.println("Effort Categories cannot be deleted. This should not be accessible");
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
        		System.out.println("Somehow selected something else and managed to delete it! This should not be possible");
        	}
           	ArrayList<String> nameOptions = new ArrayList<String>();
           	nameSelection.getItems().removeAll(nameSelection.getItems());
        	nameSelection.getItems().add("--Select a " + typeSelected + "--");
        	nameSelection.getSelectionModel().select(0);
        	getNameOptions(nameOptions,typeSelected);
        	nameSelection.getItems().addAll(nameOptions);
        	nameOptions.clear();
        }
    }

	// Code to be executed when addBtn is clicked
    @FXML
    private void handleAddClick(ActionEvent event) {
		String typeSelected = typeSelection.getSelectionModel().getSelectedItem();
		String modifyInput = modifyField.getText();
	    if (event.getSource() == addBtn) {
	    	System.out.println("add button pressed");
	    	System.out.printf("New %s named '%s' will be added.",typeSelected,modifyInput);
	    }
    }
    
	// Code to be executed when update2Btn is clicked
    @FXML
    private void handleUpdateClick(ActionEvent event) {
		String typeSelected = typeSelection.getSelectionModel().getSelectedItem();
		String nameSelected = nameSelection.getSelectionModel().getSelectedItem();
		String infoSelected = infoSelection.getSelectionModel().getSelectedItem();
		String modifyInput = modifyField.getText();
		if (event.getSource() == updateBtn) {
			System.out.println("update button pressed");
			System.out.printf("Modifying %s '%s'. Setting %s's %s to '%s'",typeSelected,nameSelected,typeSelected,infoSelected,modifyInput);
		}
    }
        
    // Method for getting all name options for the selected item type
    private void getNameOptions(ArrayList<String> nameOptions, String typeSelected) {
    	if(typeSelected.equals("Project")) {
    		for(int i=0; i<projects.size(); i++) {
    			nameOptions.add(projects.get(i).name);
    		}
    	} else if(typeSelected.equals("Life Cycle Step")) {
    		for(int i=0; i<steps.size(); i++) {
    			nameOptions.add(steps.get(i).name);
    		}
    	} else if(typeSelected.equals("Effort Category")) {
    		for(int i=0; i<efforts.size(); i++) {
    			nameOptions.add(efforts.get(i));
    		}
    	} else if(typeSelected.equals("Plan")) {
    		for(int i=0; i<plans.size(); i++) {
    			nameOptions.add(plans.get(i));
    		}
    	} else if(typeSelected.equals("Deliverable")) {
    		for(int i=0; i<deliverables.size(); i++) {
    			nameOptions.add(deliverables.get(i));
    		}
    	} else if(typeSelected.equals("Interruption")) {
    		for(int i=0; i<interruptions.size(); i++) {
    			nameOptions.add(interruptions.get(i));
    		}
    	} else if(typeSelected.equals("Defect Category")) {
    		for(int i=0; i<defects.size(); i++) {
    			nameOptions.add(defects.get(i));
    		}
    	}
    	if(!typeSelected.equals("Effort Category")) { 
    		// Effort Categories are crucial to how the code works, so can only "add new" to the other item types
    		nameOptions.add("Add new");
    	}
    }
    
    // Method for getting all info options for the selected item type
    private void getInfoOptions(ArrayList<String> infoOptions, String typeSelected) {
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
    		line = line + "{\"" + projects.get(i).name + "\",[";
    		for(int j=0; j<projects.get(i).LifeCycleSteps.size(); j++) {
    			line = line + "" + projects.get(i).LifeCycleSteps.get(j);
    			if(j < projects.get(i).LifeCycleSteps.size()-1) {
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
    		line = line + "{\"" + steps.get(i).name + "\"," + steps.get(i).effort + "," + steps.get(i).deliverable + "}";
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
    	line = "Plans={";
    	line = addNames(line,plans);
    	line += "}";
    	items.add(line);
    	// Adding the deliverables
    	line = "Deliverables={";
    	line = addNames(line,deliverables);
    	line += "}";
    	items.add(line);
    	// Adding the interruptions
    	line = "Interruptions={";
    	line = addNames(line,interruptions);
    	line += "}";
    	items.add(line);
    	// Adding the defect categories
    	line = "DefectCategories={";
    	line = addNames(line,defects);
    	line += "}";
    	items.add(line);
//    	File file = new File(fileName);
    	FileWriter writer = new FileWriter(fileName);
    	for(int i=0; i<items.size(); i++) {
    		writer.write(items.get(i));
    		writer.write("\n");
    	}
    	writer.close();
    }
        
    private ArrayList<ProjectClass> getProjects(String line) {
//    public void getProjects(String line) {
    	ArrayList<ProjectClass> projects = new ArrayList<ProjectClass>();
    	line = line.substring(line.indexOf('{')+1);
    	while(line.charAt(0) == '{') {
    		line = line.substring(2);
    		String name = line.substring(0,line.indexOf('"'));
    		line = line.substring(line.indexOf('[')+1);
    		ArrayList<Integer> list = new ArrayList<Integer>();
//    		int count = 0;
    		while(line.charAt(0) != ']') {
    			if(line.indexOf(',') > 0 && line.indexOf(',') < line.indexOf(']')) {
    				list.add(Integer.parseInt(line.substring(0,line.indexOf(','))));
//    				System.out.printf("Iteration %d: %s\n",count,line);
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
//    				System.out.printf("Iteration %d: %s\n",count,line);
    				list.add(Integer.parseInt(line.substring(0,line.indexOf(']'))));
    				line = line.substring(line.indexOf(']'));
    			}
//    			count++;
    		}
    		ProjectClass proj = new ProjectClass(name,list);
    		projects.add(proj);
//    		System.out.printf("New Project: %s\n",name);
//    		printIntegerArray(list);
//    		System.out.printf("\nRest of project line: %s\n",line);
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
    		System.out.printf("Name: '%s', steps: ",arr.get(i).name);
    		printIntegerArray(arr.get(i).LifeCycleSteps);
    	}
    	System.out.println("");
    }
    
    public void printStepArray(ArrayList<LifeCycleStep> arr) {
    	System.out.println("LIFE CYCLE STEPS:");
    	for(int i=0; i<arr.size(); i++) {
    		System.out.printf("Name: '%s', effort: %d, deliverable: %d\n",arr.get(i).name,arr.get(i).effort,arr.get(i).deliverable);
    	}
    	System.out.println("");
    }
 }