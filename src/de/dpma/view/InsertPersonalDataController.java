package de.dpma.view;

import java.io.File;

import de.dpma.FXML_GUI;
import de.dpma.model.Event;
import de.dpma.util.WriteDocxTEST;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class InsertPersonalDataController {
	
	// TODO E-Mail auto-Vervollst�ndigung
	
	public Event event;
	
	@FXML
	ComboBox<String> dienstortComboBox;
	
	ObservableList<String> dienstortComboBoxList = FXCollections.observableArrayList("M�nchen", "Jena", "Berlin");
	
	@FXML
	TextField durchwahlTextField;
	
	@FXML
	TextField vornameTextField;
	
	@FXML
	TextField nachnameTextField;
	
	@FXML
	TextField emailTextField;
	
	@FXML
	public void initialize() {
		
		dienstortComboBox.setItems(dienstortComboBoxList);
	}
	
	public void wordExport(String check) {
		
		try {
			// TODO Word export
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Speichern");
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word Datei (*.docx)", "*.docx");
			chooser.getExtensionFilters().add(extFilter);
			
			String directory = chooser.getExtensionFilters().toString();
			
			File file = chooser.showSaveDialog(FXML_GUI.primaryStage.getScene().getWindow());
			WriteDocxTEST wdoc = new WriteDocxTEST(file, check);
		}
		catch (Exception e) {
		}
	}
	
	@FXML
	private void handleSubmit() {
		
		// TODO ini datei auslesen
		if (FXML_GUI.primaryStage.getTitle().equals("Rechnungsbegleitblatt exportieren")) {
			wordExport("Rechnungsbegleitblatt");
		}
		else if (FXML_GUI.primaryStage.getTitle().equals("Auszahlung Lehrverg�tung exportieren")) {
			wordExport("Auszahlung");
		}
		else {
			// TODO DO NOTHING
		}
	}
	
	@FXML
	private void handleCancel() {
		
		FXML_GUI.primaryStage.close();
	}
}
