package de.dpma.view;

import java.io.File;

import de.dpma.FXML_GUI;
import de.dpma.MainApp;
import de.dpma.model.Event;
import de.dpma.util.AlertUtil;
import de.dpma.util.ConfigIniUtil;
import de.dpma.util.DataChecker;
import de.dpma.util.WriteDocx;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

public class InsertPersonalDataController {

	// TODO E-Mail auto-Vervollständigung

	private Event event;

	KeyEvent keyEvent;

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

	private int getStageID;

	@FXML
	public void initialize() {

		getStageID = MainApp.counter;
		confini = new ConfigIniUtil();
		dienstortComboBox.setItems(dienstortComboBoxList);
		dienstortComboBox.setValue(confini.getDienstort());
		durchwahlTextField.setText(confini.getDurchwahl());
		vornameTextField.setText(confini.getVorname());
		nachnameTextField.setText(confini.getNachname());
		emailTextField.setText(confini.getEmail());
	}

	public boolean wordExport(String check) {

		try {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Speicherort auswählen");
			chooser.setInitialFileName(check);
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word Datei (*.docx)", "*.docx");
			chooser.getExtensionFilters().add(extFilter);

			File file = chooser.showSaveDialog(FXML_GUI.primaryStage[this.getStageID].getScene().getWindow());
			if (file == null) {
				return false;
			}
			WriteDocx wdoc = new WriteDocx(file, check, event);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@FXML
	private void handleSubmit() {

		if (DataChecker.isEmpty(dienstortComboBox.getValue())) {
			alert = new AlertUtil("Dienstort ungültig",
					"Es wurde kein gültiger Dienstort ausgewählt. Bitte wählen Sie eine valide Vortragsart aus und versuchen Sie es erneut.",
					"WARNING");
			return;
		} else if (!DataChecker.isNumeric(durchwahlTextField.getText())
				|| DataChecker.isEmpty(durchwahlTextField.getText())) {
			alert = new AlertUtil("Durchwahl ungültig",
					"Bitte geben Sie eine valide Durchwahl an und versuchen Sie es erneut.", "WARNING");
			return;
		} else if (DataChecker.isEmpty(vornameTextField.getText())) {
			alert = new AlertUtil("Vorname ungültig",
					"Bitte geben Sie einen validen Vornamen an und versuchen Sie es erneut.", "WARNING");
			return;
		} else if (DataChecker.isEmpty(nachnameTextField.getText())) {
			alert = new AlertUtil("Nachname ungültig",
					"Bitte geben Sie einen validen Nachnamen an und versuchen Sie es erneut.", "WARNING");
			return;
		} else if (!DataChecker.isEmail(emailTextField.getText()) || DataChecker.isEmpty(emailTextField.getText())) {
			alert = new AlertUtil("E-Mail ungültig",
					"Bitte geben Sie eine valide E-Mail an und versuchen Sie es erneut.", "WARNING");
			return;
		}

		boolean b = false;
		confini.setConf(dienstortComboBox.getValue().toString(), durchwahlTextField.getText(),
				vornameTextField.getText(), nachnameTextField.getText(), emailTextField.getText());
		confini.writeConf();
		if (FXML_GUI.primaryStage[this.getStageID].getTitle() == "Einstellungen") {
			FXML_GUI.primaryStage[this.getStageID].close();
		} else if (FXML_GUI.primaryStage[this.getStageID].getTitle().equals("Rechnungsbegleitblatt exportieren")) {
			b = wordExport("Rechnungsbegleitblatt");
		} else if (FXML_GUI.primaryStage[this.getStageID].getTitle().equals("Auszahlung Lehrvergütung exportieren")) {
			b = wordExport("Auszahlung");
		}
		if (b == false) {
			return;
		}
		FXML_GUI.primaryStage[this.getStageID].close();
	}

	@FXML
	private void handleCancel() {

		FXML_GUI.primaryStage[this.getStageID].close();
	}

	public void handleKeyPressed(KeyEvent keyEvent) {

		this.keyEvent = keyEvent;
		switch (keyEvent.getCode()) {
		case ENTER:
			handleSubmit();

			break;
		default:
			break;
		}
	}

	@FXML
	private void handleEMail() {

		emailTextField.setText(vornameTextField.getText() + "." + nachnameTextField.getText() + "@dpma.de");
	}

	public void setEvent(Event event) {

		this.event = event;
	}
}
