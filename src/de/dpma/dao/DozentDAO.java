package de.dpma.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.dpma.model.Dozent;

public class DozentDAO {
	// SQL Statements
	final String INSERT_DOZENT = "INSERT INTO \"LEHRVERGUETUNG\".\"DOZENTEN\" (\"ANREDE\", \"TITEL\", \"VORNAME\", \"NAME\", \"STRASSE\", \"PLZ\", \"IBAN\", \"BANK\", \"BLZ\", \"ORT\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	final String DELETE_DOZENT = "DELETE FROM \"LEHRVERGUETUNG\".\"DOZENTEN\" WHERE \"ID\" = ?";

	final String UPDATE_DOZENT = "UPDATE \"LEHRVERGUETUNG\".\"DOZENTEN\" SET \"ANREDE\" = ?, \"TITEL\" = ?, \"VORNAME\" = ?, \"NAME\" = ?, \"STRASSE\" = ?, \"PLZ\" = ?, \"IBAN\" = ?, \"BANK\" = ?, \"BLZ\" = ?, \"ORT\" = ? WHERE \"ID\" = ?";

	final String SELECT_DOZENT = "SELECT * FROM \"LEHRVERGUETUNG\".\"DOZENTEN\" WHERE \"ID\" = ?";

	final String SELECT_DOZENT_ALL = "SELECT * FROM \"LEHRVERGUETUNG\".\"DOZENTEN\"";

	final String SELECT_DOZENT_SEARCH = "SELECT * FROM \"LEHRVERGUETUNG\".\"DOZENTEN\" WHERE LOWER(\"ANREDE\") LIKE ? OR LOWER(\"TITEL\") LIKE ? OR LOWER(\"VORNAME\") LIKE ? OR LOWER(\"NAME\") LIKE ? OR LOWER(\"STRASSE\") LIKE ? OR LOWER(\"PLZ\") LIKE ? OR LOWER(\"IBAN\") LIKE ? OR LOWER(\"BANK\") LIKE ? OR LOWER(\"BLZ\") LIKE ? OR LOWER(\"ORT\") LIKE ?";

	final String SELECT_DOZENT_SEARCH_FULLNAME = "SELECT * FROM \"LEHRVERGUETUNG\".\"DOZENTEN\" WHERE LOWER(\"ANREDE\") || '=' || LOWER(\"TITEL\") || '=' || LOWER(\"VORNAME\") || '=' || LOWER(\"NAME\") LIKE ? OR LOWER(\"ANREDE\") || '=' || LOWER(\"VORNAME\") || '=' || LOWER(\"NAME\") LIKE ? OR LOWER(\"ANREDE\") || LOWER(\"NAME\") LIKE ? OR LOWER(\"VORNAME\") || '=' || LOWER(\"NAME\") LIKE ?";

	final String SELECT_DOZENT_SEARCH_DOZENTID_BY_CODENAME = "SELECT \"ID\" FROM \"LEHRVERGUETUNG\".\"DOZENTEN\" WHERE LOWER(\"ANREDE\") || LOWER(\"TITEL\") || LOWER(\"VORNAME\") || LOWER(\"NAME\") LIKE ?";

	private int id;

	private final Connection con;

	/**
	 * Datenbankverbindung herstellen
	 * 
	 * @author Flo
	 * @param con
	 */
	public DozentDAO(Connection con) {
		this.con = con;
	}

	/**
	 * Einen Dozent anhand seiner ID abfragen
	 * 
	 * @author Flo
	 * @param id
	 * @return dozent
	 * @throws SQLException
	 */
	public Dozent selectDozent(int id) throws SQLException {
		PreparedStatement stat = con.prepareStatement(SELECT_DOZENT);
		stat.setInt(1, id);
		ResultSet result = stat.executeQuery();

		Dozent dozent = new Dozent();
		while (result.next()) {
			dozent.setId(result.getInt("ID"));
			dozent.setAnrede(result.getString("ANREDE"));
			dozent.setTitel(result.getString("TITEL"));
			dozent.setVorname(result.getString("VORNAME"));
			dozent.setName(result.getString("NAME"));
			dozent.setStrasse(result.getString("STRASSE"));
			dozent.setPLZ(result.getString("PLZ"));
			dozent.setIBAN(result.getString("IBAN"));
			dozent.setBank(result.getString("BANK"));
			dozent.setBLZ(result.getString("BLZ"));
			dozent.setOrt(result.getString("ORT"));
		}
		return dozent;
	}

	/**
	 * Dozent löschen
	 * 
	 * @author Flo
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Dozent deleteDozent(int id) throws SQLException {
		PreparedStatement stat = con.prepareStatement(DELETE_DOZENT);
		stat.setInt(1, id);
		stat.executeUpdate();

		return null;
	}

	/**
	 * Alle Dozenten abrufen
	 * 
	 * @author Flo
	 * @return Liste an Dozenten
	 * @throws SQLException
	 */
	public List<Dozent> selectAllDozenten() throws SQLException {
		PreparedStatement stat = con.prepareStatement(SELECT_DOZENT_ALL);
		ResultSet result = stat.executeQuery();

		ArrayList<Dozent> dozenten = new ArrayList<>();
		while (result.next()) {
			Dozent dozent = new Dozent();
			dozent.setId(result.getInt("ID"));
			dozent.setAnrede(result.getString("ANREDE"));
			dozent.setTitel(result.getString("TITEL"));
			dozent.setVorname(result.getString("VORNAME"));
			dozent.setName(result.getString("NAME"));
			dozent.setStrasse(result.getString("STRASSE"));
			dozent.setPLZ(result.getString("PLZ"));
			dozent.setIBAN(result.getString("IBAN"));
			dozent.setBank(result.getString("BANK"));
			dozent.setBLZ(result.getString("BLZ"));
			dozent.setOrt(result.getString("ORT"));
			dozenten.add(dozent);
		}
		return dozenten;
	}

	/**
	 * Dozent einfügen
	 * 
	 * @author Flo
	 * @param dozent
	 * @return dozent
	 * @throws SQLException
	 */
	public Dozent insertDozent(Dozent dozent) throws SQLException {
		PreparedStatement stat = con.prepareStatement(INSERT_DOZENT);
		stat.setString(1, dozent.getAnrede());
		stat.setString(2, dozent.getTitel());
		stat.setString(3, dozent.getVorname());
		stat.setString(4, dozent.getName());
		stat.setString(5, dozent.getStrasse());
		stat.setString(6, dozent.getPLZ());
		stat.setString(7, dozent.getIBAN());
		stat.setString(8, dozent.getBank());
		stat.setString(9, dozent.getBLZ());
		stat.setString(10, dozent.getOrt());

		stat.executeUpdate();
		return dozent;
	}

	/**
	 * Dozent Datensatz updaten
	 * 
	 * @author Flo
	 * @param dozent
	 * @return dozent
	 * @throws SQLException
	 */
	public Dozent updateDozent(Dozent dozent) throws SQLException {
		PreparedStatement stat = con.prepareStatement(UPDATE_DOZENT);
		stat.setString(1, dozent.getAnrede());
		stat.setString(2, dozent.getTitel());
		stat.setString(3, dozent.getVorname());
		stat.setString(4, dozent.getName());
		stat.setString(5, dozent.getStrasse());
		stat.setString(6, dozent.getPLZ());
		stat.setString(7, dozent.getIBAN());
		stat.setString(8, dozent.getBank());
		stat.setString(9, dozent.getBLZ());
		stat.setString(10, dozent.getOrt());
		stat.setInt(11, dozent.getId());

		stat.executeUpdate();
		return dozent;
	}

	/**
	 * Dozenten anhand sämtlichen Kriterien suchen
	 * 
	 * @author Flo
	 * @param searchString
	 * @return Liste an Dozenten
	 * @throws SQLException
	 */
	public List<Dozent> searchDozent(String searchString) throws SQLException {
		PreparedStatement stat = con.prepareStatement(SELECT_DOZENT_SEARCH);
		stat.setString(1, ("%" + searchString + "%").toLowerCase());
		stat.setString(2, ("%" + searchString + "%").toLowerCase());
		stat.setString(3, ("%" + searchString + "%").toLowerCase());
		stat.setString(4, ("%" + searchString + "%").toLowerCase());
		stat.setString(5, ("%" + searchString + "%").toLowerCase());
		stat.setString(6, ("%" + searchString + "%").toLowerCase());
		stat.setString(7, ("%" + searchString + "%").toLowerCase());
		stat.setString(8, ("%" + searchString + "%").toLowerCase());
		stat.setString(9, ("%" + searchString + "%").toLowerCase());
		stat.setString(10, ("%" + searchString + "%").toLowerCase());
		ResultSet result = stat.executeQuery();

		ArrayList<Dozent> dozenten = new ArrayList<>();
		while (result.next()) {
			Dozent dozent = new Dozent();
			dozent.setId(result.getInt("ID"));
			dozent.setAnrede(result.getString("ANREDE"));
			dozent.setTitel(result.getString("TITEL"));
			dozent.setVorname(result.getString("VORNAME"));
			dozent.setName(result.getString("NAME"));
			dozent.setStrasse(result.getString("STRASSE"));
			dozent.setPLZ(result.getString("PLZ"));
			dozent.setIBAN(result.getString("IBAN"));
			dozent.setBank(result.getString("BANK"));
			dozent.setBLZ(result.getString("BLZ"));
			dozent.setOrt(result.getString("ORT"));
			dozenten.add(dozent);
		}
		return dozenten;
	}

	/**
	 * Dozenten anhand ihres vollen Namens (Anrede, Titel, Vorname, Name) suchen
	 * 
	 * @author Flo
	 * @param searchString
	 * @return Liste an Dozenten
	 * @throws SQLException
	 */
	public List<Dozent> searchDozentFullname(String searchString) throws SQLException {
		PreparedStatement stat = con.prepareStatement(SELECT_DOZENT_SEARCH_FULLNAME);
		stat.setString(1, ("%" + searchString + "%").toLowerCase().replace(" ", "="));
		stat.setString(2, ("%" + searchString + "%").toLowerCase().replace(" ", "="));
		stat.setString(3, ("%" + searchString + "%").toLowerCase().replace(" ", "="));
		stat.setString(4, ("%" + searchString + "%").toLowerCase().replace(" ", "="));
		ResultSet result = stat.executeQuery();

		ArrayList<Dozent> dozenten = new ArrayList<>();
		while (result.next()) {
			Dozent dozent = new Dozent();
			dozent.setId(result.getInt("ID"));
			dozent.setAnrede(result.getString("ANREDE"));
			dozent.setTitel(result.getString("TITEL"));
			dozent.setVorname(result.getString("VORNAME"));
			dozent.setName(result.getString("NAME"));
			dozent.setStrasse(result.getString("STRASSE"));
			dozent.setPLZ(result.getString("PLZ"));
			dozent.setIBAN(result.getString("IBAN"));
			dozent.setBank(result.getString("BANK"));
			dozent.setBLZ(result.getString("BLZ"));
			dozent.setOrt(result.getString("ORT"));
			dozenten.add(dozent);
		}
		return dozenten;
	}

	/**
	 * Vollständigen Namen eines Dozenten in der Datenbank suchen und seine ID
	 * zurückgeben
	 * 
	 * @author Flo
	 * @param searchString
	 * @return ID wenn erfolgreich, -1 wenn nicht gefunden
	 * @throws SQLException
	 */
	public int searchDozentIdByCodename(String searchString) throws SQLException {
		PreparedStatement stat = con.prepareStatement(SELECT_DOZENT_SEARCH_DOZENTID_BY_CODENAME);
		stat.setString(1, (searchString).toLowerCase().replace(" ", ""));
		ResultSet result = stat.executeQuery();

		while (result.next()) {
			return result.getInt("ID");
		}
		return -1;
	}

}
