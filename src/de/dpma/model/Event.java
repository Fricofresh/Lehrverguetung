package de.dpma.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Event {

	private IntegerProperty id = new SimpleIntegerProperty();
	private IntegerProperty id_dozent = new SimpleIntegerProperty();

	private StringProperty schulart = new SimpleStringProperty();
	private StringProperty aktenz = new SimpleStringProperty();

	private StringProperty vfg = new SimpleStringProperty();
	private StringProperty date_start = new SimpleStringProperty();
	private StringProperty date_end = new SimpleStringProperty();

	private IntegerProperty stdzahl = new SimpleIntegerProperty();
	private IntegerProperty id_euro_std = new SimpleIntegerProperty();
	private IntegerProperty vortrg_mode = new SimpleIntegerProperty();

	public Event() {

	}

	// Standard Event Constructor
	public Event(int id, int id_dozent, String schulart, String aktenz, String vfg, String date_start, String date_end,
			int stdzahl, int id_euro_std, int vortrg_mode) {
		this.id = new SimpleIntegerProperty(id);
		this.id_dozent = new SimpleIntegerProperty(id_dozent);
		this.schulart = new SimpleStringProperty(schulart);
		this.aktenz = new SimpleStringProperty(aktenz);
		this.vfg = new SimpleStringProperty(vfg);
		this.date_start = new SimpleStringProperty(date_start);
		this.date_end = new SimpleStringProperty(date_end);
		this.stdzahl = new SimpleIntegerProperty(stdzahl);
		this.id_euro_std = new SimpleIntegerProperty(id_euro_std);
		this.vortrg_mode = new SimpleIntegerProperty(vortrg_mode);
	}

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public IntegerProperty IdProperty() {
		return this.id;
	}

	public int getId_dozent() {
		return id_dozent.get();
	}

	public void setId_dozent(int id_dozent) {
		this.id_dozent.set(id_dozent);
	}

	public IntegerProperty Id_dozentProperty() {
		return this.id_dozent;
	}

	public String getSchulart() {
		return schulart.get();
	}

	public void setSchulart(String schulart) {
		this.schulart.set(schulart);
	}

	public StringProperty SchulartProperty() {
		return this.schulart;
	}

	public String getAktenz() {
		return aktenz.get();
	}

	public void setAktenz(String aktenz) {
		this.aktenz.set(aktenz);
	}

	public StringProperty AktenzProperty() {
		return this.aktenz;
	}

	public String getVfg() {
		return vfg.get();
	}

	public void setVfg(String vfg) {
		this.vfg.set(vfg);
	}

	public StringProperty VfgProperty() {
		return this.vfg;
	}

	public String getDate_start() {
		return date_start.get();
	}

	public void setDate_start(String date_start) {
		this.date_start.set(date_start);
	}

	public StringProperty Date_startProperty() {
		return this.date_start;
	}

	public String getDate_end() {
		return date_end.get();
	}

	public void setDate_end(String date_end) {
		this.date_end.set(date_end);
	}

	public StringProperty Date_endProperty() {
		return this.date_end;
	}

	public int getStdzahl() {
		return stdzahl.get();
	}

	public void setStdzahl(int stdzahl) {
		this.stdzahl.set(stdzahl);
	}

	public IntegerProperty stdzahlProperty() {
		return this.stdzahl;
	}

	public int getId_euro_std() {
		return id_euro_std.get();
	}

	public void setId_euro_std(int id_euro_std) {
		this.id_euro_std.set(id_euro_std);
	}

	public IntegerProperty Id_euro_stdProperty() {
		return this.id_euro_std;
	}

	public int getVortrg_mode() {
		return vortrg_mode.get();
	}

	public void setVortrg_mode(int vortrg_mode) {
		this.vortrg_mode.set(vortrg_mode);
	}

	public IntegerProperty Vortrg_modeProperty() {
		return this.vortrg_mode;
	}

}
