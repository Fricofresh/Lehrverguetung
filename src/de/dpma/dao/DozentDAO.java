package de.dpma.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.dpma.model.Dozent;

public class DozentDAO {
	final String INSERT_DOZENT = "INSERT INTO \"LEHRVERGUETUNG\".\"DOZENTEN\" (\"ANREDE\", \"TITEL\", \"VORNAME\", \"NAME\", \"STRASSE\", \"PLZ\", \"IBAN\", \"BANK\", \"BLZ\", \"ORT\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	final String DELETE_DOZENT = "DELETE FROM \"LEHRVERGUETUNG\".\"DOZENTEN\" WHERE \"ID\" = ?";

	final String UPDATE_DOZENT = "UPDATE \"LEHRVERGUETUNG\".\"DOZENTEN\" SET \"ANREDE\" = ?, \"TITEL\" = ?, \"VORNAME\" = ?, \"NAME\" = ?, \"STRASSE\" = ?, \"PLZ\" = ?, \"IBAN\" = ?, \"BANK\" = ?, \"BLZ\" = ?, \"ORT\" = ? WHERE \"ID\" = ?";

	final String SELECT_DOZENT = "SELECT * FROM \"LEHRVERGUETUNG\".\"DOZENTEN\" WHERE \"ID\" = ?";

	final String SELECT_DOZENT_ALL = "SELECT * FROM \"LEHRVERGUETUNG\".\"DOZENTEN\"";

	final String SELECT_DOZENT_SEARCH = "SELECT * FROM \"LEHRVERGUETUNG\".\"DOZENTEN\" WHERE LOWER(\"ANREDE\") LIKE ? OR LOWER(\"TITEL\") LIKE ? OR LOWER(\"VORNAME\") LIKE ? OR LOWER(\"NAME\") LIKE ? OR LOWER(\"STRASSE\") LIKE ? OR LOWER(\"PLZ\") LIKE ? OR LOWER(\"IBAN\") LIKE ? OR LOWER(\"BANK\") LIKE ? OR LOWER(\"BLZ\") LIKE ? OR LOWER(\"ORT\") LIKE ?";

	private int id;

	private final Connection con;

	public DozentDAO(Connection con) {
		this.con = con;
	}

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

	public Dozent deleteDozent(int id) throws SQLException {
		PreparedStatement stat = con.prepareStatement(DELETE_DOZENT);
		stat.setInt(1, id);
		stat.executeUpdate();

		return null;
	}

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

}
