package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.util.converter.IntegerStringConverter;

public class SceneController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	public void switchToBeforeStartPressed(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("BeforeStartPressed.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root, 1250, 800);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToBeforeUserStoryPressed(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("BeforeUserStoryPressed.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root, 1250, 800);
		stage.setScene(scene);
		stage.show();

	}

	public void switchToAfterUserStoryPressed(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AfterUserStoryPressed.fxml"));
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
	private Button btnSingleFileChooser;

	@FXML
	private Label singleFile;

	@FXML
	private TextArea UserStoryText;

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
				}

			} catch (IOException e) {
				e.printStackTrace(); // Handle the exception appropriately
			}
		}

		UserStoryText.setWrapText(true);

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

		} catch (NumberFormatException e) {
			// Handle the case where the weightInput is not a valid integer
			// You may want to display an error message to the user
			System.out.println("Invalid weight input. Please enter a valid integer.");
			e.printStackTrace(); // Handle it in a more appropriate way in your application
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
	}
	
	@FXML 
	private Label userLabel;
	
	public void setUser(String username, String role) {
		userLabel.setText("Hello, " + username + ". Role: " + role);
	}
	
	// Puts objects preset in table, called in initialize
	private void setupTable() {
		Projects project1 = new Projects(1, "Project1", "creating the login", "login, security");
		Projects project2 = new Projects(4, "Project2", "This is the middle of the code", "privacy, defect");
		Projects project3 = new Projects(3, "Project3", "This is the middle of the code", "Projects");
		Projects project4 = new Projects(4, "Project4", "This is the end of the code", "log");
		tableView.getItems().addAll(project1, project2, project3, project4);
	}

	public void changeWeightCellEvent(CellEditEvent<Projects, Integer> editedCell) {
		Projects projectsSelected = tableView.getSelectionModel().getSelectedItem();
		projectsSelected.setWeights(editedCell.getNewValue());
	}

	public void changeDescriptionCellEvent(CellEditEvent editedCell) {
		Projects projectsSelected = tableView.getSelectionModel().getSelectedItem();
		projectsSelected.setDescriptions(editedCell.getNewValue().toString());
	}

	public void changeKeywordsCellEvent(CellEditEvent editedCell) {
		Projects projectsSelected = tableView.getSelectionModel().getSelectedItem();
		projectsSelected.setKeywords(editedCell.getNewValue().toString());
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		weightsColumn.setCellValueFactory(new PropertyValueFactory<Projects, Integer>("weights"));
		projectsColumn.setCellValueFactory(new PropertyValueFactory<Projects, String>("projects"));
		descriptionsColumn.setCellValueFactory(new PropertyValueFactory<Projects, String>("descriptions"));
		keywordsColumn.setCellValueFactory(new PropertyValueFactory<Projects, String>("keywords"));
		setupTable();

		descriptionInput.setWrapText(true);

		// Update table to change cells
		tableView.setEditable(true);

		weightsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		weightsColumn.setOnEditCommit(this::changeWeightCellEvent);

		descriptionsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		keywordsColumn.setCellFactory(TextFieldTableCell.forTableColumn());

	}

	@FXML
	private TextField estimateText;

	@FXML
	void getAverage(ActionEvent event) {
		ObservableList<Projects> projectsList = tableView.getItems();

		if (projectsList.isEmpty()) {
			System.out.println("No projects to calculate");
			return;
		}

		int weights = 0;

		for (Projects project : projectsList) {
			weights += project.getWeights();
		}

		double averageWeights = (double) weights / projectsList.size();

		estimateText.setText(String.valueOf(averageWeights));
	}

}