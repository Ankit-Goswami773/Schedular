package com.schedularproject.service;

import java.io.FileNotFoundException;
import java.time.Month;
import javax.mail.MessagingException;

import com.itextpdf.text.DocumentException;
import com.schedularproject.common.CommonResponse;

public interface SchedularService {
	
	public CommonResponse sendMailWithAttachment(long empId , Month month,int year) throws MessagingException, FileNotFoundException, DocumentException;
	
	public String autoGeneratePdfScheduler() throws FileNotFoundException, MessagingException, DocumentException ;
}
