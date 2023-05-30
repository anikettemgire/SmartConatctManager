package com.smart.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	public boolean sendMail(String to,String subject,String message) {
		boolean f=false;
		try {
			
			MimeMessage m=javaMailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(m);
			 helper.setFrom("smartcontact12@gmail.com");
             helper.setTo(to);
             helper.setSubject(subject);
             helper.setText(message);
             
             javaMailSender.send(m);     
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
		}
		return true;
	}

}
