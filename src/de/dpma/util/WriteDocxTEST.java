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

import de.dpma.model.Event;

public class WriteDocxTEST {
	
	public WriteDocxTEST(File file, String source, Event event) throws FileNotFoundException, IOException {
		// TODO alle Felder ersetzten
		try {
			XWPFDocument doc = new XWPFDocument(OPCPackage.open("src/de/dpma/" + source + ".docx"));
			
			for (XWPFParagraph p : doc.getParagraphs()) {
				List<XWPFRun> runs = p.getRuns();
				if (runs != null) {
					for (XWPFRun r : runs) {
						String text = r.getText(0);
						if (text != null && text.contains("{Titel}{Vorname} {Nachname}")) {
							text = text.replaceFirst("Titel}{Vorname} {Nachname}", event.DozentString());
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
