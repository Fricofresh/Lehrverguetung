package de.dpma.util;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataCheckerIBANLib {
	final static private Set ISO_COUNTRY_CODES = new HashSet();

	final static public int NOT_AN_IBAN = 0;
	final static public int BAD_IBAN_CHECKNUM = 1;
	final static public int GOOD_IBAN = 2;
	final static public int EU_IBAN = 3;

	final static public int NOT_A_BIC = 0;
	final static public int GOOD_BIC = 1;
	final static public int EU_BIC = 2;

	final static private BigInteger BIG_INT_97 = new BigInteger("97");
	final static private BigInteger BIG_INT_1 = new BigInteger("1");
	final static private Pattern IBAN_PAT = Pattern.compile(("([A-Z]{2})([0-9]{2})([\\w]{1,30})"));

	static {
		String[] cCodes = Locale.getISOCountries();

		for (int i = 0; i < cCodes.length; i++) {
			ISO_COUNTRY_CODES.add(cCodes[i]);
		}
	}

	/**
	 * checks if the given IBAN seems to be an iban (without countrycode-check)
	 * and validates the checknumber this routine is a bit cheap but also fast
	 * since no country-codes are checked
	 *
	 * @param iban
	 * @return (int) NOT_AN_IBAN , if given String does not matche basic
	 *         IBAN-format BAD_IBAN_CHECKNUM , if basic IBAN-format is OK but
	 *         not the checknumber GOOD_IBAN , if basic IBAN-format and
	 *         hecknumber are OK
	 */
	static public int isIBANCheckNumberOK(String iban) {
		int rc;
		Matcher matcher = IBAN_PAT.matcher(iban);

		if (matcher.matches()) {
			StringBuffer ibanBuff = new StringBuffer(34);
			StringBuffer valueBuff = new StringBuffer(34);

			ibanBuff.append(matcher.group(3));
			ibanBuff.append(matcher.group(1));
			ibanBuff.append(matcher.group(2));

			for (int i = 0; i < ibanBuff.length(); i++) {
				int v;
				char c = ibanBuff.charAt(i);
				if ((c >= 'A' && c <= 'Z')) {
					v = c - 'A' + 10;
					valueBuff.append(v);
				} else if ((c >= 'a' && c <= 'z')) {
					v = c - 'a' + 10;
					valueBuff.append(v);
				} else {
					valueBuff.append(c);
				}

			}

			BigInteger val = new BigInteger(valueBuff.toString());

			rc = val.divideAndRemainder(BIG_INT_97)[1].equals(BIG_INT_1) ? GOOD_IBAN : BAD_IBAN_CHECKNUM;

		} else {
			rc = NOT_AN_IBAN;
		}

		return rc;
	}

	/**
	 * checks if the given IBAN seems to be an iban with countrycode-check and
	 * validates the checknumber if this validation is allright it is checked
	 * whether the country-code is sorts to european regulations payment area or
	 * not
	 *
	 * the higher the returned value the better the IBAN so if you just want to
	 * know if the IBAN is allright but don't care about the european-area you
	 * just do something like this: <code>
	 *      if (BankingNumberValidation.isIBAN(iban) >= GOOD_IBAN)
	 *      {
	 *              ... do something
	 *      }
	 *  </code>
	 *
	 * @param iban
	 * @return (int) NOT_AN_IBAN , if given String does not matche basic
	 *         IBAN-format or countrycod is unkown BAD_IBAN_CHECKNUM , if basic
	 *         IBAN-format and countrycode are OK but not the checknumber
	 *         GOOD_IBAN , if basic IBAN-format, countrycode and checknumber are
	 *         OK EU_IBAN , if basic IBAN-format, countrycode, checknumber are
	 *         OK and the countrycode is european
	 *
	 * @see de.cmk.util.banking.EUCountryCodes.isIbanEUCountry
	 */
	static public int isIBAN(String iban) {
		int rc;
		Matcher matcher = IBAN_PAT.matcher(iban);

		if (matcher.matches() && ISO_COUNTRY_CODES.contains(iban.substring(0, 2))) {
			StringBuffer ibanBuff = new StringBuffer(34);
			StringBuffer valueBuff = new StringBuffer(34);

			ibanBuff.append(matcher.group(3));
			ibanBuff.append(matcher.group(1));
			ibanBuff.append(matcher.group(2));

			for (int i = 0; i < ibanBuff.length(); i++) {
				int v;
				char c = ibanBuff.charAt(i);
				if ((c >= 'A' && c <= 'Z')) {
					v = c - 'A' + 10;
					valueBuff.append(v);
				} else if ((c >= 'a' && c <= 'z')) {
					v = c - 'a' + 10;
					valueBuff.append(v);
				} else {
					valueBuff.append(c);
				}

			}

			BigInteger val = new BigInteger(valueBuff.toString());

			if (val.divideAndRemainder(BIG_INT_97)[1].equals(BIG_INT_1)) {
				if (DataCheckerIBANCountryCodesLib.isIbanEUCountry(iban)) {
					rc = EU_IBAN;
				} else {
					rc = GOOD_IBAN;
				}
			} else {
				rc = BAD_IBAN_CHECKNUM;
			}

		} else {
			rc = NOT_AN_IBAN;
		}

		return rc;
	}

	/**
	 * very simple formal check on given bic cheks if the countrycode is correct
	 * or even european assumes thta a bic is 11 or 8 characters long and an
	 * that the 4. and 5. offset is the country-code
	 * 
	 * @param bic
	 * @return
	 */
	static public int isBIC(String bic) {
		int rc;
		if (bic.length() == 11 || bic.length() == 8) {
			if (DataCheckerIBANCountryCodesLib.isBicEUCountry(bic)) {
				rc = EU_BIC;
			} else if (ISO_COUNTRY_CODES.contains(bic)) {
				rc = GOOD_BIC;
			} else {
				rc = NOT_A_BIC;
			}
		} else {
			rc = NOT_A_BIC;
		}

		return rc;
	}
}