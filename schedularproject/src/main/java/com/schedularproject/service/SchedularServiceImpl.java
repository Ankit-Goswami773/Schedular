package com.schedularproject.service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.schedularproject.common.CommonResponse;
import com.schedularproject.entity.EmployeeDetails;
import com.schedularproject.util.PdfGenerator;

@Service
public class SchedularServiceImpl implements SchedularService {

	@Autowired
	public RestTemplate restTemplate;

	@Autowired
	private PdfGenerator pdfGenerator;

	@Autowired
	private ObjectMapper mapper;

	// This Schedular Method is used for Automatically send the mail to every
	// employee on 5th working day of each month.
	@Scheduled(cron = "0 0 8 5 * FRI")
	@Scheduled(cron = "0 0 8 6 * FRI")
	@Scheduled(cron = "0 0 8 7 * MON-FRI")
	public String autoGeneratePdfScheduler() throws FileNotFoundException, MessagingException, DocumentException {
		List<Map<String, Object>> emp = null;

		String url = "http://localhost:8095/employee/getAllEmployee";

		CommonResponse response = restTemplate.getForObject(url, CommonResponse.class);

		if (response != null)
			emp = mapper.convertValue(response.getData(), List.class);

		LocalDate date = LocalDate.now();

		for (Map<String, Object> e : emp) {
			Object id = e.get("empId");

			sendMailWithAttachment((int) id, date.getMonth(), date.getYear());
		}

		return "Monthly Salary Slip Automatically Send to email Successfully";
	}

	/*
	 * @Author Aniket
	 * 
	 * @Note-Front End user will send Employee ID and Month for which, they want to
	 * generate their Salary Slip
	 */
	public CommonResponse sendMailWithAttachment(long empId, Month month, int year) {

		List<Map<String, Object>> map = null;
		CommonResponse response = null;

		SchedularServiceImpl serviceImpl = new SchedularServiceImpl();

		String url = "http://localhost:8095/getAttendanceByEmpIdAndMonth?empId=" + empId + "&month=" + month + "&year="
				+ year;
		response = restTemplate.getForObject(url, CommonResponse.class);
		
		if(response==null)
		{
			return response;
		}
		ByteArrayResource byteData = pdfGenerator.generatePdfFile(month, response);

		map = mapper.convertValue(response.getData(), List.class);

		Object emp = map.get(0).get("employeeDetails");

		EmployeeDetails employeeDetails = mapper.convertValue(emp, EmployeeDetails.class);

		serviceImpl.email(byteData, employeeDetails);

		response.setStatusCode(200);
		response.setMessage("Email For Salary Slip Send Successfully");
		response.setData(employeeDetails);
		return response;
	}

	public void email(ByteArrayResource bytes, EmployeeDetails emp) {

		String emailId = emp.getEmailId();

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("appemail78@gmail.com");
		mailSender.setPassword("tsknpquxfnxxnzlv");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("appemail78@gmail.com");
			helper.setTo(emailId);
			helper.setText(emp.getFirstName() + " Salary Slip.pdf");
			helper.setSubject("Salary Slip Info");
			helper.addAttachment("Salary_Slip.pdf", bytes);
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
