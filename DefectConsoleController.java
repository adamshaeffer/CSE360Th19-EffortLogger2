package application;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DefectConsoleController extends Application {
	private final ObservableList<Defect> listD = FXCollections.observableArrayList();
    private TableView<Defect> def = new TableView<>();
    
    public static void main(String[] args) {
        launch(args);
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
	public void start(Stage primaryStage) {
        primaryStage.setTitle("Defect Console");

        TableColumn<Defect, String> defectNumCol = createTableColumn("Defect Number", "defNum");
        TableColumn<Defect, String> defectNameCol = createTableColumn("Defect Name", "defectName");
        TableColumn<Defect, String> defectDesCol = createTableColumn("Defect Description", "defectDescription");
        TableColumn<Defect, String> defectImportanceCol = createTableColumn("Defect Importance", "defectImportance");
        TableColumn<Defect, String> statCol = createTableColumn("Status", "status");
        TableColumn<Defect, String> userCol = createTableColumn("User", "user");

        def.getColumns().addAll(defectNumCol, defectNameCol, defectDesCol, defectImportanceCol, statCol, userCol);
        defectNumCol.setPrefWidth(100);
        def.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        def.setItems(listD);

        Label defectNumLabel = new Label("Defect Number:");
        Label defectNameLabel = new Label("Defect Name:");
        Label defectDesLabel = new Label("Defect Description (Step when injected and/or removed):");
        Label defectImpLabel = new Label("Defect Importance (1-5):");
        Label statLabel = new Label("Status (Open or Closed):");
        Label add = new Label("Add Defect:");
        Label remove = new Label("Remove Defect (To remove, just put Defect #):");
        Label update = new Label("Update (To update, use the same Defect #):");
        Label userName = new Label("Enter your User ID:");
        
        TextField defectNumInput = new TextField();
        TextField defectNameInput = new TextField();
        TextField defectDescriptionInput = new TextField();
        ComboBox<String> defectImportance = new ComboBox<>();
        defectImportance.getItems().addAll("1", "2", "3", "4", "5");
        defectImportance.setValue("1");
        ComboBox<String> statusUpdate = new ComboBox<>();
        statusUpdate.getItems().addAll("Closed", "Open");
        statusUpdate.setValue("Open");
        TextField userInput = new TextField();

        Button addButton = new Button("Add Defect");
        addButton.setOnAction(e -> addDefect(defectNumInput, defectNameInput, defectDescriptionInput, defectImportance, statusUpdate, userInput));
        Button removeButton = new Button("Remove Defect");
        removeButton.setOnAction(e -> removeDefect(defectNumInput));
        Button updateButton = new Button("Update Defect");
        updateButton.setOnAction(e -> updateDefect(defectNumInput, defectNameInput, defectDescriptionInput, defectImportance, statusUpdate, userInput));

        defectNameInput.setPrefWidth(200);
        defectDescriptionInput.setPrefWidth(300);

        VBox vBox = new VBox();
        vBox.setPrefWidth(600);
        vBox.getChildren().addAll(def, defectNumLabel, defectNumInput, defectNameLabel, defectNameInput, defectDesLabel, defectDescriptionInput, defectImpLabel, defectImportance, statLabel, statusUpdate, userName, userInput, add, addButton, remove, removeButton, update, updateButton);
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }


    private TableColumn<Defect, String> createTableColumn(String nameCol, String property) {
        TableColumn<Defect, String> column = new TableColumn<>(nameCol);
        column.setCellValueFactory(data -> data.getValue().getPropertyByName(property));
        return column;
    }

    // Add a new defect to the list
    private void addDefect(TextField inputNum, TextField nameInput, TextField descriptionInput, ComboBox<String> categoryInput, ComboBox<String> statInput, TextField userInput) {
        String num = inputNum.getText();
    	String name = nameInput.getText();
        String description = descriptionInput.getText();
        String category = categoryInput.getValue();
        String stat = statInput.getValue();
        String user = userInput.getText();
     // Check if the defect number already exists in the list
        if (isDefectNumberUnique(num)) {
            // If unique, add the new defect to the list
            listD.add(new Defect(num, name, description, category, stat, user));
            nameInput.clear();
            descriptionInput.clear();
        }
    }
    
    private boolean isDefectNumberUnique(String defectNum) {
        // Iterate through the list of defects
        for (Defect defect : listD) {
            // Check if the defect number matches
            if (defect.getPropertyByName("defNum").get().equals(defectNum)) {
                return false; // Not unique
            }
        }
        return true; // Unique
    }

    // Remove the selected defect from the list
    private void removeDefect(TextField defectNumInput) {
    	// Get the defect number from the input field
        String defectNum = defectNumInput.getText();

        // Find and remove the defect with the matching defect number
        Defect selectedDefect = getSelectedDefect(defectNum);
        if (selectedDefect != null) {
            listD.remove(selectedDefect);
        }
    }
    
    private Defect getSelectedDefect(String defectNumInput) {
    	// Iterate through the list of defects
        for (Defect defect : listD) {
            // Check if the defect number matches
            if (defect.getPropertyByName("defNum").get().equals(defectNumInput)) {
                return defect;
            }
        }
        return null; // Return null if no matching defect is found
    }
    
    private void updateDefect(TextField inputNum, TextField nameInput, TextField descriptionInput, ComboBox<String> categoryInput, ComboBox<String> statInput, TextField userInput) {
    	// Get the defect number from the input field
        String defectNum = inputNum.getText();

        // Find and update the defect with the matching defect number
        Defect selectedDefect = getSelectedDefect(defectNum);
        if (selectedDefect != null) {
            // Update the defect with new information
            selectedDefect.setDefectName(nameInput.getText());
            selectedDefect.setDefectDescription(descriptionInput.getText());
            selectedDefect.setDefectImportance(categoryInput.getValue());
            selectedDefect.setStatus(statInput.getValue());
            selectedDefect.setStatus(userInput.getText());

            // Refresh the table view
            def.refresh();
    
            inputNum.clear();
            nameInput.clear();
            descriptionInput.clear();
        }
       
    }
    
    public static class Defect {
    	private final SimpleStringProperty defNum;
    	private final SimpleStringProperty defectName;
        private final SimpleStringProperty defectDescription;
        private final SimpleStringProperty defectImportance;
        private final SimpleStringProperty status;
        private final SimpleStringProperty user;

        public Defect(String defNum, String defectName, String defectDescription, String defectImportance, String status, String user) {
        	this.defNum = new SimpleStringProperty(defNum);
        	this.defectName = new SimpleStringProperty(defectName);
            this.defectDescription = new SimpleStringProperty(defectDescription);
            this.defectImportance = new SimpleStringProperty(defectImportance);
            this.status = new SimpleStringProperty(status);
            this.user = new SimpleStringProperty(user);
        }

        public SimpleStringProperty getPropertyByName(String propertyName) {
            switch (propertyName) {
            	case "defNum":
            		return defNum;
                case "defectName":
                    return defectName;
                case "defectDescription":
                    return defectDescription;
                case "defectImportance":
                    return defectImportance;
                case "status":
                	return status;
                case "user":
                	return user;
                default:
                    return null;
            }
        }
        public void setDefectName(String defectName) {
            this.defectName.set(defectName);
        }

        public void setDefectDescription(String defectDescription) {
            this.defectDescription.set(defectDescription);
        }

        public void setDefectImportance(String defectImportance) {
            this.defectImportance.set(defectImportance);
        }

        public void setStatus(String status) {
            this.status.set(status);
        }
        
        public void setUser(String user) {
            this.user.set(user);
        }
        
    }
}