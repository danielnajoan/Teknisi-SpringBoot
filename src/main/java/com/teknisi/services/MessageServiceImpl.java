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
	
	@Value(", You have new ticket request!")
	private String emailTicketSubject;
	
	@Value("You have new request\nRequest ID : ${request.request_id}\nMerchant : %s\nAddress : %s\nCity : %s\nPerson in charge : %s\n\nThank you")
	private String emailTicketMessage;
	
    @Override
    public void sendEmail(String email, String username) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(subject+username);
        msg.setText(testMessage);
        javaMailSender.send(msg);
    }
    
    @Override
    public void sendEmailTicketRequest(String email, String name, String request_id, String merchant_name, String address, String city, String pic) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(name+emailTicketSubject);
        msg.setText(emailTicketMessage);
        javaMailSender.send(msg);
    }
}