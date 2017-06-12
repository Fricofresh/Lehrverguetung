package de.dpma.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.dpma.model.Event;

public class EventDAO {
	final String INSERT_EVENT = "INSERT INTO \"LEHRVERGUETUNG\".\"events\" (\"ID_DOZENT\", \"SCHULART\", \"AKTENZ\", \"VFG\", \"DATE_START\", \"DATE_END\", \"STDZAHL\", \"ID_EURO_STD\", \"VORTRG_MODE\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	final String DELETE_EVENT = "DELETE FROM \"LEHRVERGUETUNG\".\"events\" WHERE \"ID\" = ?";

	final String UPDATE_EVENT = "UPDATE \"LEHRVERGUETUNG\".\"events\" SET \"ID_DOZENT\" = ?, \"SCHULART\" = ?, \"AKTENZ\" = ?, \"VFG\" = ?, \"DATE_START\" = ?, \"DATE_END\" = ?, \"STDZAHL\" = ?, \"ID_EURO_STD\" = ?, \"VORTRG_MODE\" = ? WHERE \"ID\" = ?";

	final String SELECT_EVENT = "SELECT * FROM \"LEHRVERGUETUNG\".\"events\" WHERE \"ID\" = ?";

	final String SELECT_EVENT_ALL = "SELECT * FROM \"LEHRVERGUETUNG\".\"events\"";

	final String SELECT_EVENT_SEARCH = "SELECT * FROM \"LEHRVERGUETUNG\".\"events\" WHERE LOWER(\"SCHULART\") LIKE ? OR LOWER(\"AKTENZ\") LIKE ?";

	private int id;

	private final Connection con;

	public EventDAO(Connection con) {
		this.con = con;
	}

	public Event selectEvent(int id) throws SQLException {
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
		stat.executeQuery();

		return null;
	}

	public List<Event> selectAllEvents() throws SQLException {
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

	public List<Event> searchEvent(String searchString) throws SQLException {
		PreparedStatement stat = con.prepareStatement(SELECT_EVENT_SEARCH);
		stat.setString(1, ("%" + searchString + "%").toLowerCase());
		stat.setString(2, ("%" + searchString + "%").toLowerCase());
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

}
