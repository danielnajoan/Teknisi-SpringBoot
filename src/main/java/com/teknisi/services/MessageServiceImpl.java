package com.teknisi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{
	
	@Autowired private JavaMailSender javaMailSender;
	
	@Value("${template.mail.subject:Hello }")
	private String subject;

	@Value("${template.mail.message:Testing Spring Boot Application}")
	private String testMessage;
	

    @Override
    public void sendEmail(String email, String username) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(subject+username);
        msg.setText(testMessage);
        javaMailSender.send(msg);
    }
    
    @Override
    public void sendEmailTicketRequest(String email, String name,String subject, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(name+subject);
        msg.setText(message);
        javaMailSender.send(msg);
    }
}