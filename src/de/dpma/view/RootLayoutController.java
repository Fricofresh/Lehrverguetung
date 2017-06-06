package de.dpma.view;

import java.sql.SQLException;

import de.dpma.FXML_GUI;
import de.dpma.MainApp;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RootLayoutController {
	
	Stage stage;
	
	BorderPane borderPane;
	
	FXML_GUI fxml_gui;
	
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
	
	// TODO Logout
	@FXML
	public void handleLogout() {
		
	}
	
}
