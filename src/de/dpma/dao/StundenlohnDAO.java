package de.dpma.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.dpma.model.Stundenlohn;

public class StundenlohnDAO {
	final String INSERT_STUNDENLOHN = "INSERT INTO \"LEHRVERGUETUNG\".\"STUNDENLOEHNE\" (\"LOHN\", \"CREATION_TIME\") VALUES (?, ?)";

	final String DELETE_STUNDENLOHN = "DELETE FROM \"LEHRVERGUETUNG\".\"STUNDENLOEHNE\" WHERE \"ID\" = ?";

	final String UPDATE_STUNDENLOHN = "UPDATE \"LEHRVERGUETUNG\".\"STUNDENLOEHNE\" SET \"LOHN\" = ?, \"CREATION_TIME\" = ? WHERE \"ID\" = ?";

	final String SELECT_STUNDENLOHN = "SELECT * FROM \"LEHRVERGUETUNG\".\"STUNDENLOEHNE\" WHERE \"ID\" = ? ORDER BY \"CREATION_TIME\" DESC";

	final String SELECT_STUNDENLOHN_ALL = "SELECT * FROM \"LEHRVERGUETUNG\".\"STUNDENLOEHNE\" ORDER BY \"CREATION_TIME\" DESC";

	final String SELECT_STUNDENLOHN_SEARCH = "SELECT * FROM \"LEHRVERGUETUNG\".\"STUNDENLOEHNE\" WHERE LOHN || ' €' LIKE ? OR LOHN || '0 €' LIKE ? OR LOHN || ' €' LIKE ? OR LOHN || '0 €' LIKE ? ORDER BY \"CREATION_TIME\" DESC";

	private int id;

	private final Connection con;

	public StundenlohnDAO(Connection con) {
		this.con = con;
	}

	public Stundenlohn selectStundenlohn(int id) throws SQLException {
		PreparedStatement stat = con.prepareStatement(SELECT_STUNDENLOHN);
		stat.setInt(1, id);
		ResultSet result = stat.executeQuery();

		Stundenlohn stundenlohn = new Stundenlohn();
		while (result.next()) {
			stundenlohn.setId(result.getInt("ID"));
			stundenlohn.setLohn(Double.parseDouble(result.getString("LOHN")));
			stundenlohn.setCreationTime(result.getString("CREATION_TIME").split(" ")[0]);
		}
		return stundenlohn;
	}

	public Stundenlohn deleteStundenlohn(int id) throws SQLException {
		PreparedStatement stat = con.prepareStatement(DELETE_STUNDENLOHN);
		stat.setInt(1, id);
		stat.executeUpdate();

		return null;
	}

	public List<Stundenlohn> selectAllStundenloehne() throws SQLException {
		PreparedStatement stat = con.prepareStatement(SELECT_STUNDENLOHN_ALL);
		ResultSet result = stat.executeQuery();

		ArrayList<Stundenlohn> stundenloehne = new ArrayList<>();
		while (result.next()) {
			Stundenlohn stundenlohn = new Stundenlohn();
			stundenlohn.setId(result.getInt("ID"));
			stundenlohn.setLohn(Double.parseDouble(result.getString("LOHN")));
			stundenlohn.setCreationTime(result.getString("CREATION_TIME").split(" ")[0]);
			stundenloehne.add(stundenlohn);
		}
		return stundenloehne;
	}

	public Stundenlohn insertStundenlohn(Stundenlohn stundenlohn) throws SQLException {
		PreparedStatement stat = con.prepareStatement(INSERT_STUNDENLOHN);
		stat.setString(1, Double.toString(stundenlohn.getLohn()));
		stat.setString(2, stundenlohn.getCreationTime());

		stat.executeUpdate();
		return stundenlohn;
	}

	public Stundenlohn updateStundenlohn(Stundenlohn stundenlohn) throws SQLException {
		PreparedStatement stat = con.prepareStatement(UPDATE_STUNDENLOHN);
		stat.setString(1, Double.toString(stundenlohn.getLohn()));
		stat.setString(2, stundenlohn.getCreationTime() + " 00:00:00.0");
		stat.setInt(3, stundenlohn.getId());

		stat.executeUpdate();
		return stundenlohn;
	}

	public List<Stundenlohn> searchStundenlohn(String searchString) throws SQLException {
		PreparedStatement stat = con.prepareStatement(SELECT_STUNDENLOHN_SEARCH);
		stat.setString(1, "%" + searchString.replace(".", "").replace(",", ".") + "%");
		stat.setString(2, "%" + searchString.replace(".", "").replace(",", ".") + "%");
		stat.setString(3, "%" + searchString.replace(",", ".") + "%");
		stat.setString(4, "%" + searchString.replace(",", ".") + "%");

		ResultSet result = stat.executeQuery();

		ArrayList<Stundenlohn> stundenloehne = new ArrayList<>();
		while (result.next()) {
			Stundenlohn stundenlohn = new Stundenlohn();
			stundenlohn.setId(result.getInt("ID"));
			stundenlohn.setLohn(Double.parseDouble(result.getString("LOHN")));
			stundenlohn.setCreationTime(result.getString("CREATION_TIME").split(" ")[0]);
			stundenloehne.add(stundenlohn);
		}
		return stundenloehne;
	}

}
