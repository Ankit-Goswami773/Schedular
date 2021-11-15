package com.schedularproject.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.schedularproject.common.CommonResponse;
import com.schedularproject.entity.EmployeeDetails;

@Component
public class PdfGenerator {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper mapper;

	public ByteArrayResource generatePdfFile(Month month,CommonResponse response){

		List<Map<String, Object>> list = null;
		Document document = null;
		ByteArrayOutputStream outputStream = null;
		byte[] bytes = null;
		ByteArrayResource resource = null;

		try(ByteArrayOutputStream stream = new ByteArrayOutputStream()){

			list = mapper.convertValue(response.getData(), List.class);

			Object emp = list.get(0).get("employeeDetails");

			EmployeeDetails employeeDetail = mapper.convertValue(emp, EmployeeDetails.class);

			document = new Document();

			outputStream = new ByteArrayOutputStream();

			PdfWriter.getInstance(document, outputStream);

			document.open();
			document.addTitle("Employee salary slip");

			Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, BaseColor.DARK_GRAY);
			Paragraph paragraph = new Paragraph("Salary Slip", font);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);

			document.add(new Paragraph("\n"));
			document.add(new LineSeparator());
			Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, BaseColor.BLACK);

			Paragraph paragraph2 = new Paragraph(
					"Mr." + employeeDetail.getFirstName() + " " + employeeDetail.getLastName() + "," + "\n"
							+ "We are pleased inform that your salary has been revised w.e.f. " + LocalDate.now()
							+ " as under:",
					font2);
			document.add(paragraph2);

			document.add(new Paragraph("\n"));

			String[] colNames = { "", "MONTH", "SALARY" };

			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100);

			for (String colName : colNames) {
				PdfPCell headerCell = new PdfPCell();
				headerCell.setPhrase(new Phrase(colName, font2));
				headerCell.setBackgroundColor(BaseColor.WHITE);
				headerCell.setBorderWidth(0);
				table.addCell(headerCell);

			}

			float basicSalary = (float) employeeDetail.getBasicSalary();
			float oneDaySalary = basicSalary / 30;

			PdfPCell dataCell = new PdfPCell();

			Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, BaseColor.BLACK);

			dataCell.setPhrase(new Phrase("Basic Salary", dataFont));
			table.addCell(dataCell);

			dataCell.setPhrase(new Phrase(month.toString(), dataFont));
			table.addCell(dataCell);

			dataCell.setPhrase(new Phrase(String.valueOf(basicSalary), dataFont));
			table.addCell(dataCell);

			dataCell.setPhrase(new Phrase("One Day Salary", dataFont));
			table.addCell(dataCell);

			dataCell.setPhrase(new Phrase("", dataFont));
			table.addCell(dataCell);

			dataCell.setPhrase(new Phrase(String.valueOf(oneDaySalary), dataFont));
			table.addCell(dataCell);

			dataCell.setPhrase(new Phrase("This Month Salary", dataFont));
			table.addCell(dataCell);

			dataCell.setPhrase(new Phrase(oneDaySalary + "*" + list.size(), dataFont));
			table.addCell(dataCell);

			dataCell.setPhrase(new Phrase(String.valueOf(oneDaySalary * list.size()), dataFont));
			table.addCell(dataCell);

			document.add(table);

			document.add(new Paragraph("\n"));
			document.add(new Paragraph("\n"));

			Paragraph paragraph3 = new Paragraph("Your faithfully," + "\n" + "5 EXCEPTIONS SOFTWARE SOLUTION", font2);
			document.add(paragraph3);

			document.add(new Paragraph("\n"));
			document.add(new Paragraph("\n"));

			Paragraph paragraph4 = new Paragraph("(Director's Seal and Sign)", font2);
			document.add(paragraph4);

			document.close();

			bytes = outputStream.toByteArray();

			resource = new ByteArrayResource(bytes);

		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
		return resource;
	}

}
