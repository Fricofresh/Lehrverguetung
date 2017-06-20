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

public class WriteDocxTEST {
	
	public WriteDocxTEST(File file, String source) throws FileNotFoundException, IOException {
		try {
			XWPFDocument doc = new XWPFDocument(OPCPackage.open("src/de/dpma/" + source + ".docx"));
			
			for (XWPFParagraph p : doc.getParagraphs()) {
				List<XWPFRun> runs = p.getRuns();
				if (runs != null) {
					for (XWPFRun r : runs) {
						String text = r.getText(0);
						// TODO Formularfeld ändern
						if (text != null && text.contains("{Titel} {Vorname} {Name}")) {
							text = text.replaceFirst("{Titel} {Vorname} {Name}", "");
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
