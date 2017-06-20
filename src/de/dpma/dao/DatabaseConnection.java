package de.dpma.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class DatabaseConnection {

	public Connection con = null;

	Logger log = Logger.getLogger(DatabaseConnection.class.getName());

	public DatabaseConnection() throws SQLException, ClassNotFoundException {
		log.info("Die Verbindung zur Datenbank wird gestartet");

		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

		// Datenbankverbindung
		String connectionURL = "jdbc:derby:C:/Users/FlSeidl"
				+ "/Desktop/Lehrverguetung_DB;create=false;user=LEHRVERGUETUNG;password=AR49MdPWBWBXHgSJy2ANxmQF";
		con = DriverManager.getConnection(connectionURL);
		Statement statement = con.createStatement();

		log.info("Verbindung erfolgreich");
	}

	public Connection getConnection() {

		log.info("Die Datenbankverbindung wird hergestellt");
		return con;
	}

	public void closeConnection() throws SQLException {

		log.info("Die Datenbankverbindung wird geschlossen");
		con.close();
	}
}
