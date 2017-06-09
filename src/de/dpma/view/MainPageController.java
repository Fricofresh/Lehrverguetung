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
	TableColumn<Events, String> nameTableColumn;
	
	@FXML
	TableColumn<Events, String> aktenzeichenTableColumn;
	
	@FXML
	TableColumn<Events, String> schulArt;
	
	@FXML
	TableColumn<Events, String> vfgTableColumn;
	
	@FXML
	TableColumn<Events, String> vortragTableColumn;
	
	@FXML
	TableColumn<Events, String> datumTableColumn;
	
	@FXML
	TableColumn<Events, String> euro_StdTableColumn;
	
	@FXML
	TableColumn<Events, String> stdZahlTableColumn;
	
	@FXML
	TableColumn<Events, String> betragTableColumn;
	
	@FXML
	TableColumn<Events, String> betrag_ABCTableColumn;
	
	AlertUtil alert;
	
	@FXML
	public void initialize() {
		
		ObservableList<String> inhalte = FXCollections.observableArrayList("Veranstaltungen", "Dozenten",
				"Lehrvergütungssätze");
		navigationListe.setItems(inhalte);
	}
	
	@FXML
	public void handleSelect() {
		
		String fokus = navigationListe.getFocusModel().getFocusedItem();
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
	public void handleDelete() {
		
		String aktAuswahl = "";
		// aufrufen der SQL Befehles um den Eintrag zu droppen, dann kann die
		// Tabelle nochmal aktuallisiert werden
	}
	
	@FXML
	public void handleEdit() {
		
		String aktAuswahl = "";
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
