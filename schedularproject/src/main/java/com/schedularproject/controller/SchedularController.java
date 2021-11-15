package com.schedularproject.controller;

import java.io.FileNotFoundException;
import java.time.Month;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.schedularproject.common.CommonResponse;
import com.schedularproject.service.SchedularService;

@RestController
public class SchedularController {

	@Autowired
	private SchedularService schedularService;

	
	/*
	 * @Author Aniket
	 * @Note-Here Admin pass the empID and Month for Which, they want to generate and send the salary slip 
	 * */
	@PostMapping("/sendSalarySlipPdf")
	public CommonResponse sendSalarySlipMail( @RequestParam(value = "empId") long empId , @RequestParam(value = "month") Month month , @RequestParam(value = "year") int year)
			throws FileNotFoundException, MessagingException, DocumentException {

		return schedularService.sendMailWithAttachment(empId,month,year);

	}
	
	

}
