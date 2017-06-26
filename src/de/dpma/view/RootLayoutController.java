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
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RootLayoutController {
	
	Stage stage;
	
	BorderPane borderPane;
	
	FXML_GUI fxml_gui;
	
	AlertUtil alert;
	
	@FXML
	MenuBar menuMenuBar = new MenuBar();
	
	@FXML
	MenuItem gesammtListeMenuItem = new MenuItem();
	
	@FXML
	MenuItem einstellungenMenuItem = new MenuItem();
	
	@FXML
	public void initialize() {
		
	}
	
	@FXML
	public void handleExit() {
		
		try {
			MainApp.dbcon.closeConnection();
			System.exit(0);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void handleGUI(String check) {
		
		fxml_gui = new FXML_GUI(stage, borderPane);
		switch (check) {
		
		case "configIni":
			fxml_gui.showConfigIni(null);
			break;
		default:
			alert = new AlertUtil("Falscher übergabeparameter", "Bitte kontaktieren Sie den Administrator!", "WARNING");
			break;
		}
	}
	
	public void handleGUI(String check, Object tabelle) {
		
		fxml_gui = new FXML_GUI(stage, borderPane);
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
			menuMenuBar.setDisable(false);
			gesammtListeMenuItem.setVisible(false);
			einstellungenMenuItem.setVisible(true);
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
	
	@FXML
	private void handleExport() {
		
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
	
	@FXML
	private void handleSettings() {
		
		// TODO Konfigurationsdatei
		
		handleGUI("configIni");
		FXML_GUI.primaryStage[MainApp.counter].setTitle("Einstellungen");
		
	}
}
