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
		
		initRootLayout();
	}
	
	public void initRootLayout() {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FXML_GUI.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
	
	public void showLogin() {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FXML_GUI.class.getResource("view/Login.fxml"));
			
			AnchorPane login;
			login = (AnchorPane) loader.load();
			sizeHandling(login.getPrefHeight(), login.getPrefWidth());
			rootLayout.setCenter(login);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showPasswordChange() {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FXML_GUI.class.getResource("view/PasswordChange.fxml"));
			
			AnchorPane passwordChange;
			passwordChange = (AnchorPane) loader.load();
			sizeHandling(passwordChange.getPrefHeight(), passwordChange.getPrefWidth());
			rootLayout.setCenter(passwordChange);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showInitalPasswordChange() {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FXML_GUI.class.getResource("view/PasswordChange.fxml"));
			
			AnchorPane passwordChange;
			passwordChange = (AnchorPane) loader.load();
			sizeHandling(passwordChange.getPrefHeight(), passwordChange.getPrefWidth());
			rootLayout.setCenter(passwordChange);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void showIndex() {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FXML_GUI.class.getResource("view/Index.fxml"));
			
			AnchorPane index;
			index = (AnchorPane) loader.load();
			sizeHandling(index.getPrefHeight(), index.getPrefWidth());
			rootLayout.setCenter(index);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Anpassung der Größe
	private void sizeHandling(double height, double width) {
		
		primaryStage.setWidth(width + 32);
		primaryStage.setHeight(height + 75);
	}
	
}
