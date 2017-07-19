package de.dpma.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {

	/**
	 * Wandel einen Derby DATETIME String in ein Datum im Format 19.07.2017 um
	 * 
	 * @author Flo
	 * @param oldstring
	 * @return Schönes Datum
	 * @throws ParseException
	 */
	public static String format(String oldstring) throws ParseException {

		SimpleDateFormat sourceDateFormat = new SimpleDateFormat("yyyy-MM-DD");

		Date date = sourceDateFormat.parse(oldstring);

		SimpleDateFormat targetDateFormat = new SimpleDateFormat("dd.MM.yyyy");

		return targetDateFormat.format(date);
	}

}
