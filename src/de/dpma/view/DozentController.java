package de.dpma.view;

import de.dpma.FXML_GUI;
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

	@FXML
	public void initialize() {

		anredeComboBox.setItems(anredeComboBoxList);
	}

	@FXML
	public void handleNew(Dozent dozent) {

		this.dozent = dozent;

		anredeComboBox.setValue(dozent.getAnrede()); // TODO SQL Select Befehl |
														// get Selected item
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
				alert = new AlertUtil("Anrede nicht ausgew�hlt",
						"Es wurde keine Anrede ausgew�hlt. Bitte w�hlen Sie eine Anrede aus und versuchen Sie es erneut.",
						"WARNING");
			} else if (DataChecker.isEmpty(vornameTextField.getText())) {
				alert = new AlertUtil("Vorname ung�ltig",
						"Es wurde kein g�ltiger Vorname eingegeben. Bitte geben Sie einen validen Vornamen an und versuchen Sie es erneut.",
						"WARNING");
			} else if (DataChecker.isEmpty(nameTextField.getText())) {
				alert = new AlertUtil("Name ung�ltig",
						"Es wurde kein g�ltiger Name eingegeben. Bitte geben Sie einen validen Name an und versuchen Sie es erneut.",
						"WARNING");
			} else if (DataChecker.isEmpty(strasseTextField.getText())) {
				alert = new AlertUtil("Stra�e ung�ltig",
						"Es wurde keine g�ltige Stra�e eingegeben. Bitte geben Sie eine valide Stra�e an und versuchen Sie es erneut.",
						"WARNING");
			} else if (DataChecker.isEmpty(pLZTextField.getText()) || !DataChecker.isPLZ(pLZTextField.getText())) {
				alert = new AlertUtil("Postleitzahl ung�ltig",
						"Es wurde keine g�ltige Postleitzahl eingegeben. Bitte geben Sie eine valide Postleitzahl an und versuchen Sie es erneut.",
						"WARNING");
			} else if (DataChecker.isEmpty(ortTextField.getText())) {
				alert = new AlertUtil("Ort ung�ltig",
						"Es wurde kein g�ltiger Ort eingegeben. Bitte geben Sie einen validen Ort an und versuchen Sie es erneut.",
						"WARNING");
			} else if (DataChecker.isEmpty(kontonummerTextField.getText())
					|| !DataChecker.isIBANorKontonummer(kontonummerTextField.getText())) {
				alert = new AlertUtil("IBAN oder Kontonummer ung�ltig",
						"Es wurde keine g�ltige IBAN oder Kontonummer eingegeben. Bitte geben Sie eine valide IBAN oder Kontonummer an und versuchen Sie es erneut.",
						"WARNING");
			} else if (DataChecker.isEmpty(bankTextField.getText())) {
				alert = new AlertUtil("Bankname ung�ltig",
						"Es wurde kein g�ltiger Bankname eingegeben. Bitte geben Sie einen validen Banknamen an und versuchen Sie es erneut.",
						"WARNING");
			} else if (DataChecker.isEmpty(bLZTextField.getText()) || !DataChecker.isBICorBLZ(bLZTextField.getText())) {
				alert = new AlertUtil("BIC oder Bankleitzahl ung�ltig",
						"Es wurde keine g�ltige BIC oder Bankleitzahl eingegeben. Bitte geben Sie eine valide BIC oder Bankleitzahl an und versuchen Sie es erneut.",
						"WARNING");
			} else {
				this.dozent.setTitel(titelTextField.getText());
				this.dozent.setVorname(vornameTextField.getText());
				this.dozent.setName(nameTextField.getText());
				this.dozent.setStrasse(strasseTextField.getText());
				this.dozent.setPLZ(pLZTextField.getText());
				this.dozent.setOrt(ortTextField.getText());
				this.dozent.setIBAN(kontonummerTextField.getText());
				this.dozent.setBank(bankTextField.getText());
				this.dozent.setBLZ(bLZTextField.getText());
				this.dozent.setAnrede(anredeComboBox.getValue());

				if (dozent.getId() == 0) {
					MainPageController.dozentDAO.insertDozent(this.dozent);
				} else {
					MainPageController.dozentDAO.updateDozent(this.dozent);
				}

				FXML_GUI.primaryStage.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleCancel() {

		FXML_GUI.primaryStage.close();
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
