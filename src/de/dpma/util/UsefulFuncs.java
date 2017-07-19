package de.dpma.util;

import java.io.File;
import java.io.IOException;

public class UsefulFuncs {

	/**
	 * Findet heraus, ob das Programm in Eclipse oder einer JAR File gestartet
	 * wurde
	 * 
	 * @author Flo
	 * @return boolean
	 */
	public static boolean isDevelopment() {
		try {
			return new File(".").getCanonicalPath().contains("workspace")
					|| new File(".").getCanonicalPath().contains("git");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Gibt das aktuelle Verzeichnis zurück
	 * 
	 * @author Flo
	 * @return String (Pfad)
	 */
	public static String getCurrentDirectory() {
		return new File("").getAbsolutePath();
	}

}
