package de.dpma.view;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
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
	MenuButton dokumentErstellenMenuButton;

	@FXML
	public void initialize() throws SQLException, ParseException {
		
		this.getStageID = MainApp.counter;
		FXML_GUI.primaryStage[this.getStageID].setTitle(fokus);
		navigationListe.getSelectionModel().select(fokus);
		ObservableList<String> inhalte = FXCollections.observableArrayList("Veranstaltungen", "Dozenten",
				"Lehrvergütungssätze");
		navigationListe.setItems(inhalte);

		handleSearch();
	}

	@FXML
	public void handleSelect() throws SQLException, ParseException {

		fokus = navigationListe.getFocusModel().getFocusedItem();
		if (fokus == null) {
			fokus = "Veranstaltungen";
		}

		if (!fokus.equals("Veranstaltungen")) {
			dokumentErstellenMenuButton.setDisable(true);
		} else {
			dokumentErstellenMenuButton.setDisable(false);
		}
		
		FXML_GUI.primaryStage[this.getStageID].setTitle(fokus);
		
		handleSearch();
	}

	@FXML
	public void handleNew() {

		root.handleGUI(fokus, null);

		switch (fokus) {
		case "Veranstaltungen":
			FXML_GUI.primaryStage[MainApp.counter].setTitle(fokus.replace("gen", "g") + " hinzufügen");
			break;
		case "Dozenten":
			FXML_GUI.primaryStage[MainApp.counter].setTitle(fokus.replace("ten", "t") + " hinzufügen");
			break;
		case "Lehrvergütungssätze":
			FXML_GUI.primaryStage[MainApp.counter].setTitle(fokus.replace("ätze", "atz") + " hinzufügen");
			break;

		default:
			break;
		}
	}

	@FXML
	public void handleDelete() {

		int selectedIndex = tabellenTableView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			if (fokus.equals("Veranstaltungen")) {
				event = (Event) tabellenTableView.getSelectionModel().getSelectedItem();

				Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);

				DialogPane dialogPane = confirmationAlert.getDialogPane();

				confirmationAlert.setTitle("Löschen bestätigen");
				confirmationAlert.setHeaderText("Möchten Sie diese Veranstaltung wirklich löschen?");
				confirmationAlert.setContentText(null);

				Optional<ButtonType> result = confirmationAlert.showAndWait();
				if (result.get() == ButtonType.OK) {
					try {
						eventDAO.deleteEvent(event.getId());
					} catch (SQLException e) {
						e.printStackTrace();
						return;
					}

					tabellenTableView.getItems().remove(selectedIndex);
				}
			} else if (fokus.equals("Dozenten")) {
				dozent = (Dozent) tabellenTableView.getSelectionModel().getSelectedItem();

				Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);

				DialogPane dialogPane = confirmationAlert.getDialogPane();

				confirmationAlert.setTitle("Löschen bestätigen");
				confirmationAlert.setHeaderText("Möchten Sie diesen Dozenten wirklich löschen?");
				confirmationAlert.setContentText(null);

				Optional<ButtonType> result = confirmationAlert.showAndWait();
				if (result.get() == ButtonType.OK) {
					try {
						dozentDAO.deleteDozent(dozent.getId());
					} catch (org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException e) {
						alert = new AlertUtil("Datenintegritätsfehler",
								"Sie können diesen Dozenten im Moment nicht löschen, da er von einer Veranstaltung verwendet wird.",
								"INFO");
						return;
					} catch (SQLException e) {
						e.printStackTrace();
						return;
					}

					tabellenTableView.getItems().remove(selectedIndex);
				}
			} else if (fokus.equals("Lehrvergütungssätze")) {
				stundenlohn = (Stundenlohn) tabellenTableView.getSelectionModel().getSelectedItem();

				Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);

				DialogPane dialogPane = confirmationAlert.getDialogPane();

				confirmationAlert.setTitle("Löschen bestätigen");
				confirmationAlert.setHeaderText("Möchten Sie diesen Lehrvergütungssatz wirklich löschen?");
				confirmationAlert.setContentText(null);

				Optional<ButtonType> result = confirmationAlert.showAndWait();
				if (result.get() == ButtonType.OK) {
					try {
						stundenlohnDAO.deleteStundenlohn(stundenlohn.getId());
					} catch (org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException e) {
						alert = new AlertUtil("Datenintegritätsfehler",
								"Sie können diesen Lehrvergütungssatz im Moment nicht löschen, da er von einer Veranstaltung verwendet wird.",
								"INFO");
						return;
					} catch (SQLException e) {
						e.printStackTrace();
						return;
					}

					tabellenTableView.getItems().remove(selectedIndex);
				}

			}
		} else {
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
				FXML_GUI.primaryStage[MainApp.counter].setTitle(fokus.replace("gen", "g") + " bearbeiten");
				break;
			case "Dozenten":
				dozent = (Dozent) tabellenTableView.getSelectionModel().getSelectedItem();
				root.handleGUI(fokus, dozent);
				FXML_GUI.primaryStage[MainApp.counter].setTitle(fokus.replace("ten", "t") + " bearbeiten");
				break;
			case "Lehrvergütungssätze":

				stundenlohn = (Stundenlohn) tabellenTableView.getSelectionModel().getSelectedItem();

				Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);

				DialogPane dialogPane = confirmationAlert.getDialogPane();

				confirmationAlert.setTitle("Bearbeiten bestätigen");
				confirmationAlert.setHeaderText("Möchten Sie diesen Lehrvergütungssatz wirklich bearbeiten?");
				confirmationAlert.setContentText(
						"Wenn Sie diesen Lehrvergütungssatz ändern, wird er auch in allen Veranstaltung geändert, welche ihn verwenden.");

				Optional<ButtonType> result = confirmationAlert.showAndWait();
				if (result.get() == ButtonType.OK) {
					stundenlohn = (Stundenlohn) tabellenTableView.getSelectionModel().getSelectedItem();
					root.handleGUI(fokus, stundenlohn);
					FXML_GUI.primaryStage[MainApp.counter].setTitle(fokus.replace("ätze", "atz") + " bearbeiten");
				}
				break;

			default:
				break;
			}
		} else {
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
			FXML_GUI.primaryStage[MainApp.counter].setTitle("Rechnungsbegleitblatt exportieren");
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
			FXML_GUI.primaryStage[MainApp.counter].setTitle("Auszahlung Lehrvergütung exportieren");
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
		TableColumn<Dozent, String> kontonummerTableColumn = new TableColumn("IBAN / Kontonummer");
		TableColumn<Dozent, String> bankTableColumn = new TableColumn("Bank");
		TableColumn<Dozent, String> bLZTableColumn = new TableColumn("BIC / BLZ");
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

	public void insertIntoLehrvergueungssaetzeTable(List<Stundenlohn> input) throws SQLException {

		TableColumn<Stundenlohn, String> vergütungTableColumn = new TableColumn("Vergütung");

		tabellenTableView.getColumns().setAll(vergütungTableColumn);

		ObservableList<Stundenlohn> stundenlohnData = FXCollections.observableArrayList();
		stundenlohnData = FXCollections.observableArrayList(input);

		tabellenTableView.setItems(stundenlohnData);

		vergütungTableColumn.setCellValueFactory(cellData -> cellData.getValue().LohnPropertyFull());
	}

	private void insertIntoVeranstaltungenTable(List<Event> input) throws SQLException {

		TableColumn<Event, String> nameTableColumn = new TableColumn("Dozent");
		TableColumn<Event, String> aktenzeichenTableColumn = new TableColumn("Aktenzeichen");
		TableColumn<Event, String> schulArt = new TableColumn("Schulungsart");
		TableColumn<Event, String> vfgTableColumn = new TableColumn("Verfügung");
		TableColumn<Event, String> vortragTableColumn = new TableColumn("Vortragsart");
		TableColumn<Event, String> datumTableColumn = new TableColumn("Datum");
		TableColumn<Event, String> euro_StdTableColumn = new TableColumn("Stundensatz");
		TableColumn<Event, String> stdZahlTableColumn = new TableColumn("Stundenzahl");
		TableColumn<Event, String> betragTableColumn = new TableColumn("Betrag");
		TableColumn<Event, String> betrag_ABCTableColumn = new TableColumn("Betrag in Worten");

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
		datumTableColumn.setCellValueFactory(cellData -> {
			try {
				return cellData.getValue().DateProperty();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		});
		euro_StdTableColumn.setCellValueFactory(cellData -> cellData.getValue().Euro_stdProperty());
		stdZahlTableColumn.setCellValueFactory(cellData -> cellData.getValue().getStundenZahlString());
		betragTableColumn.setCellValueFactory(cellData -> cellData.getValue().BetragProperty());
		betrag_ABCTableColumn.setCellValueFactory(cellData -> cellData.getValue().Betrag_ABCProperty());
	}

	public void handleSearch() throws SQLException, ParseException {

		if (!searchField.getText().equals("")) {
			switch (fokus) {
			case "Veranstaltungen":
				insertIntoVeranstaltungenTable(eventDAO.searchEvent(searchField.getText()));
				break;
			case "Dozenten":
				insertIntoDozentenTable(dozentDAO.searchDozentFullname(searchField.getText()));
				break;
			case "Lehrvergütungssätze":
				insertIntoLehrvergueungssaetzeTable(stundenlohnDAO.searchStundenlohn(searchField.getText()));
				break;
			default:
				break;
			}

			tabellenTableView.setPlaceholder(new Label("Keine Einträge gefunden"));
		} else {
			if (fokus.equals("Veranstaltungen")) {
				insertIntoVeranstaltungenTable(eventDAO.selectAllEvents());
			} else if (fokus.equals("Dozenten")) {
				insertIntoDozentenTable(dozentDAO.selectAllDozenten());
			} else if (fokus.equals("Lehrvergütungssätze")) {
				insertIntoLehrvergueungssaetzeTable(stundenlohnDAO.selectAllStundenloehne());
			}

			tabellenTableView.setPlaceholder(
					new Label("Keine Einträge vorhanden - Verwenden Sie \"Neu\", um einen neuen Eintrag hinzuzufügen"));
		}

	}
}
