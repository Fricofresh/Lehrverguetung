package de.dpma.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class FormatCurrrency {
	public static String format(double input, boolean euro_design) {
		DecimalFormat df = (DecimalFormat) NumberFormat.getCurrencyInstance(java.util.Locale.GERMANY);
		DecimalFormatSymbols symbols = df.getDecimalFormatSymbols();
		if (!euro_design) {
			symbols.setCurrencySymbol("");
		}
		df.setDecimalFormatSymbols(symbols);
		return df.format(input).trim();
	}

	public static String format(String input, boolean euro_design) {
		return format(Double.parseDouble(input), euro_design);
	}
}
