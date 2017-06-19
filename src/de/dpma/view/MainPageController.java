package de.dpma.view;

import java.io.File;
import java.sql.SQLException;

import de.dpma.FXML_GUI;
import de.dpma.MainApp;
import de.dpma.dao.DozentDAO;
import de.dpma.dao.EventDAO;
import de.dpma.dao.StundenlohnDAO;
import de.dpma.model.Dozent;
import de.dpma.model.Event;
import de.dpma.model.Stundenlohn;
import de.dpma.util.AlertUtil;
import de.dpma.util.WriteDocxTEST;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

public class MainPageController {
	
	@FXML
	ListView<String> navigationListe = new ListView<String>();
	
	@FXML
	TableView tabellenTableView;
	
	AlertUtil alert;
	
	RootLayoutController root = new RootLayoutController();
	
	String fokus = "Veranstaltungen";
	
	static DozentDAO dozentDAO = new DozentDAO(MainApp.dbcon.getConnection());
	
	static EventDAO eventDAO = new EventDAO(MainApp.dbcon.getConnection());
	
	static StundenlohnDAO stundenlohnDAO = new StundenlohnDAO(MainApp.dbcon.getConnection());
	
	Event event;
	
	Dozent dozent;
	
	Stundenlohn stundenlohn;
	
	@FXML
	public void initialize() {
		
		FXML_GUI.primaryStage.setTitle(fokus);
		ObservableList<String> inhalte = FXCollections.observableArrayList("Veranstaltungen", "Dozenten",
				"Lehrvergütungssätze");
		navigationListe.setItems(inhalte);
		
		handleVeranstaltungen();
		
	}
	
	@FXML
	public void handleSelect() {
		
		fokus = navigationListe.getFocusModel().getFocusedItem();
		
		FXML_GUI.primaryStage.setTitle(fokus);
		
		if (fokus.equals("Veranstaltungen")) {
			handleVeranstaltungen();
		}
		else if (fokus.equals("Dozenten")) {
			handleDozenten();
		}
		else if (fokus.equals("Lehrvergütungssätze")) {
			handleLehrverguetung();
		}
		else {
			alert = new AlertUtil("Der Ausgewählte Punkt ist ungültig", "Bitte kontaktieren Sie den Administrator",
					"WARNING");
		}
	}
	
	@FXML
	public void handleNew() {
		
		root.handleGUI(fokus, null);
	}
	
	@FXML
	public void handleDelete() {
		
		int selectedIndex = tabellenTableView.getSelectionModel().getSelectedIndex();
		if (fokus.equals("Veranstaltungen")) {
			event = (Event) tabellenTableView.getSelectionModel().getSelectedItem();
			tabellenTableView.getItems().remove(selectedIndex);
			try {
				eventDAO.deleteEvent(event.getId());
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if (fokus.equals("Dozenten")) {
			dozent = (Dozent) tabellenTableView.getSelectionModel().getSelectedItem();
		}
		else if (fokus.equals("Lehrvergütungssätze")) {
			stundenlohn = (Stundenlohn) tabellenTableView.getSelectionModel().getSelectedItem();
		}
		
		// aufrufen der SQL Befehles um den Eintrag zu droppen, dann kann die
		// Tabelle nochmal aktuallisiert werden
	}
	
	@FXML
	public void handleEdit() {
		
		int selectedIndex = tabellenTableView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			switch (fokus) {
			case "Veranstaltungen":
				event = (Event) tabellenTableView.getSelectionModel().getSelectedItem();
				VeranstaltungController vc = new VeranstaltungController();
				vc.handleNew(event);
				break;
			case "Dozenten":
				dozent = (Dozent) tabellenTableView.getSelectionModel().getSelectedItem();
				DozentController dc = new DozentController();
				dc.handleNew(dozent);
				break;
			case "Lehrvergütungssätze":
				stundenlohn = (Stundenlohn) tabellenTableView.getSelectionModel().getSelectedItem();
				LehrverguetungssaetzeController lc = new LehrverguetungssaetzeController();
				lc.handleNew(stundenlohn);
				break;
			
			default:
				break;
			}
		}
		
		root.handleGUI(fokus, null);
	}
	
	private void handleVeranstaltungen() {
		
		TableColumn<Event, String> nameTableColumn = new TableColumn("Name");
		TableColumn<Event, String> aktenzeichenTableColumn = new TableColumn("Aktenzeichen");
		TableColumn<Event, String> schulArt = new TableColumn("SchulArt");
		TableColumn<Event, String> vfgTableColumn = new TableColumn("Vfg");
		TableColumn<Event, String> vortragTableColumn = new TableColumn("Vortrag");
		TableColumn<Event, String> datumTableColumn = new TableColumn("Datum");
		TableColumn<Event, String> euro_StdTableColumn = new TableColumn("Euro_Std");
		TableColumn<Event, String> stdZahlTableColumn = new TableColumn("StdZahl");
		TableColumn<Event, String> betragTableColumn = new TableColumn("Betrag");
		TableColumn<Event, String> betrag_ABCTableColumn = new TableColumn("Betrag_ABC");
		
		tabellenTableView.getColumns().setAll(nameTableColumn, aktenzeichenTableColumn, schulArt, vfgTableColumn,
				vortragTableColumn, datumTableColumn, euro_StdTableColumn, stdZahlTableColumn, betragTableColumn,
				betrag_ABCTableColumn);
		
		try {
			ObservableList<Event> eventData = FXCollections.observableArrayList();
			eventData = FXCollections.observableArrayList((eventDAO.selectAllEvents()));
			
			tabellenTableView.setItems(eventData);
			
			nameTableColumn.setCellValueFactory(cellData -> cellData.getValue().DozentProperty());
			aktenzeichenTableColumn.setCellValueFactory(cellData -> cellData.getValue().AktenzProperty());
			schulArt.setCellValueFactory(cellData -> cellData.getValue().SchulartProperty());
			vfgTableColumn.setCellValueFactory(cellData -> cellData.getValue().VfgProperty());
			vortragTableColumn.setCellValueFactory(cellData -> cellData.getValue().VortragsArtProperty());
			datumTableColumn.setCellValueFactory(cellData -> cellData.getValue().DateProperty());
			euro_StdTableColumn.setCellValueFactory(cellData -> cellData.getValue().Euro_stdProperty());
			stdZahlTableColumn.setCellValueFactory(cellData -> cellData.getValue().getStundenZahlString());
			betragTableColumn.setCellValueFactory(cellData -> cellData.getValue().BetragProperty());
			betrag_ABCTableColumn.setCellValueFactory(cellData -> cellData.getValue().Betrag_ABCProperty());
		}
		catch (SQLException e) {
			alert = new AlertUtil("Datenbankfehler",
					"Es ist ein Fehler bei der Datenbankabfrage enstanden, Bitte kontaktieren Sie den Administrator",
					"WARNING");
			e.printStackTrace();
		}
		
	}
	
	private void handleDozenten() {
		
		TableColumn<Dozent, String> anredeTableColumn = new TableColumn("Anrede");
		TableColumn<Dozent, String> titelTableColumn = new TableColumn("Titel");
		TableColumn<Dozent, String> vornameTableColumn = new TableColumn("Vorname");
		TableColumn<Dozent, String> nameTableColumn = new TableColumn("Name");
		TableColumn<Dozent, String> straßeTableColumn = new TableColumn("Straße");
		TableColumn<Dozent, String> pLZTableColumn = new TableColumn("PLZ");
		TableColumn<Dozent, String> ortTableColumn = new TableColumn("Ort");
		TableColumn<Dozent, String> kontonummerTableColumn = new TableColumn("Kontonummer");
		TableColumn<Dozent, String> bankTableColumn = new TableColumn("Bank");
		TableColumn<Dozent, String> bLZTableColumn = new TableColumn("BLZ");
		tabellenTableView.getColumns().setAll(anredeTableColumn, titelTableColumn, vornameTableColumn, nameTableColumn,
				straßeTableColumn, pLZTableColumn, ortTableColumn, kontonummerTableColumn, bankTableColumn,
				bLZTableColumn);
		
		try {
			ObservableList<Dozent> dozentData = FXCollections.observableArrayList();
			
			dozentData = FXCollections.observableArrayList(dozentDAO.selectAllDozenten());
			
			tabellenTableView.setItems(dozentData);
			
			anredeTableColumn.setCellValueFactory(cellData -> cellData.getValue().AnredeProperty());
			titelTableColumn.setCellValueFactory(cellData -> cellData.getValue().TitelProperty());
			vornameTableColumn.setCellValueFactory(cellData -> cellData.getValue().VornameProperty());
			nameTableColumn.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
			straßeTableColumn.setCellValueFactory(cellData -> cellData.getValue().StrasseProperty());
			pLZTableColumn.setCellValueFactory(cellData -> cellData.getValue().PLZProperty());
			ortTableColumn.setCellValueFactory(cellData -> cellData.getValue().OrtProperty());
			kontonummerTableColumn.setCellValueFactory(cellData -> cellData.getValue().IBANProperty());
			bankTableColumn.setCellValueFactory(cellData -> cellData.getValue().BankProperty());
			bLZTableColumn.setCellValueFactory(cellData -> cellData.getValue().BLZProperty());
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void handleLehrverguetung() {
		
		TableColumn<Stundenlohn, String> vergütungTableColumn = new TableColumn("Vergütung");
		tabellenTableView.getColumns().setAll(vergütungTableColumn);
		
		try {
			ObservableList<Stundenlohn> stundenlohnData = FXCollections.observableArrayList();
			
			stundenlohnData = FXCollections.observableArrayList(stundenlohnDAO.selectAllStundenloehne());
			
			tabellenTableView.setItems(stundenlohnData);
			
			vergütungTableColumn.setCellValueFactory(cellData -> cellData.getValue().LohnProperty());
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void handleCreateDocRechnung() {
		
		int selectedIndex = tabellenTableView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			switch (fokus) {
			case "Veranstaltungen":
				event = (Event) tabellenTableView.getSelectionModel().getSelectedItem();
				break;
			case "Dozenten":
				
				break;
			case "Lehrvergütungssätze":
				
				break;
			
			default:
				break;
			}
		}
		
		try {
			
			// TODO Word export
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Speichern");
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word Datei (*.docx)", "*.docx");
			chooser.getExtensionFilters().add(extFilter);
			
			String directory = chooser.getExtensionFilters().toString();
			
			File file = chooser.showSaveDialog(FXML_GUI.primaryStage.getScene().getWindow());
			WriteDocxTEST wdoc = new WriteDocxTEST(file);
		}
		catch (Exception e) {
		}
	}
	
	public void handleCreateDocAuszahlung() {
		
		// TODO Word export
	}
	
	public void handleSearch() {
		
		// TODO Suchenfunktion
	}
}
