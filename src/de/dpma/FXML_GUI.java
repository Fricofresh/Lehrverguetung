package de.dpma;

import java.util.logging.Logger;

import de.dpma.util.AlertUtil;
import de.dpma.view.VeranstaltungController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FXML_GUI {
	
	Logger log = Logger.getLogger(FXML_GUI.class.getName());
	
	public static Stage primaryStage;
	
	public BorderPane rootLayout;
	
	Scene scene;
	
	AlertUtil alert;
	
	public FXML_GUI() {
		
	}
	
	public FXML_GUI(Stage primaryStage, BorderPane rootLayout) {
		
		initRootLayout();
	}
	
	public void initRootLayout() {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FXML_GUI.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (NullPointerException n) {
			String nu = n.toString();
			alert = new AlertUtil("NullPointerException", nu, "WARNING");
			n.printStackTrace();
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void showMainPage() {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FXML_GUI.class.getResource("view/MainPage.fxml"));
			
			AnchorPane mainPage;
			mainPage = (AnchorPane) loader.load();
			sizeHandling(mainPage.getPrefHeight(), mainPage.getPrefWidth());
			rootLayout.setCenter(mainPage);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Anpassung der Größe
	private void sizeHandling(double height, double width) {
		
		primaryStage.setMinWidth(width + 60);
		primaryStage.setMinHeight(height + 120);
	}
	
	public void showDozent() {
		
	}
	
	public void showLehrverguetung() {
		
	}
	
	public void showVeranstaltug(boolean ändern) {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FXML_GUI.class.getResource("view/Veranstaltung.fxml"));
			
			AnchorPane Veranstaltung;
			Veranstaltung = (AnchorPane) loader.load();
			sizeHandling(Veranstaltung.getPrefHeight(), Veranstaltung.getPrefWidth());
			VeranstaltungController vc = new VeranstaltungController(ändern);
			rootLayout.setCenter(Veranstaltung);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
