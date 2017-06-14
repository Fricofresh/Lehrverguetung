package de.dpma.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import de.dpma.MainApp;
import de.dpma.dao.DozentDAO;
import de.dpma.dao.EventDAO;
import de.dpma.dao.StundenlohnDAO;
import de.dpma.model.Dozent;
import de.dpma.model.Event;
import de.dpma.model.Stundenlohn;

public class GenerateExcelData {
	
	@SuppressWarnings("deprecation")
	public GenerateExcelData(File file) {
		try {
			String[] headers = new String[] {"Anrede", "Titel", "Vorname", "Name", "Strasse", "PLZ", "Ort",
					"Aktenzeichen", "Schulungsart", "Verfügung", "Vortragsart", "Datum", "Euro pro Stunde",
					"Stundenzahl", "Betrag", "Betrag in Worten", "Kontonummer", "Bank", "BLZ"};
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Dozentendaten");
			DataFormat format = workbook.createDataFormat();
			CellStyle style = workbook.createCellStyle();
			CellStyle style2 = workbook.createCellStyle();
			
			style.setAlignment(CellStyle.ALIGN_CENTER);
			Font font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			
			XSSFRow rowhead = sheet.createRow((short) 0);
			for (int i = 0; i < headers.length; i++) {
				Cell cell = rowhead.createCell(i);
				cell.setCellValue(headers[i]);
				cell.setCellStyle(style);
			}
			
			EventDAO eventDao = new EventDAO(MainApp.dbcon.getConnection());
			DozentDAO dozentDao = new DozentDAO(MainApp.dbcon.getConnection());
			StundenlohnDAO stundenlohnDao = new StundenlohnDAO(MainApp.dbcon.getConnection());
			
			List<Event> events = eventDao.selectAllEvents();
			
			for (int i = 0; i < events.size(); i++) {
				XSSFRow row = sheet.createRow((short) (i + 1));
				
				Dozent dozent = dozentDao.selectDozent(events.get(i).getId_dozent());
				Stundenlohn stundenlohn = stundenlohnDao.selectStundenlohn(events.get(i).getId_euro_std());
				
				row.createCell(0).setCellValue(dozent.getAnrede());
				row.createCell(1).setCellValue(dozent.getTitel());
				row.createCell(2).setCellValue(dozent.getVorname());
				row.createCell(3).setCellValue(dozent.getName());
				row.createCell(4).setCellValue(dozent.getStrasse());
				row.createCell(5).setCellValue(dozent.getPLZ());
				row.createCell(6).setCellValue(dozent.getOrt());
				row.createCell(7).setCellValue(events.get(i).getAktenz());
				row.createCell(8).setCellValue(events.get(i).getSchulart());
				row.createCell(9).setCellValue(FormatDate(events.get(i).getVfg()));
				row.createCell(10).setCellValue((events.get(i).getVortrg_mode() == 0 ? "Sonstiges" : "Schulung"));
				row.createCell(11).setCellValue(
						FormatDate(events.get(i).getDate_start()) + " - " + FormatDate(events.get(i).getDate_end()));
				
				Cell cell2 = row.createCell(12);
				cell2.setCellValue(stundenlohn.getLohn());
				style2.setAlignment(CellStyle.ALIGN_RIGHT);
				cell2.setCellStyle(style2);
				
				Cell cell3 = row.createCell(13);
				cell3.setCellValue(events.get(i).getStdzahl());
				style2.setAlignment(CellStyle.ALIGN_RIGHT);
				cell3.setCellStyle(style2);
				
				row.createCell(14).setCellValue(stundenlohn.getLohn() * events.get(i).getStdzahl());
				row.createCell(15)
						.setCellValue(NumberToText.NumberToText(stundenlohn.getLohn() * events.get(i).getStdzahl()));
				row.createCell(16).setCellValue(dozent.getIBAN());
				row.createCell(17).setCellValue(dozent.getBank());
				row.createCell(18).setCellValue(dozent.getBLZ());
			}
			
			for (int i = 0; i < headers.length; i++) {
				sheet.autoSizeColumn(i);
			}
			
			FileOutputStream fileOut = new FileOutputStream(file);
			// SafeFile(file)
			workbook.write(fileOut);
			fileOut.close();
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	private String FormatDate(String oldstring) throws ParseException {
		
		SimpleDateFormat sourceDateFormat = new SimpleDateFormat("yyyy-MM-DD");
		
		Date date = sourceDateFormat.parse(oldstring);
		
		SimpleDateFormat targetDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		
		return targetDateFormat.format(date);
	}
	
}
