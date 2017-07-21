package de.dpma.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class FormatCurrrency {
	
	/**
	 * Die Zahl in einen Eurobetrag mit 2 Nachkommastellen umwandeln, z.B. 11,1
	 * in 11,10 €.
	 * 
	 * @author Flo
	 * @author Kenneth Böhmer
	 * @param input
	 *            (Double)
	 * @param euro_design
	 *            true = "€", false = ""
	 * @return Eurobetrag mit 2 Nachkommastellen
	 */
	public static String format(double input, boolean euro_design) {
		
		DecimalFormat df = (DecimalFormat) NumberFormat.getCurrencyInstance(java.util.Locale.GERMANY);
		DecimalFormatSymbols symbols = df.getDecimalFormatSymbols();
		if (!euro_design) {
			symbols.setCurrencySymbol("");
		}
		df.setDecimalFormatSymbols(symbols);
		return df.format(input).trim();
	}
	
	/**
	 * String in schönen Eurobetrag umwandeln (z.B. 11,1 in 11,10 €)
	 * 
	 * @author Flo
	 * @param input
	 *            (String)
	 * @param euro_design
	 *            (Mit Eurozeichen oder nicht, boolean)
	 * @return Schöner Eurobetrag
	 */
	public static String format(String input, boolean euro_design) {
		
		return format(Double.parseDouble(input), euro_design);
	}
}
