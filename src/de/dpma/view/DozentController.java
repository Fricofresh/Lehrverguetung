package de.dpma.view;

import de.dpma.FXML_GUI;
import de.dpma.MainApp;
import de.dpma.model.Dozent;
import de.dpma.util.AlertUtil;
import de.dpma.util.DataChecker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class DozentController {
	
	@FXML
	ComboBox<String> anredeComboBox = new ComboBox();
	
	@FXML
	TextField titelTextField = new TextField();
	
	@FXML
	TextField vornameTextField = new TextField();
	
	@FXML
	TextField nameTextField = new TextField();
	
	@FXML
	TextField strasseTextField = new TextField();
	
	@FXML
	TextField pLZTextField = new TextField();
	
	@FXML
	TextField ortTextField = new TextField();
	
	@FXML
	TextField kontonummerTextField = new TextField();
	
	@FXML
	TextField bankTextField = new TextField();
	
	@FXML
	TextField bLZTextField = new TextField();
	
	KeyEvent keyEvent;
	
	ObservableList<String> anredeComboBoxList = FXCollections.observableArrayList("Frau", "Herr");
	
	Dozent dozent;
	
	AlertUtil alert;
	
	private int getStageID;
	
	@FXML
	public void initialize() {
		
		getStageID = MainApp.counter;
		anredeComboBox.setItems(anredeComboBoxList);
	}
	
	@FXML
	public void handleNew(Dozent dozent) {
		
		this.dozent = dozent;
		
		anredeComboBox.setValue(dozent.getAnrede());
		titelTextField.setText(dozent.getTitel());
		vornameTextField.setText(dozent.getVorname());
		nameTextField.setText(dozent.getName());
		strasseTextField.setText(dozent.getStrasse());
		pLZTextField.setText(dozent.getPLZ());
		ortTextField.setText(dozent.getOrt());
		kontonummerTextField.setText(dozent.getIBAN());
		bankTextField.setText(dozent.getBank());
		bLZTextField.setText(dozent.getBLZ());
		
	}
	
	@FXML
	private void handleSubmit() {
		
		try {
			if (dozent == null) {
				dozent = new Dozent();
			}
			
			if (DataChecker.isEmpty(anredeComboBox.getValue())) {
				alert = new AlertUtil("Anrede nicht ausgewählt",
						"Es wurde keine Anrede ausgewählt. Bitte wählen Sie eine Anrede aus und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (DataChecker.isEmpty(vornameTextField.getText())) {
				alert = new AlertUtil("Vorname ungültig",
						"Es wurde kein gültiger Vorname eingegeben. Bitte geben Sie einen validen Vornamen an und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (DataChecker.isEmpty(nameTextField.getText())) {
				alert = new AlertUtil("Name ungültig",
						"Es wurde kein gültiger Name eingegeben. Bitte geben Sie einen validen Name an und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (DataChecker.isEmpty(strasseTextField.getText())) {
				alert = new AlertUtil("Straße ungültig",
						"Es wurde keine gültige Straße eingegeben. Bitte geben Sie eine valide Straße an und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (DataChecker.isEmpty(pLZTextField.getText()) || !DataChecker.isPLZ(pLZTextField.getText())) {
				alert = new AlertUtil("Postleitzahl ungültig",
						"Es wurde keine gültige Postleitzahl eingegeben. Bitte geben Sie eine valide Postleitzahl an und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (DataChecker.isEmpty(ortTextField.getText())) {
				alert = new AlertUtil("Ort ungültig",
						"Es wurde kein gültiger Ort eingegeben. Bitte geben Sie einen validen Ort an und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (DataChecker.isEmpty(kontonummerTextField.getText())
					|| !DataChecker.isIBANorKontonummer(kontonummerTextField.getText())) {
				alert = new AlertUtil("IBAN oder Kontonummer ungültig",
						"Es wurde keine gültige IBAN oder Kontonummer eingegeben. Bitte geben Sie eine valide IBAN oder Kontonummer an und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (DataChecker.isEmpty(bankTextField.getText())) {
				alert = new AlertUtil("Bankname ungültig",
						"Es wurde kein gültiger Bankname eingegeben. Bitte geben Sie einen validen Banknamen an und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (DataChecker.isEmpty(bLZTextField.getText()) || !DataChecker.isBICorBLZ(bLZTextField.getText())) {
				alert = new AlertUtil("BIC oder Bankleitzahl ungültig",
						"Es wurde keine gültige BIC oder Bankleitzahl eingegeben. Bitte geben Sie eine valide BIC oder Bankleitzahl an und versuchen Sie es erneut.",
						"WARNING");
			}
			else {
				this.dozent.setTitel(titelTextField.getText().trim());
				this.dozent.setVorname(vornameTextField.getText().trim());
				this.dozent.setName(nameTextField.getText().trim());
				this.dozent.setStrasse(strasseTextField.getText().trim());
				this.dozent.setPLZ(pLZTextField.getText().trim());
				this.dozent.setOrt(ortTextField.getText().trim());
				this.dozent.setIBAN(kontonummerTextField.getText().trim());
				this.dozent.setBank(bankTextField.getText().trim());
				this.dozent.setBLZ(bLZTextField.getText().trim());
				this.dozent.setAnrede(anredeComboBox.getValue());
				
				if (dozent.getId() == 0) {
					MainPageController.dozentDAO.insertDozent(this.dozent);
				}
				else {
					MainPageController.dozentDAO.updateDozent(this.dozent);
				}
				
				FXML_GUI.primaryStage[this.getStageID].close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
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
	
}
