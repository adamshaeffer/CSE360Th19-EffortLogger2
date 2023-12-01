package PlanningPokerJavaFX;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

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
	
	

}