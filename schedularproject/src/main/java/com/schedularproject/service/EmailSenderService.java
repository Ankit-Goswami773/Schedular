package com.schedularproject.service;

import java.io.FileNotFoundException;
import java.time.Month;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.schedularproject.entity.EmployeeDetails;
import com.schedularproject.util.PdfGenerator;

@Service
public class EmailSenderService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private PdfGenerator pdfGenerator;

	/*
	 * @Author Aniket
	 * 
	 * @Note-Front End user will send Employee ID and Month for which, they want to
	 * generate their Salary Slip
	 */
	public String sendMailWithAttachment(long empId , Month month) throws MessagingException, FileNotFoundException, DocumentException {

		EmployeeDetails details = pdfGenerator.generatePdfFile(empId, month);
		
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setFrom("aniketsoni9211@gmail.com");
		mimeMessageHelper.setTo(details.getEmailId());
		mimeMessageHelper.setText("Employee Salary Slip");
		mimeMessageHelper.setSubject("Employee Salary Slip For Month" + month);

		
		
		FileSystemResource fileSystemResource = new FileSystemResource(
				"Salary_Slip.pdf");

		mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

		mailSender.send(mimeMessage);
		
		

		return "Mail Send Successfully";

	}

}
