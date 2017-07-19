package de.dpma.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * This Class is to read and write in the configuration file.
 * 
 * @author Kenneth Böhmer
 *
 */
public class ConfigIniUtil {
	
	Properties p = new Properties();
	
	private String dienstort;
	
	private String durchwahl;
	
	private String vorname;
	
	private String nachname;
	
	private String email;
	
	private String path = "Konfigurationsdateien\\";
	
	/**
	 * Set the fill path of this file. The file will be named like the current
	 * user.
	 * 
	 * @author Kenneth Böhmer
	 */
	private String fullpath = path + System.getProperty("user.name") + ".ini";
	
	/**
	 * The constructor checks if the file already exist and create it if not.
	 * 
	 * @author Kenneth Böhmer
	 */
	public ConfigIniUtil() {
		try {
			boolean b;
			if (!(b = (new File(path).exists()))) {
				b = (new File(path).mkdirs());
			}
			else if (b = new File(fullpath).exists()) {
				readConf();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the properties
	 * 
	 * @author Kenneth Böhmer
	 */
	private void readConf() {
		
		try {
			FileInputStream input = new FileInputStream(new File(fullpath));
			p.load(input);
			dienstort = p.getProperty("Dienstort");
			durchwahl = p.getProperty("Durchwahl");
			vorname = p.getProperty("Vorname");
			nachname = p.getProperty("Nachname");
			email = p.getProperty("E-Mail");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Write the properties out with FileOutputStream
	 * 
	 * @author Kenneth Böhmer
	 */
	public void writeConf() {
		
		try {
			// Properties werden gesetzt
			p.setProperty("Dienstort", dienstort);
			p.setProperty("Durchwahl", durchwahl);
			p.setProperty("Vorname", vorname);
			p.setProperty("Nachname", nachname);
			p.setProperty("E-Mail", email);
			// Die Datei wird angelegt
			File file = new File(fullpath);
			// Die Datei wird im nachfolgenden beschrieben
			FileOutputStream out = new FileOutputStream(file, false);
			p.store(out, "Benutzer Konfiguration");
			out.flush();
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the file
	 * 
	 * @see #ConfigIniUtil
	 * 
	 * @param dienstort
	 * @param durchwahl
	 * @param vorname
	 * @param nachname
	 * @param email
	 * @author Kenneth Böhmer
	 */
	public void setConf(String dienstort, String durchwahl, String vorname, String nachname, String email) {
		
		this.dienstort = dienstort;
		this.durchwahl = durchwahl;
		this.vorname = vorname;
		this.nachname = nachname;
		this.email = email;
	}
	
	public String getPath() {
		
		return path;
	}
	
	public void setPath(String path) {
		
		this.path = path;
	}
	
	public String getDienstort() {
		
		return dienstort;
	}
	
	public String getDurchwahl() {
		
		return durchwahl;
	}
	
	public String getVorname() {
		
		return vorname;
	}
	
	public String getNachname() {
		
		return nachname;
	}
	
	public String getEmail() {
		
		return email;
	}
}
