package de.dpma.view;

import java.io.File;
import java.sql.SQLException;

import de.dpma.FXML_GUI;
import de.dpma.MainApp;
import de.dpma.util.AlertUtil;
import de.dpma.util.GenerateExcelData;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This is the controller for the root layout. <br>
 * The controller starts the methods from {@link FXML_GUI}.
 * 
 * @author Kenneth Böhmer
 *
 */
public class RootLayoutController {
	
	Stage stage;
	
	BorderPane borderPane;
	
	FXML_GUI fxml_gui;
	
	AlertUtil alert;
	
	@FXML
	public MenuBar menuMenuBar = new MenuBar();
	
	@FXML
	public MenuItem gesammtListeMenuItem = new MenuItem();
	
	@FXML
	public MenuItem einstellungenMenuItem = new MenuItem();
	
	/**
	 * Sets the accelerator to CTRL + S for export the hole event list
	 * 
	 * @author Kenneth Böhmer
	 */
	@FXML
	public void initialize() {
		
		gesammtListeMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
	}
	
	/**
	 * Closes the database connection and the program.
	 * 
	 * @author Kenneth Böhmer
	 */
	@FXML
	private void handleExit() {
		
		try {
			MainApp.dbcon.closeConnection();
			System.exit(0);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Start the methods from {@link #fxml_gui FXML_GUI} to open the GUI.
	 * 
	 * @param check
	 *            to difference witch GUI should be used.
	 */
	public void handleGUI(String check) {
		
		// Führt den Konstruktor aus um das RootLayout zu initialisieren. Dabei
		// wird eine leere Stage und BorderPane übergeben um
		// NullPointerExeptions zu vermeiden.
		fxml_gui = new FXML_GUI(stage, borderPane);
		// TODO Rechtschreibüberprüfung
		// Switch case für Differensierung der Oberflächen, die gestarted werden
		// müssen
		switch (check) {
		
		case "configIni":
			fxml_gui.showConfigIni(null);
			break;
		default:
			alert = new AlertUtil("Falscher übergabeparameter", "Bitte kontaktieren Sie den Administrator!", "WARNING");
			break;
		}
	}
	
	/**
	 * Start the methods from {@link #fxml_gui FXML_GUI} to open the GUI.<br>
	 * 
	 * @param check
	 *            to difference witch GUI should be used.
	 * @param tabelle
	 *            is the data set from one of the tree database tables.
	 */
	public void handleGUI(String check, Object tabelle) {
		
		// Führt den Konstruktor aus um das RootLayout zu initialisieren. Dabei
		// wird eine leere Stage und BorderPane übergeben um
		// NullPointerExeptions zu vermeiden.
		fxml_gui = new FXML_GUI(stage, borderPane);
		// TODO Rechtschreibüberprüfung
		// Switch case für Differensierung der Oberflächen, die gestarted werden
		// müssen
		switch (check) {
		case "main":
			fxml_gui.showMainPage();
			break;
		case "Veranstaltungen":
			fxml_gui.showVeranstaltung(tabelle);
			
			break;
		case "Dozenten":
			fxml_gui.showDozent(tabelle);
			break;
		case "createDoc":
			fxml_gui.showConfigIni(tabelle);
			break;
		case "Lehrvergütungssätze":
			fxml_gui.showLehrverguetungssaetze(tabelle);
			break;
		
		default:
			alert = new AlertUtil("Falscher übergabeparameter", "Bitte kontaktieren Sie den Administrator!", "WARNING");
			break;
		}
	}
	
	/**
	 * Export the hole event list in a excel file
	 * 
	 * @author Kenneth Böhmer
	 */
	@FXML
	public void handleExport() {
		
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Speichern");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel Datei (*.xlsx)", "*.xlsx");
		chooser.getExtensionFilters().add(extFilter);
		
		String directory = chooser.getExtensionFilters().toString();
		
		File file = chooser.showSaveDialog(FXML_GUI.primaryStage[MainApp.counter].getScene().getWindow());
		
		if (file != null) {
			GenerateExcelData ged = new GenerateExcelData(file);
			alert = new AlertUtil("Gesamtliste exportiert",
					"Die Gesamtliste wurde erfolgreich im Ordner " + file + " abgelegt.", "INFO");
		}
	}
	
	/**
	 * Opens the GUI to change the configuration of the worker
	 * 
	 * @author Kenneth Böhmer
	 */
	@FXML
	public void handleSettings() {
		
		handleGUI("configIni");
		FXML_GUI.primaryStage[MainApp.counter].setTitle("Einstellungen");
		
	}
}
