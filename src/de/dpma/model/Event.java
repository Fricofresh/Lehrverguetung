package de.dpma.model;

import java.text.ParseException;

import de.dpma.MainApp;
import de.dpma.dao.DozentDAO;
import de.dpma.dao.StundenlohnDAO;
import de.dpma.util.FormatCurrrency;
import de.dpma.util.FormatDate;
import de.dpma.util.NumberToText;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class Event {

	private IntegerProperty id = new SimpleIntegerProperty();

	private IntegerProperty id_dozent = new SimpleIntegerProperty();

	private StringProperty schulart = new SimpleStringProperty();

	private StringProperty aktenz = new SimpleStringProperty();

	private StringProperty vfg = new SimpleStringProperty();
	private StringProperty vfgBeautiful = new SimpleStringProperty();

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

	public StringProperty getStundenZahlString() {

		StringProperty stundenZahlString = new SimpleStringProperty();
		StringConverter<Number> converter = new NumberStringConverter();
		Bindings.bindBidirectional(stundenZahlString, stdzahl, converter);
		return stundenZahlString;
	}

	public String getVfg() {

		return vfg.get();
	}

	public void setVfg(String vfg) throws ParseException {

		this.vfg.set(vfg);
		this.vfgBeautiful.set(FormatDate.format(vfg));
	}

	public StringProperty VfgProperty() {

		return this.vfgBeautiful;
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

	public StringProperty DateProperty() throws ParseException {

		StringProperty combine = new SimpleStringProperty(
				FormatDate.format(date_start.getValue()) + " - " + FormatDate.format(date_end.getValue()));
		return combine;
	}

	public StringProperty DozentProperty() {

		StringProperty dump = null;
		try {
			DozentDAO dozentDAO = new DozentDAO(MainApp.dbcon.getConnection());
			Dozent name = dozentDAO.selectDozent(this.getId_dozent());

			StringProperty combine = new SimpleStringProperty(
					name.getAnrede() + (name.getTitel() == null ? "" : " " + name.getTitel()) + " " + name.getVorname()
							+ " " + name.getName());
			return combine;

		} catch (Exception e) {
		}
		return dump;

	}

	public String DozentString() {

		String dump = null;
		try {
			DozentDAO dozentDAO = new DozentDAO(MainApp.dbcon.getConnection());
			Dozent name = dozentDAO.selectDozent(this.getId_dozent());

			String dozent = name.getAnrede() + (name.getTitel() == null ? "" : " " + name.getTitel()) + " "
					+ name.getVorname() + " " + name.getName();
			return dozent;

		} catch (Exception e) {
		}
		return dump;

	}

	public StringProperty VortragsArtProperty() {

		StringProperty temp;
		if (this.getVortrg_mode() == 1) {
			temp = new SimpleStringProperty("Schulung");
			return temp;
		} else {
			temp = new SimpleStringProperty("Sonstiges");
			return temp;
		}
	}

	public StringProperty Euro_stdProperty() {

		StringProperty temp = null;
		try {
			StundenlohnDAO stdLohnDAO = new StundenlohnDAO(MainApp.dbcon.getConnection());
			Stundenlohn stdLohn = stdLohnDAO.selectStundenlohn(this.getId_euro_std());
			temp = stdLohn.LohnProperty();
			return temp;
		} catch (Exception e) {
		}
		return temp;
	}

	public StringProperty BetragProperty() {

		StringProperty temp = null;
		try {
			StundenlohnDAO stdLohnDAO = new StundenlohnDAO(MainApp.dbcon.getConnection());
			Stundenlohn stdLohn = stdLohnDAO.selectStundenlohn(this.getId_euro_std());
			double summe = stdLohn.getLohn() * this.getStdzahl();
			String temp2 = "" + summe;
			temp2 = FormatCurrrency.format(summe, true);
			temp = new SimpleStringProperty(temp2);
			return temp;
		} catch (Exception e) {
		}
		return temp;
	}

	public StringProperty Betrag_ABCProperty() {

		StringProperty temp = null;
		try {
			StundenlohnDAO stdLohnDAO = new StundenlohnDAO(MainApp.dbcon.getConnection());
			Stundenlohn stdLohn = stdLohnDAO.selectStundenlohn(this.getId_euro_std());
			double summe = stdLohn.getLohn() * this.getStdzahl();
			temp = new SimpleStringProperty(NumberToText.NumberToText(summe));

			return temp;
		} catch (Exception e) {
		}
		return temp;
	}

}
