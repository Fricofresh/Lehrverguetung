package de.dpma.view;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import de.dpma.FXML_GUI;
import de.dpma.MainApp;
import de.dpma.dao.StundenlohnDAO;
import de.dpma.model.Stundenlohn;
import de.dpma.util.AlertUtil;
import de.dpma.util.DataChecker;
import de.dpma.util.FormatCurrrency;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * The controller for the salary. <br>
 * The controller checks if a data set has to be added or edited
 * 
 * @author Kenneth Böhmer
 */
public class LehrverguetungssaetzeController {
	
	@FXML
	TextField verguetungsSatzTextField = new TextField();
	
	StundenlohnDAO stundenlohnDAO;
	
	Stundenlohn stundenlohn;
	
	KeyEvent keyEvent;
	
	AlertUtil alert;
	
	private int getStageID;
	
	@FXML
	private void initialize() {
		
		getStageID = MainApp.counter;
		stundenlohn = new Stundenlohn();
		try {
			MainPageController.stundenlohnDAO
					.selectStundenlohn(MainPageController.stundenlohnDAO.selectAllStundenloehne().size());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void handleSubmit() throws SQLException, ParseException {
		
		// Überprüft ob die Eingabe gültig ist.
		if (!DataChecker.isNumeric(verguetungsSatzTextField.getText())) {
			alert = new AlertUtil("Invalider Vergütungssatz",
					"Der Vergüngssatz ist entweder nicht angegeben oder enthält Zeichen, welche nicht numerisch sind.",
					"WARN");
		}
		else if (!DataChecker.hasTwoOrNoDecimals(verguetungsSatzTextField.getText())) {
			alert = new AlertUtil("Invalider Vergütungssatz",
					"Der Vergüngssatz enthält ungültige Nachkommastellen. Erlaubt sind keine oder zwei Nachkommastellen.",
					"WARN");
		}
		else {
			double sdtLohn = Double.valueOf(verguetungsSatzTextField.getText().replace(",", ".").trim());
			stundenlohn.setLohn(sdtLohn);
			
			if (stundenlohn.getId() == 0) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTimestamp = sdf.format(System.currentTimeMillis());
				stundenlohn.setCreationTime(currentTimestamp);
				
				MainPageController.stundenlohnDAO.insertStundenlohn(this.stundenlohn);
				
			}
			else {
				MainPageController.stundenlohnDAO.updateStundenlohn(this.stundenlohn);
			}
			
			FXML_GUI.primaryStage[this.getStageID].close();
		}
	}
	
	/**
	 * Closes the stage
	 * 
	 * @author Kenneth Böhmer
	 */
	@FXML
	private void handleCancel() {
		
		FXML_GUI.primaryStage[this.getStageID].close();
	}
	
	/**
	 * Sets the data set.
	 * 
	 * @author Kenneth Böhmer
	 * @param stundenlohn
	 *            is the data set
	 */
	public void handleNew(Stundenlohn stundenlohn) {
		
		this.stundenlohn = stundenlohn;
		
		String stdLohn = String.valueOf(stundenlohn.getLohn());
		verguetungsSatzTextField.setText(FormatCurrrency.format(stdLohn, false).replace(".", ""));
	}
	
	/**
	 * Checks if Enter or Escape is pressed to submit or cancel.
	 * 
	 * @author Kenneth Böhmer
	 * @param keyEvent
	 */
	@FXML
	private void handleKeyPressed(KeyEvent keyEvent) throws ParseException {
		
		this.keyEvent = keyEvent;
		switch (keyEvent.getCode()) {
		case ENTER:
			try {
				handleSubmit();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
		case ESCAPE:
			handleCancel();
			break;
		default:
			break;
		}
	}
}
