package de.dpma.view;

import de.dpma.FXML_GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class DozentController {
	
	@FXML
	ComboBox<String> anredeComboBox;
	
	@FXML
	TextField titelTextField;
	
	@FXML
	TextField vornameTextField;
	
	@FXML
	TextField nameTextField;
	
	@FXML
	TextField straﬂeTextField;
	
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
	
	ObservableList<String> anredeComboBoxList = FXCollections.observableArrayList("Frau", "Herr");
	
	@FXML
	public void initialize() {
		
		anredeComboBox.setItems(anredeComboBoxList);
	}
	
	private void handleNew() {
		
		boolean ‰ndern = RootLayoutController.‰ndern;
		
		if (‰ndern) {
			// anredeComboBox.setSelectionModel("");// TODO SQL Select Befehl |
			// get Selected item
			titelTextField.setText("");
			vornameTextField.setText("");
			nameTextField.setText("");
			straﬂeTextField.setText("");
			pLZTextField.setText("");
			ortTextField.setText("");
			kontonummerTextField.setText("");
			bankTextField.setText("");
			bLZTextField.setText("");
		}
	}
	
	@FXML
	private void handleSubmit() {
		
	}
	
	@FXML
	private void handleCancel() {
		
		FXML_GUI.primaryStage.close();
	}
	
}
