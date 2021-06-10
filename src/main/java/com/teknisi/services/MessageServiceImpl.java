package com.teknisi.services;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{
	
	@Autowired private JavaMailSender javaMailSender;
	@Autowired private FileService fileService;
	
	@Value("${template.mail.subject:Hello }")
	private String templateSubject;

	@Value("${template.mail.message:Testing Spring Boot Application}")
	private String templateMessage;
	

    @Override
    public void sendEmail(String email, String username) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(templateSubject+username);
        msg.setText(templateMessage);
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
    
    public void sendEmailTicketRequestWithAttachment(String email, String name, String subject, String message) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(email);
        helper.setSubject(name+subject);
        helper.setText(message);
        // hard coded a file path
        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));
        FileSystemResource file = new FileSystemResource(fileService.getLastModified());
        helper.addAttachment(file.getFilename(), file);
        javaMailSender.send(msg);

    }
}