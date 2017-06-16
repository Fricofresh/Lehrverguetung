package de.dpma.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Dozent {
	
	private IntegerProperty id = new SimpleIntegerProperty();
	
	private StringProperty anrede = new SimpleStringProperty();
	
	private StringProperty titel = new SimpleStringProperty();
	
	private StringProperty vorname = new SimpleStringProperty();
	
	private StringProperty name = new SimpleStringProperty();
	
	private StringProperty strasse = new SimpleStringProperty();
	
	private StringProperty plz = new SimpleStringProperty();
	
	private StringProperty iban = new SimpleStringProperty();
	
	private StringProperty bank = new SimpleStringProperty();
	
	private StringProperty blz = new SimpleStringProperty();
	
	private StringProperty ort = new SimpleStringProperty();
	
	public Dozent() {
		
	}
	
	// Standard Event Constructor
	public Dozent(int id, String anrede, String titel, String vorname, String name, String strasse, String plz,
			String iban, String bank, String blz, String ort) {
		this.id = new SimpleIntegerProperty(id);
		this.anrede = new SimpleStringProperty(anrede);
		this.titel = new SimpleStringProperty(titel);
		this.vorname = new SimpleStringProperty(vorname);
		this.name = new SimpleStringProperty(name);
		this.strasse = new SimpleStringProperty(strasse);
		this.plz = new SimpleStringProperty(plz);
		this.iban = new SimpleStringProperty(iban);
		this.bank = new SimpleStringProperty(bank);
		this.blz = new SimpleStringProperty(blz);
		this.ort = new SimpleStringProperty(ort);
	}
	
	public int getId() {
		
		return id.get();
	}
	
	public void setId(int id) {
		
		this.id.set(id);
	}
	
	public StringProperty IdProperty() {
		
		return this.anrede;
	}
	
	public String getAnrede() {
		
		return anrede.get();
	}
	
	public void setAnrede(String anrede) {
		
		this.anrede.set(anrede);
	}
	
	public StringProperty AnredeProperty() {
		
		return this.anrede;
	}
	
	public String getTitel() {
		
		return titel.get();
	}
	
	public void setTitel(String titel) {
		
		this.titel.set(titel);
	}
	
	public StringProperty TitelProperty() {
		
		return this.titel;
	}
	
	public String getVorname() {
		
		return vorname.get();
	}
	
	public void setVorname(String vorname) {
		
		this.vorname.set(vorname);
	}
	
	public StringProperty VornameProperty() {
		
		return this.vorname;
	}
	
	public String getName() {
		
		return name.get();
	}
	
	public void setName(String name) {
		
		this.name.set(name);
	}
	
	public StringProperty NameProperty() {
		
		return this.name;
	}
	
	public String getStrasse() {
		
		return strasse.get();
	}
	
	public void setStrasse(String strasse) {
		
		this.strasse.set(strasse);
	}
	
	public StringProperty StrasseProperty() {
		
		return this.strasse;
	}
	
	public String getPLZ() {
		
		return plz.get();
	}
	
	public void setPLZ(String plz) {
		
		this.plz.set(plz);
	}
	
	public StringProperty PLZProperty() {
		
		return this.plz;
	}
	
	public String getIBAN() {
		
		return iban.get();
	}
	
	public void setIBAN(String iban) {
		
		this.iban.set(iban);
	}
	
	public StringProperty IBANProperty() {
		
		return this.iban;
	}
	
	public String getBank() {
		
		return bank.get();
	}
	
	public void setBank(String bank) {
		
		this.bank.set(bank);
	}
	
	public StringProperty BankProperty() {
		
		return this.bank;
	}
	
	public String getBLZ() {
		
		return blz.get();
	}
	
	public void setBLZ(String blz) {
		
		this.blz.set(blz);
	}
	
	public StringProperty BLZProperty() {
		
		return this.blz;
	}
	
	public String getOrt() {
		
		return ort.get();
	}
	
	public void setOrt(String ort) {
		
		this.ort.set(ort);
	}
	
	public StringProperty OrtProperty() {
		
		return this.ort;
	}
	
}
