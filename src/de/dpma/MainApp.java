package de.dpma;

import java.sql.SQLException;
import java.util.logging.Logger;

import de.dpma.dao.DatabaseConnection;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	public static DatabaseConnection dbcon = null;
	
	Logger log = Logger.getLogger(MainApp.class.getName());
	
	BorderPane borderPane;
	
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
			launch(args);
			
		}
		catch (ClassNotFoundException | SQLException e) {
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
