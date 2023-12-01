package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;

public class SceneController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
    public void homeScreenButtonHandler(ActionEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("StartUpScreen.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();	
	}
	
	public void switchToManfred(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("myManfred&AdamScene.fxml"));
		Parent root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root, 1250, 800);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToProjectDetails(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ProjectDetails.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root, 1250, 800);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private TextField ProjectName;
	
	@FXML
	private Label singleProject;
	
	@FXML
    private Button addProjectNameButton;
	
	@FXML
	void getProjectName(ActionEvent event) {

		if (ProjectName != null) {
			singleProject.setText(ProjectName.getText());
		}
	}
	
	
	@FXML
	private Button btnSingleFileChooser;

	@FXML
	private Label singleFile;

	@FXML
	private TextArea UserStoryText;

	private String globalUserStoryText;

	@FXML
	void uploadUserStory(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Text Documents", "*.txt"));
		File selectedFile = fc.showOpenDialog(stage);

		if (selectedFile != null) {
			singleFile.setText("Selected File:: " + selectedFile.getAbsolutePath());

			try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
				StringBuilder content = new StringBuilder();
				String line;

				while ((line = br.readLine()) != null) {
					content.append(line).append("\n");
				}

				if (UserStoryText != null) {
					UserStoryText.setText(content.toString());

					globalUserStoryText = content.toString(); // Saving the content in this variable as a string
																// to be used on another scene
				}

			} catch (IOException e) {
				e.printStackTrace(); // Handle the exception appropriately
			}
		}

		UserStoryText.setWrapText(true);

	}

	@FXML
	private Label singleFileProject;

	@FXML
	void uploadProjects(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Text Documents", "*.txt"));
		File selectedFile = fc.showOpenDialog(stage);

		if (selectedFile != null) {
			singleFileProject.setText("Selected File:: " + selectedFile.getAbsolutePath());

			try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
				ObservableList<Projects> projectsList = FXCollections.observableArrayList();

				String line;
				Projects currentProject = null;

				while ((line = br.readLine()) != null) {
					if (line.trim().isEmpty()) {
						// Empty line indicates a new project
						if (currentProject != null) {
							projectsList.add(currentProject);
						}

						currentProject = null; // Reset currentProject for a new one

					} else {
						String[] values = line.split(";");

						if (values.length == 4) {

							try { // Line has to have this number of values
								String projectName = values[0].trim();
								String description = values[1].trim();
								String keywords = values[2].trim();
								int weight = Integer.parseInt(values[3].trim());

								currentProject = new Projects(weight, projectName, description, keywords);
							} catch (NumberFormatException e) {
								System.out.println("Invalid weight format: " + values[0]);
							}
						} else {
							System.out.println("Invalid line format: " + line);
						}
					}
				}

				// Adding last project if there is one
				if (currentProject != null) {
					projectsList.add(currentProject);
				}

				tableView.setItems(projectsList);
				this.selectedFile = selectedFile; 	// Setting class variable here
				// updateTxtFile();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	@FXML
	private TextArea descriptionInput;

	@FXML
	private TableColumn<Projects, String> descriptionsColumn;

	@FXML
	private TextField keywordInput;

	@FXML
	private TableColumn<Projects, String> keywordsColumn;

	@FXML
	private TextField projectInput;

	@FXML
	private TableColumn<Projects, String> projectsColumn;

	@FXML
	private TableView<Projects> tableView;

	@FXML
	private TextField weightInput;

	@FXML
	private TableColumn<Projects, Integer> weightsColumn;

	@FXML
	void addProject(ActionEvent event) {
		try {
			int weight = Integer.parseInt(weightInput.getText());

			if (weight < 0 || weight > 4) {
				System.out.println("Weight must be between 0 and 4.");
				return;
			}

			Projects project = new Projects(weight, projectInput.getText(), descriptionInput.getText(),
					keywordInput.getText());
			ObservableList<Projects> projectsList = tableView.getItems();

			projectsList.add(project);
			tableView.setItems(projectsList);
			System.out.println("Project added successfully.");

			updateTxtFile();

		} catch (NumberFormatException e) {
			System.out.println("Invalid weight input. Please enter a valid integer.");
			e.printStackTrace();
		}

		weightInput.clear();
		projectInput.clear();
		descriptionInput.clear();
		keywordInput.clear();
	}

	@FXML
	void removeProject(ActionEvent event) {
		int selectedID = tableView.getSelectionModel().getSelectedIndex();
		tableView.getItems().remove(selectedID);

		updateTxtFile();
	}

	// Puts objects preset in table, called in initialize
	// private void setupTable() {
	// Projects project1 = new Projects(1, "Backlog_item 1", "creating the login",
	// "login, security");
	// Projects project2 = new Projects(4, "Backlog_item 2", "This is the middle of
	// the code", "privacy, defect");
	// Projects project3 = new Projects(3, "Backlog_item 3", "This is the middle of
	// the code", "Projects");
	// Projects project4 = new Projects(4, "Backlog_item 4", "This is the end of the
	// code", "log");
	// tableView.getItems().addAll(project1, project2, project3, project4);
	// }

	public void changeWeightCellEvent(CellEditEvent<Projects, Integer> editedCell) {
		Projects projectsSelected = tableView.getSelectionModel().getSelectedItem();
		projectsSelected.setWeights(editedCell.getNewValue());
		tableView.refresh();
		updateTxtFile();
	}

	public void changeDescriptionCellEvent(CellEditEvent<Projects, String> editedCell) {
		Projects projectsSelected = tableView.getSelectionModel().getSelectedItem();
		projectsSelected.setDescriptions(editedCell.getNewValue().toString());
		tableView.refresh();
		updateTxtFile();
	}

	public void changeKeywordsCellEvent(CellEditEvent<Projects, String> editedCell) {
		Projects projectsSelected = tableView.getSelectionModel().getSelectedItem();
		projectsSelected.setKeywords(editedCell.getNewValue().toString());
		tableView.refresh();
		updateTxtFile();
	}

	private File selectedFile;

	private void updateTxtFile() {

		if (selectedFile != null) {
			ObservableList<Projects> projectsList = tableView.getItems();

			try (PrintWriter writer = new PrintWriter(new FileWriter(selectedFile))) {

				double getStoryPoints = getAverage(null);

				// Adds text to the top of .txt file every time
				writer.println("**Projects Format:\n"
						+ "**Project must follow specifc format:	backlog_item;	Description;	Keywords;	Weight\n"
						+ "**Each backlog item is distinguished with empty lines between them\n"
						+ "**Failure to do so will result in backlog items not added\n"
						+ "***********************************************************\n"
						+ "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tStory Points Estimate:	" + String.valueOf(getStoryPoints) + "\n");

				for (Projects project : projectsList) {

					writer.println(project.getProjects() + ";		" + project.getDescriptions() + ";		"
							+ project.getKeywords() + ";		" + project.getWeights() + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Error updating the text file: " + e.getMessage());
			}
		} else {
			System.err.println("Selected file is null. Cannot update the text file.");
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		weightsColumn.setCellValueFactory(new PropertyValueFactory<>("weights"));
		projectsColumn.setCellValueFactory(new PropertyValueFactory<>("projects"));
		descriptionsColumn.setCellValueFactory(new PropertyValueFactory<>("descriptions"));
		keywordsColumn.setCellValueFactory(new PropertyValueFactory<>("keywords"));
		// setupTable();

		descriptionInput.setWrapText(true);

		// Update table to change cells
		tableView.setEditable(true);

		weightsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		weightsColumn.setOnEditCommit(this::changeWeightCellEvent);

		descriptionsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		descriptionsColumn.setOnEditCommit(this::changeDescriptionCellEvent);

		keywordsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		keywordsColumn.setOnEditCommit(this::changeKeywordsCellEvent);

		// Manfreds timer stuff
		this.time = new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(1.0), (evt) -> {
			this.up();
		}, new KeyValue[0]) });
		this.time.setCycleCount(-1);

	}

	@FXML
	private TextField estimateText;

	@FXML
	double getAverage(ActionEvent event) {
		ObservableList<Projects> projectsList = tableView.getItems();

		if (projectsList.isEmpty()) {
			System.out.println("No projects to calculate");
			return 0.0;
		}

		int weights = 0;

		for (Projects project : projectsList) {
			weights += project.getWeights();
		}

		double averageWeights = (double) weights / projectsList.size();

		estimateText.setText(String.valueOf(averageWeights));

		return averageWeights;
	}
	
    
	// Manfreds timer stuff
	@FXML
	private Label stop;
	@FXML
	private Button startStopButton;
	@FXML
	private Timeline time;
	private long sec = 0L;
	private long startTime;
	private boolean stopwatchRunning = false;
	private long TotalTime = 0L;
	@FXML
	private Button endButton;

	public void startTimer() {
		if (this.time != null && this.time.getStatus() != Status.RUNNING) {
			this.time.play();
		}

		this.startTime = System.currentTimeMillis();
	}

	public void stopTimer() {
		if (this.time != null) {
			this.time.stop();
		}

	}

	public void up() {
		++this.sec;
		String tS = this.formatTime(this.sec);
		this.stop.setText(tS);
	}

	private String formatTime(long sec) {
		long minute = sec / 60L;
		long seconds = sec % 60L;
		return String.format("%02d:%02d", minute, seconds);
	}

	@FXML
	public void toggleStopwatch() {
		if (!this.stopwatchRunning) {
			this.startTimer();
			this.startStopButton.setText("Stop");
			this.startStopButton.setStyle("-fx-background-color: #FF0000;");
		} else {
			this.stopTimer();
			this.startStopButton.setText("Start");
			this.startStopButton.setStyle("-fx-background-color: #228B22;");
		}

		this.stopwatchRunning = !this.stopwatchRunning;
	}

	public void EndButton() {
		if (this.time != null) {
			this.time.stop();
		}

		this.TotalTime = System.currentTimeMillis() - this.startTime;
		this.Reset();
	}

	private void Reset() {
		this.sec = 0L;
		this.stop.setText(this.formatTime(0L));
	}

	/*
	 * // Manfreds Poker Card stuff
	 * 
	 * @FXML private Label cardNum;
	 * 
	 * @FXML private TextField UserPoker;
	 * 
	 * @FXML private Label PokerNum;
	 * 
	 * 
	 * @FXML private void UserInput() { try { int userChoice =
	 * Integer.parseInt(this.UserPoker.getText()); this.UserChoice(userChoice); }
	 * catch (NumberFormatException var2) {
	 * System.out.println("Please enter a valid number"); }
	 * 
	 * }
	 * 
	 * private void UserChoice(int userChoice) { if (userChoice <= 4 && userChoice
	 * >= 0) { this.cardNum.setText(String.valueOf(userChoice)); } else {
	 * System.out.println("Pick a number 1-4"); }
	 * 
	 * }
	 */
	
	
	// Collecting the data part
		//*******************************************************************************************
	    private double collectedAverageWeights;
	    private ObservableList<Projects> collectedProjectsList;
	    private String collectedUserStory;

	    @FXML
	    void collectData(ActionEvent event) {
	        // Get average weights
	        getProjectName(null);
	        collectedAverageWeights = getAverage(null);

	        // Get items from the table
	        collectedProjectsList = tableView.getItems();
	        
	        // Get the userStory
	        collectedUserStory = UserStoryText.getText();
	        
	        //System.out.println("Collected Project Name: " + ProjectName.getText());
	        //System.out.println("Collected Average Weights: " + collectedAverageWeights);
	        //System.out.println("Collected Projects List: " + collectedProjectsList);
	        
	    }
		
	    
	    @FXML
	    void submitData(ActionEvent event) {
	        // Load the new scene
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("myManfred&AdamScene.fxml"));
	        Parent root;
	        try {
	            root = loader.load();
	            MyManfredSceneController manfredController = loader.getController();

				// Read definitions.txt and add new project to the list
				ArrayList<String> definitions = readDefinitions("src/application/Definitions.txt");
				ArrayList<ProjectClass> projects = getProjects(definitions.get(0));
				ArrayList<LifeCycleStep> steps = getSteps(definitions.get(1));
				int stepAmount = steps.size();
				int newStepAmount = collectedProjectsList.size();
				ArrayList<Integer> newStepList = new ArrayList<Integer>();
				for(int i=0; i<newStepAmount; i++) {
					newStepList.add(stepAmount+i);
					Projects story = collectedProjectsList.get(i);
					LifeCycleStep step = new LifeCycleStep(story.getProjects(),1,1,story.getDescriptions(),story.getKeywords(),story.getWeights());
					steps.add(step);
				}
				ProjectClass proj = new ProjectClass(ProjectName.getText(),newStepList);
				proj.addEffort(collectedAverageWeights);
				projects.add(proj);
				
				// Write existing and new projects to definitions.txt
				writeDefinitions("src/application/Definitions.txt",definitions,projects,steps);

	            // Pass the collected data to the new scene's controller
	            manfredController.displayData(ProjectName.getText(), collectedAverageWeights, collectedProjectsList);
	            manfredController.displayUserStoryText(collectedUserStory);

	            // Create a new stage for the new scene
	            Stage newStage = new Stage();
	            Scene newScene = new Scene(root, 1250, 800);

	            // Set the new scene on the stage
	            newStage.setScene(newScene);

	            // Show the new stage
	            newStage.show();

	        } catch (IOException e) {
	            e.printStackTrace();
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
				double avgEst = Double.parseDouble(line.substring(0,line.indexOf('}')));
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
	    		line = line.substring(line.indexOf(',')+1);
	    		int deliverable = Integer.parseInt(line.substring(0,line.indexOf(',')));
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

		private void writeDefinitions(String fileName, ArrayList<String> definitions, ArrayList<ProjectClass> projects, ArrayList<LifeCycleStep> steps) throws IOException {
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
				line = line + "]," + projects.get(i).getEffort();
				line += "}";
				if(i < projects.size()-1) {
					line += ",";
				}
			}
			line += "}";
			items.add(line);
			// Adding the lifecyclesteps
			line = "LifeCycleSteps={";
			for(int i=0; i<steps.size(); i++) {
				line = line + "{\"" + steps.get(i).getName() + "\"," + steps.get(i).getEffort() + "," + steps.get(i).getDeliverable() + ",\"" + steps.get(i).getDescription() + "\",\"" + steps.get(i).getKeywords() + "\"," + steps.get(i).getEstimate() + "}";
				if(i < steps.size()-1) {
					line += ",";
				}
			}
			line += "}";
			items.add(line);
			items.add(definitions.get(2));
			items.add(definitions.get(3));
			items.add(definitions.get(4));
			items.add(definitions.get(5));
			items.add(definitions.get(6));
	    	FileWriter writer = new FileWriter(fileName);
	    	for(int i=0; i<items.size(); i++) {
	    		writer.write(items.get(i));
	    		if(i < items.size()-1) {
	    			writer.write("\n");
	    		}
	    	}
	    	writer.close();
		}
		

}