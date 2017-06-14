package de.dpma.view;

import de.dpma.FXML_GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class VeranstaltungController {
	
	@FXML
	TextField dozentTextField;
	
	@FXML
	TextField aktenzeichenTextField;
	
	@FXML
	TextField schulArtTextField;
	
	@FXML
	TextField vfgTextField;
	
	@FXML
	ComboBox<String> vortragComboBox;
	
	@FXML
	DatePicker datumVon;
	
	@FXML
	DatePicker datumBis;
	
	@FXML
	ComboBox<String> euro_StdComboBox;
	
	@FXML
	TextField stdZahlTextField;
	
	@FXML
	TextField betragTextField;
	
	@FXML
	TextField betrag_ABCTextField;
	
	@FXML
	private void inizialize() {
		
		handleNew();
	}
	
	public void handleNew() {
		
		boolean ändern = RootLayoutController.ändern;
		// Stundenlohn Tabelle die Auswahlmöglichkeiten reinschmeißen
		ObservableList<String> euro_StdComboBoxList = FXCollections.observableArrayList(); // Stundesatz
																							// herausnehmen
		euro_StdComboBox.setItems(euro_StdComboBoxList);
		
		ObservableList<String> vortragComboBoxList = FXCollections.observableArrayList("Schulung", "Sonstiges");
		vortragComboBox.setItems(vortragComboBoxList);
		
		if (ändern) {
			dozentTextField.setText("ändern"); // TODO SQL Select Befehl | get
												// Selected item
			aktenzeichenTextField.setText("");
			schulArtTextField.setText("");
			vfgTextField.setText("");
			vortragComboBox.setValue("");
			// Das neuste eingetragegene Satz
			// euro_StdComboBox.setValue("");
			stdZahlTextField.setText("");
			betragTextField.setText("");
			betrag_ABCTextField.setText("");
		}
	}
	
	@FXML
	private void handleSubmit() {
		
		// TODO Insert befehl
		
		System.out.println(datumVon.getValue());
		System.out.println(datumBis.getValue());
		
	}
	
	@FXML
	private void handleCancel() {
		
		FXML_GUI.primaryStage.close();
	}
	
	@FXML
	private void handleTypingDozent() {
		
		// TODO Vorschläge bei Eingabe des Dozenten (list)
	}
}
