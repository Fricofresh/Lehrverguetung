package de.dpma.view;

import java.sql.SQLException;

import de.dpma.MainApp;
import de.dpma.dao.EventDAO;
import de.dpma.model.Event;
import de.dpma.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainPageController {
	
	@FXML
	ListView<String> navigationListe = new ListView<String>();
	
	@FXML
	TableView<Event> tabellenTableView;
	
	@FXML
	TableColumn<Event, String> nameTableColumn;
	
	@FXML
	TableColumn<Event, String> aktenzeichenTableColumn;
	
	@FXML
	TableColumn<Event, String> schulArt;
	
	@FXML
	TableColumn<Event, String> vfgTableColumn;
	
	@FXML
	TableColumn<Event, String> vortragTableColumn;
	
	@FXML
	TableColumn<Event, String> datumTableColumn;
	
	@FXML
	TableColumn<Event, String> euro_StdTableColumn;
	
	@FXML
	TableColumn<Event, String> stdZahlTableColumn;
	
	@FXML
	TableColumn<Event, String> betragTableColumn;
	
	@FXML
	TableColumn<Event, String> betrag_ABCTableColumn;
	
	AlertUtil alert;
	
	RootLayoutController root = new RootLayoutController();
	
	String fokus = "Veranstaltungen";
	
	private ObservableList<Event> eventData = FXCollections.observableArrayList();
	
	static EventDAO eventDAO = new EventDAO(MainApp.dbcon.getConnection());
	
	@FXML
	public void initialize() throws SQLException {
		
		ObservableList<String> inhalte = FXCollections.observableArrayList("Veranstaltungen", "Dozenten",
				"Lehrvergütungssätze");
		navigationListe.setItems(inhalte);
		
		eventData = FXCollections.observableArrayList((eventDAO.selectAllEvents()));
		
		tabellenTableView.setItems(eventData);
		
		// nameTableColumn.setCellValueFactory(cellData ->
		// cellData.getValue().dozentName());
		aktenzeichenTableColumn.setCellValueFactory(cellData -> cellData.getValue().AktenzProperty());
		schulArt.setCellValueFactory(cellData -> cellData.getValue().SchulartProperty());
		vfgTableColumn.setCellValueFactory(cellData -> cellData.getValue().VfgProperty());
		// vortragTableColumn.setCellValueFactory(cellData ->
		// cellData.getValue().vortragName());
		// datumTableColumn.setCellValueFactory(
		// cellData -> cellData.getValue().getDate_start() + " " +
		// cellData.getValue().getDate_end());
		// euro_StdTableColumn.setCellValueFactory(cellData ->
		// cellData.getValue().euroStdString());
		stdZahlTableColumn.setCellValueFactory(cellData -> cellData.getValue().getStundenZahlString());
		// betragTableColumn.setCellValueFactory(cellData ->
		// cellData.getValue().betragString());
		// betrag_ABCTableColumn.setCellValueFactory(cellData ->
		// cellData.getValue().betrag_ABCString());
		
	}
	
	@FXML
	public void handleSelect() {
		
		fokus = navigationListe.getFocusModel().getFocusedItem();
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
		
		root.handleGUI(fokus, false);
	}
	
	@FXML
	public void handleDelete() {
		
		String aktAuswahl = "";
		// aufrufen der SQL Befehles um den Eintrag zu droppen, dann kann die
		// Tabelle nochmal aktuallisiert werden
	}
	
	@FXML
	public void handleEdit() {
		
		root.handleGUI(fokus, true);
		// Select Befehl um das ausgewählte Tabelle
	}
	
	private void handleVeranstaltungen() {
		
		// TODO Tabelle mit den Daten füllen
		
	}
	
	private void handleDozenten() {
		
		// TODO Tabelle mit den Daten füllen
		
	}
	
	private void handleLehrverguetung() {
		
		// TODO Tabelle mit den Daten füllen
		
	}
}
