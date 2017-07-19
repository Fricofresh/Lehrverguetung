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

/**
 * Safes the data from the worker. <br>
 * Export a docx file if the {@link #event} has been passed.
 * 
 * @author Kenneth B�hmer
 *
 */
public class InsertPersonalDataController {
	
	private Event event;
	
	KeyEvent keyEvent;
	
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
	AlertUtil alert;
	
	ConfigIniUtil confini;
	
	private int getStageID;
	
	/**
	 * Puts the data in the text fields if data exits.
	 * 
	 * @author Kenneth B�hmer
	 */
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
	
	/**
	 * 
	 * @param check
	 *            to difference which template to use.
	 * @return <b>true</b> if a path is set, <b>false</b> if not.
	 */
	private boolean wordExport(String check) {
		
		try {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Speicherort ausw�hlen");
			chooser.setInitialFileName(check);
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word Datei (*.docx)", "*.docx");
			chooser.getExtensionFilters().add(extFilter);
			
			File file = chooser.showSaveDialog(FXML_GUI.primaryStage[this.getStageID].getScene().getWindow());
			if (file == null) {
				return false;
			}
			
			alert = new AlertUtil("Dokument wird exportiert...", null, "LOADING");
			WriteDocx wdoc = new WriteDocx(file, check, event);
			alert.closeAlert();
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		return true;
	}
	
	/**
	 * Checks if everything is filled and checks if the settings just have to be
	 * saved or to be exported to a document
	 * 
	 * @author Kenneth B�hmer
	 */
	@FXML
	private void handleSubmit() {
		
		// �berpr�ft ob die Eingabe g�ltig ist.
		if (DataChecker.isEmpty(dienstortComboBox.getValue())) {
			alert = new AlertUtil("Dienstort ung�ltig",
					"Es wurde kein g�ltiger Dienstort ausgew�hlt. Bitte w�hlen Sie eine valide Vortragsart aus und versuchen Sie es erneut.",
					"WARNING");
			return;
		}
		else if (!DataChecker.isNumeric(durchwahlTextField.getText())
				|| DataChecker.isEmpty(durchwahlTextField.getText())) {
			alert = new AlertUtil("Durchwahl ung�ltig",
					"Bitte geben Sie eine valide Durchwahl an und versuchen Sie es erneut.", "WARNING");
			return;
		}
		else if (DataChecker.isEmpty(vornameTextField.getText())) {
			alert = new AlertUtil("Vorname ung�ltig",
					"Bitte geben Sie einen validen Vornamen an und versuchen Sie es erneut.", "WARNING");
			return;
		}
		else if (DataChecker.isEmpty(nachnameTextField.getText())) {
			alert = new AlertUtil("Nachname ung�ltig",
					"Bitte geben Sie einen validen Nachnamen an und versuchen Sie es erneut.", "WARNING");
			return;
		}
		else if (!DataChecker.isEmail(emailTextField.getText()) || DataChecker.isEmpty(emailTextField.getText())) {
			alert = new AlertUtil("E-Mail ung�ltig",
					"Bitte geben Sie eine valide E-Mail an und versuchen Sie es erneut.", "WARNING");
			return;
		}
		
		boolean b = false;
		confini.setConf(dienstortComboBox.getValue().toString(), durchwahlTextField.getText(),
				vornameTextField.getText(), nachnameTextField.getText(), emailTextField.getText());
		confini.writeConf();
		if (FXML_GUI.primaryStage[this.getStageID].getTitle() == "Einstellungen") {
			FXML_GUI.primaryStage[this.getStageID].close();
		}
		else if (FXML_GUI.primaryStage[this.getStageID].getTitle().equals("Rechnungsbegleitblatt exportieren")) {
			b = wordExport("Rechnungsbegleitblatt");
		}
		else if (FXML_GUI.primaryStage[this.getStageID].getTitle().equals("Auszahlung Lehrverg�tung exportieren")) {
			b = wordExport("Auszahlung");
		}
		if (b == false) {
			return;
		}
		FXML_GUI.primaryStage[this.getStageID].close();
	}
	
	/**
	 * Closes the stage
	 * 
	 * @author Kenneth B�hmer
	 */
	@FXML
	private void handleCancel() {
		
		FXML_GUI.primaryStage[this.getStageID].close();
	}
	
	/**
	 * Checks if Enter or Escape is pressed to submit or cancel.
	 * 
	 * @author Kenneth B�hmer
	 * @param keyEvent
	 */
	@FXML
	private void handleKeyPressed(KeyEvent keyEvent) {
		
		this.keyEvent = keyEvent;
		switch (keyEvent.getCode()) {
		case ENTER:
			handleSubmit();
			
			break;
		case ESCAPE:
			handleCancel();
			break;
		default:
			break;
		}
	}
	
	/**
	 * Writes the e-mail address of the worker with his for- and surname.
	 * 
	 * @author Kenneth B�hmer
	 */
	@FXML
	private void handleEMail() {
		
		if (!DataChecker.isEmpty(vornameTextField.getText()) && !DataChecker.isEmpty(nachnameTextField.getText())) {
			emailTextField.setText(vornameTextField.getText() + "." + nachnameTextField.getText() + "@dpma.de");
		}
		else {
			emailTextField.setText("");
		}
	}
	
	public void setEvent(Event event) {
		
		this.event = event;
	}
}
