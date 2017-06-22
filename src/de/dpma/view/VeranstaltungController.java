package de.dpma.view;

import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import de.dpma.FXML_GUI;
import de.dpma.model.Event;
import de.dpma.model.Stundenlohn;
import de.dpma.util.AlertUtil;
import de.dpma.util.DataChecker;
import de.dpma.util.FormatCurrrency;
import de.dpma.util.NumberToText;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class VeranstaltungController {

	@FXML
	TextField dozentTextField = new TextField();

	@FXML
	TextField aktenzeichenTextField = new TextField();

	@FXML
	TextField schulArtTextField = new TextField();

	@FXML
	DatePicker vfgDatePicker = new DatePicker();

	@FXML
	ComboBox<String> vortragComboBox = new ComboBox();

	@FXML
	DatePicker datumVonDatePicker = new DatePicker();

	@FXML
	DatePicker datumBisDatePicker = new DatePicker();

	@FXML
	ComboBox<String> euro_StdComboBox = new ComboBox();

	@FXML
	TextField stdZahlTextField = new TextField();

	@FXML
	TextField betragTextField = new TextField();

	@FXML
	TextField betrag_ABCTextField = new TextField();

	Event event;

	AlertUtil alert;

	KeyEvent keyEvent;

	@FXML
	private void initialize() {

		try {
			ObservableList<Stundenlohn> selectStundenloehne = FXCollections
					.observableArrayList(MainPageController.stundenlohnDAO.selectAllStundenloehne());
			ObservableList<String> euro_StdComboBoxList = FXCollections.observableArrayList();
			for (int i = 0; i < selectStundenloehne.size(); i++) {
				euro_StdComboBoxList.add(FormatCurrrency.format(selectStundenloehne.get(i).getLohn(), false));
			}
			euro_StdComboBox.setItems(euro_StdComboBoxList);
		} catch (SQLException e) {

			e.printStackTrace();
		}

		ObservableList<String> vortragComboBoxList = FXCollections.observableArrayList("Schulung", "Sonstiges");
		vortragComboBox.setItems(vortragComboBoxList);
	}

	public static final LocalDate LOCAL_DATE(String dateString) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
	}

	public void handleNew(Event event) throws SQLException {

		// Stundenlohn Tabelle die Auswahlmöglichkeiten reinschmeißen
		this.event = event;

		dozentTextField.setText(event.DozentString());
		aktenzeichenTextField.setText(event.getAktenz());
		schulArtTextField.setText(event.getSchulart());
		vfgDatePicker.setValue(LOCAL_DATE(event.getVfg()));
		String vortrag;
		if (event.getVortrg_mode() == 1) {
			vortrag = "Schulung";
		} else {
			vortrag = "Sonstiges";
		}
		vortragComboBox.setValue(vortrag);
		datumVonDatePicker.setValue(LOCAL_DATE(event.getDate_start()));
		datumBisDatePicker.setValue(LOCAL_DATE(event.getDate_end()));
		euro_StdComboBox.setValue(
				String.valueOf(MainPageController.stundenlohnDAO.selectStundenlohn(event.getId_euro_std()).getLohn())
						.replace(".", ","));
		String stdZahl = String.valueOf(event.getStdzahl());
		stdZahlTextField.setText(stdZahl);
		String betrag = event.BetragProperty().getValue();
		betragTextField.setText(betrag);
		String betrag_ABC = event.Betrag_ABCProperty().getValue();
		betrag_ABCTextField.setText(betrag_ABC);
	}

	@FXML
	private void handleSubmit() {

		if (dozentTextField.getText().isEmpty() /* || Dozent nicht Vorhanden */) {
			alert = new AlertUtil("Dozent ungültig",
					"Es wurde kein gültiger Dozent eingegeben. Bitte geben Sie einen validen Dozent an und versuchen Sie es erneut.",
					"WARNING");
		} else if (aktenzeichenTextField.getText().isEmpty()) {
			alert = new AlertUtil("Aktenzeichen ungültig",
					"Es wurde kein gültiges Aktenzeichen eingegeben. Bitte geben Sie ein valides Aktenzeichen an und versuchen Sie es erneut.",
					"WARNING");
		} else if (schulArtTextField.getText().isEmpty()) {
			alert = new AlertUtil("Schulungsart ungültig",
					"Es wurde keine gültige Schulungsart eingegeben. Bitte geben Sie eine valide Schulungsart an und versuchen Sie es erneut.",
					"WARNING");
		} else if (vfgDatePicker.getValue().toString().isEmpty()) {
			alert = new AlertUtil("Verfügungstag ungültig",
					"Es wurde kein gültiger Verfügungstag eingegeben. Bitte geben Sie einen validen Verfügungstag an und versuchen Sie es erneut.",
					"WARNING");
		} else if (vortragComboBox.getValue().isEmpty()) {
			alert = new AlertUtil("Vortragsart ungültig",
					"Es wurde keine gültige Vortragsart eingegeben. Bitte geben Sie eine valide Vortragsart an und versuchen Sie es erneut.",
					"WARNING");
		} else if (datumVonDatePicker.getValue().toString().isEmpty()) {
			alert = new AlertUtil("Datum ungültig",
					"Es wurde kein gültiges Startdatum eingegeben. Bitte geben Sie ein valides Startdatum an und versuchen Sie es erneut.",
					"WARNING");
		} else if (datumBisDatePicker.getValue().toString().isEmpty()) {
			alert = new AlertUtil("Datum ungültig",
					"Es wurde kein gültiges Enddatum eingegeben. Bitte geben Sie ein valides Enddatum an und versuchen Sie es erneut.",
					"WARNING");
		}

		else if (stdZahlTextField.getText().isEmpty()) {
			alert = new AlertUtil("Stundenanzahl ungültig",
					"Es wurde keine gültige Stundenanzahl eingegeben. Bitte geben Sie eine valide Stundenanzahl an und versuchen Sie es erneut.",
					"WARNING");
		} else {

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
				} else {
					MainPageController.eventDAO.updateEvent(event);
				}
				FXML_GUI.primaryStage.close();
			} catch (Exception e) {
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

		if (euro_StdComboBox.getValue().isEmpty() || stdZahlTextField.getText().isEmpty()
				|| !DataChecker.isNumeric(euro_StdComboBox.getValue().replace(",", "."))
				|| !DataChecker.isNumeric(stdZahlTextField.getText().replace(",", "."))) {
			betragTextField.setText("");
			betrag_ABCTextField.setText("");
		} else {
			DecimalFormat df2 = new DecimalFormat("#.##");
			df2.setRoundingMode(RoundingMode.HALF_UP);

			Double rechnung = Double.valueOf(df2.format((Double.valueOf(euro_StdComboBox.getValue().replace(",", "."))
					* Double.valueOf(stdZahlTextField.getText()))).replace(",", "."));
			betragTextField.setText(FormatCurrrency.format(rechnung, true));
			betrag_ABCTextField.setText(NumberToText.NumberToText(Double.valueOf(rechnung)));

		}
	}

	@FXML
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
