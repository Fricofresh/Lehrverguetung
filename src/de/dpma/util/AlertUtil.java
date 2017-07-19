package de.dpma.util;

import de.dpma.FXML_GUI;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AlertUtil {

	FXML_GUI fxml_gui;

	Stage stage = new Stage();

	private Alert alert;

	/**
	 * ÷ffnet ein Alertfenster.
	 * 
	 * @author Flo
	 * @param title
	 * @param content
	 * @param type
	 */
	public AlertUtil(String title, String content, String type) {

		if (type.equalsIgnoreCase("warning") || type.equalsIgnoreCase("warn") || type.equalsIgnoreCase("w")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warnung");
			alert.setHeaderText(title);
			alert.setContentText(content);
			alert.showAndWait();
		} else if (type.equalsIgnoreCase("info") || type.equalsIgnoreCase("i")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText(title);
			alert.setContentText(content);
			alert.showAndWait();
		} else if (type.equalsIgnoreCase("error") || type.equalsIgnoreCase("err") || type.equalsIgnoreCase("e")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setHeaderText(title);
			alert.setContentText(content);
			alert.showAndWait();
		} else if (type.equalsIgnoreCase("confirmation") || type.equalsIgnoreCase("confirm")
				|| type.equalsIgnoreCase("conf") || type.equalsIgnoreCase("c")) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Best‰tigung");
			alert.setHeaderText(title);
			alert.setContentText(content);
			alert.showAndWait();
		} else if (type.equalsIgnoreCase("loading") || type.equalsIgnoreCase("load") || type.equalsIgnoreCase("l")) {
			alert = new Alert(AlertType.NONE);
			alert.setTitle("L‰dt");
			alert.setHeaderText(title);
			alert.setContentText(content);
			alert.show();
		} else {
			Alert alert = new Alert(AlertType.NONE);
			alert.setTitle("Meldung");
			alert.setHeaderText(title);
			alert.setContentText(content);
			alert.showAndWait();
		}
	}

	/**
	 * Schlieﬂt ein Alertfenster ohne Buttons (Typ "loading").
	 * 
	 * @author Flo
	 */
	public void closeAlert() {
		alert.getButtonTypes().add(ButtonType.CANCEL);
		alert.hide();
		alert.getButtonTypes().remove(ButtonType.CANCEL);
	}
}
