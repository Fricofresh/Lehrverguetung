package de.dpma;

import java.sql.SQLException;
import java.util.logging.Logger;

import de.dpma.dao.DatabaseConnection;
import de.dpma.util.AlertUtil;
import de.dpma.dao.EventDAO;
import de.dpma.model.Event;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	public static DatabaseConnection dbcon;
	
	Logger log = Logger.getLogger(MainApp.class.getName());
	
	static AlertUtil alert;
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			FXML_GUI fxml_gui = new FXML_GUI();
			fxml_gui.primaryStage = primaryStage;
			fxml_gui.initRootLayout();
			fxml_gui.showMainPage();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		try {
			dbcon = new DatabaseConnection();

			EventDAO dao = new EventDAO(dbcon.getConnection());
			Event evt = dao.selectEvent(11);
			System.out.println(evt.getVfg());

			launch(args);
			
		}
		catch (ClassNotFoundException | SQLException e) {
			alert = new AlertUtil("Datenbankverbindung konnte nicht hergestellt werden.",
					"Bitte �berpr�fen Sie ihre Internetverbindung.", "WARNING");
			e.printStackTrace();
		}
		finally {
			try {
				dbcon.closeConnection();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// System.out.println(new File(".").getAbsoluteFile());
		// WriteDocx docx = new WriteDocx();
		
	}
}