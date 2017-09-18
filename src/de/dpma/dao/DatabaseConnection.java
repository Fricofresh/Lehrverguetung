package de.dpma.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import de.dpma.util.UsefulFuncs;

public class DatabaseConnection {
	
	public Connection con = null;
	
	Logger log = Logger.getLogger(DatabaseConnection.class.getName());
	
	/**
	 * Baut eine Datenbankverbindung auf.
	 * 
	 * @author Flo
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public DatabaseConnection() throws SQLException, ClassNotFoundException {
		log.info("Die Verbindung zur Datenbank wird gestartet");
		
		System.setProperty("derby.system.home", new File("/Datenbank").getAbsolutePath());
		
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		
		// Datenbankverbindung
		String connectionURL;
		if (UsefulFuncs.isDevelopment()) {
			connectionURL = "jdbc:derby:" + UsefulFuncs.getCurrentDirectory()
					+ "/Datenbank;create=false;user=LEHRVERGUETUNG;password=AR49MdPWBWBXHgSJy2ANxmQF";
		}
		else {
			connectionURL = "jdbc:derby:" + UsefulFuncs.getCurrentDirectory()
					+ "/Datenbank;create=false;user=LEHRVERGUETUNG;password=AR49MdPWBWBXHgSJy2ANxmQF";
		}
		con = DriverManager.getConnection(connectionURL);
		
		Statement statement = con.createStatement();
		
		log.info("Verbindung erfolgreich");
	}
	
	/**
	 * Holt sich die Datenbankverbindung.
	 * 
	 * @return con (Connection)
	 */
	public Connection getConnection() {
		
		log.info("Die Datenbankverbindung wird hergestellt");
		return con;
	}
	
	/**
	 * Schlieﬂt die Datenbankverbindung, falls sie existiert.
	 */
	public void closeConnection() throws SQLException {
		
		if (con != null) {
			try {
				con.close();
			}
			catch (SQLException e) {
				// Ignorieren
			}
		}
	}
}
