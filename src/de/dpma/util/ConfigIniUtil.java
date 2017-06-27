package de.dpma.util;

import java.io.File;
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

	private String path = "Konfigurationsdateien\\";

	private String fullpath = path + System.getProperty("user.name") + ".ini";

	public ConfigIniUtil() {
		try {
			// System.getProperty("user.home")
			boolean b;
			if (!(b = (new File(path).exists()))) {
				b = (new File(path).mkdirs());
			} else if (b = new File(fullpath).exists()) {
				readConf();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readConf() {

		try {
			FileInputStream input = new FileInputStream(new File(fullpath));
			p.load(input);
			dienstort = p.getProperty("Dienstort");
			durchwahl = p.getProperty("Durchwahl");
			vorname = p.getProperty("Vorname");
			nachname = p.getProperty("Nachname");
			email = p.getProperty("E-Mail");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void writeConf() {

		try {
			p.setProperty("Dienstort", dienstort);
			p.setProperty("Durchwahl", durchwahl);
			p.setProperty("Vorname", vorname);
			p.setProperty("Nachname", nachname);
			p.setProperty("E-Mail", email);
			File file = new File(fullpath);
			FileOutputStream out = new FileOutputStream(file, false);
			p.store(out, "Benutzer Konfiguration");
			out.flush();
			out.close();
		} catch (Exception e) {
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
