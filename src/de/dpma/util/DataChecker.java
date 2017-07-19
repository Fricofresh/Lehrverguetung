package de.dpma.util;

import java.text.NumberFormat;
import java.text.ParsePosition;

import org.apache.commons.validator.routines.EmailValidator;

public class DataChecker {
	/**
	 * Pr�ft, ob ein String leer ist. Testet mit Trim.
	 * 
	 * @author Flo
	 * @param input
	 *            (String)
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		return input == null || input.trim().isEmpty();
	}

	/**
	 * Pr�ft, ob ein Integer leer ist.
	 * 
	 * @author Flo
	 * @param input
	 *            (int)
	 * @return boolean
	 */
	public static boolean isEmpty(int input) {
		return isEmpty(String.valueOf(input));
	}

	/**
	 * Pr�ft, ob ein Double leer ist.
	 * 
	 * @author Flo
	 * @param input
	 *            (Double)
	 * @return boolean
	 */
	public static boolean isEmpty(Double input) {
		return isEmpty(String.valueOf(input));
	}

	/**
	 * Pr�ft, ob ein String numerisch ist.
	 * 
	 * @author Flo
	 * @param input
	 *            (String)
	 * @return boolean
	 */
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

	/**
	 * Pr�ft, ob ein String zwei oder keine Nachkommastellen hat.
	 * 
	 * @author Flo
	 * @param input
	 *            (String)
	 * @return boolean
	 */
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

	/**
	 * Pr�ft, ob ein Double zwei oder keine Nachkommastellen hat.
	 * 
	 * @author Flo
	 * @param input
	 *            (Double)
	 * @return boolean
	 */
	public static boolean hasTwoOrNoDecimals(Double input) {
		return hasTwoOrNoDecimals(String.valueOf(input));
	}

	/**
	 * Pr�ft, ob ein String eine IBAN ist. Testet mit einer Library auch die
	 * Pr�fziffer, Country Codes und L�nge etc. Funktioniert auch bei mit
	 * Leerzeichen getrennten IBANs.
	 * 
	 * @author Flo
	 * @param input
	 *            (String)
	 * @return boolean
	 */
	public static boolean isIBAN(String input) {
		if (isEmpty(input)) {
			return false;
		}

		input = input.replaceAll("\\s+", "");
		return DataCheckerIBANLib.isIBAN(input) >= 2;
	}

	/**
	 * Pr�ft mittels einer Library, ob ein String eine BIC ist. Funktioniert
	 * auch bei mit Leerzeichen getrennten BICs.
	 * 
	 * @author Flo
	 * @param input
	 *            (String)
	 * @return boolean
	 */
	public static boolean isBIC(String input) {
		if (isEmpty(input)) {
			return false;
		}

		input = input.replaceAll("\\s+", "");
		return DataCheckerIBANLib.isBIC(input) >= 1;
	}

	/**
	 * Pr�ft, ob ein String eine BLZ (Bankleitzahl) ist.
	 * 
	 * @author Flo
	 * @param input
	 *            (String)
	 * @return boolean
	 */
	public static boolean isBLZ(String input) {
		if (isEmpty(input)) {
			return false;
		}

		input = input.replaceAll("\\s+", "");
		return isNumeric(input) && input.length() == 8;
	}

	/**
	 * Pr�ft, ob ein Int eine BLZ (Bankleitzahl) ist.
	 * 
	 * @author Flo
	 * @param input
	 *            (Int)
	 * @return boolean
	 */
	public static boolean isBLZ(int input) {
		return isBLZ(String.valueOf(input));
	}

	/**
	 * Pr�ft, ob ein String eine BIC oder BLZ (Bankleitzahl) ist.
	 * 
	 * @author Flo
	 * @param input
	 *            (String)
	 * @return boolean
	 */
	public static boolean isBICorBLZ(String input) {
		return isBIC(input) || isBLZ(input);
	}

	/**
	 * Pr�ft, ob ein String eine Kontonummer ist.
	 * 
	 * @author Flo
	 * @param input
	 *            (String)
	 * @return boolean
	 */
	public static boolean isKontonummer(String input) {
		if (isEmpty(input)) {
			return false;
		}

		input = input.replaceAll("\\s+", "");
		return isNumeric(input) && input.length() >= 5 && input.length() <= 10;
	}

	/**
	 * Pr�ft, ob ein Int eine Kontonummer ist.
	 * 
	 * @author Flo
	 * @param input
	 *            (Int)
	 * @return boolean
	 */
	public static boolean isKontonummer(int input) {
		return isKontonummer(String.valueOf(input));
	}

	/**
	 * Pr�ft, ob ein String eine IBAN oder Kontonummer ist.
	 * 
	 * @author Flo
	 * @param input
	 *            (String)
	 * @return boolean
	 */
	public static boolean isIBANorKontonummer(String input) {
		return isIBAN(input) || isKontonummer(input);
	}

	/**
	 * Pr�ft, ob ein String eine PLZ (Postleitzahl) ist.
	 * 
	 * @author Flo
	 * @param input
	 *            (String)
	 * @return boolean
	 */
	public static boolean isPLZ(String input) {
		if (isEmpty(input)) {
			return false;
		}

		return isNumeric(input) && input.length() == 5;
	}

	/**
	 * Pr�ft, ob ein Int eine PLZ (Postleitzahl) ist.
	 * 
	 * @author Flo
	 * @param input
	 *            (Int)
	 * @return boolean
	 */
	public static boolean isPLZ(int input) {
		return isPLZ(String.valueOf(input));
	}

	/**
	 * Pr�ft, ob ein String eine Email ist. Hierzu wird die Apache Commons
	 * Validation Library verwendet.
	 * 
	 * @author Flo
	 * @param input
	 *            (String)
	 * @return boolean
	 */
	public static boolean isEmail(String input) {
		return EmailValidator.getInstance().isValid(input);
	}
}
