package de.dpma.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class ConfigIniUtil {
	
	Properties p = new Properties();
	
	private String dienstort;
	
	private String durchwahl;
	
	private String vorname;
	
	private String nachname;
	
	private String email;
	
	private String path;
	
	public ConfigIniUtil() {
		try {
			if (path.isEmpty()) {
				path = System.getProperty("user.home") + "/Lehrvergütung/" + System.getProperty("user.name") + ".ini";
			}
			
			p.load(new FileInputStream(path));
			readConf();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void readConf() {
		
		p.getProperty("Dienstort");
		p.getProperty("Durchwahl");
		p.getProperty("Vorname");
		p.getProperty("Nachname");
		p.getProperty("E-Mail");
	}
	
	public void writeConf() {
		
		try {
			p.setProperty("Dienstort", dienstort);
			p.setProperty("Durchwahl", durchwahl);
			p.setProperty("Vorname", vorname);
			p.setProperty("Nachname", nachname);
			p.setProperty("E-Mail", email);
			FileOutputStream out = new FileOutputStream(path);
			p.save(out, "Benutzer Konfiguration");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
