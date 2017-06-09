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
	
	public void handleGUI(String check, boolean �ndern) {
		
		fxml_gui = new FXML_GUI(stage, borderPane);
		switch (check) {
		case "main":
			fxml_gui.showMainPage();
			break;
		case "Veranstaltungen":
			fxml_gui.showVeranstaltug(�ndern);
			break;
		case "Dozenten":
			fxml_gui.showDozent();
			break;
		case "Lehrverg�tungss�tze":
			fxml_gui.showLehrverguetung();
			break;
		case "":
			
			break;
		
		default:
			alert = new AlertUtil("Falscher �bergabeparameter", "Bitte kontaktieren Sie den Administrator!", "WARNING");
			break;
		}
	}
}