package de.dpma.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import de.dpma.model.Dozent;
import de.dpma.model.Event;
import de.dpma.model.Stundenlohn;
import de.dpma.view.MainPageController;

public class WriteDocx {

	/**
	 * Mithilfe der Replace Funktion Variablen im Word Dokument replacen
	 * 
	 * @author Kenneth + Flo
	 * @param file
	 * @param source
	 * @param event
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public WriteDocx(File file, String source, Event event) throws FileNotFoundException, IOException {

		try {

			XWPFDocument doc = new XWPFDocument(OPCPackage.open("Vorlagen/" + source + ".docx"));
			Dozent dozent = MainPageController.dozentDAO.selectDozent(event.getId_dozent());
			Stundenlohn stdlohn = MainPageController.stundenlohnDAO.selectStundenlohn(event.getId_euro_std());
			ConfigIniUtil confini = new ConfigIniUtil();

			if (source == "Auszahlung") {
				for (XWPFParagraph p : doc.getParagraphs()) {
					Map<String, String> rpl = new HashMap<String, String>();
					rpl.put("Name", dozent.getName());
					rpl.put("Vorname", dozent.getVorname());
					if (!dozent.getTitel().isEmpty()) {
						rpl.put("Titel", dozent.getTitel() + " ");
					}
					rpl.put("Std_lohn", FormatCurrrency.format(String.valueOf(stdlohn.getLohn()), false));
					rpl.put("Anrede2", dozent.getAnrede());
					if (dozent.getAnrede().equals("Herr")) {
						rpl.put("Anrede1", dozent.getAnrede() + "n");
					} else {
						rpl.put("Anrede1", dozent.getAnrede());
					}
					rpl.put("Straße", dozent.getStrasse());
					rpl.put("PLZ", dozent.getPLZ());
					rpl.put("Vfg", FormatDate.format(event.getVfg()));
					rpl.put("Vortrag", event.getSchulart());
					rpl.put("Date_start", FormatDate.format(event.getDate_start()));
					rpl.put("Date_end", FormatDate.format(event.getDate_end()));
					rpl.put("Sdt_anzahl", String.valueOf(event.getStdzahl()));
					rpl.put("Betrag",
							FormatCurrrency.format(String.valueOf((stdlohn.getLohn() * event.getStdzahl())), false));
					rpl.put("Betrag_ABC", NumberToText.NumberToText((stdlohn.getLohn() * event.getStdzahl())));
					rpl.put("IBAN", dozent.getIBAN());
					rpl.put("Bank", dozent.getBank());
					rpl.put("BIC", dozent.getBLZ());
					rpl.put("Ort", dozent.getOrt());
					rpl.put("Bearbeiter", confini.getVorname() + " " + confini.getNachname());
					rpl.put("Durchwahl", confini.getDurchwahl());
					rpl.put("Dienstort", confini.getDienstort());
					rpl.put("email", confini.getEmail());
					replace(p, rpl);
				}
				for (XWPFTable tbl : doc.getTables()) {
					for (XWPFTableRow row : tbl.getRows()) {
						for (XWPFTableCell cell : row.getTableCells()) {
							for (XWPFParagraph p : cell.getParagraphs()) {
								Map<String, String> rpl = new HashMap<String, String>();
								rpl.put("Name", dozent.getName());
								rpl.put("Vorname", dozent.getVorname());
								if (!dozent.getTitel().isEmpty()) {
									rpl.put("Titel", dozent.getTitel() + " ");
								}
								rpl.put("Std_lohn", FormatCurrrency.format(String.valueOf(stdlohn.getLohn()), false));
								rpl.put("Anrede2", dozent.getAnrede());
								if (dozent.getAnrede().equals("Herr")) {
									rpl.put("Anrede1", dozent.getAnrede() + "n");
								} else {
									rpl.put("Anrede1", dozent.getAnrede());
								}
								rpl.put("Straße", dozent.getStrasse());
								rpl.put("PLZ", dozent.getPLZ());
								rpl.put("Vfg", FormatDate.format(event.getVfg()));
								rpl.put("Vortrag", event.getSchulart());
								rpl.put("Date_start", FormatDate.format(event.getDate_start()));
								rpl.put("Date_end", FormatDate.format(event.getDate_end()));
								rpl.put("Sdt_anzahl", String.valueOf(event.getStdzahl()));
								rpl.put("Betrag", FormatCurrrency
										.format(String.valueOf((stdlohn.getLohn() * event.getStdzahl())), false));
								rpl.put("Betrag_ABC",
										NumberToText.NumberToText((stdlohn.getLohn() * event.getStdzahl())));
								rpl.put("IBAN", dozent.getIBAN());
								rpl.put("Bank", dozent.getBank());
								rpl.put("BIC", dozent.getBLZ());
								rpl.put("Ort", dozent.getOrt());
								rpl.put("Bearbeiter", confini.getVorname() + " " + confini.getNachname());
								rpl.put("Durchwahl", confini.getDurchwahl());
								rpl.put("Dienstort", confini.getDienstort());
								rpl.put("email", confini.getEmail());
								replace(p, rpl);
							}
						}
					}
				}
			} else {
				for (XWPFParagraph p : doc.getParagraphs()) {
					Map<String, String> rpl = new HashMap<String, String>();
					rpl.put("Name", dozent.getName());
					rpl.put("Betrag",
							FormatCurrrency.format(String.valueOf((stdlohn.getLohn() * event.getStdzahl())), false));
					rpl.put("Bearbeiter", confini.getVorname() + " " + confini.getNachname());
					replace(p, rpl);
				}
				for (XWPFTable tbl : doc.getTables()) {
					for (XWPFTableRow row : tbl.getRows()) {
						for (XWPFTableCell cell : row.getTableCells()) {
							for (XWPFParagraph p : cell.getParagraphs()) {
								Map<String, String> rpl = new HashMap<String, String>();
								rpl.put("Name", dozent.getName());
								rpl.put("Betrag", FormatCurrrency
										.format(String.valueOf((stdlohn.getLohn() * event.getStdzahl())), false));
								rpl.put("Bearbeiter", confini.getVorname() + " " + confini.getNachname());
								replace(p, rpl);
							}
						}
					}
				}
			}

			doc.write(new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Text im Word Dokument ersetzen. Besonderheit: Sucht über mehrere Runs
	 * gemeinsam. Variablen müssen in der Form ${variablenname} sein.
	 * 
	 * @author Stackoverflow Dude
	 * @param p
	 * @param data
	 */
	private void replace(XWPFParagraph p, Map<String, String> data) {
		String pText = p.getText(); // complete paragraph as string
		if (pText.contains("${")) { // if paragraph does not include our
									// pattern, ignore
			TreeMap<Integer, XWPFRun> posRuns = getPosToRuns(p);
			Pattern pat = Pattern.compile("\\$\\{(.+?)\\}");
			Matcher m = pat.matcher(pText);
			while (m.find()) { // for all patterns in the paragraph
				String g = m.group(1); // extract key start and end pos
				int s = m.start(1);
				int e = m.end(1);
				String key = g;
				String x = data.get(key);
				if (x == null)
					x = "";
				SortedMap<Integer, XWPFRun> range = posRuns.subMap(s - 2, true, e + 1, true); // get
																								// runs
																								// which
																								// contain
																								// the
																								// pattern
				boolean found1 = false; // found $
				boolean found2 = false; // found {
				boolean found3 = false; // found }
				XWPFRun prevRun = null; // previous run handled in the loop
				XWPFRun found2Run = null; // run in which { was found
				int found2Pos = -1; // pos of { within above run
				for (XWPFRun r : range.values()) {
					if (r == prevRun)
						continue; // this run has already been handled
					if (found3)
						break; // done working on current key pattern
					prevRun = r;
					for (int k = 0;; k++) { // iterate over texts of run r
						if (found3)
							break;
						String txt = null;
						try {
							txt = r.getText(k); // note: should return null, but
												// throws exception if the text
												// does not exist
						} catch (Exception ex) {

						}
						if (txt == null)
							break; // no more texts in the run, exit loop
						if (txt.contains("$") && !found1) { // found $, replace
															// it with value
															// from data map
							txt = txt.replaceFirst("\\$", x);
							found1 = true;
						}
						if (txt.contains("{") && !found2 && found1) {
							found2Run = r; // found { replace it with empty
											// string and remember location
							found2Pos = txt.indexOf('{');
							txt = txt.replaceFirst("\\{", "");
							found2 = true;
						}
						if (found1 && found2 && !found3) { // find } and set all
															// chars between {
															// and } to blank
							if (txt.contains("}")) {
								if (r == found2Run) { // complete pattern was
														// within a single run
									txt = txt.substring(0, found2Pos) + txt.substring(txt.indexOf('}'));
								} else // pattern spread across multiple runs
									txt = txt.substring(txt.indexOf('}'));
							} else if (r == found2Run) // same run as { but no
														// }, remove all text
														// starting at {
								txt = txt.substring(0, found2Pos);
							else
								txt = ""; // run between { and }, set text to
											// blank
						}
						if (txt.contains("}") && !found3) {
							txt = txt.replaceFirst("\\}", "");
							found3 = true;
						}
						r.setText(txt, k);
					}
				}
			}

		}

	}

	/**
	 * Helper-Funktion für Replace
	 * 
	 * @author Stackoverflow Dude
	 * @param paragraph
	 * @return map
	 */
	private TreeMap<Integer, XWPFRun> getPosToRuns(XWPFParagraph paragraph) {
		int pos = 0;
		TreeMap<Integer, XWPFRun> map = new TreeMap<Integer, XWPFRun>();
		for (XWPFRun run : paragraph.getRuns()) {
			String runText = run.text();
			if (runText != null && runText.length() > 0) {
				for (int i = 0; i < runText.length(); i++) {
					map.put(pos + i, run);
				}
				pos += runText.length();
			}

		}
		return map;
	}
}
