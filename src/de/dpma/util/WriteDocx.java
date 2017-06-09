package de.dpma.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WriteDocx {
	public WriteDocx() throws FileNotFoundException, IOException {

		int count = 0;
		XWPFDocument document = new XWPFDocument();
		XWPFDocument docx = new XWPFDocument(new FileInputStream("src/de/dpma/test.docx"));
		XWPFWordExtractor we = new XWPFWordExtractor(docx);
		String text = we.getText();
		if (text.contains("SMS")) {
			text = text.replace("SMS", "sms");
			System.out.println(text);
		}
		char[] c = text.toCharArray();
		for (int i = 0; i < c.length; i++) {

			if (c[i] == '\n') {
				count++;
			}
		}
		System.out.println(c[0]);
		StringTokenizer st = new StringTokenizer(text, "\n");

		XWPFParagraph para = document.createParagraph();
		para.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run = para.createRun();
		run.setBold(true);
		run.setFontSize(36);
		run.setText("Apache POI works well!");

		List<XWPFParagraph> paragraphs = new ArrayList<XWPFParagraph>();
		List<XWPFRun> runs = new ArrayList<XWPFRun>();
		int k = 0;
		for (k = 0; k < count + 1; k++) {
			paragraphs.add(document.createParagraph());
		}
		k = 0;
		while (st.hasMoreElements()) {
			paragraphs.get(k).setAlignment(ParagraphAlignment.LEFT);
			paragraphs.get(k).setSpacingAfter(0);
			paragraphs.get(k).setSpacingBefore(0);
			run = paragraphs.get(k).createRun();
			run.setText(st.nextElement().toString());
			k++;
		}

		document.write(new FileOutputStream("test2.docx"));
	}
}
