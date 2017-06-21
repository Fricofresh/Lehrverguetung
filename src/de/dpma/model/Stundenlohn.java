package de.dpma.model;

import de.dpma.util.FormatCurrrency;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class Stundenlohn {

	private IntegerProperty id = new SimpleIntegerProperty();

	private SimpleDoubleProperty lohn = new SimpleDoubleProperty();

	private SimpleStringProperty lohnString = new SimpleStringProperty();

	private StringProperty creation_time = new SimpleStringProperty();

	public Stundenlohn() {

	}

	// Standard Stundenlohn Constructor
	public Stundenlohn(int id, double lohn, String creation_time) {
		this.id = new SimpleIntegerProperty(id);
		this.lohn = new SimpleDoubleProperty(lohn);
		this.creation_time = new SimpleStringProperty(creation_time);
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

	public double getLohn() {

		return lohn.get();
	}

	public void setLohn(double lohn) {

		this.lohn.set(lohn);
	}

	// public DoubleProperty LohnProperty() {
	// return this.lohn;
	// }
	//
	public StringProperty LohnProperty() {
		StringConverter<Number> converter = new NumberStringConverter();
		Bindings.bindBidirectional(lohnString, lohn, converter);
		return lohnString;
	}

	public StringProperty LohnPropertyFull() {
		SimpleStringProperty temp = new SimpleStringProperty(FormatCurrrency.format(lohn.get(), true));
		return temp;
	}

	public String getCreationTime() {

		return creation_time.get();
	}

	public void setCreationTime(String creation_time) {

		this.creation_time.set(creation_time);
	}

	public StringProperty CreationTimeProperty() {

		return this.creation_time;
	}

}
