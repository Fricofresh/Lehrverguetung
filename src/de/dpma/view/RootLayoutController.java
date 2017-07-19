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
 * @author Kenneth B�hmer
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
	 * @author Kenneth B�hmer
	 */
	@FXML
	public void initialize() {
		
		gesammtListeMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
	}
	
	/**
	 * Closes the database connection and the program.
	 * 
	 * @author Kenneth B�hmer
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
		
		// F�hrt den Konstruktor aus um das RootLayout zu initialisieren. Dabei
		// wird eine leere Stage und BorderPane �bergeben um
		// NullPointerExeptions zu vermeiden.
		fxml_gui = new FXML_GUI(stage, borderPane);
		// TODO Rechtschreib�berpr�fung
		// Switch case f�r Differensierung der Oberfl�chen, die gestarted werden
		// m�ssen
		switch (check) {
		
		case "configIni":
			fxml_gui.showConfigIni(null);
			break;
		default:
			alert = new AlertUtil("Falscher �bergabeparameter", "Bitte kontaktieren Sie den Administrator!", "WARNING");
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
		
		// F�hrt den Konstruktor aus um das RootLayout zu initialisieren. Dabei
		// wird eine leere Stage und BorderPane �bergeben um
		// NullPointerExeptions zu vermeiden.
		fxml_gui = new FXML_GUI(stage, borderPane);
		// TODO Rechtschreib�berpr�fung
		// Switch case f�r Differensierung der Oberfl�chen, die gestarted werden
		// m�ssen
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
		case "Lehrverg�tungss�tze":
			fxml_gui.showLehrverguetungssaetze(tabelle);
			break;
		
		default:
			alert = new AlertUtil("Falscher �bergabeparameter", "Bitte kontaktieren Sie den Administrator!", "WARNING");
			break;
		}
	}
	
	/**
	 * Export the hole event list in a excel file
	 * 
	 * @author Kenneth B�hmer
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
	 * @author Kenneth B�hmer
	 */
	@FXML
	public void handleSettings() {
		
		handleGUI("configIni");
		FXML_GUI.primaryStage[MainApp.counter].setTitle("Einstellungen");
		
	}
}
