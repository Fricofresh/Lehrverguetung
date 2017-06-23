package de.dpma.util;

public class StringIndexOf {

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
