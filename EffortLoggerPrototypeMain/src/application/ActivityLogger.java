package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Label;

/*
 * Activity Logger Controller
 * Controller for the ActivityLogger page
 * Author: Adam Shaeffer
 */


public class ActivityLogger {

	//FXML injections
    @FXML
    private ComboBox<String> projectSelection;
    @FXML
    private ComboBox<String> storySelection;
    @FXML
    private ComboBox<String> effortSelection;
    @FXML
    private ComboBox<String> deliverableSelection;
    
    @FXML
    private Button startBtn;
    @FXML
    private Button stopBtn;

    @FXML
    private Label deliverableLabel;
    @FXML
    private Label clockLabel;
    @FXML
    private Label otherLabel;
    
    @FXML
    private TextField otherField;
    
    @FXML
    private Rectangle clockRect;
    
    private Alert warnings = new Alert(AlertType.WARNING);

    private ArrayList<ProjectClass> projects = new ArrayList<ProjectClass>();
    private ArrayList<LifeCycleStep> steps = new ArrayList<LifeCycleStep>();
    private ArrayList<String> efforts = new ArrayList<String>();
    private ArrayList<String> plans = new ArrayList<String>();
    private ArrayList<String> deliverables = new ArrayList<String>();
    private ArrayList<String> interruptions = new ArrayList<String>();
    private ArrayList<String> defects = new ArrayList<String>();
	private ArrayList<ProjectLog> projectLogs = new ArrayList<ProjectLog>();
	
	private Date startTime;
	private Date stopTime;

    public void initialize() throws FileNotFoundException {
    	assert projectSelection != null : "fx:id=\"projectSelection\" was not injected: check your FXML file 'ActivityLogger.fxml'.";
    	assert storySelection != null : "fx:id=\"storySelection\" was not injected: check your FXML file 'ActivityLogger.fxml'.";
    	assert effortSelection != null : "fx:id=\"effortSelection\" was not injected: check your FXML file 'ActivityLogger.fxml'.";
    	assert deliverableSelection != null : "fx:id=\"deliverableSelection\" was not injected: check your FXML file 'ActivityLogger.fxml'.";
    	assert startBtn != null : "fx:id=\"startBtn\" was not injected: check your FXML file 'ActivityLogger.fxml'.";
    	assert stopBtn != null : "fx:id=\"startBtn\" was not injected: check your FXML file 'ActivityLogger.fxml'.";
    	assert deliverableLabel != null : "fx:id=\"deliverableLabel\" was not injected: check your FXML file 'ActivityLogger.fxml'.";
    	assert clockLabel != null : "fx:id=\"clockLabel\" was not injected: check your FXML file 'ActivityLogger.fxml'.";
    	assert clockRect != null : "fx:id=\"clockRect\" was not injected: check your FXML file 'ActivityLogger.fxml'.";
    	
    	ArrayList<String> definitions = readDefinitions("src/application/Definitions.txt");
    	projects = getProjects(definitions.get(0));
    	steps = getSteps(definitions.get(1));
    	efforts = getNames(definitions.get(2));
    	plans = getNames(definitions.get(3));
    	deliverables = getNames(definitions.get(4));
    	interruptions = getNames(definitions.get(5));
    	defects = getNames(definitions.get(6));
    	
    	ArrayList<String> activityLog = readDefinitions("src/application/ActivityLog.txt");
    	projectLogs = new ArrayList<ProjectLog>();
    	if(activityLog.size() > 0) {
	    	ProjectLog current = new ProjectLog(activityLog.get(0).substring(0,activityLog.get(0).indexOf('=')));
	    	for(int i=1; i<activityLog.size(); i++) {
	    		if(activityLog.get(i).charAt(0) == '"') {
	    			String act = activityLog.get(i).substring(1);
	    			act = act.substring(0,act.indexOf('"'));
	    			current.addActivity(act);
	    		} else if(activityLog.get(i).charAt(0) != '}') {
	    	    	current = new ProjectLog(activityLog.get(i).substring(0,activityLog.get(i).indexOf('=')));
	    		} else {
	    			projectLogs.add(current);
	    		}
	    	}
    	}
    	
    	projectSelection.getItems().removeAll(projectSelection.getItems());
    	projectSelection.getItems().add("--Select a Project--");
    	for(int i=0; i<projects.size(); i++) {
    		projectSelection.getItems().add(projects.get(i).getName());
    	}
    	projectSelection.getSelectionModel().select(0);

    	storySelection.getItems().removeAll(storySelection.getItems());
    	effortSelection.getItems().removeAll(effortSelection.getItems());
    	effortSelection.getItems().add("--Select an Effort Category--");
    	for(int i=0; i<efforts.size(); i++) {
    		effortSelection.getItems().add(efforts.get(i));
    	}
    	deliverableSelection.getItems().removeAll(deliverableSelection.getItems());
    	
    	otherLabel.setText("Enter " + efforts.get(4) + " details here");
    	otherLabel.setVisible(false);
		otherField.setPromptText("At most 50 characters and contain only numbers, letters, ' ', '-'. Cannot be empty.");
    	otherField.setVisible(false);
    }
    
    @FXML
    private void handleProjectSelection(ActionEvent event) {
    	String projectSelected = projectSelection.getSelectionModel().getSelectedItem();
    	if(projectSelected != null && projectSelected.charAt(0) != '-') {
	    	int projectIndex = projectSelection.getSelectionModel().getSelectedIndex()-1;
	    	storySelection.getItems().removeAll(storySelection.getItems());
	    	storySelection.getItems().add("--Select a User Story--");
	    	ArrayList<Integer> projSteps = projects.get(projectIndex).getSteps();
	    	for(int i=0; i<projSteps.size(); i++) {
	    		storySelection.getItems().add((projSteps.get(i)) +":"+ steps.get(projSteps.get(i)-1).getName());
	    	}
	    	storySelection.getSelectionModel().select(0);
    	} else {
        	storySelection.getItems().removeAll(storySelection.getItems());
        	effortSelection.getItems().removeAll(effortSelection.getItems());
        	effortSelection.getItems().add("--Select an Effort Category--");
        	for(int i=0; i<efforts.size(); i++) {
        		effortSelection.getItems().add(efforts.get(i));
        	}
        	deliverableSelection.getItems().removeAll(deliverableSelection.getItems());
    	}
    }
    
    @FXML
    private void handleStorySelection(ActionEvent event) {
    	String storySelected = storySelection.getSelectionModel().getSelectedItem();
    	if(storySelected != null && storySelected.charAt(0) != '-') {
        	LifeCycleStep story = steps.get(Integer.parseInt(storySelected.substring(0,storySelected.indexOf(':')))-1);  
    		effortSelection.getSelectionModel().select(story.getEffort());
    	} else {
        	effortSelection.getItems().removeAll(effortSelection.getItems());
        	effortSelection.getItems().add("--Select an Effort Category--");
        	for(int i=0; i<efforts.size(); i++) {
        		effortSelection.getItems().add(efforts.get(i));
        	}
        	deliverableSelection.getItems().removeAll(deliverableSelection.getItems());
    	}
    }
    
    @FXML
    private void handleEffortSelection(ActionEvent event) {
    	String effortSelected = effortSelection.getSelectionModel().getSelectedItem();
    	int effortIndex = effortSelection.getSelectionModel().getSelectedIndex();
    	if(effortSelected != null && effortSelected.charAt(0) != '-' && !effortSelected.equals("Others")) {
    		String effortName = effortSelected.substring(0,effortSelected.length()-1);
			deliverableSelection.getItems().removeAll(deliverableSelection.getItems());
			deliverableSelection.getItems().add("--Select a " + effortName + "--");
			deliverableSelection.getSelectionModel().select(0);
			ArrayList<String> effortList;
    		deliverableLabel.setText(effortSelected);
    		otherLabel.setVisible(false);
    		otherField.setVisible(false);
	    	switch(effortIndex) {
	    	case 1:
	    		effortList = plans;
	    		break;
	    	case 2:
	    		effortList = deliverables;
	    		break;
	    	case 3:
	    		effortList = interruptions;
	    		break;
	    	case 4:
	    		effortList = defects;
	    		break;
	    	default:
	    		effortList = new ArrayList<String>();
	    		deliverableSelection.getItems().removeAll(deliverableSelection.getItems());
	    		deliverableLabel.setText("");
	    		otherLabel.setVisible(true);
	    		otherField.setVisible(true);
	    	}
    		for(int i=0; i<effortList.size(); i++) {
    			deliverableSelection.getItems().add(effortList.get(i));
    		}
    	} else {
        	deliverableSelection.getItems().removeAll(deliverableSelection.getItems());
    	}
    	String storySelected = storySelection.getSelectionModel().getSelectedItem();
    	if(storySelected != null && storySelected.charAt(0) != '-') {
        	LifeCycleStep story = steps.get(Integer.parseInt(storySelected.substring(0,storySelected.indexOf(':')))-1);  
    		if(effortIndex == story.getEffort()) {
            	deliverableSelection.getSelectionModel().select(story.getDeliverable());
    		} else {
    			deliverableSelection.getSelectionModel().select(0);
    		}
    	}
    }
    
    @FXML
    private void handleStartBtn(ActionEvent event) {
    	// Check that clock is stopped
    	if(clockLabel.getText().equals("Clock is stopped")) {
    		// Clock is stopped, modify the rectangle and text
    		clockRect.setFill(Paint.valueOf("#6fff72"));
    		clockLabel.setText("Clock is running");
    		// Start the clock
    		startTime = new Date();
    	} else {
    		// Warning clock is already running
			warnings.setHeaderText("WARNING: clock already running");
			warnings.setContentText("Cannot start activity as an activity is currently ongoing.");
    		warnings.show();
    	}
    }

    @FXML
    private void handleStopBtn(ActionEvent event) throws IOException {
    	// Check that clock is running
    	if(clockLabel.getText().equals("Clock is running")) {
        	// Check that input is all filled out.
    		String projectSelected = projectSelection.getSelectionModel().getSelectedItem();
    		String storySelected = storySelection.getSelectionModel().getSelectedItem();
    		String effortSelected = effortSelection.getSelectionModel().getSelectedItem();
    		String deliverableSelected = deliverableSelection.getSelectionModel().getSelectedItem();
    		// find log index for the given project
    		if(projectSelected != null && projectSelected.charAt(0) != '-' && storySelected != null && storySelected.charAt(0) != '-' && effortSelected != null && effortSelected.charAt(0) != '-') {
    			int effortIndex = effortSelection.getSelectionModel().getSelectedIndex();
    			if(effortIndex == 5) {
    				// Check that otherField has been filled out according to restrictions.
    				// other restrictions: At most 50 characters and contain only numbers, letters, ' ', '-'. Cannot be empty.
    				String otherText = otherField.getText();
    				if(otherText != null && otherText.length() > 0 && otherText.length() <= 50) {
    					boolean valid = true;
    					for(int i=0; i<otherText.length(); i++) {
    						char c = otherText.charAt(i);
    						if(!(c >= 48 && c <= 57) && !(c >= 65 && c <= 90) && !(c >= 97 && c <= 122) && c != 32 && c != 45) {
    							valid = false;
    							// Warning bad input
    							warnings.setHeaderText("WARNING: input does not meet requirements");
    							warnings.setContentText("The 'other' text input does not meet requirement.\nPlease make sure it meets requirements and try again.");
    				    		warnings.show();
    							break;
    						}
    					}
    					if(valid) {
				    		// Clock is active and input good, modify the rectangle and text
				    		clockRect.setFill(Paint.valueOf("#ff7171"));
				    		clockLabel.setText("Clock is stopped");
				    		// stop the clock
				    		stopTime = new Date();
							// save the activity to the .txt file
	    		    		String logEntry = String.format("%s; %s; %s",storySelected.substring(storySelected.indexOf(':')+1),effortSelected,deliverableSelected);
	    		    		writeEntry(logEntry,projectSelected,"src/application/ActivityLog.txt");
    					}
    				} else {
    					// Warning bad input
						warnings.setHeaderText("WARNING: input does not meet requirements");
						warnings.setContentText("The 'other' text input does not meet requirement.\nPlease make sure it meets requirements and try again.");
			    		warnings.show();
    				}
    			} else {
    				if(deliverableSelected != null && deliverableSelected.charAt(0) != '-') {
			    		// Clock is active and input good, modify the rectangle and text
			    		clockRect.setFill(Paint.valueOf("#ff7171"));
			    		clockLabel.setText("Clock is stopped");
    		    		// stop the clock
			    		stopTime = new Date();
						// save the activity to the .txt file
    		    		String logEntry = String.format("%s; %s; %s",storySelected.substring(storySelected.indexOf(':')+1),effortSelected,deliverableSelected);
    		    		writeEntry(logEntry,projectSelected,"src/application/ActivityLog.txt");
    				} else {
    					// Warning bad input
						warnings.setHeaderText("WARNING: missing details");
						warnings.setContentText("One of the activity details is missing input.\nMake sure all have been selected.");
			    		warnings.show();
    				}
    			}
    		} else {
    			// Warning, missing details
				warnings.setHeaderText("WARNING: missing details");
				warnings.setContentText("One of the activity details is missing input.\nMake sure all have been selected.");
	    		warnings.show();
    		}
    	} else {
    		// Warning, clock is already stopped
			warnings.setHeaderText("WARNING: clock already stopped");
			warnings.setContentText("Cannot stop the clock as an activity is not currently ongoing.");
    		warnings.show();
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
            
    private void writeEntry(String entry, String project, String fileName) throws IOException {
    	int logIndex = -1;
    	ProjectLog projLog;
    	for(int i=0; i<projectLogs.size(); i++) {
    		if(projectLogs.get(i).getName().equals(project)) {
    			logIndex = i;
    		}
    	}
    	if(logIndex >= 0) {
    		projLog = projectLogs.get(logIndex);
    	} else {
    		projLog = new ProjectLog(project);
    		projectLogs.add(projLog);
    	}
    	logIndex = projLog.getActivities().size()+1;
    	SimpleDateFormat year = new SimpleDateFormat("yyyy");
    	SimpleDateFormat month = new SimpleDateFormat("MM");
    	SimpleDateFormat day = new SimpleDateFormat("dd");
    	SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
    	int startYear = Integer.parseInt(year.format(startTime));
    	int startMonth = Integer.parseInt(month.format(startTime));
    	int startDay = Integer.parseInt(day.format(startTime));
    	int stopYear = Integer.parseInt(year.format(stopTime));
    	int stopMonth = Integer.parseInt(month.format(stopTime));
    	int stopDay = Integer.parseInt(day.format(stopTime));
    	if(startYear == stopYear && startMonth == stopMonth && startDay == stopDay) {
        	String date = String.format("%d-%02d-%d",startYear,startMonth,startDay);
        	entry = String.format("%d. %s (%s-%s) %s",logIndex,date,time.format(startTime),time.format(stopTime),entry);
        	projLog.addActivity(entry);
    	} else { // Stop day not the same as start
        	String date = String.format("%d-%02d-%d",startYear,startMonth,startDay);
        	entry = String.format("%d. %s (%s-%s) %s",logIndex,date,time.format(startTime),"23:59:59",entry);
        	projLog.addActivity(entry);
    	} // Code after iterates through every day, month, and year until it reaches the stop date, starting from the start date
    	int currDay = startDay+1;
    	int currYear = startYear;
    	int currMonth = startMonth;
    	while(currYear < stopYear) { // not the same year, go until month 12
    		while(currMonth <= 12) {
    			int monthMax;
    			if(currMonth == 2) {
    				monthMax = 28;
    				if(currYear%4 == 0) {
    					monthMax = 29;
    				}
    			} else if(currMonth == 4 || currMonth == 6 || currMonth == 8 || currMonth == 9 || currMonth == 11) {
    				monthMax = 30;
    			} else {
    				monthMax = 31;
    			}
    			while(currDay <= monthMax) {
		        	String date = String.format("%d-%02d-%d",currYear,currMonth,currDay);
		        	entry = String.format("%d. %s (%s-%s) %s",logIndex,date,"00:00:00","23:59:59",entry);
		        	projLog.addActivity(entry);
		        	currDay++;
    			}
    			currDay = 1;
    			currMonth++;
    		}
    		currMonth = 1;
    		currYear++;
    	}
		while(currMonth < stopMonth) { // not the same month, go until day 31/30/28/29
			int monthMax;
			if(currMonth == 2) {
				monthMax = 28;
				if(currYear%4 == 0) {
					monthMax = 29;
				}
			} else if(currMonth == 4 || currMonth == 6 || currMonth == 8 || currMonth == 9 || currMonth == 11) {
				monthMax = 30;
			} else {
				monthMax = 31;
			}
			while(currDay <= monthMax) {
	        	String date = String.format("%d-%02d-%d",currYear,currMonth,currDay);
	        	entry = String.format("%d. %s (%s-%s) %s",logIndex,date,"00:00:00","23:59:59",entry);
	        	projLog.addActivity(entry);
	        	currDay++;
			}
			currDay = 1;
			currMonth++;
		}
		while(currDay < stopDay) { // not the same day
        	String date = String.format("%d-%02d-%d",currYear,currMonth,currDay);
        	entry = String.format("%d. %s (%s-%s) %s",logIndex,date,"00:00:00","23:59:59",entry);
        	projLog.addActivity(entry);
        	currDay++;
		}
		if(currDay == stopDay) { // finally same day
        	String date = String.format("%d-%02d-%d",currYear,currMonth,currDay);
        	entry = String.format("%d. %s (%s-%s) %s",logIndex,date,"00:00:00",time.format(stopTime),entry);
        	projLog.addActivity(entry);
		}
//    	printProjectLogs(projectLogs);
    	FileWriter writer = new FileWriter(fileName);
    	for(int i=0; i<projectLogs.size(); i++) {
    		writer.write(projectLogs.get(i).getName() + "={\n");
    		ArrayList<String> act = projectLogs.get(i).getActivities();
    		for(int j=0; j<act.size(); j++) {
    			writer.write("\"" + act.get(j) + "\",\n");
    		}
    		writer.write("}");
    		if(i < projectLogs.size()-1) {
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
			line = line.substring(line.indexOf(',')+1);
			double avgEst = Double.parseDouble(line.substring(0,line.indexOf('}')-1));
    		ProjectClass proj = new ProjectClass(name,list);
			proj.addEffort(avgEst);
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
			line = line.substring(line.indexOf(',')+2);
			String des = line.substring(0,line.indexOf('"'));
			line = line.substring(line.indexOf(',')+2);
			String key = line.substring(0,line.indexOf('"'));
			line = line.substring(line.indexOf(',')+1);
			int est = Integer.parseInt(line.substring(0,line.indexOf('}')));
    		line = line.substring(line.indexOf('}'));
    		LifeCycleStep step = new LifeCycleStep(name,effort,deliverable,des,key,est);
    		steps.add(step);
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
        
    private void printProjectLogs(ArrayList<ProjectLog> log) {
    	for(int i=0; i<log.size(); i++) {
    		System.out.println(log.get(i).getName());
    		ArrayList<String> act = log.get(i).getActivities();
    		for(int j=0; j<act.size(); j++) {
    			System.out.println(act.get(j));
    		}
    	}
    }
    
    /*
	 * If the defect logger console button is pressed, the screen is switched to the effort logger console
	 */
	public void deffectLoggerConsoleButtonHandler(ActionEvent event) throws IOException {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		DefectConsoleController Controller = new DefectConsoleController();
		Controller.start(stage);
	}
	
    public void homeScreenButtonHandler(ActionEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("StartUpScreen.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();	
	}
}
