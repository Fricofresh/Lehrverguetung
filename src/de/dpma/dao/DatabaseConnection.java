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
		String connectionURL = "jdbc:derby:C:/Users/KeBöhmer/Documents/Arbeit;create=false;user=;password=";
		// String connectionURL =
		// "jdbc:derby:localhost:1527/C:/Users/KeBöhmer/Desktop/WebMonitorLogin/Datenbank/DB_WEB;create=false;user=ref233;password=123456";
		
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
