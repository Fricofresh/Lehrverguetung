package de.dpma.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import de.dpma.model.Event;

public class EventDAO {
	final String INSERT_EVENT = "INSERT INTO \"LEHRVERGUETUNG\".\"events\" (\"ID_DOZENT\", \"SCHULART\", \"AKTENZ\", \"VFG\", \"DATE_START\", \"DATE_END\", \"STDZAHL\", \"ID_EURO_STD\", \"VORTRG_MODE\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	final String DELETE_EVENT = "DELETE FROM \"LEHRVERGUETUNG\".\"events\" WHERE \"ID\" = ?";

	final String UPDATE_EVENT = "UPDATE \"LEHRVERGUETUNG\".\"events\" SET \"ID_DOZENT\" = ?, \"SCHULART\" = ?, \"AKTENZ\" = ?, \"VFG\" = ?, \"DATE_START\" = ?, \"DATE_END\" = ?, \"STDZAHL\" = ?, \"ID_EURO_STD\" = ?, \"VORTRG_MODE\" = ? WHERE \"ID\" = ?";

	final String SELECT_EVENT = "SELECT * FROM \"LEHRVERGUETUNG\".\"events\" WHERE \"ID\" = ?";

	final String SELECT_EVENT_ALL = "SELECT * FROM \"LEHRVERGUETUNG\".\"events\"";

	final String SELECT_EVENT_SEARCH = "" + "SELECT " + "\"events\".*, \"STUNDENLOEHNE\".*, \"DOZENTEN\".* " + "FROM "
			+ "\"LEHRVERGUETUNG\".\"events\", \"LEHRVERGUETUNG\".\"STUNDENLOEHNE\", \"LEHRVERGUETUNG\".\"DOZENTEN\" "
			+ "WHERE " + "\"DOZENTEN\".\"ID\" = \"events\".\"ID_DOZENT\" AND "
			+ "\"STUNDENLOEHNE\".\"ID\" = \"events\".\"ID_EURO_STD\" AND " + "(\"events\".\"VORTRG_MODE\" = ? OR "
			+ "LOWER(\"events\".\"SCHULART\") LIKE ? OR " + "LOWER(\"events\".\"AKTENZ\") LIKE ? OR "
			+ "CAST(\"events\".\"DATE_END\" AS CHAR(40)) LIKE ? OR "
			+ "CAST(\"events\".\"DATE_START\" AS CHAR(40)) LIKE ? OR "
			+ "CAST(\"events\".\"STDZAHL\" AS CHAR(10)) LIKE ? OR "
			+ "LOWER(\"STUNDENLOEHNE\".\"LOHN\") LIKE ? OR LOWER(\"DOZENTEN\".\"TITEL\") LIKE ? OR "
			+ "LOWER(\"DOZENTEN\".\"ANREDE\") LIKE ? OR LOWER(\"DOZENTEN\".\"VORNAME\") LIKE ? OR "
			+ "LOWER(\"DOZENTEN\".\"NAME\") LIKE ?)";
	// RIGHT OUTER JOIN \"LEHRVERGUETUNG\".\"STUNDENLOEHNE\" ON
	// \"events\".\"ID_EUR_STD\" = \"STUNDENLOEHNE\".\"ID\"

	private int id;

	private final Connection con;

	public EventDAO(Connection con) {
		this.con = con;
	}

	public Event selectEvent(int id) throws SQLException, ParseException {
		PreparedStatement stat = con.prepareStatement(SELECT_EVENT);
		stat.setInt(1, id);
		ResultSet result = stat.executeQuery();

		Event event = new Event();
		while (result.next()) {
			event.setId(result.getInt("ID"));
			event.setId_dozent(result.getInt("ID_DOZENT"));
			event.setSchulart(result.getString("SCHULART"));
			event.setAktenz(result.getString("AKTENZ"));
			event.setVfg(result.getString("VFG").split(" ")[0]);
			event.setDate_start(result.getString("DATE_START").split(" ")[0]);
			event.setDate_end(result.getString("DATE_END").split(" ")[0]);
			event.setStdzahl(result.getInt("STDZAHL"));
			event.setId_euro_std(result.getInt("ID_EURO_STD"));
			event.setVortrg_mode(result.getInt("VORTRG_MODE"));
		}
		return event;
	}

	public Event deleteEvent(int id) throws SQLException {
		PreparedStatement stat = con.prepareStatement(DELETE_EVENT);
		stat.setInt(1, id);
		stat.executeUpdate();

		return null;
	}

	public List<Event> selectAllEvents() throws SQLException, ParseException {
		PreparedStatement stat = con.prepareStatement(SELECT_EVENT_ALL);
		ResultSet result = stat.executeQuery();

		ArrayList<Event> events = new ArrayList<>();
		while (result.next()) {
			Event event = new Event();
			event.setId(result.getInt("ID"));
			event.setId_dozent(result.getInt("ID_DOZENT"));
			event.setSchulart(result.getString("SCHULART"));
			event.setAktenz(result.getString("AKTENZ"));
			event.setVfg(result.getString("VFG").split(" ")[0]);
			event.setDate_start(result.getString("DATE_START").split(" ")[0]);
			event.setDate_end(result.getString("DATE_END").split(" ")[0]);
			event.setStdzahl(result.getInt("STDZAHL"));
			event.setId_euro_std(result.getInt("ID_EURO_STD"));
			event.setVortrg_mode(result.getInt("VORTRG_MODE"));
			events.add(event);
		}
		return events;
	}

	public Event insertEvent(Event event) throws SQLException {
		PreparedStatement stat = con.prepareStatement(INSERT_EVENT);
		stat.setInt(1, event.getId_dozent());
		stat.setString(2, event.getSchulart());
		stat.setString(3, event.getAktenz());
		stat.setString(4, event.getVfg() + " 00:00:00.0");
		stat.setString(5, event.getDate_start() + " 00:00:00.0");
		stat.setString(6, event.getDate_end() + " 00:00:00.0");
		stat.setInt(7, event.getStdzahl());
		stat.setInt(8, event.getId_euro_std());
		stat.setInt(9, event.getVortrg_mode());

		stat.executeUpdate();
		return event;
	}

	public Event updateEvent(Event event) throws SQLException {
		PreparedStatement stat = con.prepareStatement(UPDATE_EVENT);
		stat.setInt(1, event.getId_dozent());
		stat.setString(2, event.getSchulart());
		stat.setString(3, event.getAktenz());
		stat.setString(4, event.getVfg() + " 00:00:00.0");
		stat.setString(5, event.getDate_start() + " 00:00:00.0");
		stat.setString(6, event.getDate_end() + " 00:00:00.0");
		stat.setInt(7, event.getStdzahl());
		stat.setInt(8, event.getId_euro_std());
		stat.setInt(9, event.getVortrg_mode());
		stat.setInt(10, event.getId());

		stat.executeUpdate();
		return event;
	}

	public List<Event> searchEvent(String searchString) throws SQLException, ParseException {
		int SearchIntVortrag;
		PreparedStatement stat = con.prepareStatement(SELECT_EVENT_SEARCH);

		if (searchString.equalsIgnoreCase("Schulung")) {
			SearchIntVortrag = 1;
		} else if (searchString.equalsIgnoreCase("Sonstiges")) {
			SearchIntVortrag = 0;
		} else {
			SearchIntVortrag = 2;
		}

		stat.setInt(1, SearchIntVortrag);
		stat.setString(2, ("%" + searchString + "%").toLowerCase());
		stat.setString(3, ("%" + searchString + "%").toLowerCase());
		stat.setString(4, ("%" + searchString + "%").toLowerCase());
		stat.setString(5, ("%" + searchString + "%").toLowerCase());
		stat.setString(6, ("%" + searchString + "%").toLowerCase());
		stat.setString(7, "%" + searchString.replace(",", ".") + "%");
		stat.setString(8, ("%" + searchString + "%").toLowerCase());
		stat.setString(9, ("%" + searchString + "%").toLowerCase());
		stat.setString(10, ("%" + searchString + "%").toLowerCase());
		stat.setString(11, ("%" + searchString + "%").toLowerCase());
		ResultSet result = stat.executeQuery();
		// ResultSetMetaData rsmd = result.getMetaData();
		// int columnsNumber = rsmd.getColumnCount();
		//
		// while (result.next()) {
		// for (int i = 1; i <= columnsNumber; i++) {
		// if (i > 1)
		// System.out.print(", ");
		// String columnValue = result.getString(i);
		// System.out.print(columnValue + " " + rsmd.getColumnName(i));
		// }
		// System.out.println("");
		// }

		ArrayList<Event> events = new ArrayList<>();
		while (result.next()) {
			Event event = new Event();
			event.setId(result.getInt("ID"));
			event.setId_dozent(result.getInt("ID_DOZENT"));
			event.setSchulart(result.getString("SCHULART"));
			event.setAktenz(result.getString("AKTENZ"));
			if (result.getString("VFG") != null) {
				event.setVfg(result.getString("VFG").split(" ")[0]);
			}
			if (result.getString("DATE_START") != null) {
				event.setDate_start(result.getString("DATE_START").split(" ")[0]);
			}
			if (result.getString("DATE_END") != null) {
				event.setDate_end(result.getString("DATE_END").split(" ")[0]);
			}
			event.setStdzahl(result.getInt("STDZAHL"));
			event.setId_euro_std(result.getInt("ID_EURO_STD"));
			event.setVortrg_mode(result.getInt("VORTRG_MODE"));
			events.add(event);
		}
		return events;
	}

}
