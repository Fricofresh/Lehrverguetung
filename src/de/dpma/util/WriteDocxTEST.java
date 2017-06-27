package de.dpma.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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

public class WriteDocxTEST {
	
	public WriteDocxTEST(File file, String source, Event event) throws FileNotFoundException, IOException {
		// TODO alle Felder ersetzten
		try {
			
			XWPFDocument doc = new XWPFDocument(OPCPackage.open("src/de/dpma/" + source + ".docx"));
			Dozent dozent = MainPageController.dozentDAO.selectDozent(event.getId_dozent());
			Stundenlohn stdlohn = MainPageController.stundenlohnDAO.selectStundenlohn(event.getId_euro_std());
			ConfigIniUtil confini = new ConfigIniUtil();
			
			for (XWPFTable tbl : doc.getTables()) {
				for (XWPFTableRow row : tbl.getRows()) {
					for (XWPFTableCell cell : row.getTableCells()) {
						for (XWPFParagraph p : cell.getParagraphs()) {
							for (XWPFRun r : p.getRuns()) {
								String text = r.getText(0);
								if (text != null && text.contains("{Titel}")) {
									// BUG bei Titel wird der Leerzeichen
									// entfernt
									text = text.replace("{Titel}", dozent.getTitel()) + " ";
									r.setText(text, 0);
								}
								if (text != null && text.contains("{Vorname}")) {
									text = text.replace("{Vorname}", dozent.getVorname());
									r.setText(text, 0);
								}
								if (text != null && text.contains("{Name}")) {
									text = text.replace("{Name}", dozent.getName());
									r.setText(text, 0);
								}
								if (text != null && text.contains("{Std_lohn}")) {
									text = text.replace("{Std_lohn}", String.valueOf(stdlohn.getLohn()));
									r.setText(text, 0);
								}
								if (text != null && text.contains("{Anrede2}")) {
									text = text.replace("{Anrede2}", dozent.getAnrede());
									r.setText(text, 0);
								}
								if (text != null && text.contains("{Anrede1}")) {
									if (dozent.getAnrede().equals("Herr")) {
										text = text.replace("{Anrede1}", dozent.getAnrede() + "n");
									}
									else {
										text = text.replace("{Anrede1}", dozent.getAnrede());
									}
									r.setText(text, 0);
								}
								if (text != null && text.contains("{Straﬂe}")) {
									text = text.replace("{Straﬂe}", dozent.getStrasse());
									r.setText(text, 0);
								}
								if (text != null && text.contains("{PLZ}")) {
									text = text.replace("{PLZ}", dozent.getPLZ());
									r.setText(text, 0);
								}
								if (text != null && text.contains("{Vfg}")) {
									text = text.replace("{Vfg}", event.getVfg());
									r.setText(text, 0);
								}
								if (text != null && text.contains("{Vortrag}")) {
									text = text.replace("{Vortrag}", event.getSchulart());
									r.setText(text, 0);
								}
								if (text != null && text.contains("{Date_start}")) {
									text = text.replace("{Date_start}", event.getDate_start());
									r.setText(text, 0);
								}
								if (text != null && text.contains("{Date_end}")) {
									text = text.replace("{Date_end}", event.getDate_end());
									r.setText(text, 0);
								}
								if (text != null && text.contains("{Sdt_anzahl}")) {
									text = text.replace("{Sdt_anzahl}", String.valueOf(event.getStdzahl()));
									r.setText(text, 0);
								}
								if (text != null && text.contains("{Betrag}")) {
									text = text.replace("{Betrag}",
											String.valueOf((stdlohn.getLohn() * event.getStdzahl())));
								}
								if (text != null && text.contains("{Betrag_ABC}")) {
									text = text.replace("{Betrag_ABC}",
											NumberToText.NumberToText((stdlohn.getLohn() * event.getStdzahl())));
									r.setText(text, 0);
								}
								if (text != null && text.contains("{IBAN}")) {
									text = text.replace("{IBAN}", dozent.getIBAN());
									r.setText(text, 0);
								}
								if (text != null && text.contains("{Bank}")) {
									text = text.replace("{Bank}", dozent.getBank());
									r.setText(text, 0);
								}
								if (text != null && text.contains("{BIC}")) {
									text = text.replace("{BIC}", dozent.getBLZ());
									r.setText(text, 0);
								}
								if (text != null && text.contains("{Ort}")) {
									text = text.replace("{Ort}", dozent.getOrt());
									r.setText(text, 0);
								}
								
								if (text != null && text.contains("{Bearbeiter}")) {
									text = text.replace("{Bearbeiter}",
											confini.getVorname() + " " + confini.getNachname());
									r.setText(text, 0);
								}
								if (text != null && text.contains("{Durchwahl}")) {
									text = text.replace("{Durchwahl}", confini.getDurchwahl());
									r.setText(text, 0);
								}
								if (text != null && text.contains("{Dienstort}")) {
									text = text.replace("{Dienstort}", confini.getDienstort());
									r.setText(text, 0);
								}
								if (text != null && text.contains("{email}")) {
									text = text.replace("{email}", confini.getEmail());
									r.setText(text, 0);
								}
							}
						}
					}
				}
			}
			for (XWPFParagraph p : doc.getParagraphs()) {
				List<XWPFRun> runs = p.getRuns();
				if (runs != null) {
					for (XWPFRun r : runs) {
						String text = r.getText(0);
						if (text != null && text.contains("{Titel}")) {
							// BUG bei Titel wird der Leerzeichen entfernt
							text = text.replace("{Titel}", dozent.getTitel()) + " ";
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Vorname}")) {
							text = text.replace("{Vorname}", dozent.getVorname());
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Name}")) {
							text = text.replace("{Name}", dozent.getName());
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Std_lohn}")) {
							text = text.replace("{Nachname}", "" + stdlohn.getLohn());
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Anrede1}")) {
							text = text.replace("{Anrede1}", dozent.getAnrede() + "n");
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Anrede2}")) {
							text = text.replace("{Anrede2}", dozent.getAnrede());
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Anrede1}")) {
							if (dozent.getAnrede().equals("Herr")) {
								text = text.replace("{Anrede1}", dozent.getAnrede() + "n");
							}
							else {
								text = text.replace("{Anrede1}", dozent.getAnrede());
							}
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Straﬂe}")) {
							text = text.replace("{Straﬂe}", dozent.getStrasse());
							r.setText(text, 0);
						}
						if (text != null && text.contains("{PLZ}")) {
							text = text.replace("{PLZ}", dozent.getPLZ());
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Vfg}")) {
							text = text.replace("{Vfg}", event.getVfg());
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Vortrag}")) {
							String vortrag;
							if (event.getVortrg_mode() == 1) {
								vortrag = "Schulung";
							}
							else {
								vortrag = "Sonstiges";
							}
							text = text.replace("{Vortrag}", vortrag);
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Date_start}")) {
							text = text.replace("{Date_start}", event.getDate_start());
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Date_end}")) {
							text = text.replace("{Date_end}", event.getDate_end());
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Sdt_anzahl}")) {
							text = text.replace("{Sdt_anzahl}", String.valueOf(event.getStdzahl()));
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Betrag}")) {
							text = text.replace("{Betrag}", String.valueOf((stdlohn.getLohn() + event.getStdzahl())));
						}
						if (text != null && text.contains("{Betrag_ABC}")) {
							text = text.replace("{Betrag_ABC}",
									NumberToText.NumberToText((stdlohn.getLohn() + event.getStdzahl())));
							r.setText(text, 0);
						}
						if (text != null && text.contains("{IBAN}")) {
							text = text.replace("{IBAN}", dozent.getIBAN());
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Bank}")) {
							text = text.replace("{Bank}", dozent.getBank());
							r.setText(text, 0);
						}
						if (text != null && text.contains("{BIC}")) {
							text = text.replace("{BIC}", dozent.getBLZ());
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Ort}")) {
							text = text.replace("{Ort}", dozent.getOrt());
							r.setText(text, 0);
						}
						
						if (text != null && text.contains("{Bearbeiter}")) {
							text = text.replace("{Bearbeiter}", confini.getVorname() + confini.getNachname());
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Durchwahl}")) {
							text = text.replace("{Durchwahl}", confini.getDurchwahl());
							r.setText(text, 0);
						}
						if (text != null && text.contains("{Dienstort}")) {
							text = text.replace("{Dienstort}", confini.getDienstort());
							r.setText(text, 0);
						}
						if (text != null && text.contains("{email}")) {
							text = text.replace("{email}", confini.getEmail());
							r.setText(text, 0);
						}
					}
				}
			}
			
			doc.write(new FileOutputStream(file));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
