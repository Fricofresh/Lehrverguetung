package de.dpma.util;

import de.dpma.FXML_GUI;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

public class AlertUtil {

	FXML_GUI fxml_gui;

	Stage stage = new Stage();

	public AlertUtil(String title, String content, String type) {

		if (type.equalsIgnoreCase("warning") || type.equalsIgnoreCase("warn") || type.equalsIgnoreCase("w")) {
			Alert alert = new Alert(AlertType.WARNING);

			DialogPane dialogPane = alert.getDialogPane();

			alert.setTitle("Warnung");
			alert.setHeaderText(title);
			alert.setContentText(content);
			// alert.initOwner(stage);
			alert.showAndWait();
		} else if (type.equalsIgnoreCase("info") || type.equalsIgnoreCase("i")) {
			Alert alert = new Alert(AlertType.INFORMATION);

			DialogPane dialogPane = alert.getDialogPane();

			alert.setTitle("Information");
			alert.setHeaderText(title);
			alert.setContentText(content);
			// alert.initOwner(stage);
			alert.showAndWait();
		} else if (type.equalsIgnoreCase("error") || type.equalsIgnoreCase("err") || type.equalsIgnoreCase("e")) {
			Alert alert = new Alert(AlertType.ERROR);

			DialogPane dialogPane = alert.getDialogPane();

			alert.setTitle("Fehler");
			alert.setHeaderText(title);
			alert.setContentText(content);
			// alert.initOwner(stage);
			alert.showAndWait();
		} else if (type.equalsIgnoreCase("confirmation") || type.equalsIgnoreCase("confirm")
				|| type.equalsIgnoreCase("conf") || type.equalsIgnoreCase("c")) {
			Alert alert = new Alert(AlertType.CONFIRMATION);

			DialogPane dialogPane = alert.getDialogPane();

			alert.setTitle("Bestätigung");
			alert.setHeaderText(title);
			alert.setContentText(content);
			// alert.initOwner(stage);
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.NONE);

			DialogPane dialogPane = alert.getDialogPane();

			alert.setTitle("Meldung");
			alert.setHeaderText(title);
			alert.setContentText(content);
			// alert.initOwner(stage);
			alert.showAndWait();
		}
	}
}
