package de.dpma.util;

public class StringIndexOf {

	/**
	 * Gibt zurück, wie oft ein String in einem String gefunden wurde
	 * 
	 * @author Flo
	 * @param string
	 * @param substring
	 * @return count
	 */
	public static int count(String string, String substring) {

		int atIndex = 0;
		int count = 0;

		while (atIndex != -1) {
			atIndex = string.indexOf(substring, atIndex);

			if (atIndex != -1) {
				count++;
				atIndex += 5;
			}
		}

		return count;
	}

}
