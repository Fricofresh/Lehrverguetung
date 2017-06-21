package de.dpma.view;

import java.sql.SQLException;
import java.util.List;

import de.dpma.FXML_GUI;
import de.dpma.MainApp;
import de.dpma.dao.DozentDAO;
import de.dpma.dao.EventDAO;
import de.dpma.dao.StundenlohnDAO;
import de.dpma.model.Dozent;
import de.dpma.model.Event;
import de.dpma.model.Stundenlohn;
import de.dpma.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainPageController {
	
	@FXML
	ListView<String> navigationListe = new ListView<String>();
	
	@FXML
	TableView tabellenTableView;
	
	@FXML
	TextField searchField = new TextField();
	
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
	public void initialize() throws SQLException {
		
		FXML_GUI.primaryStage.setTitle(fokus);
		navigationListe.getSelectionModel().select(fokus);
		ObservableList<String> inhalte = FXCollections.observableArrayList("Veranstaltungen", "Dozenten",
				"Lehrvergütungssätze");
		navigationListe.setItems(inhalte);
		
		handleSearch();
		
	}
	
	@FXML
	public void handleSelect() throws SQLException {
		
		fokus = navigationListe.getFocusModel().getFocusedItem();
		
		FXML_GUI.primaryStage.setTitle(fokus);
		
		handleSearch();
	}
	
	@FXML
	public void handleNew() {
		
		root.handleGUI(fokus, null);
	}
	
	@FXML
	public void handleDelete() {
		
		int selectedIndex = tabellenTableView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
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
				tabellenTableView.getItems().remove(selectedIndex);
				
				try {
					dozentDAO.deleteDozent(dozent.getId());
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if (fokus.equals("Lehrvergütungssätze")) {
				stundenlohn = (Stundenlohn) tabellenTableView.getSelectionModel().getSelectedItem();
				tabellenTableView.getItems().remove(selectedIndex);
				
				try {
					stundenlohnDAO.deleteStundenlohn(stundenlohn.getId());
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			alert = new AlertUtil("Keine Auswahl",
					"Sie haben kein zu löschendes Element ausgewählt. Bitte wählen Sie ein Element aus und versuchen Sie es erneut.",
					"INFO");
		}
		
		// aufrufen der SQL Befehles um den Eintrag zu droppen, dann kann die
		// Tabelle nochmal aktuallisiert werden
	}
	
	@FXML
	public void handleEdit() throws SQLException {
		
		int selectedIndex = tabellenTableView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			switch (fokus) {
			case "Veranstaltungen":
				event = (Event) tabellenTableView.getSelectionModel().getSelectedItem();
				root.handleGUI(fokus, event);
				FXML_GUI.primaryStage.setTitle(fokus.replace("gen", "g") + " ändern");
				break;
			case "Dozenten":
				dozent = (Dozent) tabellenTableView.getSelectionModel().getSelectedItem();
				root.handleGUI(fokus, dozent);
				FXML_GUI.primaryStage.setTitle(fokus.replace("ten", "t") + " ändern");
				break;
			case "Lehrvergütungssätze":
				stundenlohn = (Stundenlohn) tabellenTableView.getSelectionModel().getSelectedItem();
				root.handleGUI(fokus, stundenlohn);
				FXML_GUI.primaryStage.setTitle(fokus.replace("ätze", "atz") + " ändern");
				break;
			
			default:
				break;
			}
		}
		else {
			alert = new AlertUtil("Keine Auswahl",
					"Sie haben kein zu bearbeitendes Element ausgewählt. Bitte wählen Sie ein Element aus und versuchen Sie es erneut.",
					"INFO");
		}
		
		// root.handleGUI(fokus, null);
	}
	
	public void handleCreateDocRechnung() {
		
		int selectedIndex = tabellenTableView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			event = (Event) tabellenTableView.getSelectionModel().getSelectedItem();
			root.handleGUI("createDoc");
			FXML_GUI.primaryStage.setTitle("Rechnungsbegleitblatt exportieren");
		}
		else {
			// TODO Rechtschreibprüfung
			alert = new AlertUtil("Keine Auswahl",
					"Sie haben kein zu exportierendes Element ausgewählt. Bitte wählen Sie ein Element aus und versuchen Sie es erneut.",
					"INFO");
		}
		
	}
	
	public void handleCreateDocAuszahlung() {
		
		int selectedIndex = tabellenTableView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			event = (Event) tabellenTableView.getSelectionModel().getSelectedItem();
			root.handleGUI("createDoc");
			FXML_GUI.primaryStage.setTitle("Auszahlung Lehrvergütung exportieren");
		}
		else {
			// TODO Rechtschreibprüfung
			alert = new AlertUtil("Keine Auswahl",
					"Sie haben kein zu exportierendes Element ausgewählt. Bitte wählen Sie ein Element aus und versuchen Sie es erneut.",
					"INFO");
		}
	}
	
	private void insertIntoDozentenTable(List<Dozent> input) throws SQLException {
		
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
		
		ObservableList<Dozent> dozentData = FXCollections.observableArrayList();
		
		dozentData = FXCollections.observableArrayList(input);
		
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
	
	private void insertIntoLehrvergueungssaetzeTable(List<Stundenlohn> input) throws SQLException {
		
		TableColumn<Stundenlohn, String> vergütungTableColumn = new TableColumn("Vergütung");
		
		tabellenTableView.getColumns().setAll(vergütungTableColumn);
		
		ObservableList<Stundenlohn> stundenlohnData = FXCollections.observableArrayList();
		stundenlohnData = FXCollections.observableArrayList(input);
		
		tabellenTableView.setItems(stundenlohnData);
		
		vergütungTableColumn.setCellValueFactory(cellData -> cellData.getValue().LohnProperty());
	}
	
	private void insertIntoVeranstaltungenTable(List<Event> input) throws SQLException {
		
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
		
		ObservableList<Event> eventData = FXCollections.observableArrayList();
		eventData = FXCollections.observableArrayList(input);
		
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
	
	public void handleSearch() throws SQLException {
		
		if (searchField.getText() != "") {
			switch (fokus) {
			case "Veranstaltungen":
				insertIntoVeranstaltungenTable(eventDAO.searchEvent(searchField.getText()));
				break;
			case "Dozenten":
				insertIntoDozentenTable(dozentDAO.searchDozent(searchField.getText()));
				break;
			case "Lehrvergütungssätze":
				insertIntoLehrvergueungssaetzeTable(stundenlohnDAO.searchStundenlohn(searchField.getText()));
				break;
			default:
				break;
			}
		}
		else {
			if (fokus.equals("Veranstaltungen")) {
				insertIntoVeranstaltungenTable(eventDAO.selectAllEvents());
			}
			else if (fokus.equals("Dozenten")) {
				insertIntoDozentenTable(dozentDAO.selectAllDozenten());
			}
			else if (fokus.equals("Lehrvergütungssätze")) {
				insertIntoLehrvergueungssaetzeTable(stundenlohnDAO.selectAllStundenloehne());
			}
			
		}
		
	}
}
