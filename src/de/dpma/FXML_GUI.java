package de.dpma;

import java.util.logging.Logger;

import de.dpma.model.Dozent;
import de.dpma.model.Event;
import de.dpma.model.Stundenlohn;
import de.dpma.util.AlertUtil;
import de.dpma.view.DozentController;
import de.dpma.view.InsertPersonalDataController;
import de.dpma.view.LehrverguetungssaetzeController;
import de.dpma.view.RootLayoutController;
import de.dpma.view.VeranstaltungController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FXML_GUI {
	
	Logger log = Logger.getLogger(FXML_GUI.class.getName());
	
	// Stage als einen Array um die Unabhängigkeit der einzelnen Oberflächen zu
	// gewährleisten.
	public static Stage[] primaryStage = new Stage[300];
	
	// Für die Einheitlichkeit der Oberflächen werden die einzelnen Fenster in
	// den Center hinzufügt.
	public BorderPane rootLayout;
	
	Scene scene;
	
	AlertUtil alert;
	
	Object tabelle;
	
	public FXML_GUI() {
		
	}
	
	/**
	 * This constructor initialize the root layout and increment the counter by
	 * 1.
	 * 
	 * @author Kenneth Böhmer
	 * @param primaryStage
	 * @param rootLayout
	 */
	public FXML_GUI(Stage primaryStage, BorderPane rootLayout) {
		
		MainApp.counter++;
		this.rootLayout = rootLayout;
		
		initRootLayout(true);
	}
	
	// public FXML_GUI(Stage primaryStage, BorderPane rootLayout, String dump) {
	// this.primaryStage[MainApp.counter] = primaryStage;
	// this.rootLayout = rootLayout;
	// initRootLayout(true);
	// }
	/**
	 * Initialize the root layout.
	 * 
	 * @author Kenneth Böhmer
	 * @param b
	 *            This boolean is needed to know if the menu bar is visible with
	 *            the right parameters
	 */
	public void initRootLayout(boolean b) {
		
		try {
			primaryStage[MainApp.counter] = new Stage();
			FXMLLoader loader = new FXMLLoader();
			primaryStage[MainApp.counter].getIcons().add(new Image("file:ressources/euro.png"));
			loader.setLocation(FXML_GUI.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			if (b) {
				RootLayoutController root = loader.getController();
				root.menuMenuBar.setDisable(false);
				root.gesammtListeMenuItem.setVisible(false);
				root.einstellungenMenuItem.setVisible(false);
			}
			
			primaryStage[MainApp.counter].setScene(scene);
			
			primaryStage[MainApp.counter].show();
			
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
	
	/**
	 * @see #handleChildren(String) handleChildren
	 */
	public void showMainPage() {
		
		handleChildren("MainPage");
		
	}
	
	/**
	 * Handle the size, so it can not be too small.
	 * 
	 * @author Kenneth Böhmer
	 * @param height
	 * @param width
	 */
	private void sizeHandling(double height, double width) {
		
		primaryStage[MainApp.counter].setMinWidth(width + 60);
		primaryStage[MainApp.counter].setMinHeight(height + 120);
	}
	
	/**
	 * @see #handleChildren(String, Object, String) handleChildren to edit
	 * @see #handleChildren(String) handleChildren to add a new one
	 * @author Kenneth Böhmer
	 * @param tabelle
	 *            The data set for editing
	 */
	public void showDozent(Object tabelle) {
		
		if (tabelle != null) {
			handleChildren("Dozent", tabelle, "dc");
		}
		else {
			handleChildren("Dozent");
		}
	}
	
	/**
	 * @see #handleChildren(String, Object, String) handleChildren to edit
	 * @see #handleChildren(String) handleChildren to add a new one
	 * @author Kenneth Böhmer
	 * @param tabelle
	 *            The data set for editing
	 */
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
	
	/**
	 * @see #handleChildren(String, Object, String) handleChildren to edit
	 * @see #handleChildren(String) handleChildren to add a new one
	 * @author Kenneth Böhmer
	 * @param tabelle
	 *            The data set for editing
	 */
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
	
	/**
	 * Puts the FXML GUI to the center of the {@link #rootLayout rootLayout}.
	 * 
	 * @param FXML_Name
	 *            To difference which GUI have to be loaded.
	 */
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
	
	// TODO Spell check/Überprüfen ob es überhaupt einen Sinn ergibt
	/**
	 * Puts the FXML GUI to the center of the {@link #rootLayout rootLayout}.
	 * 
	 * @param FXML_Name
	 *            To difference which GUI have to be loaded.
	 * @param tabelle
	 *            The data set for editing.
	 * @param check
	 *            To decide where the object has to go.
	 */
	private void handleChildren(String FXML_Name, Object tabelle, String check) {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			// Die FXML Datei wird geladen
			loader.setLocation(this.getClass().getResource("view/" + FXML_Name + ".fxml"));
			
			AnchorPane children;
			// Die FXML Datei wird in einen AnchorPane geladen/casted.
			children = (AnchorPane) loader.load();
			// Unterscheidung zu welchen Controller der Datensatz übergeben
			// wird.
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
	
	/**
	 * @see #handleChildren(String, Object, String) handleChildren
	 * @param tabelle
	 *            For the document export
	 */
	public void showConfigIni(Object tabelle) {
		
		handleChildren("InsertPersonalData", tabelle, "confIni");
		
	}
}
