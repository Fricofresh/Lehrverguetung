package de.dpma.view;

import de.dpma.model.Events;
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
	TableView<String> tabellenTableView;
	
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
	
	RootLayoutController root;
	
	String fokus = "Veranstaltungen";
	
	@FXML
	public void initialize() {
		
		ObservableList<String> inhalte = FXCollections.observableArrayList("Veranstaltungen", "Dozenten",
				"Lehrvergütungssätze");
		navigationListe.setItems(inhalte);
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
