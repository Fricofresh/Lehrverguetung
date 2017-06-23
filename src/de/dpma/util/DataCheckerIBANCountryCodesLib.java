package de.dpma.util;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public class DataCheckerIBANCountryCodesLib {
	static private Set m_ibanCountryCodes = new HashSet();
	static private Set m_bicCountryCodes = new HashSet();

	static {
		m_ibanCountryCodes.add("AT");
		m_ibanCountryCodes.add("BE");
		m_ibanCountryCodes.add("CY");
		m_ibanCountryCodes.add("CZ");
		m_ibanCountryCodes.add("DE");
		m_ibanCountryCodes.add("DK");
		m_ibanCountryCodes.add("EE");
		m_ibanCountryCodes.add("ES");
		m_ibanCountryCodes.add("FI");
		m_ibanCountryCodes.add("FR");
		m_ibanCountryCodes.add("GB");
		m_ibanCountryCodes.add("GI");
		m_ibanCountryCodes.add("GR");
		m_ibanCountryCodes.add("HU");
		m_ibanCountryCodes.add("IE");
		m_ibanCountryCodes.add("IS");
		m_ibanCountryCodes.add("IT");
		m_ibanCountryCodes.add("LI");
		m_ibanCountryCodes.add("LT");
		m_ibanCountryCodes.add("LU");
		m_ibanCountryCodes.add("LV");
		m_ibanCountryCodes.add("MT");
		m_ibanCountryCodes.add("NL");
		m_ibanCountryCodes.add("NO");
		m_ibanCountryCodes.add("PL");
		m_ibanCountryCodes.add("PT");
		m_ibanCountryCodes.add("SE");
		m_ibanCountryCodes.add("SI");
		m_ibanCountryCodes.add("SK");

		m_bicCountryCodes.add("AT");
		m_bicCountryCodes.add("BE");
		m_bicCountryCodes.add("CY");
		m_bicCountryCodes.add("CZ");
		m_bicCountryCodes.add("DE");
		m_bicCountryCodes.add("DK");
		m_bicCountryCodes.add("EE");
		m_bicCountryCodes.add("ES");
		m_bicCountryCodes.add("FI");
		m_bicCountryCodes.add("FR");
		m_bicCountryCodes.add("GB");
		m_bicCountryCodes.add("IE");
		m_bicCountryCodes.add("GF");
		m_bicCountryCodes.add("GI");
		m_bicCountryCodes.add("GP");
		m_bicCountryCodes.add("GR");
		m_bicCountryCodes.add("HU");
		m_bicCountryCodes.add("IE");
		m_bicCountryCodes.add("IS");
		m_bicCountryCodes.add("IT");
		m_bicCountryCodes.add("LI");
		m_bicCountryCodes.add("LT");
		m_bicCountryCodes.add("LU");
		m_bicCountryCodes.add("LV");
		m_bicCountryCodes.add("MQ");
		m_bicCountryCodes.add("MT");
		m_bicCountryCodes.add("NL");
		m_bicCountryCodes.add("NO");
		m_bicCountryCodes.add("PL");
		m_bicCountryCodes.add("PT");
		m_bicCountryCodes.add("RE");
		m_bicCountryCodes.add("SE");
		m_bicCountryCodes.add("SI");
		m_bicCountryCodes.add("SK");
	}

	static public boolean isBicEUCountry(String bic) {
		return m_bicCountryCodes.contains(bic.substring(4, 6));
	}

	static public boolean isIbanEUCountry(String iban) {
		return m_ibanCountryCodes.contains(iban.substring(0, 2));
	}
}