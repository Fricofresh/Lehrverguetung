package de.dpma.view;

import java.io.File;

import de.dpma.FXML_GUI;
import de.dpma.model.Event;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

public class InsertPersonalDataController {
	@FXML
	public void wordExport(Event event) {

		try {
			// TODO Word export
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Speichern");
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word Datei (*.docx)", "*.docx");
			chooser.getExtensionFilters().add(extFilter);

			String directory = chooser.getExtensionFilters().toString();

			File file = chooser.showSaveDialog(FXML_GUI.primaryStage.getScene().getWindow());
			// WriteDocxTEST wdoc = new WriteDocxTEST(file);
		} catch (Exception e) {
		}
	}
}
