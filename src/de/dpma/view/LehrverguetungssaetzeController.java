package de.dpma.view;

import java.sql.SQLException;

import de.dpma.FXML_GUI;
import de.dpma.dao.StundenlohnDAO;
import de.dpma.model.Stundenlohn;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LehrverguetungssaetzeController {

	@FXML
	TextField verguetungsSatzTextField = new TextField();
	StundenlohnDAO stundenlohnDAO;

	Stundenlohn stundenlohn;

	public void initialize() {

		stundenlohn = new Stundenlohn();
		try {
			MainPageController.stundenlohnDAO
					.selectStundenlohn(MainPageController.stundenlohnDAO.selectAllStundenloehne().size());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void handleSubmit() throws SQLException {

		// TODO insert oder update Befehl
		double sdtLohn = Double.valueOf(verguetungsSatzTextField.getText());
		stundenlohn.setLohn(sdtLohn);
		if (stundenlohn.getId() == 0) {
			MainPageController.stundenlohnDAO.insertStundenlohn(this.stundenlohn);
		} else {
			MainPageController.stundenlohnDAO.updateStundenlohn(this.stundenlohn);
		}

		FXML_GUI.primaryStage.close();

	}

	public void handleCancel() {

		FXML_GUI.primaryStage.close();
	}

	public void handleNew(Stundenlohn stundenlohn) {

		this.stundenlohn = stundenlohn;

		String stdLohn = String.valueOf(stundenlohn.getLohn());
		verguetungsSatzTextField.setText(stdLohn);
	}
}
