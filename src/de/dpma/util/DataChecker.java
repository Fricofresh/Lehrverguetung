package de.dpma.util;

import java.text.NumberFormat;
import java.text.ParsePosition;

import org.apache.commons.validator.routines.EmailValidator;

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

	public static boolean isIBAN(String input) {
		if (isEmpty(input)) {
			return false;
		}

		input = input.replaceAll("\\s+", "");
		return DataCheckerIBANLib.isIBAN(input) >= 2;
	}

	public static boolean isBIC(String input) {
		if (isEmpty(input)) {
			return false;
		}

		input = input.replaceAll("\\s+", "");
		return DataCheckerIBANLib.isBIC(input) >= 1;
	}

	public static boolean isBLZ(String input) {
		if (isEmpty(input)) {
			return false;
		}

		input = input.replaceAll("\\s+", "");
		return isNumeric(input) && input.length() == 8;
	}

	public static boolean isBLZ(int input) {
		return isBLZ(String.valueOf(input));
	}

	public static boolean isBICorBLZ(String input) {
		return isBIC(input) || isBLZ(input);
	}

	public static boolean isKontonummer(String input) {
		if (isEmpty(input)) {
			return false;
		}

		input = input.replaceAll("\\s+", "");
		return isNumeric(input) && input.length() >= 5 && input.length() <= 10;
	}

	public static boolean isKontonummer(int input) {
		return isKontonummer(String.valueOf(input));
	}

	public static boolean isIBANorKontonummer(String input) {
		return isIBAN(input) || isKontonummer(input);
	}

	public static boolean isPLZ(String input) {
		if (isEmpty(input)) {
			return false;
		}

		return isNumeric(input) && input.length() == 5;
	}

	public static boolean isPLZ(int input) {
		return isPLZ(String.valueOf(input));
	}

	public static boolean isEmail(String input) {
		return EmailValidator.getInstance().isValid(input);
	}
}
