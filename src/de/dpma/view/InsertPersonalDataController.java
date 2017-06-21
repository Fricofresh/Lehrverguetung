package de.dpma.view;

import java.io.File;

import de.dpma.FXML_GUI;
import de.dpma.model.Event;
import de.dpma.util.AlertUtil;
import de.dpma.util.ConfigIniUtil;
import de.dpma.util.WriteDocxTEST;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class InsertPersonalDataController {
	
	// TODO E-Mail auto-Vervollständigung
	
	public Event event;
	
	@FXML
	ComboBox<String> dienstortComboBox;
	
	ObservableList<String> dienstortComboBoxList = FXCollections.observableArrayList("München", "Jena", "Berlin");
	
	@FXML
	TextField durchwahlTextField;
	
	@FXML
	TextField vornameTextField;
	
	@FXML
	TextField nachnameTextField;
	
	@FXML
	TextField emailTextField;
	
	@FXML
	AlertUtil alert;
	
	ConfigIniUtil confini;
	
	@FXML
	public void initialize() {
		
		confini = new ConfigIniUtil();
		dienstortComboBox.setItems(dienstortComboBoxList);
		dienstortComboBox.setValue(confini.getDienstort());
		vornameTextField.setText(confini.getVorname());
		nachnameTextField.setText(confini.getNachname());
		emailTextField.setText(confini.getEmail());
	}
	
	public boolean wordExport(String check) {
		
		try {
			// TODO Word export
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Speichern");
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word Datei (*.docx)", "*.docx");
			chooser.getExtensionFilters().add(extFilter);
			
			String directory = chooser.getExtensionFilters().toString();
			
			File file = chooser.showSaveDialog(FXML_GUI.primaryStage.getScene().getWindow());
			if (file == null) {
				alert = new AlertUtil("Pfad ungültig", "Bitte wählen Sie einen Pfad an", "WARNING");
				return false;
			}
			WriteDocxTEST wdoc = new WriteDocxTEST(file, check, event);
		}
		catch (Exception e) {
		}
		return true;
	}
	
	@FXML
	private void handleSubmit() {
		
		boolean b = false;
		
		confini.setConf(dienstortComboBox.getValue().toString(), durchwahlTextField.getText(),
				vornameTextField.getText(), nachnameTextField.getText(), emailTextField.getText());
		confini.writeConf();
		if (FXML_GUI.primaryStage.getTitle() == "Einstellungen") {
			FXML_GUI.primaryStage.close();
		}
		else if (FXML_GUI.primaryStage.getTitle().equals("Rechnungsbegleitblatt exportieren")) {
			b = wordExport("Rechnungsbegleitblatt");
		}
		else if (FXML_GUI.primaryStage.getTitle().equals("Auszahlung Lehrvergütung exportieren")) {
			b = wordExport("Auszahlung");
		}
		if (b == false) {
			return;
		}
		FXML_GUI.primaryStage.close();
	}
	
	@FXML
	private void handleCancel() {
		
		FXML_GUI.primaryStage.close();
	}
}
