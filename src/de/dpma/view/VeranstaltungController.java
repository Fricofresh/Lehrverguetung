package de.dpma.view;

import de.dpma.FXML_GUI;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class VeranstaltungController {
	
	@FXML
	TextField dozentTextField;
	
	@FXML
	TextField aktenzeichenTextField;
	
	@FXML
	TextField schulArtTextField;
	
	@FXML
	TextField vfgTextField;
	
	@FXML
	TextField vortragTextField;
	
	@FXML
	DatePicker datumVon;
	
	@FXML
	DatePicker datumBis;
	
	@FXML
	TextField euro_StdTextField;
	
	@FXML
	TextField stdZahlTextField;
	
	@FXML
	TextField betragTextField;
	
	@FXML
	TextField betrag_ABCTextField;
	
	public void handleNew(boolean ändern) {
		
		if (ändern) {
			dozentTextField.setText(""); // TODO SQL Select Befehl | get
											// Selected item
			aktenzeichenTextField.setText("");
			schulArtTextField.setText("");
			vfgTextField.setText("");
			vortragTextField.setText("");
			euro_StdTextField.setText("");
			stdZahlTextField.setText("");
			betragTextField.setText("");
			betrag_ABCTextField.setText("");
		}
	}
	
	@FXML
	private void handleSubmit() {
		
		// TODO insert befehl
		
		System.out.println(datumVon.getValue());
		System.out.println(datumBis.getValue());
		
	}
	
	@FXML
	private void handleCancel() {
		
		FXML_GUI.primaryStage.close();
	}
}
