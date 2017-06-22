package de.dpma.util;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class DataChecker {
	public static boolean isEmpty(String input) {
		return input == null || input.trim().isEmpty();
	}

	public static boolean isEmpty(int input) {
		return isEmpty(String.valueOf(input));
	}

	public static boolean isEmpty(Double input) {
		return isEmpty(String.valueOf(input));
	}

	public static boolean isNumeric(String input) {
		if (isEmpty(input)) {
			return false;
		}

		input = input.replace(",", ".");

		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(input.trim(), pos);
		return input.length() == pos.getIndex();
	}

	public static boolean hasTwoOrNoDecimals(String input) {
		if (isEmpty(input) || !isNumeric(input)) {
			return false;
		}

		input = input.replace(",", ".");

		if (input.contains(".")) {
			input = input.substring(input.indexOf(".")).substring(1);
			return input.length() == 2;
		} else {
			return true;
		}
	}

	public static boolean hasTwoOrNoDecimals(Double input) {
		return hasTwoOrNoDecimals(String.valueOf(input));
	}
}
