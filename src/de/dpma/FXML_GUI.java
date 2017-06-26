package de.dpma;

import java.util.logging.Logger;

import de.dpma.model.Dozent;
import de.dpma.model.Event;
import de.dpma.model.Stundenlohn;
import de.dpma.util.AlertUtil;
import de.dpma.view.DozentController;
import de.dpma.view.InsertPersonalDataController;
import de.dpma.view.LehrverguetungssaetzeController;
import de.dpma.view.VeranstaltungController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FXML_GUI {
	
	Logger log = Logger.getLogger(FXML_GUI.class.getName());
	
	// TODO Array erstellen gegen Bugs
	// static integer auf MainApp als counter um die insgesammte anzahl
	// herauszufinden und das es möglich ist die neuste Stage zu zuweisen
	// bei den einzelnen Controller wird die ID der oberfläche als privater int
	// gespeichert, der int bekommt den Wert des MainApp counter. Die Stages
	// wird von der MainApp genommen
	public static Stage[] primaryStage = new Stage[100];
	
	public BorderPane rootLayout;
	
	Scene scene;
	
	AlertUtil alert;
	
	Object tabelle;
	
	public FXML_GUI() {
		
	}
	
	public FXML_GUI(Stage primaryStage, BorderPane rootLayout) {
//		this.primaryStage[MainApp.counter] = primaryStage;
		
		MainApp.counter++;
		this.rootLayout = rootLayout;
		initRootLayout(false);
	}
	
	// public FXML_GUI(Stage primaryStage, BorderPane rootLayout, String dump) {
	// this.primaryStage[MainApp.counter] = primaryStage;
	// this.rootLayout = rootLayout;
	// initRootLayout(true);
	// }
	
	public void initRootLayout(boolean b) {
		
		try {
			primaryStage[MainApp.counter] = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FXML_GUI.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			
			primaryStage[MainApp.counter].setScene(scene);
			if (!b) {
				primaryStage[MainApp.counter].show();
			}
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
		
		primaryStage[MainApp.counter].setMinWidth(width + 60);
		primaryStage[MainApp.counter].setMinHeight(height + 120);
	}
	
	public void showDozent(Object tabelle) {
		
		if (tabelle != null) {
			handleChildren("Dozent", tabelle, "dc");
		}
		else {
			handleChildren("Dozent");
		}
	}
	
	public void showVeranstaltung(Object tabelle) {
		
		// Path currentRelativePath = Paths.get("");
		// String s = currentRelativePath.toAbsolutePath().toString();
		// System.out.println("Current relative path is: " + s);
		if (tabelle != null) {
			this.tabelle = tabelle;
			handleChildren("Veranstaltung", tabelle, "vc");
		}
		else {
			
			handleChildren("Veranstaltung");
		}
		
	}
	
	public void showLehrverguetungssaetze(Object tabelle) {
		
		if (tabelle != null) {
			handleChildren("Lehrverguetungssaetze", tabelle, "lc");
		}
		else {
			handleChildren("Lehrverguetungssaetze");
		}
		primaryStage[MainApp.counter].setMaxWidth(400 + 60);
		primaryStage[MainApp.counter].setMaxHeight(250 + 120);
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
	
	private void handleChildren(String FXML_Name, Object tabelle, String check) {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("view/" + FXML_Name + ".fxml"));
			
			AnchorPane children;
			children = (AnchorPane) loader.load();
			if (check.equals("vc")) {
				Event event = (Event) tabelle;
				VeranstaltungController vc = loader.getController();
				vc.handleNew(event);
			}
			else if (check.equals("dc")) {
				Dozent dozent = (Dozent) tabelle;
				DozentController dc = loader.getController();
				dc.handleNew(dozent);
			}
			else if (check.equals("lc")) {
				Stundenlohn stundenlohn = (Stundenlohn) tabelle;
				LehrverguetungssaetzeController lc = loader.getController();
				lc.handleNew(stundenlohn);
			}
			else if (check.equals("confIni")) {
				Event event = (Event) tabelle;
				InsertPersonalDataController sipd = loader.getController();
				sipd.setEvent(event);
			}
			sizeHandling(children.getPrefHeight(), children.getPrefWidth());
			rootLayout.setCenter(children);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void showConfigIni(Object tabelle) {
		
		handleChildren("InsertPersonalData", tabelle, "confIni");
		
	}
	
}
