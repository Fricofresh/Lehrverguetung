package de.dpma.util;

import java.io.File;
import java.io.IOException;

public class UsefulFuncs {

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

	public static String getCurrentDirectory() {
		return new File("").getAbsolutePath();
	}

}
