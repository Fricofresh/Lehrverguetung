package de.dpma.view;

import de.dpma.FXML_GUI;
import de.dpma.model.Event;
import de.dpma.util.AlertUtil;
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
	DatePicker vfgDatePicker;
	
	@FXML
	ComboBox<String> vortragComboBox;
	
	@FXML
	DatePicker datumVonDatePicker;
	
	@FXML
	DatePicker datumBisDatePicker;
	
	@FXML
	ComboBox<String> euro_StdComboBox;
	
	@FXML
	TextField stdZahlTextField;
	
	@FXML
	TextField betragTextField;
	
	@FXML
	TextField betrag_ABCTextField;
	
	Event event;
	
	AlertUtil alert;
	
	@FXML
	private void inizialize() {
		
		ObservableList<String> euro_StdComboBoxList = FXCollections.observableArrayList(); // Stundesatz
		// herausnehmen
		euro_StdComboBox.setItems(euro_StdComboBoxList);
		
		ObservableList<String> vortragComboBoxList = FXCollections.observableArrayList("Schulung", "Sonstiges");
		vortragComboBox.setItems(vortragComboBoxList);
	}
	
	public void handleNew(Event event) {
		
		// Stundenlohn Tabelle die Auswahlmöglichkeiten reinschmeißen
		this.event = event;
		
		dozentTextField.setText(event.DozentString());
		aktenzeichenTextField.setText(event.getAktenz());
		schulArtTextField.setText(event.getSchulart());
		// vfgDatePicker.setValue(event.getVfg());
		String vortrag;
		if (event.getVortrg_mode() == 1) {
			vortrag = "Schulung";
		}
		else {
			vortrag = "Sonstiges";
		}
		vortragComboBox.setValue(vortrag);
		// datumVonDatePicker.setValue();
		// datumBisDatePicker.setValue();
		// TODO Das neuste eingetragegene Satz anzeigen lassen
		// euro_StdComboBox.setValue("");
		String stdZahl = String.valueOf(event.getStdzahl());
		stdZahlTextField.setText(stdZahl);
		String betrag = String.valueOf(event.BetragProperty());
		betragTextField.setText(betrag);
		String betrag_ABC = String.valueOf(event.Betrag_ABCProperty());
		betrag_ABCTextField.setText(betrag_ABC);
	}
	
	@FXML
	private void handleSubmit() {
		
		if (dozentTextField.getText().isEmpty() /* || Dozent nicht Vorhanden */) {
			alert = new AlertUtil("Dozent ungültig",
					"Es wurde kein gültiger Dozent eingegeben. Bitte geben Sie einen validen Dozent an und versuchen Sie es erneut.",
					"WARNING");
		}
		else if (aktenzeichenTextField.getText().isEmpty()) {
			alert = new AlertUtil("Aktenzeichen ungültig",
					"Es wurde kein gültiges Aktenzeichen eingegeben. Bitte geben Sie einen validen Aktenzeichen an und versuchen Sie es erneut.",
					"WARNING");
		}
		else if (schulArtTextField.getText().isEmpty()) {
			alert = new AlertUtil("Schulungsart ungültig",
					"Es wurde kein gültige Schulungsart eingegeben. Bitte geben Sie einen validen Schulungsart an und versuchen Sie es erneut.",
					"WARNING");
		}
		else if (vfgDatePicker.getValue().toString().isEmpty()) {
			alert = new AlertUtil("Verfügungstag ungültig",
					"Es wurde kein gültiger Verfügungstag eingegeben. Bitte geben Sie einen validen Verfügungstag an und versuchen Sie es erneut.",
					"WARNING");
		}
		else if (vortragComboBox.getValue().isEmpty()) {
			alert = new AlertUtil("Vortrag ungültig",
					"Es wurde kein gültiger Vortrag eingegeben. Bitte geben Sie einen validen Vortrag an und versuchen Sie es erneut.",
					"WARNING");
		}
		else if (datumVonDatePicker.getValue().toString().isEmpty()) {
			alert = new AlertUtil("Datum ungültig",
					"Es wurde kein gültiges Datum eingegeben. Bitte geben Sie einen validen Datum an und versuchen Sie es erneut.",
					"WARNING");
		}
		else if (datumBisDatePicker.getValue().toString().isEmpty()) {
			alert = new AlertUtil("Datum ungültig",
					"Es wurde kein gültiges Datum eingegeben. Bitte geben Sie einen validen Datum an und versuchen Sie es erneut.",
					"WARNING");
		}
		
		else if (stdZahlTextField.getText().isEmpty()) {
			alert = new AlertUtil("Stundenanzahl ungültig",
					"Es wurde kein gültiger Stundenanzahl eingegeben. Bitte geben Sie einen validen Stundenanzahl an und versuchen Sie es erneut.",
					"WARNING");
		}
		else {
			
			try {
				if (event == null) {
					event = new Event();
				}
				// TODO Insert befehl Dozent
				// this.event.setId_dozent();
				this.event.setAktenz(aktenzeichenTextField.getText());
				this.event.setDate_start(datumVonDatePicker.getValue().toString());
				this.event.setDate_end(datumBisDatePicker.getValue().toString());
				// this.event.setId_euro_std();
				this.event.setSchulart(schulArtTextField.getText());
				int stdZahl = Integer.parseInt(stdZahlTextField.getText());
				this.event.setStdzahl(stdZahl);
				this.event.setVfg(vfgDatePicker.getValue().toString());
				if (event.getId() == 0) {
					MainPageController.eventDAO.insertEvent(event);
				}
				else {
					MainPageController.eventDAO.updateEvent(event);
				}
				FXML_GUI.primaryStage.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void handleCancel() {
		
		FXML_GUI.primaryStage.close();
	}
	
	@FXML
	private void handleTypingDozent() {
		
		// TODO Vorschläge bei Eingabe des Dozenten (list)
		
	}
	
	@FXML
	private void handleCounting() {
		
		double rechnung = Double.valueOf(euro_StdComboBox.getValue()) * Double.valueOf(stdZahlTextField.getText());
		betragTextField.setText("" + rechnung);
	}
}
