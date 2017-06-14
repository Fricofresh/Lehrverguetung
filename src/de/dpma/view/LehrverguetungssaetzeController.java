package de.dpma.view;

import de.dpma.FXML_GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LehrverguetungssaetzeController {
	
	@FXML
	TextField vergütungsSatzTextField;
	
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
		
		boolean ändern = RootLayoutController.ändern;
		
		if (ändern) {
			headerLabel.setText("Lehrvergütung ändern");
			// vergütungsSatzTextField.setText(""); // TODO davor Selected item
			// übernehmen
		}
	}
}
