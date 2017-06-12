package de.dpma.view;

import de.dpma.FXML_GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class DozentController {
	
	@FXML
	ComboBox anredeComboBox;
	
	@FXML
	TextField titelTextField;
	
	@FXML
	TextField vornameTextField;
	
	@FXML
	TextField nameTextField;
	
	@FXML
	TextField straßeTextField;
	
	@FXML
	TextField pLZTextField;
	
	@FXML
	TextField ortTextField;
	
	@FXML
	TextField kontonummerTextField;
	
	@FXML
	TextField bankTextField;
	
	@FXML
	TextField bLZTextField;
	
	ObservableList<String> categoryComboBoxList = FXCollections.observableArrayList("Patente", "Marken", "Lernbücher",
			"Zeitschriften");
	
	@FXML
	public void initialize() {
		
	}
	
	@FXML
	private void handleSubmit() {
		
	}
	
	@FXML
	private void handleCancel() {
		
		FXML_GUI.primaryStage.close();
	}
	
}
