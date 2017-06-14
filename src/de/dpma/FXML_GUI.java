package de.dpma;

import java.util.logging.Logger;

import de.dpma.util.AlertUtil;
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
		this.primaryStage = primaryStage;
		this.rootLayout = rootLayout;
		initRootLayout();
	}
	
	public void initRootLayout() {
		
		try {
			primaryStage = new Stage();
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
		
		handleChildren("MainPage");
		
	}
	
	// Anpassung der Größe
	private void sizeHandling(double height, double width) {
		
		primaryStage.setMinWidth(width + 60);
		primaryStage.setMinHeight(height + 120);
	}
	
	public void showDozent() {
		
		handleChildren("Dozent");
	}
	
	public void showVeranstaltung() {
		
		// Path currentRelativePath = Paths.get("");
		// String s = currentRelativePath.toAbsolutePath().toString();
		// System.out.println("Current relative path is: " + s);
		
		handleChildren("Veranstaltung");
		
	}
	
	public void showLehrverguetungssaetze() {
		
		handleChildren("Lehrverguetungssaetze");
		primaryStage.setMaxWidth(400 + 60);
		primaryStage.setMaxHeight(250 + 120);
	}
	
	private void handleChildren(String FXML_Name) {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("view/" + FXML_Name + ".fxml"));
			
			AnchorPane children;
			children = (AnchorPane) loader.load();
			sizeHandling(children.getPrefHeight(), children.getPrefWidth());
			rootLayout.setCenter(children);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
