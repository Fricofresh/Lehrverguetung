package de.dpma;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import de.dpma.dao.DatabaseConnection;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

	public static DatabaseConnection dbcon = null;

	// Logger log = Logger.getLogger(MainApp.class.getName());
	//
	// BorderPane borderPane;
	//
	// @Override
	// public void start(Stage primaryStage) {
	//
	// try {
	// FXML_GUI fxml_gui = new FXML_GUI();
	// fxml_gui.primaryStage = primaryStage;
	// fxml_gui.initRootLayout();
	// fxml_gui.showLogin();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	public static void main(String[] args) throws FileNotFoundException, IOException {

		try {
			dbcon = new DatabaseConnection();
			launch(args);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				dbcon.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/*
		 * System.out.println(new File(".").getAbsoluteFile()); WriteDocx docx =
		 * new WriteDocx();
		 */
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}
}
