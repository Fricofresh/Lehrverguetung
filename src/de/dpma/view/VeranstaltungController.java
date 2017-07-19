package de.dpma.view;

import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import de.dpma.FXML_GUI;
import de.dpma.MainApp;
import de.dpma.model.Dozent;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * The controller for the events. <br>
 * The controller checks if a data set has to be added or edited
 * 
 * @author Kenneth Böhmer
 *
 */
public class VeranstaltungController {
	
	@FXML
	ComboBox<String> dozentComboBox = new ComboBox();
	
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
	
	private int getStageID;
	
	/**
	 * Fills the combobox.
	 * 
	 * @author Kenneth Böhmer
	 */
	@FXML
	private void initialize() {
		
		try {
			// Stage ID zuweisen
			getStageID = MainApp.counter;
			// Stundenlöhne Combobox füllen
			ObservableList<Stundenlohn> selectStundenloehne = FXCollections
					.observableArrayList(MainPageController.stundenlohnDAO.selectAllStundenloehne());
			ObservableList<String> euro_StdComboBoxList = FXCollections.observableArrayList();
			for (int i = 0; i < selectStundenloehne.size(); i++) {
				euro_StdComboBoxList.add(FormatCurrrency.format(selectStundenloehne.get(i).getLohn(), false));
			}
			euro_StdComboBox.setItems(euro_StdComboBoxList);
			
			// Dozenten Combobox initial mit Auswahlmöglichkeiten füllen
			fillDozentenCheckbox(FXCollections.observableArrayList(MainPageController.dozentDAO.selectAllDozenten()),
					false);
		}
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		// Vortragsart mit Daten füllen
		ObservableList<String> vortragComboBoxList = FXCollections.observableArrayList("Schulung", "Sonstiges");
		vortragComboBox.setItems(vortragComboBoxList);
	}
	
	/**
	 * Formats the date.
	 * 
	 * @author Kenneth Böhmer
	 * @param dateString
	 * @return the date
	 */
	public static final LocalDate LOCAL_DATE(String dateString) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
	}
	
	/**
	 * Sets the data set.
	 * 
	 * @author Kenneth Böhmer
	 * @param event
	 *            is the data set
	 * @throws SQLException
	 */
	public void handleNew(Event event) {
		
		try {
			this.event = event;
			dozentComboBox.getEditor().setText(event.DozentString());
			aktenzeichenTextField.setText(event.getAktenz());
			schulArtTextField.setText(event.getSchulart());
			vfgDatePicker.setValue(LOCAL_DATE(event.getVfg()));
			String vortrag;
			if (event.getVortrg_mode() == 1) {
				vortrag = "Schulung";
			}
			else {
				vortrag = "Sonstiges";
			}
			vortragComboBox.setValue(vortrag);
			datumVonDatePicker.setValue(LOCAL_DATE(event.getDate_start()));
			datumBisDatePicker.setValue(LOCAL_DATE(event.getDate_end()));
			euro_StdComboBox.setValue(String
					.valueOf(MainPageController.stundenlohnDAO.selectStundenlohn(event.getId_euro_std()).getLohn())
					.replace(".", ","));
			String stdZahl = String.valueOf(event.getStdzahl());
			stdZahlTextField.setText(stdZahl);
			String betrag = event.BetragProperty().getValue();
			betragTextField.setText(betrag);
			String betrag_ABC = event.Betrag_ABCProperty().getValue();
			betrag_ABCTextField.setText(betrag_ABC);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks the date if it is not possible like 21.08.2015 to 19.01.2000
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	private boolean checkDateRange(String date1, String date2) {
		
		java.sql.Date gettedDatePickerDate1 = java.sql.Date.valueOf(date1);
		java.sql.Date gettedDatePickerDate2 = java.sql.Date.valueOf(date2);
		
		long time1 = gettedDatePickerDate1.getTime();
		long time2 = gettedDatePickerDate2.getTime();
		
		return time2 >= time1;
	}
	
	@FXML
	private void handleSubmit() {
		
		int dozentId;
		try {
			dozentId = MainPageController.dozentDAO.searchDozentIdByCodename(dozentComboBox.getEditor().getText());
			
			// Überprüft ob die Eingabe gültig ist.
			int stundenlohnId = euro_StdComboBox.getValue() == null ? -2
					: MainPageController.stundenlohnDAO.searchStundenlohnByValue(
							String.valueOf(Double.valueOf(euro_StdComboBox.getValue().replace(",", "."))));
			
			if (DataChecker.isEmpty(dozentComboBox.getEditor().getText())) {
				alert = new AlertUtil("Dozent ungültig",
						"Es wurde kein gültiger Dozent eingegeben. Bitte geben Sie einen validen Dozent an und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (dozentId < 0) {
				alert = new AlertUtil("Dozent ungültig",
						"Es wurde kein gültiger Dozent ausgewählt. Bitte wählen Sie einen validen Dozent aus und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (DataChecker.isEmpty(aktenzeichenTextField.getText())) {
				alert = new AlertUtil("Aktenzeichen ungültig",
						"Es wurde kein gültiges Aktenzeichen eingegeben. Bitte geben Sie ein valides Aktenzeichen an und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (DataChecker.isEmpty(schulArtTextField.getText())) {
				alert = new AlertUtil("Schulungsart ungültig",
						"Es wurde keine gültige Schulungsart ausgewählt. Bitte wählen Sie eine valide Schulungsart aus und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (vfgDatePicker.getValue() == null || DataChecker.isEmpty(vfgDatePicker.getValue().toString())) {
				alert = new AlertUtil("Verfügungstag ungültig",
						"Es wurde kein gültiger Verfügungstag eingegeben. Bitte geben Sie einen validen Verfügungstag an und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (DataChecker.isEmpty(vortragComboBox.getValue())) {
				alert = new AlertUtil("Vortragsart ungültig",
						"Es wurde keine gültige Vortragsart ausgewählt. Bitte wählen Sie eine valide Vortragsart aus und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (datumVonDatePicker.getValue() == null
					|| DataChecker.isEmpty(datumVonDatePicker.getValue().toString())) {
				alert = new AlertUtil("Startdatum ungültig",
						"Es wurde kein gültiges Startdatum eingegeben. Bitte geben Sie ein valides Startdatum an und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (datumBisDatePicker.getValue() == null
					|| DataChecker.isEmpty(datumBisDatePicker.getValue().toString())) {
				alert = new AlertUtil("Enddatum ungültig",
						"Es wurde kein gültiges Enddatum eingegeben. Bitte geben Sie ein valides Enddatum an und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (!checkDateRange(datumVonDatePicker.getValue().toString(),
					datumBisDatePicker.getValue().toString())) {
				alert = new AlertUtil("Datumsbereich ungültig",
						"Das Enddatum liegt vor dem Startdatum. Bitte geben Sie einen validen Datumsbereich an und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (stdZahlTextField.getText().isEmpty()) {
				alert = new AlertUtil("Stundenanzahl ungültig",
						"Es wurde keine gültige Stundenanzahl eingegeben. Bitte geben Sie eine valide Stundenanzahl an und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (stundenlohnId < -1) {
				alert = new AlertUtil("Stundensatz ungültig",
						"Es wurde kein gültiger Stundensatz ausgewählt. Bitte wählen Sie einen validen Stundensatz aus und versuchen Sie es erneut.",
						"WARNING");
			}
			else if (stundenlohnId < 0) {
				alert = new AlertUtil("Stundensatz konnte nicht validiert werden",
						"Es ist ein interner Fehler bei der Validierung des Stundensatzes aufgetreten.", "ERROR");
			}
			else {
				
				if (event == null) {
					event = new Event();
				}
				
				this.event.setId_dozent(dozentId);
				this.event.setAktenz(aktenzeichenTextField.getText().trim());
				this.event.setDate_start(datumVonDatePicker.getValue().toString().trim());
				this.event.setDate_end(datumBisDatePicker.getValue().toString().trim());
				this.event.setId_euro_std(stundenlohnId);
				this.event.setSchulart(schulArtTextField.getText().trim());
				int stdZahl = Integer.parseInt(stdZahlTextField.getText().trim());
				this.event.setStdzahl(stdZahl);
				this.event.setVfg(vfgDatePicker.getValue().toString().trim());
				if (event.getId() == 0) {
					MainPageController.eventDAO.insertEvent(event);
				}
				else {
					MainPageController.eventDAO.updateEvent(event);
				}
				FXML_GUI.primaryStage[this.getStageID].close();
			}
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Closes the stage
	 * 
	 * @author Kenneth Böhmer
	 */
	@FXML
	private void handleCancel() {
		
		FXML_GUI.primaryStage[this.getStageID].close();
	}
	
	/**
	 * Search the lecturer while typing.
	 * 
	 * @author Kenneth Böhmer
	 */
	@FXML
	private void handleTypingDozent() {
		
		if (dozentComboBox.isFocused()) {
			try {
				fillDozentenCheckbox(FXCollections.observableArrayList(
						MainPageController.dozentDAO.searchDozentFullname(dozentComboBox.getEditor().getText())), true);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Schows the results of possible lecturer.
	 * 
	 * @author Kenneth Böhmer
	 * @param dataSource
	 * @param autoHider
	 * @throws SQLException
	 */
	private void fillDozentenCheckbox(ObservableList<Dozent> dataSource, boolean autoHider) throws SQLException {
		
		ObservableList<Dozent> listResult = dataSource;
		
		dozentComboBox.setCellFactory(lv -> {
			ListCell<String> cell = new ListCell<String>() {
				
				@Override
				protected void updateItem(String item, boolean empty) {
					
					super.updateItem(item, empty);
					setText(empty ? null : item);
				}
			};
			cell.setOnMousePressed(e -> {
				if (!cell.isEmpty()) {
					dozentComboBox.setValue(null);
					dozentComboBox.getEditor().setText(cell.getText());
				}
			});
			return cell;
		});
		
		ObservableList<String> results = FXCollections.observableArrayList();
		for (int i = 0; i < listResult.size(); i++) {
			results.add(listResult.get(i).getAnrede()
					+ (listResult.get(i).getTitel().isEmpty() ? "" : " " + listResult.get(i).getTitel()) + " "
					+ listResult.get(i).getVorname() + " " + listResult.get(i).getName());
			
		}
		dozentComboBox.setItems(results);
		
		if (autoHider) {
			if (!dozentComboBox.getItems().isEmpty()) {
				dozentComboBox.show();
			}
			else {
				dozentComboBox.hide();
			}
		}
	}
	
	@FXML
	private void handleCounting() {
		
		if (euro_StdComboBox.getValue() == null || euro_StdComboBox.getValue().isEmpty()
				|| stdZahlTextField.getText() == null || stdZahlTextField.getText().isEmpty()
				|| !DataChecker.isNumeric(euro_StdComboBox.getValue().replace(",", "."))
				|| !DataChecker.isNumeric(stdZahlTextField.getText().replace(",", "."))) {
			betragTextField.setText("");
			betrag_ABCTextField.setText("");
		}
		else {
			DecimalFormat df2 = new DecimalFormat("#.##");
			df2.setRoundingMode(RoundingMode.HALF_UP);
			// Der Betrag wird berechnet
			Double rechnung = Double.valueOf(df2.format((Double.valueOf(euro_StdComboBox.getValue().replace(",", "."))
					* Double.valueOf(stdZahlTextField.getText()))).replace(",", "."));
			betragTextField.setText(FormatCurrrency.format(rechnung, true));
			betrag_ABCTextField.setText(NumberToText.NumberToText(Double.valueOf(rechnung)));
			
		}
	}
	
	/**
	 * Checks if Enter or Escape is pressed to submit or cancel.
	 * 
	 * @author Kenneth Böhmer
	 * @param keyEvent
	 */
	@FXML
	private void handleKeyPressed(KeyEvent keyEvent) throws SQLException {
		
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
	
}
