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
		}
	}
}
