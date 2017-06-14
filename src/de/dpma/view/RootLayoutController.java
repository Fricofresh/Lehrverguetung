package de.dpma.view;

import java.sql.SQLException;

import de.dpma.FXML_GUI;
import de.dpma.MainApp;
import de.dpma.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RootLayoutController {
	
	Stage stage;
	
	BorderPane borderPane;
	
	FXML_GUI fxml_gui;
	
	AlertUtil alert;
	
	static boolean ändern;
	
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
		
	}
	
	public void handleGUI(String check, boolean ändern) {
		
		this.ändern = ändern;
		fxml_gui = new FXML_GUI(stage, borderPane);
		switch (check) {
		case "main":
			fxml_gui.showMainPage();
			break;
		case "Veranstaltungen":
			fxml_gui.showVeranstaltung();
			break;
		case "Dozenten":
			fxml_gui.showDozent();
			break;
		case "Lehrvergütungssätze":
			fxml_gui.showLehrverguetungssaetze();
			break;
		case "":
			
			break;
		
		default:
			alert = new AlertUtil("Falscher übergabeparameter", "Bitte kontaktieren Sie den Administrator!", "WARNING");
			break;
		}
	}
}
