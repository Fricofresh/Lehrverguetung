package de.dpma.view;

import de.dpma.FXML_GUI;
import de.dpma.model.Dozent;
import de.dpma.util.AlertUtil;
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
	
	Dozent dozent;
	
	AlertUtil alert;
	
	@FXML
	public void initialize() {
		
		anredeComboBox.setItems(anredeComboBoxList);
	}
	
	@FXML
	public void handleNew(Dozent dozent) {
		
		// anredeComboBox.setSelectionModel("");// TODO SQL Select Befehl |
		// get Selected item
		titelTextField.setText(dozent.getTitel());
		vornameTextField.setText(dozent.getVorname());
		nameTextField.setText(dozent.getName());
		straﬂeTextField.setText(dozent.getStrasse());
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
			// TODO Insert befehl Dozent
			// this.event.setId_dozent();
			this.dozent.setTitel(titelTextField.getText());
			this.dozent.setVorname(vornameTextField.getText());
			this.dozent.setName(nameTextField.getText());
			this.dozent.setStrasse(straﬂeTextField.getText());
			this.dozent.setPLZ(pLZTextField.getText());
			this.dozent.setOrt(ortTextField.getText());
			this.dozent.setIBAN(kontonummerTextField.getText());
			this.dozent.setBank(bankTextField.getText());
			this.dozent.setBLZ(bLZTextField.getText());
			if (dozent.getId() == 0) {
				MainPageController.dozentDAO.insertDozent(this.dozent);
				alert = new AlertUtil("Datensatz eingetragen", "Es wurde erfolgreich ein neuer Dozent hinzugef¸gt",
						"INFO");
			}
			else {
				MainPageController.dozentDAO.updateDozent(this.dozent);
				alert = new AlertUtil("Datensatz ge‰ndert", "Es wurde erfolgreich ein Dozent bearbeitet", "INFO");
				
			}
			FXML_GUI.primaryStage.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleCancel() {
		
		FXML_GUI.primaryStage.close();
	}
	
}
