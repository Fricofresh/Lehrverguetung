package de.dpma.view;

import de.dpma.FXML_GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LehrverguetungssaetzeController {
	
	@FXML
	TextField verg�tungsSatzTextField;
	
	@FXML
	Label headerLabel;
	
	public void initialize() {
		
		handleNew();
	}
	
	public void handleSubmit() {
		// TODO insert oder update Befehl
		
	}
	
	public void handleCancel() {
		
		FXML_GUI.primaryStage.close();
	}
	
	private void handleNew() {
		
		boolean �ndern = RootLayoutController.�ndern;
		
		if (�ndern) {
			headerLabel.setText("Lehrverg�tung �ndern");
			// verg�tungsSatzTextField.setText(""); // TODO davor Selected item
			// �bernehmen
		}
	}
}
